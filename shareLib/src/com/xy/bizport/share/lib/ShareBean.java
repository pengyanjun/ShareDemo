package com.xy.bizport.share.lib;

import java.io.Serializable;
import java.util.List;

public class ShareBean implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 分享平台
     */
    private List<SharePlatform> platforms;
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
     * 分享的图片对象
     */
    private ShareImage umImage;
    /**
     * 分享的图片下载地址
     */
    private String imageUrl;

    public ShareBean() {
    }

    public ShareBean(List<SharePlatform> platforms, String shareUrl, String sharetitle, String shareContent, String imageUrl) {
        this.platforms = platforms;
        this.shareUrl = shareUrl;
        this.sharetitle = sharetitle;
        this.shareContent = shareContent;
        this.imageUrl = imageUrl;
    }

    public List<SharePlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<SharePlatform> platforms) {
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

    public ShareImage getUmImage() {
        return umImage;
    }

    public void setUmImage(ShareImage umImage) {
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
