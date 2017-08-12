/*
 * Copyright (C) ExBin Project
 *
 * This application or library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This application or library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along this application.  If not, see <http://www.gnu.org/licenses/>.
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
