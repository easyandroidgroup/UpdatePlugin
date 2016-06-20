package org.lzh.framework.updatepluginlib.model;

/**
 * Parse http response data to Update class
 *
 * @author lzh
 */
public interface UpdateParser {

    /**
     * Parse http response data to Update class
     *
     * @param httpResponse The response data to be parsed
     * @param <T> The class extends by Update
     * @return a new Update instance
     */
    <T extends Update> T parse(String httpResponse);
}
