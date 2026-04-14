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
package org.exbin.jaguif.plugin.flatlaf_laf;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatPropertiesLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import java.io.File;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.exbin.jaguif.App;
import org.exbin.jaguif.PluginModule;
import org.exbin.jaguif.plugin.flatlaf_laf.gui.LafOptionsPanel;
import org.exbin.jaguif.plugin.flatlaf_laf.options.LafOptions;
import org.exbin.jaguif.options.api.OptionsStorage;
import org.exbin.jaguif.options.api.OptionsModuleApi;
import org.exbin.jaguif.options.settings.api.OptionsSettingsManagement;
import org.exbin.jaguif.options.settings.api.OptionsSettingsModuleApi;
import org.exbin.jaguif.options.settings.api.SettingsComponent;
import org.exbin.jaguif.options.settings.api.SettingsComponentProvider;
import org.exbin.jaguif.ui.theme.api.ConfigurableLafProvider;
import org.exbin.jaguif.ui.theme.api.UiThemeModuleApi;

/**
 * FlatLaf look and feel plugin.
 */
@ParametersAreNonnullByDefault
public class FlatLafLafModule implements PluginModule {

    public static final String FLATLAF = "FlatLaf";
    public static final String FLATLAF_DARK = FlatDarkLaf.NAME;
    public static final String FLATLAF_LIGHT = FlatLightLaf.NAME;
    public static final String FLATLAF_INTELLIJ = FlatIntelliJLaf.NAME;
    public static final String FLATLAF_DARCULA = FlatDarculaLaf.NAME;
    public static final String FLATLAF_MAC_DARK = FlatMacDarkLaf.NAME;
    public static final String FLATLAF_MAC_LIGHT = FlatMacLightLaf.NAME;

    public FlatLafLafModule() {
    }

    @Override
    public void register() {
        OptionsSettingsModuleApi settingsModule = App.getModule(OptionsSettingsModuleApi.class);
        OptionsSettingsManagement settingsManagement = settingsModule.getMainSettingsManager();
        settingsManagement.registerSettingsOptions(LafOptions.class, (optionsStorage) -> new LafOptions(optionsStorage));

        UiThemeModuleApi themeModule = App.getModule(UiThemeModuleApi.class);
        themeModule.registerLafPlugin(new ConfigurableLafProvider() {
            @Nonnull
            @Override
            public String getLafId() {
                return FlatLaf.class.getName();
            }

            @Nonnull
            @Override
            public String getLafName() {
                return FLATLAF;
            }

            @Override
            public void installLaf() {
                UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(getLafName(), getLafId()));
                // registerLafPlugin(flatDarkClassName, this);
            }

            @Override
            public void applyLaf() {
                applyLookAndFeel(getLafId());
            }

            @Nonnull
            @Override
            public SettingsComponentProvider getSettingsComponentProvider() {
                return new SettingsComponentProvider() {
                    @Nonnull
                    @Override
                    public SettingsComponent createComponent() {
                        return new LafOptionsPanel();
                    }
                };
            }
        });
    }

    public void applyLookAndFeel(String className) {
        try {
            OptionsModuleApi optionsModule = App.getModule(OptionsModuleApi.class);
            OptionsStorage optionsStorage = optionsModule.getAppOptions();
            LafOptions lafOptions = new LafOptions(optionsStorage);

            if (lafOptions.isUseWindowDecorations()) {
                if (SystemInfo.isLinux) {
                    // enable custom window decorations
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                }
                System.setProperty("flatlaf.useWindowDecorations", "true");
            }
            if (lafOptions.isEmbeddedMenuBar()) {
                System.setProperty("flatlaf.menuBarEmbedded", "true");
            }

            if (lafOptions.isUseBuildInTheme()) {
                String buildInTheme = lafOptions.getBuildInTheme();
                if (buildInTheme != null && !buildInTheme.isEmpty()) {
                    if (FLATLAF_DARK.equals(buildInTheme)) {
                        UIManager.setLookAndFeel(new FlatDarkLaf());
                    } else if (FLATLAF_LIGHT.equals(buildInTheme)) {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                    } else if (FLATLAF_INTELLIJ.equals(buildInTheme)) {
                        UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    } else if (FLATLAF_DARCULA.equals(buildInTheme)) {
                        UIManager.setLookAndFeel(new FlatDarculaLaf());
                    } else if (FLATLAF_MAC_DARK.equals(buildInTheme)) {
                        UIManager.setLookAndFeel(new FlatMacDarkLaf());
                    } else if (FLATLAF_MAC_LIGHT.equals(buildInTheme)) {
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                    }
                } else {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                }
            } else {
                String customThemeFile = lafOptions.getCustomFileTheme();
                UIManager.setLookAndFeel(new FlatPropertiesLaf("Custom", new File(customThemeFile)));
            }
        } catch (Throwable ex) {
            System.err.println("Failed to initialize LaF");
        }
    }
}
