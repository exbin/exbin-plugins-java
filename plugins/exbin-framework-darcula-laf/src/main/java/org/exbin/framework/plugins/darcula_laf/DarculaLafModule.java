/*
 * Copyright (C) ExBin Project
 *
 * This application or library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This application or library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along this application.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.exbin.framework.plugins.darcula_laf;

import com.bulenkov.darcula.DarculaLaf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.xbup.plugin.XBModuleHandler;

/**
 * Darcula look and feel plugin.
 *
 * @version 0.2.0 2017/11/19
 * @author ExBin Project (http://exbin.org)
 */
public class DarculaLafModule implements XBApplicationModule {

    private XBApplication application;

    public DarculaLafModule() {
    }

    @Override
    public void init(XBModuleHandler moduleHandler) {
        javax.swing.UIManager.getFont("Label.font");
//        try {
//            UIManager.setLookAndFeel(new DarculaLaf());
//        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(DarculaLafModule.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            moduleHandler.getClass().getClassLoader().loadClass(DarculaLaf.class.getName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DarculaLafModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo(DarculaLaf.NAME, DarculaLaf.class.getName()));
//        UIManager.put("Nb.DarculaLFCustoms", new DarculaLFCustoms());
    }

    @Override
    public void unregisterModule(String moduleId) {
    }
}
