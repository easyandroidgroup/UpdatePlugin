package org.lzh.framework.updatepluginlib.util;

/**
 * Recycler
 * Created by lzh on 16-6-22.
 */
public class Recycler {

    /**
     * When params object is instanceof {@link Recycleable},invoke it release method
     * @param object The instance to be released
     */
    public static void release (Object object) {
        Recycleable recycleable = null;
        if (object instanceof Recycleable) {
            recycleable = (Recycleable) object;
        }

        if (recycleable != null) {
            recycleable.release();
        }
    }

    public interface Recycleable {
        /**
         * release all of member vars by handle
         */
        void release ();
    }
}
