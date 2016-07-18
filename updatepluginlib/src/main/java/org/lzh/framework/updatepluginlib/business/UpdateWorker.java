package org.lzh.framework.updatepluginlib.business;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.util.HandlerUtil;
import org.lzh.framework.updatepluginlib.util.Recycler;
import org.lzh.framework.updatepluginlib.util.Recycler.Recycleable;

/**
 * The network task to check out whether or not a new version of apk is exist
 */
public abstract class UpdateWorker extends UnifiedWorker implements Runnable,Recycleable{

    /**
     * To see {@link org.lzh.framework.updatepluginlib.UpdateConfig#url}
     */
//    protected String url;

    protected CheckEntity entity;
    /**
     * The instance of {@link org.lzh.framework.updatepluginlib.callback.DefaultCheckCB}
     */
    protected UpdateCheckCB checkCB;

    /**
     * set by {@link org.lzh.framework.updatepluginlib.UpdateConfig#updateChecker(UpdateChecker)} or
     * {@link org.lzh.framework.updatepluginlib.UpdateBuilder#updateChecker(UpdateChecker)}<br>
     *     <br>
     *     according to instance {@link Update} to check out whether or not should be updated
     */
    protected UpdateChecker checker;
    /**
     * set by {@link org.lzh.framework.updatepluginlib.UpdateConfig#jsonParser(UpdateParser)]} or
     * {@link org.lzh.framework.updatepluginlib.UpdateBuilder#jsonParser(UpdateParser)}<br><br>
     *
     *     according to response data from url to create update instance
     */
    protected UpdateParser parser;

    public void setEntity(CheckEntity entity) {
        this.entity = entity;
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
            String response = check(entity);
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
            he.printStackTrace();
            sendOnErrorMsg(he.getCode(),he.getErrorMsg());
        } catch (Exception e) {
            e.printStackTrace();
            sendOnErrorMsg(-1,e.getMessage());
        } finally {
            setRunning(false);
        }
    }

    /**
     * access the url and get response data back
     * @param url The url to be accessed
     * @return response data from url
     * @throws Exception
     */
    protected abstract String check(CheckEntity url) throws Exception;

    private void sendHasUpdate(final Update update) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.hasUpdate(update);
                Recycler.release(this);
            }
        });
    }

    private void sendNoUpdate() {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.noUpdate();
                Recycler.release(this);
            }
        });
    }

    private void sendOnErrorMsg(final int code, final String errorMsg) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.onCheckError(code,errorMsg);
                Recycler.release(this);
            }
        });
    }

    @Override
    public void release() {
        this.checkCB = null;
        this.checker = null;
        this.parser = null;
    }
}
