package com.xy.bizport.share.lib;

import com.umeng.socialize.bean.SHARE_MEDIA;

public enum ShareMedia {
    WEIXIN,
    WEIXIN_CIRCLE,
    QZONE,
    QQ,
    SINA;

    public static SHARE_MEDIA getUmengMedia(ShareMedia shareMedia){
        if (shareMedia.toString().equals(QQ.toString())){
            return SHARE_MEDIA.QQ;
        }else if (shareMedia.toString().equals(QZONE.toString())){
            return SHARE_MEDIA.QZONE;
        }else if (shareMedia.toString().equals(WEIXIN.toString())){
            return SHARE_MEDIA.WEIXIN;
        }else if (shareMedia.toString().equals(WEIXIN_CIRCLE.toString())){
            return SHARE_MEDIA.WEIXIN_CIRCLE;
        } else if (shareMedia.toString().equals(SINA.toString())){
            return SHARE_MEDIA.SINA;
        }else {
            return null;
        }
    }
}
