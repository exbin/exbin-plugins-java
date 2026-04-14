/*
 * Copyright (C) ExBin Project, https://exbin.org
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
package org.exbin.jaguif.plugins.tools.collect_language_files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Tool to create single aggregated language file.
 */
public class AggregateLanguageFile {

    private static final String LANGUAGE_CODE = "undef";
    private static final String PLUGIN_CODE = LANGUAGE_CODE;
//    private static final String PLUGIN_CODE = "undef~";
//    private static final String PLUGIN_CODE = "en_US";
//    private static final String LANGUAGE_CODE = "";
    private static final String PROJECT_DIR = "../../../bined";
    private static final String FRAMEWORK_DIR = "../../../exbin-framework-java";
    private static final String TARGET_DIR = "../../plugins/exbin-framework-language-" + PLUGIN_CODE + "/src/main/resources";

    // private static final Set<String> subGroups = new HashSet<>(java.util.Arrays.asList("bined", "editor", "addon", "action", "window", "operation"));
    private static final Set<String> subGroups = new HashSet<>(java.util.Arrays.asList("bined", "editor"));

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
                    processModuleDir(TARGET_DIR + "/org/exbin/jaguif/" + childFile.getName(), childFile.getName(), out);
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
                        processModuleDir(TARGET_DIR + "/org/exbin/jaguif/" + subGroup + "/" + childFile.getName(), subGroup, out);
                    } else if (childFile.isDirectory()) {
                        processModuleDir(TARGET_DIR + "/org/exbin/jaguif/" + subGroup + "/" + childFile.getName(), subGroup + "-" + childFile.getName(), out);
                    }
                }
            }

            out.close();
        } catch (IOException ex) {
            Logger.getLogger(AggregateLanguageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void processModuleDir(String directory, String moduleName, OutputStreamWriter out) {
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
                String childModule = moduleName;
                // Additional groups
                // System.out.println(moduleName + " : " + childFile.getName());
                if ("help".equals(moduleName) && "online".equals(childFile.getName())) {
                    childModule = "help-online";
                } else if ("help".equals(moduleName) && "local".equals(childFile.getName())) {
                    childModule = "help-local";
                } else if ("menu".equals(moduleName) && "popup".equals(childFile.getName())) {
                    childModule = "menu-popup";
                } else if ("document".equals(moduleName) && "text".equals(childFile.getName())) {
                    childModule = "document-text";
                } else if ("document".equals(moduleName) && "recent".equals(childFile.getName())) {
                    childModule = "document-recent";
                } else if ("document".equals(moduleName) && "syntax".equals(childFile.getName())) {
                    childModule = "document-syntax";
                } else if ("docking".equals(moduleName) && "multi".equals(childFile.getName())) {
                    childModule = "docking-multi";
                } else if ("ui".equals(moduleName) && "theme".equals(childFile.getName())) {
                    childModule = "ui-theme";
                } else if ("options".equals(moduleName) && "settings".equals(childFile.getName())) {
                    childModule = "options-settings";
                } else if ("text".equals(moduleName) && "encoding".equals(childFile.getName())) {
                    childModule = "text-encoding";
                } else if ("text".equals(moduleName) && "font".equals(childFile.getName())) {
                    childModule = "text-font";
                } else if ("action".equals(moduleName) && "manager".equals(childFile.getName())) {
                    childModule = "action-manager";
                } else if ("addon".equals(moduleName) && "manager".equals(childFile.getName())) {
                    childModule = "addon-manager";
                } else if ("addon".equals(moduleName) && "catalog".equals(childFile.getName())) {
                    childModule = "addon-catalog";
                } else if ("operation".equals(moduleName) && "manager".equals(childFile.getName())) {
                    childModule = "operation-manager";
                } else if ("operation".equals(moduleName) && "undo".equals(childFile.getName())) {
                    childModule = "operation-undo";
                } else if ("window".equals(moduleName) && "api".equals(childFile.getName())) {
                    childModule = "window-api";
                } else if ("addon".equals(moduleName) && "update".equals(childFile.getName())) {
                    childModule = "addon-update";
                } else if ("addon".equals(moduleName) && "packs".equals(childFile.getName())) {
                    childModule = "addon-packs";
                } else if ("bined-operation".equals(moduleName) && "bouncycastle".equals(childFile.getName())) {
                    childModule = "bined-operation-bouncycastle";
                } else if ("bined-operation".equals(moduleName) && "code".equals(childFile.getName())) {
                    childModule = "bined-operation-code";
                } else if ("bined-inspector".equals(moduleName) && "pixelmap".equals(childFile.getName())) {
                    childModule = "bined-inspector-pixelmap";
                } else if ("bined-inspector".equals(moduleName) && "table".equals(childFile.getName())) {
                    childModule = "bined-inspector-table";
                } else if ("bined-tool".equals(moduleName) && "content".equals(childFile.getName())) {
                    childModule = "bined-tool-content";
                }
                processModuleDir(directory + "/" + childFile.getName(), childModule, out);
            } else if (childFile.isFile() && childFile.getName().endsWith(LANGUAGE_CODE + ".properties")) {
                String fileName = childFile.getName();
                String propertiesFileName = fileName.substring(0, fileName.length() - (LANGUAGE_CODE.isEmpty() ? 11 : 12 + LANGUAGE_CODE.length()));

                try (FileInputStream source = new FileInputStream(childFile)) {
                    InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                    try (BufferedReader reader = new BufferedReader(isr)) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            if (line.isEmpty()) {
                                continue;
                            }
                            String keyValue;
                            int valuePos = line.indexOf("=");
                            if (valuePos > 0) {
                                keyValue = line.substring(0, valuePos) + "=" + StringEscapeUtils.unescapeJava(line.substring(valuePos + 1).replace("\\n", "\\\\n"));
                            } else {
                                keyValue = line;
                            }

                            String outputLine = moduleName + "." + propertiesFileName + "." + keyValue;
                            // Temporary conversion 0.2.4 -> 0.2.5
                            if (outputLine.startsWith("action.ActionModule.popup")) {
                                outputLine = "action.ActionModule." + Character.toLowerCase(outputLine.charAt(25)) + outputLine.substring(26);
                            } else if (outputLine.startsWith("action-popup.DefaultPopupMenu.")) {
                                outputLine = "menu-popup.DefaultPopupMenu." + outputLine.substring(30);
                            } else if (outputLine.startsWith("ui.MainOptionsManager.")) {
                                outputLine = "ui-theme.UiThemeModule." + outputLine.substring(22);
                            } else if (outputLine.startsWith("editor-text.Text")) {
                                out.write("document-text.Text" + outputLine.substring(16) + "\n");
                                outputLine = "text-font.Text" + outputLine.substring(16);
                            } else if (outputLine.startsWith("bined.BinedModule.")) {
                                out.write(outputLine + "\n");
                                outputLine = "bined-viewer.BinedViewerModule." + outputLine.substring(18);
                            } else if (outputLine.startsWith("bined.ColorProfilePanel.")) {
                                outputLine = "bined-theme.ColorProfilePanel." + outputLine.substring(24);
                            } else if (outputLine.startsWith("bined.LayoutProfilePanel.")) {
                                outputLine = "bined-theme.LayoutProfilePanel." + outputLine.substring(25);
                            } else if (outputLine.startsWith("bined.ThemeProfilePanel.")) {
                                outputLine = "bined-theme.ThemeProfilePanel." + outputLine.substring(24);
                            } else if (outputLine.startsWith("editor.UnsavedFilesPanel.")) {
                                outputLine = "docking-multi.ModifiedDocumentsPanel." + outputLine.substring(25);
                            } else if (outputLine.startsWith("editor-text.EditorTextModule.")) {
                                outputLine = "document-text.DocumentTextModule." + outputLine.substring(29);
                            } else if (outputLine.startsWith("editor-text.FindTextPanel.")) {
                                outputLine = "document-text.FindTextPanel." + outputLine.substring(26);
                            } else if (outputLine.startsWith("bined.EditSelectionPanel.")) {
                                out.write("document-text.EditSelectionPanel." + outputLine.substring(25) + "\n");
                                outputLine = "bined-editor.EditSelectionPanel." + outputLine.substring(25);
                            } else if (outputLine.startsWith("bined.EditSelectionPanel.")) {
                                outputLine = "bined-editor.EditSelectionPanel." + outputLine.substring(25);
                            } else if (outputLine.startsWith("bined.CodeAreaOptionsPanel.")) {
                                outputLine = "bined-viewer.CodeAreaSettingsPanel." + outputLine.substring(27);
                            } else if (outputLine.startsWith("ui.MainOptionsPanel.")) {
                                outputLine = "ui-theme.ThemeSettingsPanel." + outputLine.substring(20);
                            } else if (outputLine.startsWith("file.FileModule.")) {
                                out.write(outputLine + "\n");
                                outputLine = "docking.DockingModule." + outputLine.substring(16);
                            }
                            out.write(outputLine + "\n");
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
        File targetFile = new File(TARGET_DIR, "aggregate-matching.properties");
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

            projectDir = new File(PROJECT_DIR + "/plugins");
            projectModules = projectDir.listFiles(new FileFilter() {
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
                if (!targetFile.isFile()) {
                    continue;
                }

                try (FileInputStream source = new FileInputStream(targetFile)) {
                    InputStreamReader isr = new InputStreamReader(source, "UTF-8");
                    try (BufferedReader reader = new BufferedReader(isr)) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            if (line.isEmpty()) {
                                continue;
                            }
                            String keyValue;
                            int valuePos = line.indexOf("=");
                            if (valuePos > 0) {
                                keyValue = line.substring(0, valuePos) + "=" + StringEscapeUtils.unescapeJava(line.substring(valuePos + 1).replace("\\n", "\\\\n"));
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
