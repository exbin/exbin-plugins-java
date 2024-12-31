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
package org.exbin.framework.plugin.flatlaf_laf;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JComponent;
import javax.swing.UIManager;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.plugin.flatlaf_laf.gui.LafOptionsPanel;
import org.exbin.framework.plugin.flatlaf_laf.options.impl.LafOptionsImpl;
import org.exbin.framework.plugin.flatlaf_laf.preferences.LafPreferences;
import org.exbin.framework.preferences.api.Preferences;
import org.exbin.framework.ui.api.ConfigurableLafProvider;
import org.exbin.framework.ui.api.LafOptionsHandler;
import org.exbin.framework.ui.api.UiModuleApi;

/**
 * FlatLaf look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class FlatLafLafModule implements PluginModule {

    public static final String FLATLAF_DARK = "FlatDark";
    public static final String FLATLAF_LIGHT = "FlatLight";
    public static final String FLATLAF_INTELLIJ = "FlatLafIntelliJ";

    public FlatLafLafModule() {
    }

    @Override
    public void register() {
        UiModuleApi uiModule = App.getModule(UiModuleApi.class);
        uiModule.registerLafPlugin(new ConfigurableLafProvider() {
            @Nonnull
            @Override
            public String getLafId() {
                return FlatDarkLaf.class.getName();
            }

            @Nonnull
            @Override
            public String getLafName() {
                return FLATLAF_DARK;
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
            public LafOptionsHandler getOptionsHandler() {
                return new FlatLafLafOptionsHandler();
            }
        });

        uiModule.registerLafPlugin(new ConfigurableLafProvider() {
            @Nonnull
            @Override
            public String getLafId() {
                return FlatLightLaf.class.getName();
            }

            @Nonnull
            @Override
            public String getLafName() {
                return FLATLAF_LIGHT;
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
            public LafOptionsHandler getOptionsHandler() {
                return new FlatLafLafOptionsHandler();
            }
        });
    }

    public void applyLookAndFeel(String className) {
        String flatDarkClassName = FlatDarkLaf.class.getName();
        String flatLightClassName = FlatLightLaf.class.getName();
        if (className.equals(flatDarkClassName)) {
            try {
                FlatDarkLaf.install();
                FlatDarkLaf flatDarkLaf = new FlatDarkLaf();
                UIManager.setLookAndFeel(flatDarkLaf);
            } catch (Throwable ex) {
                System.err.println("Failed to initialize LaF");
            }
        } else if (className.equals(flatLightClassName)) {
            try {
                FlatLightLaf.install();
                FlatLightLaf flatLightLaf = new FlatLightLaf();
                UIManager.setLookAndFeel(flatLightLaf);
            } catch (Throwable ex) {
                System.err.println("Failed to initialize LaF");
            }
        } else {
            try {
                FlatIntelliJLaf.install();
                FlatIntelliJLaf flatLaf = new FlatIntelliJLaf();
                UIManager.setLookAndFeel(flatLaf);
            } catch (Throwable ex) {
                System.err.println("Failed to initialize LaF");
            }
        }
    }

    @ParametersAreNonnullByDefault
    private class FlatLafLafOptionsHandler implements LafOptionsHandler {

        private LafOptionsImpl lafOptions = new LafOptionsImpl();

        @Nonnull
        @Override
        public JComponent createOptionsComponent() {
            LafOptionsPanel lafOptionsPanel = new LafOptionsPanel();
            lafOptionsPanel.loadFromOptions(lafOptions);
            return lafOptionsPanel;
        }

        @Override
        public void loadFromPreferences(Preferences preferences) {
            LafPreferences lafPreferences = new LafPreferences(preferences);
            lafOptions.setUnifiedWindowTitleBar(lafPreferences.isUnifiedWindowTitleBar());
        }

        @Override
        public void saveToPreferences(Preferences preferences) {
            LafPreferences lafPreferences = new LafPreferences(preferences);
            lafPreferences.setUnifiedWindowTitleBar(lafOptions.isUnifiedWindowTitleBar());
        }
    }
}
