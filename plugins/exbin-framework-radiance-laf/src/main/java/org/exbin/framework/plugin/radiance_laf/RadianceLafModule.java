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
package org.exbin.framework.plugin.radiance_laf;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.ui.theme.api.LafProvider;
import org.exbin.framework.ui.theme.api.UiThemeModuleApi;
import org.pushingpixels.radiance.theming.api.skin.RadianceBusinessLookAndFeel;

/**
 * Darcula look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class RadianceLafModule implements PluginModule {

    public static final String LAF_NAME = "Radiance";

    public RadianceLafModule() {
    }

    @Override
    public void register() {
        UiThemeModuleApi themeModule = App.getModule(UiThemeModuleApi.class);
        themeModule.registerLafPlugin(new LafProvider() {
            @Override
            public String getLafId() {
                return RadianceBusinessLookAndFeel.class.getName();
            }

            @Override
            public void applyLaf() {
                String className = RadianceBusinessLookAndFeel.class.getName();
                applyLookAndFeel(className);
            }

            @Override
            public String getLafName() {
                return LAF_NAME;
            }

            @Override
            public void installLaf() {
                String className = RadianceBusinessLookAndFeel.class.getName();
                UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(LAF_NAME, className));
            }
        });
    }

    public void applyLookAndFeel(String className) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    UIManager.setLookAndFeel(RadianceBusinessLookAndFeel.class.getCanonicalName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(RadianceLafModule.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(RadianceLafModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void unregisterModule(String moduleId) {
    }
}
