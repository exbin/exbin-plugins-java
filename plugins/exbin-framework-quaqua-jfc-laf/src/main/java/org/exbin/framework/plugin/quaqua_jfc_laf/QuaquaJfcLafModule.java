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
package org.exbin.framework.plugin.quaqua_jfc_laf;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.file.api.FileModuleApi;
import org.exbin.framework.file.api.FileDialogsProvider;
import org.exbin.framework.file.api.FileTypes;
import org.exbin.framework.file.api.OpenFileResult;
import org.exbin.framework.file.api.UsedDirectoryApi;
import org.exbin.framework.ui.theme.api.LafProvider;
import org.exbin.framework.ui.theme.api.UiThemeModuleApi;

/**
 * Quaqua file dialogs plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class QuaquaJfcLafModule implements PluginModule {
    
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
                return "Quaqua";
            }

            @Nonnull
            public OpenFileResult showOpenFileDialog(FileTypes fileTypes, @Nullable File selectedFile, @Nullable UsedDirectoryApi usedDirectory, @Nullable String dialogName) {
                // TODO
                return null;
            }

            @Nonnull
            public OpenFileResult showSaveFileDialog(FileTypes fileTypes, @Nullable File selectedFile, @Nullable UsedDirectoryApi usedDirectory, @Nullable String dialogName) {
                // TODO
                return null;
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
                String className = ch.randelshofer.quaqua.QuaquaLookAndFeel.class.getName();
                applyLookAndFeel(className);
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

    public void applyLookAndFeel(String className) {
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
}
