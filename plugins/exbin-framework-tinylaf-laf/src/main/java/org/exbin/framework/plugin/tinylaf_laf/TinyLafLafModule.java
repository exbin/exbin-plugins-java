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
package org.exbin.framework.plugin.tinylaf_laf;

import java.awt.Toolkit;
import java.io.File;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import net.sf.tinylaf.Theme;
import net.sf.tinylaf.ThemeDescription;
import net.sf.tinylaf.TinyLookAndFeel;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.plugin.tinylaf_laf.gui.LafOptionsPanel;
import org.exbin.framework.plugin.tinylaf_laf.options.impl.LafOptionsImpl;
import org.exbin.framework.plugin.tinylaf_laf.preferences.LafPreferences;
import org.exbin.framework.preferences.api.Preferences;
import org.exbin.framework.preferences.api.PreferencesModuleApi;
import org.exbin.framework.ui.api.ConfigurableLafProvider;
import org.exbin.framework.ui.api.LafOptionsHandler;
import org.exbin.framework.ui.api.UiModuleApi;

/**
 * TinyLaf look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class TinyLafLafModule implements PluginModule {

    public TinyLafLafModule() {
    }

    @Override
    public void register() {
        UiModuleApi uiModule = App.getModule(UiModuleApi.class);
        uiModule.registerLafPlugin(new ConfigurableLafProvider() {
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
            public LafOptionsHandler getOptionsHandler() {
                return new TinyLafLafOptionsHandler();
            }
        });
    }

    public void applyLookAndFeel(String className) {
        try {
            PreferencesModuleApi preferencesModule = App.getModule(PreferencesModuleApi.class);
            Preferences preferences = preferencesModule.getAppPreferences();
            LafPreferences lafPreferences = new LafPreferences(preferences);

            if (lafPreferences.isDynamicLayout()) {
                Toolkit.getDefaultToolkit().setDynamicLayout(true);
            }
            if (lafPreferences.isNoEraseOnResize()) {
                System.setProperty("sun.awt.noerasebackground", "true");
            }

            if (lafPreferences.isFramesDecoration()) {
                JFrame.setDefaultLookAndFeelDecorated(true);
            }
            if (lafPreferences.isDialogsDecoration()) {
                JDialog.setDefaultLookAndFeelDecorated(true);
            }

            TinyLookAndFeel laf = new TinyLookAndFeel();
            UIManager.setLookAndFeel(laf);

            if (lafPreferences.isUseBuildInTheme()) {
                String buildInTheme = lafPreferences.getBuildInTheme();
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
                String customThemeFile = lafPreferences.getCustomFileTheme();
                Theme.loadTheme(new File(customThemeFile));
                UIManager.setLookAndFeel(laf);
            }
        } catch (Throwable ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }
    }

    @ParametersAreNonnullByDefault
    private class TinyLafLafOptionsHandler implements LafOptionsHandler {

        private LafOptionsPanel lafOptionsPanel = new LafOptionsPanel();

        @Nonnull
        @Override
        public JComponent createOptionsComponent() {
            return lafOptionsPanel;
        }

        @Override
        public void loadFromPreferences(Preferences preferences) {
            LafOptionsImpl lafOptions = new LafOptionsImpl();
            LafPreferences lafPreferences = new LafPreferences(preferences);
            lafOptions.setUseBuildInTheme(lafPreferences.isUseBuildInTheme());
            lafOptions.setBuildInTheme(lafPreferences.getBuildInTheme());
            lafOptions.setCustomThemeFile(lafPreferences.getCustomFileTheme());
            lafOptions.setDialogsDecoration(lafPreferences.isDialogsDecoration());
            lafOptions.setFramesDecoration(lafPreferences.isFramesDecoration());
            lafOptions.setNoEraseOnResize(lafPreferences.isNoEraseOnResize());
            lafOptions.setDynamicLayout(lafPreferences.isDynamicLayout());
            lafOptionsPanel.loadFromOptions(lafOptions);
        }

        @Override
        public void saveToPreferences(Preferences preferences) {
            LafOptionsImpl lafOptions = new LafOptionsImpl();
            lafOptionsPanel.saveToOptions(lafOptions);
            LafPreferences lafPreferences = new LafPreferences(preferences);
            lafPreferences.setUseBuildInTheme(lafOptions.isUseBuildInTheme());
            lafPreferences.setBuildInTheme(lafOptions.getBuildInTheme());
            lafPreferences.setCustomThemeFile(lafOptions.getCustomFileTheme());
            lafPreferences.setDialogsDecoration(lafOptions.isDialogsDecoration());
            lafPreferences.setFramesDecoration(lafOptions.isFramesDecoration());
            lafPreferences.setNoEraseOnResize(lafOptions.isNoEraseOnResize());
            lafPreferences.setDynamicLayout(lafOptions.isDynamicLayout());
        }
    }
}
