package org.lzh.framework.updatepluginlib.creator;

import org.lzh.framework.updatepluginlib.model.Update;

public interface InstallChecker {

    boolean check (Update update, String file);
}
