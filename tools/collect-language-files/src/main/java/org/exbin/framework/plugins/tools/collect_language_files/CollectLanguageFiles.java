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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tool to collect language files.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class CollectLanguageFiles {

    private static final String PROJECT_DIR = "/home/hajdam/Software/Projekty/exbin/bined";
    private static final String FRAMEWORK_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-framework-java";
    private static final String TARGET_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-plugins-java/plugins/exbin-framework-language-en_US/src/main/resources";

    public static void main(String[] args) {
        File projectDir = new File(PROJECT_DIR + "/modules");
        File[] projectModules = projectDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File module : projectModules) {
            if (module.isDirectory()) {
                processModuleResources(module, "");
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
                processModuleResources(module, "");
            }
        }
    }

    private static void processModuleResources(File module, String prefix) {
        File moduleResources = new File(module, "src/main/resources" + prefix);
        for (File childFile : moduleResources.listFiles()) {
            if (childFile.isDirectory()) {
                processModuleResources(module, prefix + "/" + childFile.getName());
            } else if (childFile.isFile() && childFile.getName().endsWith(".properties")) {
                File targetDir = new File(TARGET_DIR + prefix);
//                new File(targetDir, child.getName()).delete();
                targetDir.mkdirs();
                String fileName = childFile.getName();
                String targetFileName = fileName.substring(0, fileName.length() - 11) + ".properties";
                File targetFile = new File(targetDir, targetFileName);

                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
                    
                    try (FileInputStream source = new FileInputStream(childFile)) {
                        InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                        try (BufferedReader reader = new BufferedReader(isr)) {
                            while (reader.ready()) {
                                String line = reader.readLine();
                                if (line.contains(".smallIcon=") || line.contains(".icon=") || line.contains(".accelerator=") || line.contains("options.path=") || line.contains("options.name=") || (!line.isBlank() && line.indexOf("=") == line.length() - 1)) {
                                    continue;
                                }
                                out.write(line + "\n");
                            }
                        }
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
//                try {
//                    Files.copy(child.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                } catch (IOException ex) {
//                    Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        }
    }
}
