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
package org.exbin.framework.plugins.language.zh_CN;

import java.util.Locale;
import org.exbin.framework.api.XBApplication;
import org.exbin.framework.api.XBApplicationModule;
import org.exbin.xbup.plugin.XBModuleHandler;

/**
 * Language resources plugin for Chinese language.
 *
 * @version 0.2.0 2017/08/12
 * @author ExBin Project (http://exbin.org)
 */
public class LanguageZhCnModule implements XBApplicationModule {

    private XBApplication application;

    public LanguageZhCnModule() {
    }

    @Override
    public void init(XBModuleHandler moduleHandler) {
        this.application = (XBApplication) moduleHandler;
        application.registerLanguagePlugin(new Locale("zh", "CN"), getClass().getClassLoader());
    }

    @Override
    public void unregisterModule(String moduleId) {
    }
}
