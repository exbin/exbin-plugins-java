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
package org.exbin.framework.plugins.napkin_laf;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import org.exbin.xbup.plugin.LookAndFeelApplier;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.xbup.plugin.XBModuleHandler;

/**
 * Napkin look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class NapkinLafModule implements XBApplicationModule, LookAndFeelApplier {

    public static final String NAPKIN_LAF_CLASS = "net.sourceforge.napkinlaf.NapkinLookAndFeel";
    private XBApplication application;

    public NapkinLafModule() {
    }

    @Override
    public void init(XBModuleHandler moduleHandler) {
        this.application = (XBApplication) moduleHandler;

        UIManager.installLookAndFeel("Napkin", NAPKIN_LAF_CLASS);
        application.registerLafPlugin(NAPKIN_LAF_CLASS, this);
    }

    @Override
    public void applyLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(NAPKIN_LAF_CLASS);
        } catch (Throwable ex) {
            Logger.getLogger(NapkinLafModule.class.getName()).log(Level.SEVERE, "Failed to initialize LaF", ex);
        }
    }

    @Override
    public void unregisterModule(String moduleId) {
    }
}
