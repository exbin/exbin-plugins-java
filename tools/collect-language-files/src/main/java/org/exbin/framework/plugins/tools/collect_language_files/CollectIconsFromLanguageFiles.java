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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tool to collect language files. Fixed target to en_US.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class CollectIconsFromLanguageFiles {

    private static final String PROJECT_DIR = "../../../bined";
    private static final String FRAMEWORK_DIR = "../../../exbin-framework-java";
    private static final String TARGET_DIR = "../../plugins/exbin-framework-iconset/src/main/resources";
    private static final List<String> EXCEPTIONS = Arrays.asList(
            "Application.release", "Application.mode", "Application.version", "Application.homepage", "Application.vendorId", "Application.id", "Application.lookAndFeel", "Application.product", "Application.vendor",
            "Application.licenseFile", "Application.authors");

    public static void main(String[] args) {
        File targetFile = new File(TARGET_DIR, "iconset.properties");
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
                    processModuleResources(module, "", out);
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
                    processModuleResources(module, "", out);
                }
            }

            projectDir = new File(PROJECT_DIR + "/plugins");
            projectModules = projectDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            });

            for (File module : projectModules) {
                if (module.isDirectory()) {
                    processModuleResources(module, "", out);
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
                    processModuleResources(module, "", out);
                }
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(CollectIconsFromLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void processModuleResources(File module, String prefix, OutputStreamWriter out) {
        File moduleResources = new File(module, "src/main/resources" + prefix);
        if (!moduleResources.isDirectory()) {
            return;
        }

        for (File childFile : moduleResources.listFiles()) {
            if (childFile.isDirectory()) {
                processModuleResources(module, prefix + "/" + childFile.getName(), out);
            } else if (childFile.isFile() && childFile.getName().endsWith(".properties")) {
                String pathPrefix = prefix.substring(1).replace("/", ".") + "." + childFile.getName().substring(0, childFile.getName().length() - 11) + ".";
                try (FileInputStream source = new FileInputStream(childFile)) {
                    InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                    try (BufferedReader reader = new BufferedReader(isr)) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            int valuePos = line.indexOf("=");
                            if (valuePos > 0) {
                                String key = line.substring(0, valuePos).trim();
                                if (EXCEPTIONS.contains(key)) {
                                    continue;
                                }
                            }

                            if (line.contains(".smallIcon=") || line.contains(".icon=")) {
                                out.write(pathPrefix + line + "\n");
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CollectIconsFromLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
//                try {
//                    Files.copy(child.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                } catch (IOException ex) {
//                    Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                try {
//                    Files.copy(child.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                } catch (IOException ex) {
//                    Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        }
    }
}
