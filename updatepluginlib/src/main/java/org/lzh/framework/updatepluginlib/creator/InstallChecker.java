package org.lzh.framework.updatepluginlib.creator;

import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

public interface InstallChecker {

    boolean check (Update update, String file);
}
