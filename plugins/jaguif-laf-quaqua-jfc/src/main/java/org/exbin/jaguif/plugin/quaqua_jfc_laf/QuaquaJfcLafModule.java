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
package org.exbin.jaguif.plugin.quaqua_jfc_laf;

import java.awt.Component;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import org.exbin.jaguif.App;
import org.exbin.jaguif.PluginModule;
import org.exbin.jaguif.file.api.FileModuleApi;
import org.exbin.jaguif.file.api.FileDialogsProvider;
import org.exbin.jaguif.file.api.FileType;
import org.exbin.jaguif.file.api.FileTypes;
import org.exbin.jaguif.file.api.OpenFileResult;
import org.exbin.jaguif.file.api.UsedDirectoryApi;
import org.exbin.jaguif.ui.theme.api.LafProvider;
import org.exbin.jaguif.ui.theme.api.UiThemeModuleApi;

/**
 * Quaqua file dialogs plugin.
 */
@ParametersAreNonnullByDefault
public class QuaquaJfcLafModule implements PluginModule {

    public static final String ALL_FILES_FILTER = "AllFilesFilter";
    public static final String LAF_NAME = "Quaqua JFC";
    public static final String PROVIDER_ID = "quaqua-jfc";

    public QuaquaJfcLafModule() {
    }

    @Override
    public void register() {
        FileModuleApi fileModule = App.getModule(FileModuleApi.class);
        fileModule.registerFileDialogsProvider(PROVIDER_ID, new FileDialogsProvider() {
            @Nonnull
            public String getProviderName() {
                return "Quaqua JFC";
            }

            @Nonnull
            @Override
            public OpenFileResult showOpenFileDialog(Component parentComponent, FileTypes fileTypes, @Nullable File selectedFile, @Nullable UsedDirectoryApi usedDirectory, @Nullable String dialogName) {
                LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
                applyLookAndFeel();
                JFileChooser openFileChooser = new JFileChooser();
                setupFileFilters(openFileChooser, fileTypes);
                if (usedDirectory != null) {
                    openFileChooser.setCurrentDirectory(usedDirectory.getLastUsedDirectory().orElse(null));
                }
                if (selectedFile != null) {
                    openFileChooser.setSelectedFile(selectedFile);
                }
                if (dialogName != null) {
                    openFileChooser.setDialogTitle(dialogName);
                }
                int dialogResult = openFileChooser.showOpenDialog(parentComponent);
                FileFilter fileFilter = openFileChooser.getFileFilter();
                try {
                    UIManager.setLookAndFeel(lookAndFeel);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(QuaquaJfcLafModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                return new OpenFileResult(
                        dialogResult, openFileChooser.getSelectedFile(),
                        fileFilter instanceof FileType ? (FileType) fileFilter : null
                );
            }

            @Override
            public OpenFileResult showSaveFileDialog(Component parentComponent, FileTypes fileTypes, @Nullable File selectedFile, @Nullable UsedDirectoryApi usedDirectory, @Nullable String dialogName) {
                LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
                applyLookAndFeel();
                JFileChooser saveFileChooser = new JFileChooser();
                saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                setupFileFilters(saveFileChooser, fileTypes);
                if (usedDirectory != null) {
                    saveFileChooser.setCurrentDirectory(usedDirectory.getLastUsedDirectory().orElse(null));
                }
                if (selectedFile != null) {
                    saveFileChooser.setSelectedFile(selectedFile);
                }
                if (dialogName != null) {
                    saveFileChooser.setDialogTitle(dialogName);
                }
                int dialogResult = saveFileChooser.showSaveDialog(parentComponent);
                FileFilter fileFilter = saveFileChooser.getFileFilter();
                try {
                    UIManager.setLookAndFeel(lookAndFeel);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(QuaquaJfcLafModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                return new OpenFileResult(
                        dialogResult, saveFileChooser.getSelectedFile(),
                        fileFilter instanceof FileType ? (FileType) fileFilter : null
                );
            }

            public void setupFileFilters(JFileChooser fileChooser, FileTypes fileTypes) {
                fileChooser.setAcceptAllFileFilterUsed(false);
                for (FileType fileType : fileTypes.getFileTypes()) {
                    fileChooser.addChoosableFileFilter((FileFilter) fileType);
                }

                if (fileTypes.allowAllFiles()) {
                    fileChooser.addChoosableFileFilter(new AllFilesFilter());
                }
            }
        });

        UiThemeModuleApi themeModule = App.getModule(UiThemeModuleApi.class);
        themeModule.registerLafPlugin(new LafProvider() {
            @Override
            public String getLafId() {
                return ch.randelshofer.quaqua.QuaquaLookAndFeel.class.getName();
            }

            @Override
            public void applyLaf() {
                applyLookAndFeel();
            }

            @Override
            public String getLafName() {
                return LAF_NAME;
            }

            @Override
            public void installLaf() {
                String className = ch.randelshofer.quaqua.QuaquaLookAndFeel.class.getName();
                UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(LAF_NAME, className));
            }
        });
    }

    public void applyLookAndFeel() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(QuaquaJfcLafModule.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(QuaquaJfcLafModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unregisterModule(String moduleId) {
    }

    @ParametersAreNonnullByDefault
    public static class AllFilesFilter extends FileFilter implements FileType {

        @Override
        public boolean accept(File file) {
            return true;
        }

        @Nonnull
        @Override
        public String getDescription() {
            return "All files (*)";
        }

        @Nonnull
        @Override
        public String getFileTypeId() {
            return ALL_FILES_FILTER;
        }
    }
}
