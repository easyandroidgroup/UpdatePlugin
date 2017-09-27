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
package org.lzh.framework.updatepluginlib.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * 一个简单的用于自动管理的Activity模拟栈。 主要用于在进行更新通知时，提供最顶层的Activity实例作为有效的Context进行使用。
 *
 * @author haoge
 */
public final class ActivityManager implements Application.ActivityLifecycleCallbacks {
    private Context applicationContext;
    private static ActivityManager manager = new ActivityManager();
    public static ActivityManager get () {
        return manager;
    }
    private LinkedList<Activity> stack = new LinkedList<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!stack.contains(activity)) {
            stack.add(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (stack.contains(activity)) {
            stack.remove(activity);
        }
    }

    public Activity topActivity () {
        Activity activity = null;
        if (!stack.isEmpty()) {
            activity = stack.getLast();
        }
        return activity;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    void registerSelf(Context context) {
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(ActivityManager.get());
        this.applicationContext = context.getApplicationContext();
    }

    public void finishAll() {
        while (!stack.isEmpty()) {
            Activity pop = stack.pop();
            if (!pop.isFinishing()) {
                pop.finish();
            }
        }
    }
}
