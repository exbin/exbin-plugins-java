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
package org.exbin.framework.plugins.darcula_laf;

import com.bulenkov.darcula.DarculaLaf;
import com.bulenkov.darcula.DarculaLookAndFeelInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.ui.api.LafProvider;
import org.exbin.framework.ui.api.UiModuleApi;

/**
 * Darcula look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class DarculaLafModule implements PluginModule {

    public DarculaLafModule() {
    }

    @Override
    public void register() {
        UiModuleApi languageModule = App.getModule(UiModuleApi.class);
        languageModule.registerLafPlugin(new LafProvider() {
            @Override
            public String getLafId() {
                return DarculaLaf.class.getName();
            }

            @Override
            public void applyLaf() {
                String className = DarculaLaf.class.getName();
                applyLookAndFeel(className);
            }

            @Override
            public String getLafName() {
                return DarculaLaf.NAME;
            }

            @Override
            public void installLaf() {
                String className = DarculaLaf.class.getName();
                UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(DarculaLaf.NAME, className));
            }
        });
    }

    public void applyLookAndFeel(String className) {
        try {
            // Workaround for https://github.com/bulenkov/iconloader/issues/14
            javax.swing.UIManager.getFont("Label.font");

            UIManager.setLookAndFeel(DarculaLaf.class.getCanonicalName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            try {
                UIManager.installLookAndFeel(new DarculaLookAndFeelInfo());
                DarculaLaf darculaLaf = new DarculaLaf();
                darculaLaf.initialize();
                UIManager.setLookAndFeel(darculaLaf);
            } catch (UnsupportedLookAndFeelException ex2) {
                Logger.getLogger(DarculaLafModule.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        //UIManager.put("Nb.DarculaLFCustoms", new DarculaLFCustoms());
    }

    public void unregisterModule(String moduleId) {
    }
}
