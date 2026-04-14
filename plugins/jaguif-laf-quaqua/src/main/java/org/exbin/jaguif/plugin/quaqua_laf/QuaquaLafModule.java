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
package org.exbin.jaguif.plugin.quaqua_laf;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import org.exbin.jaguif.App;
import org.exbin.jaguif.Module;
import org.exbin.jaguif.ui.theme.api.LafProvider;
import org.exbin.jaguif.ui.theme.api.UiThemeModuleApi;

/**
 * Quaqua look and feel plugin.
 */
@ParametersAreNonnullByDefault
public class QuaquaLafModule implements Module {

    public static final String LAF_NAME = "Quaqua";
    public static final String QUAQUA_LAF_CLASS = "ch.randelshofer.quaqua.QuaquaLookAndFeel";

    public QuaquaLafModule() {
        if (System.getProperty("os.name", "").startsWith("Mac OS")) {
            UiThemeModuleApi themeModule = App.getModule(UiThemeModuleApi.class);
            themeModule.registerLafPlugin(new LafProvider() {
                @Nonnull
                @Override
                public String getLafId() {
                    return QUAQUA_LAF_CLASS;
                }

                @Nonnull
                @Override
                public String getLafName() {
                    return LAF_NAME;
                }

                @Override
                public void installLaf() {
                    UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(getLafName(), getLafId()));
                }

                @Override
                public void applyLaf() {
                    applyLookAndFeel(getLafId());
                }
            });
        }
    }

    public void applyLookAndFeel(String className) {
         // set system properties here that affect Quaqua
         // for example the default layout policy for tabbed
         // panes:
         System.setProperty(
            "Quaqua.tabLayoutPolicy","wrap"

         );
         
         // set the Quaqua Look and Feel in the UIManager
         try {
              UIManager.setLookAndFeel(
                  ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel()
              );
             // set UI manager properties here that affect Quaqua
         } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
         }
    }

    public void unregisterModule(String moduleId) {
    }
}
