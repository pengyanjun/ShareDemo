package com.xy.bizport.share.lib;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class ShareBaseActivity extends Activity implements UMShareListener {

    //是否需手动关闭界面；目前发现进入微信，朋友圈，支付宝解锁界面，按返回键返回demo时有透明阴影，透明Activity没有销毁。微信，朋友圈，支付宝需要手动关闭
    public boolean isNeedClose = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    public void close(){
        try{
            isNeedClose = false;
            finish();
            overridePendingTransition(0,0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @descrption 分享开始的回调
     * @param platform 平台类型
     */
    @Override
    public void onStart(SHARE_MEDIA platform) {

    }

    /**
     * @descrption 分享成功的回调
     * @param platform 平台类型
     */
    @Override
    public void onResult(SHARE_MEDIA platform) {
        if (!SHARE_MEDIA.WEIXIN.equals(platform) && !SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform)){
            //微信分享取消分享之后的回调仍是分享成功，斌哥说微信分享干脆不弹提示了
            Toast.makeText(this,"成功了", Toast.LENGTH_LONG).show();
        }
        close();
    }

    /**
     * @descrption 分享失败的回调
     * @param platform 平台类型
     * @param t 错误原因
     */
    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        Toast.makeText(this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        close();
    }

    /**
     * @descrption 分享取消的回调
     * @param platform 平台类型
     */
    @Override
    public void onCancel(SHARE_MEDIA platform) {
        close();
    }
}
