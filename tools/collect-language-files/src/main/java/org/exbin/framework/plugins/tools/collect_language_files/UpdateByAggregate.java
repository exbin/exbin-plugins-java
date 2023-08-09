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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tool to create single aggregated language file.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class UpdateByAggregate {

    private static final String languageCode = "undef";
    private static final String PROJECT_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-framework-java";
    private static final String FRAMEWORK_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-framework-java";
    private static final String TARGET_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-plugins-java/plugins/exbin-framework-language-" + languageCode + "/src/main/resources";
    
    private static final Map<String, Map<String, File>> propertyFiles = new HashMap<>();

    public static void main(String[] args) {
        File targetFile = new File(TARGET_DIR, "aggregate.properties");
        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
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
            Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
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
                String targetFileName = fileName.substring(0, fileName.length() - 11) + "_" + languageCode + ".properties";
                File targetFile = new File(targetDir, targetFileName);

                try (FileInputStream source = new FileInputStream(targetFile)) {
                    InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                    try (BufferedReader reader = new BufferedReader(isr)) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            if (line.isBlank()) {
                                continue;
                            }
                            String propertiesFileName = childFile.getName();
                            if (propertiesFileName.endsWith(".properties")) {
                                propertiesFileName = propertiesFileName.substring(0, propertiesFileName.length() - 11);
                            }
                            out.write(moduleName + "." + propertiesFileName + "." + line + "\n");
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(UpdateByAggregate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
