package com.xy.bizport.share.lib;

import android.content.Context;
import android.text.TextUtils;

public class ShareAppId {
    public String UMENG_APP_KEY = "";
    public String UMENG_CHANNEL = "";

    public String WEIXIN_APP_ID = "";
    public String WEIXIN_APP_SECRET = "";

    public String SINA_APP_KEY = "";
    public String SINA_APP_SECRET = "";
    public String SINA_CALLBACK = "";

    public String QQ_APP_ID = "";
    public String QQ_APP_KEY = "";

    public String getUmengAppKey(Context context) {
        if (TextUtils.isEmpty(UMENG_APP_KEY)){
            UMENG_APP_KEY =  SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_UMENG_APP_KEY);
        }
        return UMENG_APP_KEY;
    }

    public String getUmengChannel(Context context) {
        if (TextUtils.isEmpty(UMENG_CHANNEL)){
            UMENG_CHANNEL = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_UMENG_CHANNEL);
        }
        return UMENG_CHANNEL;
    }

    public String getWeixinAppId(Context context) {
        if (TextUtils.isEmpty(WEIXIN_APP_ID)){
            WEIXIN_APP_ID = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_WEIXIN_APP_ID);
        }
        return WEIXIN_APP_ID;
    }

    public String getWeixinAppSecret(Context context) {
        if (TextUtils.isEmpty(WEIXIN_APP_SECRET)){
            WEIXIN_APP_SECRET = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_WEIXIN_APP_SECRET);
        }
        return WEIXIN_APP_SECRET;
    }

    public String getSinaAppKey(Context context) {
        if (TextUtils.isEmpty(SINA_APP_KEY)){
            SINA_APP_KEY = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_SINA_APP_KEY);
        }
        return SINA_APP_KEY;
    }

    public String getSinaAppSecret(Context context) {
        if (TextUtils.isEmpty(SINA_APP_SECRET)){
            SINA_APP_SECRET = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_SINA_APP_SECRET);
        }
        return SINA_APP_SECRET;
    }

    public String getSinaCallback(Context context) {
        if (TextUtils.isEmpty(SINA_CALLBACK)){
            SINA_CALLBACK = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_SINA_CALLBACK);
        }
        return SINA_CALLBACK;
    }

    public String getQqAppId(Context context) {
        if (TextUtils.isEmpty(QQ_APP_ID)){
            QQ_APP_ID = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_QQ_APP_ID);
        }
        return QQ_APP_ID;
    }

    public String getQqAppKey(Context context) {
        if (TextUtils.isEmpty(QQ_APP_KEY)){
            QQ_APP_KEY = SPUtils.getSharedPreferencesValue(context, ShareConstant.CONSTANT_QQ_APP_KEY);
        }
        return QQ_APP_KEY;
    }
}
