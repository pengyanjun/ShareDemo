package com.umeng.soexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.umeng.message.lib.ShareBean;
import com.umeng.message.lib.SharePopupWindow;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
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
        switch(view.getId()){
            case R.id.mianban_btn:
                if (sharePopupWindow == null){
                    sharePopupWindow = new SharePopupWindow(this, shareListener);
                }
                List<ShareBean> shareBeans = getShareData();

                sharePopupWindow.setShareMediaList(shareBeans);

                sharePopupWindow.showAtLocation(this.findViewById(R.id.share_activity_root_ll), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }

    }

    private List<ShareBean> getShareData(){
        List<ShareBean> shareBeans = new ArrayList<>();

        //分享的图片，UMImage的构建有如下几种形式
        //UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
        //UMImage image = new UMImage(ShareActivity.this, file);//本地文件
        //UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
        //UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
        //UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流
        UMImage umImage = new UMImage(this, R.drawable.ic_launcher);//分享的图片
        String sharetitle ="This is title";//标题
        String shareContent = "This is content";//分享的文本内容
        String shareUrl = "http://mobile.umeng.com/social";//分享的链接

        ShareBean shareBean = new ShareBean(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform(), shareUrl, sharetitle, shareContent, umImage);
        shareBeans.add(shareBean);

        shareBean = new ShareBean(SHARE_MEDIA.WEIXIN.toSnsPlatform(), shareUrl, sharetitle, shareContent, umImage);
        shareBeans.add(shareBean);

        shareBean = new ShareBean(SHARE_MEDIA.QQ.toSnsPlatform(), shareUrl, sharetitle, shareContent, umImage);
        shareBeans.add(shareBean);

        shareBean = new ShareBean(SHARE_MEDIA.QZONE.toSnsPlatform(), shareUrl, sharetitle, shareContent, umImage);
        shareBeans.add(shareBean);

        shareBean = new ShareBean(SHARE_MEDIA.SINA.toSnsPlatform(), shareUrl, sharetitle, shareContent, umImage);
        shareBeans.add(shareBean);

        shareBean = new ShareBean(SHARE_MEDIA.ALIPAY.toSnsPlatform(), shareUrl, sharetitle, shareContent, umImage);
        shareBeans.add(shareBean);

        return shareBeans;
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
