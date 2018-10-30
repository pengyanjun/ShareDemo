package com.umeng.message.lib;

import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.io.Serializable;

public class ShareBean implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 分享平台
     */
    private SnsPlatform platform;
    /**
     * 分享的链接
     */
    private String shareUrl;
    /**
     * 分享的标题
     */
    private String sharetitle;
    /**
     * 分享的文本内容
     */
    private String shareContent;
    /**
     * 分享的图片，UMImage的构建有如下几种形式
     * UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
     * UMImage image = new UMImage(ShareActivity.this, file);//本地文件
     * UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
     * UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
     * UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流
     */
    private UMImage image;

    public ShareBean(SnsPlatform platform, String shareUrl, String sharetitle, String shareContent, UMImage image) {
        this.platform = platform;
        this.shareUrl = shareUrl;
        this.sharetitle = sharetitle;
        this.shareContent = shareContent;
        this.image = image;
    }

    public SnsPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(SnsPlatform platform) {
        this.platform = platform;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getSharetitle() {
        return sharetitle;
    }

    public void setSharetitle(String sharetitle) {
        this.sharetitle = sharetitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public UMImage getImage() {
        return image;
    }

    public void setImage(UMImage image) {
        this.image = image;
    }

}
