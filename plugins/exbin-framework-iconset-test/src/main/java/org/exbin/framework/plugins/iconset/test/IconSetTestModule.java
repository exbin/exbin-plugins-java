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
package org.exbin.framework.plugins.iconset.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.language.api.IconSetProvider;
import org.exbin.framework.language.api.LanguageModuleApi;

/**
 * Test icon set.
 *
 * @author ExBin Project (https://exbin.org)
 */
public class IconSetTestModule implements PluginModule {

    public IconSetTestModule() {
    }

    @Override
    public void register() {
        LanguageModuleApi languageModule = App.getModule(LanguageModuleApi.class);
        languageModule.registerIconSetProvider(new IconSetProvider() {

            private Map<String, String> keys = null;

            @Override
            public String getId() {
                return "org.exbin.framework.plugins.iconset.test";
            }

            @Override
            public String getName() {
                return "Test";
            }

            @Override
            public String getIconKey(String key) {
                if (keys == null) {
                    keys = new HashMap<>();

                    InputStream stream = getClass().getResourceAsStream("/org/exbin/framework/plugins/iconset/test/iconset.properties");
                    try {
                        InputStreamReader streamReader = new InputStreamReader(stream, "UTF-8");
                        BufferedReader reader = new BufferedReader(streamReader);
                        while (reader.ready()) {
                            String line = reader.readLine();
                            int splitPos = line.indexOf("=");
                            if (splitPos > 0) {
                                keys.put(line.substring(0, splitPos), line.substring(splitPos + 1));
                            }
                        }
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(IconSetTestModule.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(IconSetTestModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                return keys.get(key);
            }
        });
    }

    public void unregisterModule(String moduleId) {
    }
}
