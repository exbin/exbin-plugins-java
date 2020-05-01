/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tool to collect language files
 *
 * @version 0.2.0 2017/01/08
 * @author ExBin Project (http://exbin.org)
 */
public class CollectLanguageFiles {

    private static final String PROJECT_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-framework-java";
    private static final String TARGET_DIR = "/home/hajdam/Software/Projekty/exbin/exbin-plugins-java/plugins/exbin-framework-language-en_US/src/main/resources";

    public static void main(String[] args) {
        File projectDir = new File(PROJECT_DIR + "/modules");
        File[] modules = projectDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File module : modules) {
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
                    Files.copy(child.toPath(), new File(targetDir, child.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    Logger.getLogger(CollectLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
