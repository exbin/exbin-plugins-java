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
import com.formdev.flatlaf.FlatLightLaf;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JComponent;
import javax.swing.UIManager;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.plugin.flatlaf_laf.gui.LafOptionsPanel;
import org.exbin.framework.ui.api.ConfigurableLafProvider;
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

    public FlatLafLafModule() {
    }

    @Override
    public void register() {
        UiModuleApi uiModule = App.getModule(UiModuleApi.class);
        uiModule.registerLafPlugin(new ConfigurableLafProvider() {
            @Override
            public String getLafId() {
                return FlatDarkLaf.class.getName();
            }

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

            @Override
            public JComponent getOptionsPanel() {
                return new LafOptionsPanel();
            }
        });

        uiModule.registerLafPlugin(new ConfigurableLafProvider() {
            @Override
            public String getLafId() {
                return FlatLightLaf.class.getName();
            }

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

            @Override
            public JComponent getOptionsPanel() {
                return new LafOptionsPanel();
            }
        });
    }

    public void applyLookAndFeel(String className) {
        String flatDarkClassName = FlatDarkLaf.class.getName();
        if (className.equals(flatDarkClassName)) {
            try {
                FlatDarkLaf.install();
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (Throwable ex) {
                System.err.println("Failed to initialize LaF");
            }
        } else {
            try {
                FlatLightLaf.install();
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Throwable ex) {
                System.err.println("Failed to initialize LaF");
            }
        }
    }

    public void unregisterModule(String moduleId) {
    }
}
