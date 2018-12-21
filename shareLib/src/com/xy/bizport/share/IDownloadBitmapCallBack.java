package com.xy.bizport.share;

public interface IDownloadBitmapCallBack {

    /**
     * 解析短信并显示卡片
     * @param view
     * @return BINDING 渲染未完成, BIND_FAILED 渲染失败, BIND_SUCCESS 渲染成功
     */
    /**
     * 下载图片回调
     * @param code 结果码，0表示成功，1表示失败
     * @param bytes  图片数据
     */
    void downloadBitmapCallBack(int code, byte[] bytes, String message);
}
