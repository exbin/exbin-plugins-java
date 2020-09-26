/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.exbin.xbup.plugin.LookAndFeelApplier;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.xbup.plugin.XBModuleHandler;

/**
 * Darcula look and feel plugin.
 *
 * @version 0.2.0 2020/09/26
 * @author ExBin Project (http://exbin.org)
 */
public class DarculaLafModule implements XBApplicationModule, LookAndFeelApplier {

    private XBApplication application;

    public DarculaLafModule() {
    }

    @Override
    public void init(XBModuleHandler moduleHandler) {
        this.application = (XBApplication) moduleHandler;
        String className = DarculaLaf.class.getName();
        UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(DarculaLaf.NAME, className));
        application.registerLafPlugin(className, this);
    }

    @Override
    public void applyLookAndFeel(String className) {
        try {
            // Workaround for https://github.com/bulenkov/iconloader/issues/14
            javax.swing.UIManager.getFont("Label.font");

            UIManager.setLookAndFeel(DarculaLaf.class.getCanonicalName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            try {
                UIManager.installLookAndFeel(new DarculaLookAndFeelInfo());
                UIManager.setLookAndFeel(new DarculaLaf());
            } catch (UnsupportedLookAndFeelException ex2) {
                Logger.getLogger(DarculaLafModule.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
        //UIManager.put("Nb.DarculaLFCustoms", new DarculaLFCustoms());
    }

    @Override
    public void unregisterModule(String moduleId) {
    }
}
