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
package org.exbin.framework.plugin.napkin_laf;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.ui.api.LafProvider;
import org.exbin.framework.ui.api.UiModuleApi;

/**
 * Napkin look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class NapkinLafModule implements PluginModule {

    public static final String NAPKIN_LAF_CLASS = "net.sourceforge.napkinlaf.NapkinLookAndFeel";

    public NapkinLafModule() {
    }

    @Override
    public void register() {
        UiModuleApi languageModule = App.getModule(UiModuleApi.class);
        languageModule.registerLafPlugin(new LafProvider() {
            @Nonnull
            @Override
            public String getLafId() {
                return NAPKIN_LAF_CLASS;
            }

            @Nonnull
            @Override
            public String getLafName() {
                return "Napkin";
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
        try {
            UIManager.setLookAndFeel(NAPKIN_LAF_CLASS);
        } catch (Throwable ex) {
            Logger.getLogger(NapkinLafModule.class.getName()).log(Level.SEVERE, "Failed to initialize LaF", ex);
        }
    }

    public void unregisterModule(String moduleId) {
    }
}
