/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lzh.framework.updatepluginlib.model;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.util.UpdatePreference;
import org.lzh.framework.updatepluginlib.util.Utils;

public class DefaultChecker implements UpdateChecker {
    @Override
    public boolean check(Update update) {
        try {
            int curVersion = Utils.getApkVersion(UpdateConfig.getConfig().getContext());
            if (update.getVersionCode() > curVersion &&
                    (update.isForced()||
                    !UpdatePreference.getIgnoreVersions().contains(String.valueOf(update.getVersionCode())))) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
