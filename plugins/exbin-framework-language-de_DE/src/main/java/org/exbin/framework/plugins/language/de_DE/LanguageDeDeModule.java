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
package org.exbin.framework.plugins.language.de_DE;

import java.util.Locale;
import java.util.Optional;
import javax.swing.ImageIcon;
import org.exbin.framework.api.LanguageProvider;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.xbup.plugin.XBModuleHandler;

/**
 * Language resources plugin for German language.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class LanguageDeDeModule implements XBApplicationModule {

    private XBApplication application;

    public LanguageDeDeModule() {
    }

    @Override
    public void init(XBModuleHandler moduleHandler) {
        this.application = (XBApplication) moduleHandler;
        try {
            application.registerLanguagePlugin(new LanguageProvider() {
                @Override
                public Locale getLocale() {
                    return new Locale("de", "DE");
                }

                @Override
                public Optional<ClassLoader> getClassLoader() {
                    return Optional.of(getClass().getClassLoader());
                }

                @Override
                public Optional<ImageIcon> getFlag() {
                    return Optional.of(new ImageIcon(getClass().getResource("/resources/images/flags/de.png")));
                }
            });
        } catch (Throwable ex) {
            application.registerLanguagePlugin(new Locale("de", "DE"), getClass().getClassLoader());
        }
    }

    @Override
    public void unregisterModule(String moduleId) {
    }
}
