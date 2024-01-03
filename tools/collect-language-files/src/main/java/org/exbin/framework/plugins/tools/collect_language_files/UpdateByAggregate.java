/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.framework.plugins.tools.collect_language_files;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Tool to update language files from single aggregated file.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class UpdateByAggregate {

    private static final String PLUGIN_CODE = "zh_TW";
    private static final String LANGUAGE_CODE = PLUGIN_CODE;
    private static final String PROJECT_DIR = "/home/hajdam/Software/Projekty/exbin/bined";
    private static final String FRAMEWORK_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-framework-java";
    private static final String TARGET_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-plugins-java/plugins/exbin-framework-language-" + PLUGIN_CODE + "/src/main/resources";
    private static boolean generateDiffFile = true;

    private static final Map<String, Map<String, Map<String, String>>> aggregateKeys = new HashMap<>();

    public static void main(String[] args) {
        File aggregateFile = new File(TARGET_DIR, "aggregate.properties");
        try (FileInputStream source = new FileInputStream(aggregateFile)) {
            int lineIndex = 0;
            InputStreamReader isr = new InputStreamReader(source, "UTF-8");
            try (BufferedReader reader = new BufferedReader(isr)) {
                while (reader.ready()) {
                    String line = reader.readLine();
                    int moduleNameSplit = line.indexOf(".");
                    if (moduleNameSplit < 1) {
                        throw new IllegalStateException("Missing module name on line " + lineIndex);
                    }
                    int keySplit = line.indexOf(".", moduleNameSplit + 1);
                    if (keySplit < 0) {
                        throw new IllegalStateException("Missing property file name on line " + lineIndex);
                    }
                    int valueSplit = line.indexOf("=", keySplit + 1);
                    if (valueSplit < 0) {
                        throw new IllegalStateException("Missing value on line " + lineIndex);
                    }

                    String moduleName = line.substring(0, moduleNameSplit);
                    String propertFileName = line.substring(moduleNameSplit + 1, keySplit);
                    String key = line.substring(keySplit + 1, valueSplit);
                    String value = line.substring(valueSplit + 1);

                    Map<String, Map<String, String>> aggregateModuleKeys = aggregateKeys.get(moduleName);
                    if (aggregateModuleKeys == null) {
                        aggregateModuleKeys = new HashMap<>();
                        aggregateKeys.put(moduleName, aggregateModuleKeys);
                    }
                    Map<String, String> aggregatePropertyFileKeys = aggregateModuleKeys.get(propertFileName);
                    if (aggregatePropertyFileKeys == null) {
                        aggregatePropertyFileKeys = new HashMap<>();
                        aggregateModuleKeys.put(propertFileName, aggregatePropertyFileKeys);
                    }
                    aggregatePropertyFileKeys.put(key.trim(), value.trim());

                    lineIndex++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(UpdateByAggregate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FileOutputStream diffFos = null;
        OutputStreamWriter diffOut = null;
        if (generateDiffFile) {
            try {
                diffFos = new FileOutputStream(new File(TARGET_DIR, "diff.properties"));
                diffOut = new OutputStreamWriter(diffFos, "UTF-8");
            } catch (IOException ex) {
                Logger.getLogger(UpdateByAggregate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        File appsDir = new File(PROJECT_DIR + "/apps");
        File[] appsModules = appsDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File module : appsModules) {
            if (module.isDirectory()) {
                String moduleName = module.getName();
                processModuleResources(module, moduleName, "", diffOut);
            }
        }

        File projectDir = new File(PROJECT_DIR + "/modules");
        File[] projectModules = projectDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File module : projectModules) {
            if (module.isDirectory()) {
                String moduleName = module.getName();
                if (moduleName.startsWith("exbin-framework-")) {
                    moduleName = moduleName.substring(16);
                }
                processModuleResources(module, moduleName, "", diffOut);
            }
        }

        File frameworkDir = new File(FRAMEWORK_DIR + "/modules");
        File[] frameworkModules = frameworkDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File module : frameworkModules) {
            if (module.isDirectory()) {
                String moduleName = module.getName();
                if (moduleName.startsWith("exbin-framework-")) {
                    moduleName = moduleName.substring(16);
                }
                processModuleResources(module, moduleName, "", diffOut);
            }
        }
        
        if (diffOut != null) {
            try {
                diffOut.close();
                if (diffFos != null) {
                    diffFos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(UpdateByAggregate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void processModuleResources(File module, String moduleName, String prefix, OutputStreamWriter diffOut) {
        File moduleResources = new File(module, "src/main/resources" + prefix);
        File[] listFiles = moduleResources.listFiles();
        if (listFiles == null) {
            return;
        }

        Map<String, Map<String, String>> aggregateModuleKeys = aggregateKeys.get(moduleName);
        if (aggregateModuleKeys == null && diffOut == null) {
            // No aggregate overrides
            return;
        }

        for (File childFile : listFiles) {
            if (childFile.isDirectory()) {
                processModuleResources(module, moduleName, prefix + "/" + childFile.getName(), diffOut);
            } else if (childFile.isFile() && childFile.getName().endsWith(".properties")) {
                File targetDir = new File(TARGET_DIR + prefix);
                String fileName = childFile.getName();
                String targetFileName = fileName.substring(0, fileName.length() - 11) + "_" + LANGUAGE_CODE + ".properties";
                File targetFile = new File(targetDir, targetFileName);

                // Copy file to memory
                byte[] fileContent;
                try {
                    fileContent = Files.readAllBytes(targetFile.toPath());
                } catch (IOException ex) {
                    throw new IllegalStateException("Error processing file " + targetFile.toPath(), ex);
                }

                String propertiesFileName = childFile.getName();
                if (propertiesFileName.endsWith(".properties")) {
                    propertiesFileName = propertiesFileName.substring(0, propertiesFileName.length() - 11);
                }
                Map<String, String> aggregatePropertyFileKeys = aggregateModuleKeys == null ? null : aggregateModuleKeys.get(propertiesFileName);
                if (aggregatePropertyFileKeys == null && diffOut == null) {
                    // No aggregate overrides
                    continue;
                }

                // Rewrite file with values from aggregate
                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    try (OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8")) {
                        try (ByteArrayInputStream source = new ByteArrayInputStream(fileContent)) {
                            InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                            try (BufferedReader reader = new BufferedReader(isr)) {
                                while (reader.ready()) {
                                    String line = reader.readLine();
                                    int valuePos = line.indexOf("=");
                                    if (valuePos > 0) {
                                        String key = line.substring(0, valuePos);

                                        String override = aggregatePropertyFileKeys == null ? null : aggregatePropertyFileKeys.get(key.trim());
                                        if (override != null) {
                                            out.write(key + "=" + StringEscapeUtils.escapeJava(override) + "\n");
                                        } else {
                                            if (diffOut != null) {
                                                diffOut.write(moduleName + "." + propertiesFileName + "." + line + "\n");
                                            }
                                            out.write(line + "\n");
                                        }
                                    } else {
                                        out.write(line + "\n");
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(UpdateByAggregate.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(UpdateByAggregate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(UpdateByAggregate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
