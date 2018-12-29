package com.xy.bizport.share.lib;

import com.umeng.socialize.bean.SHARE_MEDIA;

public enum ShareMedia {
    WEIXIN,
    WEIXIN_CIRCLE,
    QZONE,
    QQ,
    SINA;

    public SharePlatform toSharePlatform(){
        SharePlatform sharePlatform = new SharePlatform();
        sharePlatform.mPlatform = this;
        return sharePlatform;
    }

    public String toString() {
        return super.toString();
    }

    public static SHARE_MEDIA getUmengMedia(ShareMedia shareMedia){
        if (shareMedia.toString().equals("QQ")){
            return SHARE_MEDIA.QQ;
        }else if (shareMedia.toString().equals("QZONE")){
            return SHARE_MEDIA.QZONE;
        }else if (shareMedia.toString().equals("WEIXIN")){
            return SHARE_MEDIA.WEIXIN;
        }else if (shareMedia.toString().equals("WEIXIN_CIRCLE")){
            return SHARE_MEDIA.WEIXIN_CIRCLE;
        } else if (shareMedia.toString().equals("SINA")){
            return SHARE_MEDIA.SINA;
        }else {
            return null;
        }
    }
}
