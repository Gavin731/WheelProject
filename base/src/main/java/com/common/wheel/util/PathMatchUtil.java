package com.common.wheel.util;

import com.common.wheel.BaseApplication;

import java.io.File;
import java.util.Objects;

public class PathMatchUtil {

    public static String getPath(String path, String defaultRoot) {
        return Objects.requireNonNull(BaseApplication.getInstance().getExternalFilesDir(path)).getAbsolutePath();
    }

    public static String getPath(String path) {
        return Objects.requireNonNull(BaseApplication.getInstance().getExternalFilesDir(path)).getAbsolutePath();
    }

    public static File getFile(String path) {
        return BaseApplication.getInstance().getExternalFilesDir(path);
    }


}
