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
package org.exbin.framework.plugin.language.ja_JP;

import java.util.Locale;
import java.util.Optional;
import javax.swing.ImageIcon;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.language.api.LanguageModuleApi;
import org.exbin.framework.language.api.LanguageProvider;

/**
 * Language resources plugin for Japanese language.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class LanguageJaJpModule implements PluginModule {

    public LanguageJaJpModule() {
    }

    @Override
    public void register() {
        LanguageModuleApi languageModule = App.getModule(LanguageModuleApi.class);
        languageModule.registerLanguagePlugin(new LanguageProvider() {
            @Override
            public Locale getLocale() {
                return new Locale("ja", "JP");
            }

            @Override
            public Optional<ClassLoader> getClassLoader() {
                return Optional.of(getClass().getClassLoader());
            }

            @Override
            public Optional<ImageIcon> getFlag() {
                return Optional.of(new ImageIcon(getClass().getResource("/resources/images/flags/jp.png")));
            }
        });
    }

    public void unregisterModule(String moduleId) {
    }
}
