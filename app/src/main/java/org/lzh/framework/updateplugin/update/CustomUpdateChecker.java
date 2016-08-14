package org.lzh.framework.updateplugin.update;

import org.lzh.framework.updatepluginlib.model.DefaultChecker;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;

/**
 * 自定义更新检查器
 */
public class CustomUpdateChecker implements UpdateChecker {
    /**
     * @param update 配置jsonParser返回的update实体类
     * @return true 此版本需要更新
     */
    @Override
    public boolean check(Update update) {
        // 在此对配置jsonParser返回的update实体类做是否需要更新的检查。
        return new DefaultChecker().check(update);
    }
}
