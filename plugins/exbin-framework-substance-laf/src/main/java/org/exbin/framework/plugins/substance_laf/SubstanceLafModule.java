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
package org.exbin.framework.plugins.substance_laf;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.ui.api.LafProvider;
import org.exbin.framework.ui.api.UiModuleApi;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

/**
 * Substance look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class SubstanceLafModule implements PluginModule {

    public static final String SUBSTANCE_GRAPHITE = "Substance Graphite";
    public static final String SUBSTANCE_AUTUMN = "Substance Autumn";

    public SubstanceLafModule() {
    }

    @Override
    public void register() {
        UiModuleApi uiModule = App.getModule(UiModuleApi.class);
        uiModule.registerLafPlugin(new LafProvider() {
            @Override
            public String getLafId() {
                return SubstanceLookAndFeel.class.getName();
            }

            @Override
            public String getLafName() {
                return SUBSTANCE_GRAPHITE;
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
        });

        uiModule.registerLafPlugin(new LafProvider() {
            @Override
            public String getLafId() {
                return SubstanceAutumnLookAndFeel.class.getName();
            }

            @Override
            public String getLafName() {
                return SUBSTANCE_AUTUMN;
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
        });
    }

    public void applyLookAndFeel(String className) {
        String substanceGraphiteClassName = SubstanceGraphiteLookAndFeel.class.getName();
        if (className.equals(substanceGraphiteClassName)) {
            try {
                UIManager.setLookAndFeel(new SubstanceGraphiteLookAndFeel());
            } catch (Throwable ex) {
                ex.printStackTrace();
                System.err.println("Failed to initialize LaF");
            }
        } else {
            try {
                UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel());
            } catch (Throwable ex) {
                ex.printStackTrace();
                System.err.println("Failed to initialize LaF");
            }
        }
    }

    public void unregisterModule(String moduleId) {
    }
}
