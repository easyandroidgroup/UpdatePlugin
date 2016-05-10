package org.lzh.framework.updatepluginlib.model;

/**
 * @author Administrator
 */
public interface UpdateParser {

    Update parse(String httpResponse);
}
