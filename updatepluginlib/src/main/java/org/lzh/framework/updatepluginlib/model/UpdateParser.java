package org.lzh.framework.updatepluginlib.model;

/**
 * @author Administrator
 */
public interface UpdateParser {

    <T extends Update> T parse(String httpResponse);
}
