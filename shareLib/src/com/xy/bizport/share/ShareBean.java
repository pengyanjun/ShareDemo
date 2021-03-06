package com.xy.bizport.share;

import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.io.Serializable;
import java.util.List;

public class ShareBean implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 分享平台
     */
    private List<SnsPlatform> platforms;
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
    private UMImage umImage;
    /**
     * 分享的图片下载地址
     */
    private String imageUrl;

    public ShareBean() {
    }

    public ShareBean(List<SnsPlatform> platforms, String shareUrl, String sharetitle, String shareContent, String imageUrl) {
        this.platforms = platforms;
        this.shareUrl = shareUrl;
        this.sharetitle = sharetitle;
        this.shareContent = shareContent;
        this.imageUrl = imageUrl;
    }

    public List<SnsPlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<SnsPlatform> platforms) {
        this.platforms = platforms;
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

    public UMImage getUmImage() {
        return umImage;
    }

    public void setUmImage(UMImage umImage) {
        this.umImage = umImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ShareBean{" +
                "platforms=" + platforms +
                ", shareUrl='" + shareUrl + '\'' +
                ", sharetitle='" + sharetitle + '\'' +
                ", shareContent='" + shareContent + '\'' +
                ", umImage=" + umImage +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
