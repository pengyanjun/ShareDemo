package com.xy.bizport.demo.ipmessage;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initUmengShare();
    }

    private void initUmengShare(){
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
//        UMConfigure.setEncryptEnabled(true);
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey，非必须参数，如果Manifest文件中已配置AppKey，该参数可以传空，则使用Manifest中配置的AppKey，否则该参数必须传入。
         * 参数3:【友盟+】 Channel，非必须参数，如果Manifest文件中已配置Channel，该参数可以传空，则使用Manifest中配置的Channel，否则该参数必须传入，Channel命名请详见Channel渠道命名规范。
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */
//        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
//                null);//友盟提供的AppKey
        UMConfigure.init(this, "	586b0c96a40fa36bec000291", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                null);//公司申请的友盟AppKey


        PlatformConfig.setWeixin("wxcf863cb060968914", "4d6ba32e2385e24183c7e09c6be9c1f3");//公司申请
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");//友盟提供


        PlatformConfig.setSinaWeibo("1300669930", "9af732a740e94c84790e5be800b457df", "http://sns.whalecloud.com");//公司申请
//        PlatformConfig.setSinaWeibo("3036915936", "c3fbfaf8c2236e94883e8f42b9fe26de", "http://sns.whalecloud.com");//我申请
//        PlatformConfig.setSinaWeibo("742430951", "a6def1108b3ac4d186157b6ec1320204", "http://sns.whalecloud.com");//我申请
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");//友盟提供


        PlatformConfig.setQQZone("1107943046", "Uv77AP8JDiLfvT6J");//公司申请
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");//友盟提供
        PlatformConfig.setAlipay("2015111700822536");
    }
}
