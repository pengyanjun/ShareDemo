package com.xy.bizport.demo.ipmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.umeng.message.lib.ShareTool;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class SharePopWindowActivity extends Activity implements View.OnClickListener, UMShareListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_pop_activity);
        findViewById(R.id.mianban_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.mianban_btn:
                try{
                    String sharetitle = "title-特稿｜陆家嘴金融骗子和P2P“爆雷潮”，全是自作孽不可活！";
                    String shareContent = "content-位于上海浦东新区黄浦江畔的陆家嘴金融中心，是众多跨国银行的大中华区或东亚总部所在地，是中国最具影响力的金融中心之一。";
                    String shareUrl = "http://gz.feixin.10086.cn/I8LG36_eu2QVr";
                    String image = "http://pa.rcscdn.fetionpic.com//Public/Uploads/user/4/7/62/2/497834762/imgs/5b6015a52540d.jpg";
                    ShareTool.getInstance().showSharePopupWindow(this, sharetitle, shareContent, shareUrl, image);
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
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
        Toast.makeText(this,"成功了", Toast.LENGTH_LONG).show();
    }

    /**
     * @descrption 分享失败的回调
     * @param platform 平台类型
     * @param t 错误原因
     */
    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        Toast.makeText(this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
    }

    /**
     * @descrption 分享取消的回调
     * @param platform 平台类型
     */
    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Toast.makeText(this,"取消了",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        ShareTool.getInstance().clearCache();
    }
}
