package com.umeng.soexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.List;

public class ShareTestActivity extends Activity implements View.OnClickListener{

    SharePopupWindow sharePopupWindow = null;
    private UMShareListener shareListener = new UMShareListener() {
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
            Toast.makeText(ShareTestActivity.this,"成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareTestActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareTestActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_test_activity);
        findViewById(R.id.mianban_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        UMWeb web = new UMWeb("http://mobile.umeng.com/social");
//        web.setTitle("This is music title");//标题
//        web.setThumb(new UMImage(this, R.drawable.ic_launcher)); //缩略图
//        web.setDescription("my description");//描述

        switch(view.getId()){
            case R.id.mianban_btn:
//                new ShareAction(this).withMedia(web)
//                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
//                        .setCallback(shareListener).open();
                if (sharePopupWindow == null){
                    sharePopupWindow = new SharePopupWindow(this, shareListener);
                }
                List<SnsPlatform> platforms = new ArrayList<>();
                platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
                platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
                platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());
                platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
                platforms.add(SHARE_MEDIA.SINA.toSnsPlatform());
                sharePopupWindow.setShareMediaList(platforms);
                sharePopupWindow.showAtLocation(this.findViewById(R.id.share_activity_root_ll), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }

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
    }
}
