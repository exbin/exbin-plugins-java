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
package org.exbin.jaguif.plugin.tinylaf_laf;

import java.awt.Toolkit;
import java.io.File;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import net.sf.tinylaf.Theme;
import net.sf.tinylaf.ThemeDescription;
import net.sf.tinylaf.TinyLookAndFeel;
import org.exbin.jaguif.App;
import org.exbin.jaguif.PluginModule;
import org.exbin.jaguif.plugin.tinylaf_laf.gui.LafOptionsPanel;
import org.exbin.jaguif.plugin.tinylaf_laf.options.LafOptions;
import org.exbin.jaguif.options.api.OptionsStorage;
import org.exbin.jaguif.options.api.OptionsModuleApi;
import org.exbin.jaguif.options.settings.api.OptionsSettingsManagement;
import org.exbin.jaguif.options.settings.api.OptionsSettingsModuleApi;
import org.exbin.jaguif.options.settings.api.SettingsComponent;
import org.exbin.jaguif.options.settings.api.SettingsComponentProvider;
import org.exbin.jaguif.ui.theme.api.ConfigurableLafProvider;
import org.exbin.jaguif.ui.theme.api.UiThemeModuleApi;

/**
 * TinyLaf look and feel plugin.
 */
@ParametersAreNonnullByDefault
public class TinyLafLafModule implements PluginModule {

    public TinyLafLafModule() {
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
                return TinyLookAndFeel.class.getName();
            }

            @Nonnull
            @Override
            public String getLafName() {
                return "TinyLaf";
            }

            @Override
            public void installLaf() {
                UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(getLafName(), getLafId()));
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

            if (lafOptions.isDynamicLayout()) {
                Toolkit.getDefaultToolkit().setDynamicLayout(true);
            }
            if (lafOptions.isNoEraseOnResize()) {
                System.setProperty("sun.awt.noerasebackground", "true");
            }

            if (lafOptions.isFramesDecoration()) {
                JFrame.setDefaultLookAndFeelDecorated(true);
            }
            if (lafOptions.isDialogsDecoration()) {
                JDialog.setDefaultLookAndFeelDecorated(true);
            }

            TinyLookAndFeel laf = new TinyLookAndFeel();
            UIManager.setLookAndFeel(laf);

            if (lafOptions.isUseBuildInTheme()) {
                String buildInTheme = lafOptions.getBuildInTheme();
                if (buildInTheme != null && !buildInTheme.isEmpty()) {
                    ThemeDescription[] availableThemes = Theme.getAvailableThemes();
                    for (ThemeDescription theme : availableThemes) {
                        if (buildInTheme.equals(theme.getName())) {
                            Theme.loadTheme(theme);
                            UIManager.setLookAndFeel(laf);
                            break;
                        }
                    }
                }
            } else {
                String customThemeFile = lafOptions.getCustomFileTheme();
                Theme.loadTheme(new File(customThemeFile));
                UIManager.setLookAndFeel(laf);
            }
        } catch (Throwable ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }
    }
}
