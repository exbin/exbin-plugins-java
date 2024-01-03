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
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Tool to create single aggregated language file.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class AggregateLanguageFile {

    private static final String PLUGIN_CODE = "zh_Hant";
    private static final String LANGUAGE_CODE = PLUGIN_CODE;
    private static final String PROJECT_DIR = "../../../bined";
    private static final String FRAMEWORK_DIR = "../../../exbin-framework-java";
    private static final String TARGET_DIR = "../../plugins/exbin-framework-language-" + PLUGIN_CODE + "/src/main/resources";
    private static final Set<String> subGroups = new HashSet<>(Arrays.asList("bined", "editor"));

    public static void main(String[] args) {
        aggregateByCollecting();
        // aggregateMatchingOnly();
    }

    private static void aggregateByCollecting() {
        File targetFile = new File(TARGET_DIR, "aggregate.properties");
        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            processModuleDir(TARGET_DIR + "/org/exbin/bined/editor", "bined-editor", out);

            File processedFolder = new File(TARGET_DIR, "org/exbin/framework");
            File[] listFiles = processedFolder.listFiles();
            if (listFiles == null) {
                return;
            }

            for (File childFile : listFiles) {
                if (subGroups.contains(childFile.getName())) {
                    continue;
                }

                if (childFile.isDirectory()) {
                    processModuleDir(TARGET_DIR + "/org/exbin/framework/" + childFile.getName(), childFile.getName(), out);
                }
            }
            
            for (String subGroup : subGroups) {
                processedFolder = new File(TARGET_DIR, "org/exbin/framework/" + subGroup);
                listFiles = processedFolder.listFiles();
                if (listFiles == null) {
                    return;
                }

                for (File childFile : listFiles) {
                    if ("resources".equals(childFile.getName()) || "options".equals(childFile.getName()) || "gui".equals(childFile.getName())) {
                        processModuleDir(TARGET_DIR + "/org/exbin/framework/" + subGroup + "/" + childFile.getName(), subGroup, out);
                    } else if (childFile.isDirectory()) {
                        processModuleDir(TARGET_DIR + "/org/exbin/framework/" + subGroup + "/" + childFile.getName(), subGroup + "-" + childFile.getName(), out);
                    }
                }
            }
            
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void processModuleDir(String directory, String module, OutputStreamWriter out) {
        // Temporary conversion
        /*
        if ("bined-blockedit".equals(module)) {
            module = "bined-operation";
        } else if ("bined-clipboard".equals(module)) {
            module = "bined-tool-content";
        } */

        File moduleDir = new File(directory);
        File[] listFiles = moduleDir.listFiles();
        if (listFiles == null) {
            return;
        }

        for (File childFile : listFiles) {
            if (childFile.isDirectory()) {
                if ("operation".equals(module) && "undo".equals(childFile.getName())) {
                    module = "operation-undo";
                } else if ("help".equals(module) && "online".equals(childFile.getName())) {
                    module = "help-online";
                }
                processModuleDir(directory + "/" + childFile.getName(), module, out);
            } else if (childFile.isFile() && childFile.getName().endsWith(LANGUAGE_CODE + ".properties")) {
                String fileName = childFile.getName();
                String propertiesFileName = fileName.substring(0, fileName.length() - 12 - LANGUAGE_CODE.length());

                try (FileInputStream source = new FileInputStream(childFile)) {
                    InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                    try (BufferedReader reader = new BufferedReader(isr)) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            if (line.isBlank()) {
                                continue;
                            }
                            String keyValue;
                            int valuePos = line.indexOf("=");
                            if (valuePos > 0) {
                                keyValue = line.substring(0, valuePos) + "=" + StringEscapeUtils.unescapeJava(line.substring(valuePos + 1));
                            } else {
                                keyValue = line;
                            }

                            out.write(module + "." + propertiesFileName + "." + keyValue + "\n");
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void aggregateMatchingOnly() {
        File targetFile = new File(TARGET_DIR, "aggregate.properties");
        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            File appDir = new File(PROJECT_DIR + "/apps");
            File[] appModules = appDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            });

            for (File module : appModules) {
                if (module.isDirectory()) {
                    String moduleName = module.getName();
                    processModuleResources(module, moduleName, "", out);
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
                    processModuleResources(module, moduleName, "", out);
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
                    processModuleResources(module, moduleName, "", out);
                }
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void processModuleResources(File module, String moduleName, String prefix, OutputStreamWriter out) {
        File moduleResources = new File(module, "src/main/resources" + prefix);
        File[] listFiles = moduleResources.listFiles();
        if (listFiles == null) {
            return;
        }

        for (File childFile : listFiles) {
            if (childFile.isDirectory()) {
                processModuleResources(module, moduleName, prefix + "/" + childFile.getName(), out);
            } else if (childFile.isFile() && childFile.getName().endsWith(".properties")) {
                File targetDir = new File(TARGET_DIR + prefix);
                String fileName = childFile.getName();
                String targetFileName = fileName.substring(0, fileName.length() - 11) + "_" + LANGUAGE_CODE + ".properties";
                File targetFile = new File(targetDir, targetFileName);

                try (FileInputStream source = new FileInputStream(targetFile)) {
                    InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                    try (BufferedReader reader = new BufferedReader(isr)) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            if (line.isBlank()) {
                                continue;
                            }
                            String keyValue;
                            int valuePos = line.indexOf("=");
                            if (valuePos > 0) {
                                keyValue = line.substring(0, valuePos) + "=" + StringEscapeUtils.unescapeJava(line.substring(valuePos + 1));
                            } else {
                                keyValue = line;
                            }

                            String propertiesFileName = childFile.getName();
                            if (propertiesFileName.endsWith(".properties")) {
                                propertiesFileName = propertiesFileName.substring(0, propertiesFileName.length() - 11);
                            }
                            out.write(moduleName + "." + propertiesFileName + "." + keyValue + "\n");
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
