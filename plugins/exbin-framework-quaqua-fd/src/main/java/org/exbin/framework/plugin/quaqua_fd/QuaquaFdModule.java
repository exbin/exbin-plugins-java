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
package org.exbin.framework.plugin.quaqua_fd;

import java.io.File;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.exbin.framework.App;
import org.exbin.framework.PluginModule;
import org.exbin.framework.file.api.FileModuleApi;
import org.exbin.framework.file.api.FileDialogsProvider;
import org.exbin.framework.file.api.FileTypes;
import org.exbin.framework.file.api.OpenFileResult;
import org.exbin.framework.file.api.UsedDirectoryApi;

/**
 * Quaqua file dialogs plugin.
 *
 * @author ExBin Project (https://exbin.org)
 */
@ParametersAreNonnullByDefault
public class QuaquaFdModule implements PluginModule {
    
    public static final String PROVIDER_ID = "quaqua";

    public QuaquaFdModule() {
    }

    @Override
    public void register() {
        FileModuleApi fileModule = App.getModule(FileModuleApi.class);
        fileModule.registerFileDialogsProvider(PROVIDER_ID, new FileDialogsProvider() {
            @Nonnull
            public String getProviderName() {
                return "Quaqua";
            }

            @Nonnull
            public OpenFileResult showOpenFileDialog(FileTypes fileTypes, @Nullable File selectedFile, @Nullable UsedDirectoryApi usedDirectory, @Nullable String dialogName) {
                // TODO
                return null;
            }

            @Nonnull
            public OpenFileResult showSaveFileDialog(FileTypes fileTypes, @Nullable File selectedFile, @Nullable UsedDirectoryApi usedDirectory, @Nullable String dialogName) {
                // TODO
                return null;
            }
            
        });
    }
    
    public void unregisterModule(String moduleId) {
    }
}
