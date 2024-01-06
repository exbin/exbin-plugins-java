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
package org.exbin.framework.plugins.vaqua_laf;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;
import org.exbin.xbup.plugin.LookAndFeelApplier;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.xbup.plugin.XBModuleHandler;

/**
 * VAqua look and feel plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class VAquaLafModule implements XBApplicationModule, LookAndFeelApplier {

    public static final String VAQUA_LAF_CLASS = "org.violetlib.aqua.AquaLookAndFeel";
    private XBApplication application;

    public VAquaLafModule() {
    }

    @Override
    public void init(XBModuleHandler moduleHandler) {
        this.application = (XBApplication) moduleHandler;

        if (System.getProperty("os.name", "").startsWith("Mac OS")) {
            UIManager.installLookAndFeel("VAqua", VAQUA_LAF_CLASS);
            application.registerLafPlugin(VAQUA_LAF_CLASS, this);
        }
    }

    @Override
    public void applyLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(VAQUA_LAF_CLASS);
        } catch (Throwable ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    @Override
    public void unregisterModule(String moduleId) {
    }
}
