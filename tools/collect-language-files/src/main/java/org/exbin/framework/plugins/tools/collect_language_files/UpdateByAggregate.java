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

    private static final String PLUGIN_CODE = "cs_CZ";
    private static final String LANGUAGE_CODE = PLUGIN_CODE;
    private static final String PROJECT_DIR = "../../../bined";
    private static final String FRAMEWORK_DIR = "../../../exbin-framework-java";
    private static final String TARGET_DIR = "../../plugins/exbin-framework-language-" + PLUGIN_CODE + "/src/main/resources";
    private static boolean generateDiffFile = true;

    private static final Map<String, Map<String, Map<String, String>>> aggregateKeys = new HashMap<>();
    
    private static final Map<String, String> conversion = new HashMap<>();
    
    static {
/*        conversion.put("bined-search.BinedSearchModule.editFindAction.text", "bined-search.BinedSearchModule.binarySearchFindAction.text");
        conversion.put("bined-search.BinedSearchModule.editFindAction.shortDescription", "bined-search.BinedSearchModule.binarySearchFindAction.shortDescription");
        conversion.put("bined-search.BinedSearchModule.editFindAgainAction.text", "bined-search.BinedSearchModule.binarySearchFindAgainAction.text");
        conversion.put("bined-search.BinedSearchModule.editFindAgainAction.shortDescription", "bined-search.BinedSearchModule.binarySearchFindAgainAction.shortDescription");
        conversion.put("bined-search.BinedSearchModule.editReplaceAction.text", "bined-search.BinedSearchModule.binarySearchReplaceAction.text");
        conversion.put("bined-search.BinedSearchModule.editReplaceAction.shortDescription", "bined-search.BinedSearchModule.binarySearchReplaceAction.shortDescription");
*/
        
        conversion.put("bined-search.BinedSearchModule.editFindAction.text", "search.SearchModule.editFindAction.text");
        conversion.put("bined-search.BinedSearchModule.editFindAction.shortDescription", "search.SearchModule.editFindAction.shortDescription");
        conversion.put("bined-search.BinedSearchModule.editFindAgainAction.text", "search.SearchModule.editFindAgainAction.text");
        conversion.put("bined-search.BinedSearchModule.editFindAgainAction.shortDescription", "search.SearchModule.editFindAgainAction.shortDescription");
        conversion.put("bined-search.BinedSearchModule.editReplaceAction.text", "search.SearchModule.editReplaceAction.text");
        conversion.put("bined-search.BinedSearchModule.editReplaceAction.shortDescription", "search.SearchModule.editReplaceAction.shortDescription");
        

        conversion.put("bined-editor.BinedEditor.Application.name", "bined-launcher.BinedLauncherModule.Application.name");
        conversion.put("bined-editor.BinedEditor.Application.title", "bined-launcher.BinedLauncherModule.Application.title");
        conversion.put("bined-editor.BinedEditor.Application.description", "bined-launcher.BinedLauncherModule.Application.description");
        conversion.put("bined-editor.BinedEditor.Application.license", "bined-launcher.BinedLauncherModule.Application.license");
        conversion.put("bined-editor.BinedEditor.cl_syntax", "bined-launcher.BinedLauncherModule.cl_syntax");
        conversion.put("bined-editor.BinedEditor.cl_option_help", "bined-launcher.BinedLauncherModule.cl_option_help");
        conversion.put("bined-editor.BinedEditor.cl_option_verbose", "bined-launcher.BinedLauncherModule.cl_option_verbose");
        conversion.put("bined-editor.BinedEditor.cl_option_dev", "bined-launcher.BinedLauncherModule.cl_option_dev");
        conversion.put("bined-editor.BinedEditor.cl_option_fullscreen", "bined-launcher.BinedLauncherModule.cl_option_fullscreen");
        conversion.put("bined-editor.BinedEditor.cl_option_single_file", "bined-launcher.BinedLauncherModule.cl_option_single_file");
        conversion.put("bined-editor.BinedEditor.cl_option_multi_file", "bined-launcher.BinedLauncherModule.cl_option_multi_file");

        conversion.put("popup.DefaultPopupMenu.popupCutAction.text", "action-popup.DefaultPopupMenu.popupCutAction.text");
        conversion.put("popup.DefaultPopupMenu.popupCutAction.shortDescription", "action-popup.DefaultPopupMenu.popupCutAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupCopyAction.text", "action-popup.DefaultPopupMenu.popupCopyAction.text");
        conversion.put("popup.DefaultPopupMenu.popupCopyAction.shortDescription", "action-popup.DefaultPopupMenu.popupCopyAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupPasteAction.text", "action-popup.DefaultPopupMenu.popupPasteAction.text");
        conversion.put("popup.DefaultPopupMenu.popupPasteAction.shortDescription", "action-popup.DefaultPopupMenu.popupPasteAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupDeleteAction.text", "action-popup.DefaultPopupMenu.popupDeleteAction.text");
        conversion.put("popup.DefaultPopupMenu.popupDeleteAction.shortDescription", "action-popup.DefaultPopupMenu.popupDeleteAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupSelectAllAction.text", "action-popup.DefaultPopupMenu.popupSelectAllAction.text");
        conversion.put("popup.DefaultPopupMenu.popupSelectAllAction.shortDescription", "action-popup.DefaultPopupMenu.popupSelectAllAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupCopyTextAction.text", "action-popup.DefaultPopupMenu.popupCopyTextAction.text");
        conversion.put("popup.DefaultPopupMenu.popupCopyTextAction.shortDescription", "action-popup.DefaultPopupMenu.popupCopyTextAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupCopyLinkAction.text", "action-popup.DefaultPopupMenu.popupCopyLinkAction.text");
        conversion.put("popup.DefaultPopupMenu.popupCopyLinkAction.shortDescription", "action-popup.DefaultPopupMenu.popupCopyLinkAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupOpenLinkAction.text", "action-popup.DefaultPopupMenu.popupOpenLinkAction.text");
        conversion.put("popup.DefaultPopupMenu.popupOpenLinkAction.shortDescription", "action-popup.DefaultPopupMenu.popupOpenLinkAction.shortDescription");
        conversion.put("popup.DefaultPopupMenu.popupCopyImageAction.text", "action-popup.DefaultPopupMenu.popupCopyImageAction.text");
        conversion.put("popup.DefaultPopupMenu.popupCopyImageAction.shortDescription", "action-popup.DefaultPopupMenu.popupCopyImageAction.shortDescription");

        conversion.put("utils.DefaultControlPanel.okButton.text", "window-api.DefaultControlPanel.okButton.text");
        conversion.put("utils.DefaultControlPanel.cancelButton.text", "window-api.DefaultControlPanel.cancelButton.text");
        conversion.put("utils.CloseControlPanel.closeButton.text", "window-api.CloseControlPanel.closeButton.text");
        conversion.put("utils.MultiStepControlPanel.finishButton.text", "window-api.MultiStepControlPanel.finishButton.text");
        conversion.put("utils.MultiStepControlPanel.cancelButton.text", "window-api.MultiStepControlPanel.cancelButton.text");
        conversion.put("utils.MultiStepControlPanel.nextButton.text", "window-api.MultiStepControlPanel.nextButton.text");
        conversion.put("utils.MultiStepControlPanel.previousButton.text", "window-api.MultiStepControlPanel.previousButton.text");
        conversion.put("utils.OptionsControlPanel.saveButton.text", "window-api.OptionsControlPanel.saveButton.text");
        conversion.put("utils.OptionsControlPanel.cancelButton.text", "window-api.OptionsControlPanel.cancelButton.text");
        conversion.put("utils.OptionsControlPanel.applyOnceButton.text", "window-api.OptionsControlPanel.applyOnceButton.text");
        conversion.put("utils.RemovalControlPanel.okButton.text", "window-api.RemovalControlPanel.okButton.text");
        conversion.put("utils.RemovalControlPanel.cancelButton.text", "window-api.RemovalControlPanel.cancelButton.text");
        conversion.put("utils.RemovalControlPanel.removeButton.text", "window-api.RemovalControlPanel.removeButton.text");

        conversion.put("options.MainOptionsManager.locale.defaultLanguage", "ui.MainOptionsManager.locale.defaultLanguage");
        conversion.put("options.MainOptionsManager.locale.englishFlag", "ui.MainOptionsManager.locale.englishFlag");
        conversion.put("options.MainOptionsManager.theme.defaultTheme", "ui.MainOptionsManager.theme.defaultTheme");
        conversion.put("options.MainOptionsManager.theme.crossPlatformTheme", "ui.MainOptionsManager.theme.crossPlatformTheme");
        conversion.put("options.MainOptionsManager.renderingMethod.default", "ui.MainOptionsManager.renderingMethod.default");
        conversion.put("options.MainOptionsManager.renderingMethod.directdraw", "ui.MainOptionsManager.renderingMethod.directdraw");
        conversion.put("options.MainOptionsManager.renderingMethod.hw_scale", "ui.MainOptionsManager.renderingMethod.hw_scale");
        conversion.put("options.MainOptionsManager.renderingMethod.software", "ui.MainOptionsManager.renderingMethod.software");
        conversion.put("options.MainOptionsManager.renderingMethod.software.windows", "ui.MainOptionsManager.renderingMethod.software.windows");
        conversion.put("options.MainOptionsManager.renderingMethod.opengl", "ui.MainOptionsManager.renderingMethod.opengl");
        conversion.put("options.MainOptionsManager.renderingMethod.xrender", "ui.MainOptionsManager.renderingMethod.xrender");
        conversion.put("options.MainOptionsManager.renderingMethod.metal", "ui.MainOptionsManager.renderingMethod.metal");
        conversion.put("options.MainOptionsManager.fontAntialiasing.default", "ui.MainOptionsManager.fontAntialiasing.default");
        conversion.put("options.MainOptionsManager.fontAntialiasing.off", "ui.MainOptionsManager.fontAntialiasing.off");
        conversion.put("options.MainOptionsManager.fontAntialiasing.gasp", "ui.MainOptionsManager.fontAntialiasing.gasp");
        conversion.put("options.MainOptionsManager.fontAntialiasing.lcd", "ui.MainOptionsManager.fontAntialiasing.lcd");
        conversion.put("options.MainOptionsManager.fontAntialiasing.lcd_hrgb", "ui.MainOptionsManager.fontAntialiasing.lcd_hrgb");
        conversion.put("options.MainOptionsManager.fontAntialiasing.lcd_hbgr", "ui.MainOptionsManager.fontAntialiasing.lcd_hbgr");
        conversion.put("options.MainOptionsManager.fontAntialiasing.lcd_vrgb", "ui.MainOptionsManager.fontAntialiasing.lcd_vrgb");
        conversion.put("options.MainOptionsManager.fontAntialiasing.lcd_vbgr", "ui.MainOptionsManager.fontAntialiasing.lcd_vbgr");
        conversion.put("options.MainOptionsManager.guiScaling.default", "ui.MainOptionsManager.guiScaling.default");
        conversion.put("options.MainOptionsManager.guiScaling.true", "ui.MainOptionsManager.guiScaling.true");
        conversion.put("options.MainOptionsManager.guiScaling.false", "ui.MainOptionsManager.guiScaling.false");
        conversion.put("options.MainOptionsManager.guiScaling.custom", "ui.MainOptionsManager.guiScaling.custom");
        conversion.put("options.MainOptionsManager.macOsAppearances.default", "ui.MainOptionsManager.macOsAppearances.default");
        conversion.put("options.MainOptionsManager.macOsAppearances.light", "ui.MainOptionsManager.macOsAppearances.light");
        conversion.put("options.MainOptionsManager.macOsAppearances.dark", "ui.MainOptionsManager.macOsAppearances.dark");
        conversion.put("options.MainOptionsManager.macOsAppearances.system", "ui.MainOptionsManager.macOsAppearances.system");
        conversion.put("options.AppearanceOptionsPanel.options.caption", "ui.AppearanceOptionsPanel.options.caption");
        conversion.put("options.AppearanceOptionsPanel.showToolbarCheckBox.text", "ui.AppearanceOptionsPanel.showToolbarCheckBox.text");
        conversion.put("options.AppearanceOptionsPanel.showCaptionsCheckBox.text", "ui.AppearanceOptionsPanel.showCaptionsCheckBox.text");
        conversion.put("options.AppearanceOptionsPanel.showStatusbarCheckBox.text", "ui.AppearanceOptionsPanel.showStatusbarCheckBox.text");
        conversion.put("options.MainOptionsPanel.options.caption", "ui.MainOptionsPanel.options.caption");
        conversion.put("options.MainOptionsPanel.visualThemeLabel.text", "ui.MainOptionsPanel.visualThemeLabel.text");
        conversion.put("options.MainOptionsPanel.languageLabel.text", "ui.MainOptionsPanel.languageLabel.text");
        conversion.put("options.MainOptionsPanel.requireRestartLabel.text", "ui.MainOptionsPanel.requireRestartLabel.text");
        conversion.put("options.MainOptionsPanel.renderingModeLabel.text", "ui.MainOptionsPanel.renderingModeLabel.text");
        conversion.put("options.MainOptionsPanel.fontAntialiasingLabel.text", "ui.MainOptionsPanel.fontAntialiasingLabel.text");
        conversion.put("options.MainOptionsPanel.guiScalingLabel.text", "ui.MainOptionsPanel.guiScalingLabel.text");
        conversion.put("options.MainOptionsPanel.macOsAppearanceLabel.text", "ui.MainOptionsPanel.macOsAppearanceLabel.text");
        conversion.put("options.MainOptionsPanel.useScreenMenuBarCheckBox.text", "ui.MainOptionsPanel.useScreenMenuBarCheckBox.text");

        conversion.put("update.ApplicationUpdateOptionsPanel.options.caption", "addon-update.ApplicationUpdateOptionsPanel.options.caption");
        conversion.put("update.ApplicationUpdateOptionsPanel.checkForUpdatesOnStartCheckBox.text", "addon-update.ApplicationUpdateOptionsPanel.checkForUpdatesOnStartCheckBox.text");
        conversion.put("update.CheckForUpdateAction.checkUpdateAction.text", "addon-update.CheckForUpdateAction.checkUpdateAction.text");
        conversion.put("update.CheckForUpdateAction.checkUpdateAction.shortDescription", "addon-update.CheckForUpdateAction.checkUpdateAction.shortDescription");
        conversion.put("update.CheckForUpdatePanel.dialog.title", "addon-update.CheckForUpdatePanel.dialog.title");
        conversion.put("update.CheckForUpdatePanel.header.title", "addon-update.CheckForUpdatePanel.header.title");
        conversion.put("update.CheckForUpdatePanel.header.description", "addon-update.CheckForUpdatePanel.header.description");
        conversion.put("update.CheckForUpdatePanel.closeButton.text", "addon-update.CheckForUpdatePanel.closeButton.text");
        conversion.put("update.CheckForUpdatePanel.copyLinkMenuItem.text", "addon-update.CheckForUpdatePanel.copyLinkMenuItem.text");
        conversion.put("update.CheckForUpdatePanel.availableVersionLabel.text", "addon-update.CheckForUpdatePanel.availableVersionLabel.text");
        conversion.put("update.CheckForUpdatePanel.currentVersionLabel.text", "addon-update.CheckForUpdatePanel.currentVersionLabel.text");
        conversion.put("update.CheckForUpdatePanel.unknown", "addon-update.CheckForUpdatePanel.unknown");
        conversion.put("update.CheckForUpdatePanel.status.checking.text", "addon-update.CheckForUpdatePanel.status.checking.text");
        conversion.put("update.CheckForUpdatePanel.status.updateUrlNotSet.text", "addon-update.CheckForUpdatePanel.status.updateUrlNotSet.text");
        conversion.put("update.CheckForUpdatePanel.status.noConnection.text", "addon-update.CheckForUpdatePanel.status.noConnection.text");
        conversion.put("update.CheckForUpdatePanel.status.connectionIssue.text", "addon-update.CheckForUpdatePanel.status.connectionIssue.text");
        conversion.put("update.CheckForUpdatePanel.status.notFound.text", "addon-update.CheckForUpdatePanel.status.notFound.text");
        conversion.put("update.CheckForUpdatePanel.status.noUpdateAvailable.text", "addon-update.CheckForUpdatePanel.status.noUpdateAvailable.text");
        conversion.put("update.CheckForUpdatePanel.status.updateFound.text", "addon-update.CheckForUpdatePanel.status.updateFound.text");
        conversion.put("update.CheckForUpdatePanel.downloadButton.text", "addon-update.CheckForUpdatePanel.downloadButton.text");
        conversion.put("update.CheckForUpdatePanel.recheckButton.text", "addon-update.CheckForUpdatePanel.recheckButton.text");
        conversion.put("update.CheckForUpdatePanel.statusTextLabel.text", "addon-update.CheckForUpdatePanel.statusTextLabel.text");

        conversion.put("options.OptionsTreePanel.dialog.title", "options.OptionsListPanel.dialog.title");
        conversion.put("options.OptionsTreePanel.cancelButton.text", "options.OptionsListPanel.cancelButton.text");
        conversion.put("options.OptionsTreePanel.applyOnceButton.text", "options.OptionsListPanel.applyOnceButton.text");
        conversion.put("options.OptionsTreePanel.saveButton.text", "options.OptionsListPanel.saveButton.text");
        conversion.put("options.OptionsTreePanel.options.root.caption", "options.OptionsListPanel.optionsAreaTitleLabel.text");
        conversion.put("options.OptionsTreePanel.optionsAreaTitleLabel.text", "options.OptionsListPanel.categoryLabel.text");

        conversion.put("operation-undo.OperationUndoModule.editUndoManagerAction.text", "operation-manager.OperationManagerModule.editUndoManagerAction.text");
        conversion.put("operation-undo.OperationUndoModule.editUndoManagerAction.shortDescription", "operation-manager.OperationManagerModule.editUndoManagerAction.shortDescription");
        conversion.put("operation-undo.UndoManagerControlPanel.revertButton.text", "operation-manager.UndoManagerControlPanel.revertButton.text");
        conversion.put("operation-undo.UndoManagerControlPanel.closeButton.text", "operation-manager.UndoManagerControlPanel.closeButton.text");
        conversion.put("operation-undo.UndoManagerPanel.dialog.title", "operation-manager.UndoManagerPanel.dialog.title");
        conversion.put("operation-undo.UndoManagerPanel.header.title", "operation-manager.UndoManagerPanel.header.title");
        conversion.put("operation-undo.UndoManagerPanel.header.description", "operation-manager.UndoManagerPanel.header.description");
        conversion.put("operation-undo.UndoManagerPanel.propertiesButton.text", "operation-manager.UndoManagerPanel.propertiesButton.text");
        conversion.put("operation-undo.UndoManagerPanel.exportButton.text", "operation-manager.UndoManagerPanel.exportButton.text");
        conversion.put("operation-undo.UndoManagerPanel.undoDetailInfoPanel.border.title", "operation-manager.UndoManagerPanel.undoDetailInfoPanel.border.title");
        conversion.put("operation-undo.UndoManagerPanel.commandCaptionLabel.text", "operation-manager.UndoManagerPanel.commandCaptionLabel.text");
        conversion.put("operation-undo.UndoManagerPanel.commandTypeLabel.text", "operation-manager.UndoManagerPanel.commandTypeLabel.text");
        conversion.put("operation-undo.UndoManagerPanel.operationCaptionLabel.text", "operation-manager.UndoManagerPanel.operationCaptionLabel.text");
        conversion.put("operation-undo.UndoManagerPanel.operationTypeLabel.text", "operation-manager.UndoManagerPanel.operationTypeLabel.text");
        conversion.put("operation-undo.UndoManagerPanel.executionTimeLabel.text", "operation-manager.UndoManagerPanel.executionTimeLabel.text");
        conversion.put("operation-undo.UndoManagerPanel.dataSizeLabel.text", "operation-manager.UndoManagerPanel.dataSizeLabel.text");

        conversion.put("bined.BinaryPropertiesPanel.header.title", "bined.BinEdFilePropertiesPanel.header.title");
        conversion.put("bined.BinaryPropertiesPanel.header.description", "bined.BinEdFilePropertiesPanel.header.description");
        conversion.put("bined.BinaryPropertiesPanel.closeButton.text", "bined.BinEdFilePropertiesPanel.closeButton.text");
        conversion.put("bined.BinaryPropertiesPanel.fileNameLabel.text", "bined.BinEdFilePropertiesPanel.fileNameLabel.text");
        conversion.put("bined.BinaryPropertiesPanel.documentSizePanel.border.title", "bined.BinEdFilePropertiesPanel.documentSizePanel.border.title");
        conversion.put("bined.BinaryPropertiesPanel.linesCountLabel.text", "bined.BinEdFilePropertiesPanel.linesCountLabel.text");
        conversion.put("bined.BinaryPropertiesPanel.charCountLabel.text", "bined.BinEdFilePropertiesPanel.charCountLabel.text");
        conversion.put("bined.BinaryPropertiesPanel.fileSizeLabel.text", "bined.BinEdFilePropertiesPanel.fileSizeLabel.text");
        conversion.put("bined.BinaryPropertiesPanel.wordsCountLabel.text", "bined.BinEdFilePropertiesPanel.wordsCountLabel.text");
        conversion.put("bined.BinaryPropertiesPanel.structurePanel.border.title", "bined.BinEdFilePropertiesPanel.structurePanel.border.title");
    }

    public static void main(String[] args) {
        File aggregateFile = new File(TARGET_DIR, "aggregate.properties");
        try (FileInputStream source = new FileInputStream(aggregateFile)) {
            int lineIndex = 0;
            InputStreamReader isr = new InputStreamReader(source, "UTF-8");
            try (BufferedReader reader = new BufferedReader(isr)) {
                while (reader.ready()) {
                    String line = reader.readLine();
                    if (!conversion.isEmpty()) {
                        int rowValueSplit = line.indexOf("=");
                        if (rowValueSplit > 0) {
                            String fullKey = line.substring(0, rowValueSplit).trim();
                            String match = conversion.get(fullKey);
                            if (match != null) {
                                line = match + line.substring(rowValueSplit);
                            }
                        }
                    }
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
