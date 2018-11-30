package com.xy.rcstest;

import android.app.Application;

import com.xy.bizport.share.ShareTool;

public class MyApplication extends Application {
    private static final String UMENG_APP_KEY = "586b0c96a40fa36bec000291";
    private static final String UMENG_CHANNEL = "Umeng";

    private static final String WEIXIN_APP_ID = "wxcf863cb060968914";
    private static final String WEIXIN_APP_SECRET = "4d6ba32e2385e24183c7e09c6be9c1f3";

    private static final String SINA_APP_KEY = "1300669930";
    private static final String SINA_APP_SECRET = "9af732a740e94c84790e5be800b457df";
    private static final String SINA_CALLBACK = "http://sns.whalecloud.com";//新浪后台的授权回调地址

    private static final String QQ_APP_ID = "1107943046";
    private static final String QQ_APP_KEY = "Uv77AP8JDiLfvT6J";

    @Override
    public void onCreate() {
        super.onCreate();
        ShareTool.getInstance().initUmengShare(this,true,false,
                UMENG_APP_KEY, UMENG_CHANNEL,
                WEIXIN_APP_ID, WEIXIN_APP_SECRET,
                SINA_APP_KEY, SINA_APP_SECRET, SINA_CALLBACK,
                QQ_APP_ID, QQ_APP_KEY);
    }

}
