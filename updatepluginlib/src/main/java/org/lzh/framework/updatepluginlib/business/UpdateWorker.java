package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.util.HandlerUtil;
import org.lzh.framework.updatepluginlib.util.InstallUtil;

/**
 *
 */
public abstract class UpdateWorker implements Runnable{

    protected String url;
    protected UpdateCheckCB checkCB;
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

    @Override
    public void run() {
        try {
            String response = check(url);
            Update parse = parser.parse(response);
            if (parse == null) {
                throw new IllegalArgumentException("parse response to update failed by " + parser.getClass().getCanonicalName());
            }
            int curVersion = InstallUtil.getApkVersion(UpdateConfig.getConfig().getContext());
            if (parse.getVersionCode() > curVersion) {
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
