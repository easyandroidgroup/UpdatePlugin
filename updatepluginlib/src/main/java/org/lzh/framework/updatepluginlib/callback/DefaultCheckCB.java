package org.lzh.framework.updatepluginlib.callback;

import android.app.Activity;
import android.app.DialogFragment;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.business.UpdateExecutor;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.util.SafeDialogOper;

import java.lang.ref.WeakReference;

/**
 * @author Administrator
 */
public class DefaultCheckCB implements UpdateCheckCB {

    private WeakReference<Activity> actRef = null;
    private UpdateBuilder builder;

    public DefaultCheckCB(Activity activity) {
        this.actRef = new WeakReference<>(activity);
    }

    public void setBuilder (UpdateBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void hasUpdate(Update update) {
        if (!builder.getStrategy().isShowUpdateDialog(update)) {
            UpdateDownloadCB downloadCB = new DefaultDownloadCB(update,builder);
            UpdateExecutor.getInstance().download(update.getUpdateUrl(),downloadCB);
            return;
        }
        DialogFragment dialog = (DialogFragment) UpdateConfig.getInstance().getNeedUpdateDialog();
        SafeDialogOper.safeShowDialog(dialog,actRef.get(),"NEED_UPDATE_DIALOG");
    }

    @Override
    public void noUpdate() {
    }

    @Override
    public void onCheckError(int code, String errorMsg) {
    }
}
