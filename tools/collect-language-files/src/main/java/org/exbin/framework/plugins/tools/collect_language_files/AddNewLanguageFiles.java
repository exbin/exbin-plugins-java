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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tool to collect language files.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class AddNewLanguageFiles {

    private static final String PLUGIN_CODE = "undef";
    private static final String LANGUAGE_CODE = PLUGIN_CODE;
    private static final String SOURCE_DIR = "../../plugins/exbin-framework-language-en_US/src/main/resources";
    private static final String TARGET_DIR = "../../plugins/exbin-framework-language-" + PLUGIN_CODE + "/src/main/resources";

    public static void main(String[] args) {
        File projectDir = new File(SOURCE_DIR);
        processModuleResources(projectDir, "");
    }

    private static void processModuleResources(File module, String prefix) {
        File moduleResources = new File(module, prefix);
        for (File child : moduleResources.listFiles()) {
            if (child.isDirectory()) {
                processModuleResources(module, prefix + "/" + child.getName());
            } else if (child.isFile() && child.getName().endsWith(".properties")) {
                File targetDir = new File(TARGET_DIR + prefix);
//                new File(targetDir, child.getName()).delete();
                targetDir.mkdirs();
                try {
                    String fileName = child.getName();
                    String targetFileName = fileName.substring(0, fileName.length() - 11) + "_" + LANGUAGE_CODE + ".properties";
                    // TODO remove various lines
                    Files.copy(child.toPath(), new File(targetDir, targetFileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    Logger.getLogger(AddNewLanguageFiles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
