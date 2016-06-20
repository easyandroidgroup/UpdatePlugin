package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.util.HandlerUtil;
import org.lzh.framework.updatepluginlib.util.InstallUtil;

/**
 * 进行新版本检测接口访问的网络任务
 */
public abstract class UpdateWorker implements Runnable{
    // 新版本检测接口url，由UpdateConfig或者UpdateBuilder进行设置
    protected String url;
    // 版本检测回调，
    protected UpdateCheckCB checkCB;
    // 版本检测器。用于检测当前是否为需要更新的版本
    protected UpdateChecker checker;
    // url接口返回数据解析器。
    protected UpdateParser parser;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCheckCB(UpdateCheckCB checkCB) {
        this.checkCB = checkCB;
    }

    public void setParser(UpdateParser parser) {
        this.parser = parser;
    }

    public void setChecker(UpdateChecker checker) {
        this.checker = checker;
    }

    @Override
    public void run() {
        try {
            String response = check(url);
            Update parse = parser.parse(response);
            if (parse == null) {
                throw new IllegalArgumentException("parse response to update failed by " + parser.getClass().getCanonicalName());
            }
            if (!parse.isIgnore() && checker.check(parse)) {
                sendHasUpdate(parse);
            } else {
                sendNoUpdate();
            }
        } catch (HttpException he) {
            sendOnErrorMsg(he.getCode(),he.getErrorMsg());
        } catch (Exception e) {
            sendOnErrorMsg(-1,e.getMessage());
        }
    }

    /**
     * access the url and get response data back
     * @param url The url to be accessed
     * @return response data from url
     * @throws Exception
     */
    protected abstract String check(String url) throws Exception;

    private void sendHasUpdate(final Update update) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.hasUpdate(update);
            }
        });
    }

    private void sendNoUpdate() {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.noUpdate();
            }
        });
    }

    private void sendOnErrorMsg(final int code, final String errorMsg) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.onCheckError(code,errorMsg);
            }
        });
    }
}
