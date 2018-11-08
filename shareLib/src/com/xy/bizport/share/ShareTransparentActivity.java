package com.xy.bizport.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xy.bizport.share.net.NetStateUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;

public class ShareTransparentActivity extends Activity implements UMShareListener{
    private TextView cancelBtn;
    private ShareNoScroolGridView shareGridView;
    private SharePopAdapter sharePopAdapter;
    private String sharetitle;
    private String shareContent;
    private String shareUrl;
    private String imageUrl;
    private LinearLayout share_popwindow_layout;

    //是否需手动关闭界面；目前发现进入微信，朋友圈，支付宝解锁界面，按返回键返回demo时有透明阴影，透明Activity没有销毁。微信，朋友圈，支付宝需要手动关闭
    private boolean isNeedClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_transparent_activity);
        Log.e("pyj","onCreate");
        initView();
        initData();
    }

    private void initView(){
        shareGridView = (ShareNoScroolGridView)this.findViewById(R.id.share_gv);
        sharePopAdapter = new SharePopAdapter(null, this);
        shareGridView.setAdapter(sharePopAdapter);
        shareGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dealShareClick(position);
            }
        });
        cancelBtn = (TextView) this.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        share_popwindow_layout = (LinearLayout) this.findViewById(R.id.share_popwindow_layout);

    }

    private void dealShareClick(int position){
        try{
            //接入时，要判断网络，如果无网络则弹出提示
            if (!NetStateUtils.isNetworkConnected(this)){
                Toast.makeText(this, ShareTool.getString(this, R.string.share_network_disabled),Toast.LENGTH_SHORT).show();
                return;
            }
            ShareBean shareBean = sharePopAdapter.getShareBean();
            if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0){
                Toast.makeText(this,"分享数据为空",Toast.LENGTH_SHORT).show();
                return;
            }
            SnsPlatform platform = (SnsPlatform)sharePopAdapter.getItem(position);

            if (platform.mPlatform == null){
                Toast.makeText(this,"分享平台为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if (shareBean.getUmImage() == null && !TextUtils.isEmpty(shareBean.getImageUrl())){
                ShareTool.getInstance().getImageBitmap(this, shareBean.getImageUrl(), shareBean, 100, 100);
            }
            UMImage umImage = null;
            if (shareBean.getUmImage() == null){
                //设置默认分享的缩略图
                Log.e("pyj","dealShareClick 分享缩略图为空 shareBean.getImageUrl() = " + shareBean.getImageUrl());
                umImage = new UMImage(this, R.drawable.share_default_image);
            }else{
                umImage = shareBean.getUmImage();
            }

            share_popwindow_layout.setVisibility(View.GONE);
            if (SHARE_MEDIA.WEIXIN.equals(platform.mPlatform) || SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform.mPlatform)
                    || SHARE_MEDIA.ALIPAY.equals(platform.mPlatform)){
                isNeedClose = true;
            }else {
                isNeedClose = false;
            }
            ShareTool.getInstance().share(this, platform.mPlatform,shareBean.getShareUrl(),
                    shareBean.getSharetitle(), umImage,shareBean.getShareContent(), this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initData(){
        try{
            Intent intent = this.getIntent();
            if (intent == null || intent.getExtras() == null) {
                return;
            }
            Bundle bundle = intent.getExtras();
            sharetitle = bundle.getString(ShareTool.SHARE_TITLE);
            shareContent = bundle.getString(ShareTool.SHARE_CONTENT);
            shareUrl = bundle.getString(ShareTool.SHARE_URL);
            imageUrl = bundle.getString(ShareTool.SHARE_IMAGE_URL);
            sharePopAdapter.notifyDataChage(ShareTool.getInstance().getShareData(this, sharetitle, shareContent, shareUrl, imageUrl));
        }catch (Exception e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.e("pyj","onActivityResult");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                close();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            close();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        Log.e("pyj","onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("pyj","onResume isNeedClose = "+ isNeedClose);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pyj","onPause isNeedClose = "+ isNeedClose);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("pyj","onStop isNeedClose = "+ isNeedClose);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("pyj","onStart  isNeedClose = "+ isNeedClose);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("pyj","onRestart isNeedClose = "+ isNeedClose);
        if (isNeedClose){
            close();
        }
    }

    private void close(){
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
        Toast.makeText(this,"成功了", Toast.LENGTH_LONG).show();
//        Intent data=new Intent();
//        data.putExtra("result", "成功了");
//        setResult(MainActivity.SHARE_RESULT_CODE, data);
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
//        Intent data=new Intent();
//        data.putExtra("result", "失败"+t.getMessage());
//        setResult(MainActivity.SHARE_RESULT_CODE, data);
        close();
    }

    /**
     * @descrption 分享取消的回调
     * @param platform 平台类型
     */
    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Toast.makeText(this,"取消了",Toast.LENGTH_LONG).show();
//        Intent data=new Intent();
//        data.putExtra("result", "取消了");
//        setResult(MainActivity.SHARE_RESULT_CODE, data);
        close();

    }
}
