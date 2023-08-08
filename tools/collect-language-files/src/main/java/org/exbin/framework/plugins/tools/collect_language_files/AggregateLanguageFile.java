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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tool to create single aggregated language file.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class AggregateLanguageFile {

    private static final String PROJECT_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-framework-java";
    private static final String FRAMEWORK_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-framework-java";
    private static final String TARGET_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-plugins-java/plugins/exbin-framework-language-cs_CZ/src/main/resources";
    
    private static final Map<String, Map<String, File>> propertyFiles = new HashMap<>();

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
        for (File child : moduleResources.listFiles()) {
            if (child.isDirectory()) {
                processModuleResources(module, prefix + "/" + child.getName());
            } else if (child.isFile() && child.getName().endsWith(".properties")) {
                File targetDir = new File(TARGET_DIR + prefix);
//                new File(targetDir, child.getName()).delete();
                targetDir.mkdirs();
                try {
                    String fileName = child.getName();
                    String targetFileName = fileName.substring(0, fileName.length() - 11) + "_cs_CZ.properties";
                    Files.copy(child.toPath(), new File(targetDir, targetFileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
