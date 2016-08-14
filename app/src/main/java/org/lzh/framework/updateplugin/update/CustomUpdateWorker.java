package org.lzh.framework.updateplugin.update;

import org.lzh.framework.updatepluginlib.business.UpdateWorker;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;

/**
 * 自定义检查更新接口
 */
public class CustomUpdateWorker extends UpdateWorker {
    /**
     * 此方法运行于子线程，在此进行检查更新接口的访问。将接口返回数据进行返回
     * @param entity 包含有更新接口相关信息的实体类。包括需要访问的URL,请求方式GET/POST,以及POST提交时的BODY传参params
     *               此实体类。由配置类UpdateConfig或者UpdateBuilder中的url(url)或者checkEntity(entity)方法指定
     * @return 返回此接口的返回数据
     * @throws Exception
     */
    @Override
    protected String check(CheckEntity entity) throws Exception {
        // 此方法返回的数据会传递至配置类UpdateConfig中的jsonParser所指定的配置中。
        // 在此进行网络请求。并把请求数据返回，此处为模拟请求返回
        return "mock data";
    }
}
