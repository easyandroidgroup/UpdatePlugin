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
package org.lzh.framework.updatepluginlib.flow;

/**
 * 此类用于对框架的网络任务进行限制。避免执行的网络任务栈中。同一网络任务被同时多次执行。防止由此所导致的各种问题
 *
 * @author haoge
 */
public class UnifiedWorker {

    private volatile boolean isRunning;

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning () {
        return isRunning;
    }
}
