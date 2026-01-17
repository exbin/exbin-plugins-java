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
package org.exbin.framework.plugin.vaqua_laf;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import org.exbin.framework.App;
import org.exbin.framework.Module;
import org.exbin.framework.ui.theme.api.LafProvider;
import org.exbin.framework.ui.theme.api.UiThemeModuleApi;

/**
 * VAqua look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class VAquaLafModule implements Module {

    public static final String VAQUA_LAF_CLASS = "org.violetlib.aqua.AquaLookAndFeel";

    public VAquaLafModule() {
        if (System.getProperty("os.name", "").startsWith("Mac OS")) {
            UiThemeModuleApi themeModule = App.getModule(UiThemeModuleApi.class);
            themeModule.registerLafPlugin(new LafProvider() {
                @Nonnull
                @Override
                public String getLafId() {
                    return VAQUA_LAF_CLASS;
                }

                @Nonnull
                @Override
                public String getLafName() {
                    return "VAqua";
                }

                @Override
                public void installLaf() {
                    UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(getLafName(), getLafId()));
//                UIManager.installLookAndFeel("VAqua", VAQUA_LAF_CLASS);
                    // registerLafPlugin(flatDarkClassName, this);
                }

                @Override
                public void applyLaf() {
                    applyLookAndFeel(getLafId());
                }
            });
        }
    }

    public void applyLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(VAQUA_LAF_CLASS);
        } catch (Throwable ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    public void unregisterModule(String moduleId) {
    }
}
