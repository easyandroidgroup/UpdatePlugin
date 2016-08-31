package org.lzh.framework.updateplugin;

import android.app.Activity;

import java.util.LinkedList;

/**
 * 模拟Activity管理栈.
 * Created by admin on 16/8/31.
 */
public class ActivityStack {

    private static LinkedList<Activity> stack = new LinkedList<>();

    public static Activity pop () {
        if (stack.isEmpty()) return null;

        return stack.pop();
    }

    public static void pull (Activity act) {
        if (stack.contains(act)) return;
        stack.add(act);
    }

    public static Activity current () {
        return stack.getLast();
    }
}
