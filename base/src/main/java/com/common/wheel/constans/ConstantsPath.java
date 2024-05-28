package com.common.wheel.constans;


import android.os.Environment;

/**
 * 文件路径的常量（文件名，文件路径）
 */
public interface ConstantsPath {

    /**
     * DB数据库名
     */
    String DATABASE_NAME = "pekon.db";

    /**
     * SD卡路径
     */
    String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * android数据目录
     */
    String DATA_PATH = "/data/data/com.pekon.mobileretail";


    /**
     * 存储文件目录名
     */
    String APPLICATION_NAME = "pekoncom.pekon.mobileretail".replace("com.pekon", "").replace(".mobileretail","").toUpperCase();

    /**
     * 闪退日志保存路径
     */
    String CRASH = "/CRASH";


    /**
     * witpos的SD卡路径
     */
    String WITPOS_SD_PATH = SDCARD_PATH + "/" + APPLICATION_NAME;

    /**
     * witpos的系统路径
     */
    String WITPOS_DATA_PATH = DATA_PATH + "/" + APPLICATION_NAME;

    /**
     * 数据的实际存储位置
     */
    /**
     * 促销引擎文件目录
     */
    String PROMOTION_PATH = "/promotion";
    //促销引擎文件名
    String PROMOTION = "promotion.js";
    //促销引擎文件名
    String FUNCTIONS = "functions.js";
    //js业务文件名
    String JS_SOURCE_ZIP = "source.zip";

    // 会员
    String MEMBER_ADD_ADRESSS = "/memInfo?paramData=";

    /**
     * 打印图片下载路径
     */
    String DOWNLOAD_IMAGE_PATH = "/" + "downloadimages";

    /**
     * 门店消息文件目录
     */
    String MESSAGE_PATH = "/message";

    /***
     *
     * log日志文件保存目录
     */
    String LOG = "/log";

    /**
     *  数据库地址
     */
    String DB_FILE = "/"+"databases"+"/"+ DATABASE_NAME;

    /**
     *
     * 压缩文件存放目录
     * */
    String ZIP_FOLDER = "/" + "zip" + "/";
}
