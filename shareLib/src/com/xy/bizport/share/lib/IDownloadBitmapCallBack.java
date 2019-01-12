package com.xy.bizport.share.lib;

public interface IDownloadBitmapCallBack {

    /**
     * 下载图片回调
     * @param code 结果码，0表示成功，1表示失败
     * @param bytes  图片数据
     */
    void downloadBitmapCallBack(int code, byte[] bytes, String message);
}
