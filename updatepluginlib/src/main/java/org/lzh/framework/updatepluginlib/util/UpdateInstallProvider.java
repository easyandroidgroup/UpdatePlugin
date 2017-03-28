package org.lzh.framework.updatepluginlib.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import org.lzh.framework.updatepluginlib.UpdateConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This provider should only be used by UpdatePlugin,Adaptive app versions above 24+ to install with packageInstaller
 */
public class UpdateInstallProvider extends ContentProvider {

    static Map<Uri,File> mCache = new HashMap<>();

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
        UpdateConfig.getConfig().init(context);
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        File file = mCache.get(uri);
        final int fileMode = modeToMode(mode);
        return ParcelFileDescriptor.open(file, fileMode);
    }

    public static Uri getUriByFile (File file,String author) {
        String path;
        try {
            path = file.getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
        }
        Uri uri =  new Uri.Builder().scheme("content")
                .authority(author).encodedPath(path).build();
        mCache.put(uri,file);
        return uri;
    }

    /**
     * Copied from ContentResolver.java
     */
    private static int modeToMode(String mode) {
        int modeBits;
        if ("r".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_READ_ONLY;
        } else if ("w".equals(mode) || "wt".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_WRITE_ONLY
                    | ParcelFileDescriptor.MODE_CREATE
                    | ParcelFileDescriptor.MODE_TRUNCATE;
        } else if ("wa".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_WRITE_ONLY
                    | ParcelFileDescriptor.MODE_CREATE
                    | ParcelFileDescriptor.MODE_APPEND;
        } else if ("rw".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_READ_WRITE
                    | ParcelFileDescriptor.MODE_CREATE;
        } else if ("rwt".equals(mode)) {
            modeBits = ParcelFileDescriptor.MODE_READ_WRITE
                    | ParcelFileDescriptor.MODE_CREATE
                    | ParcelFileDescriptor.MODE_TRUNCATE;
        } else {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        return modeBits;
    }
}
