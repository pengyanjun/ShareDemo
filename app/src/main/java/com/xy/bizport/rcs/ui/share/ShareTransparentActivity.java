package com.xy.bizport.rcs.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xy.bizport.androidcommon.util.LogManager;
import com.xy.bizport.rcs.ui.share.recyclerview.GridLayoutItemDecoration;
import com.xy.bizport.rcs.ui.share.recyclerview.RecyclerViewAdapter;
import com.xy.bizport.rcs.ui.share.recyclerview.LinearLayoutItemDecoration;
import com.xy.bizport.share.lib.BizportShare;
import com.xy.bizport.share.lib.CheckInstallShareApp;
import com.xy.bizport.share.lib.IDownloadBitmapCallBack;
import com.xy.bizport.share.lib.ShareBaseActivity;
import com.xy.bizport.share.lib.ShareBean;
import com.xy.bizport.share.lib.ShareConstant;
import com.xy.bizport.share.lib.ShareImage;
import com.xy.bizport.share.lib.ShareMedia;
import com.xy.bizport.share.lib.SharePlatform;
import com.xy.bizport.share.lib.ShareTool;
import com.xy.rcstest.R;

public class ShareTransparentActivity extends ShareBaseActivity {

    private TextView cancelBtn;
    private String sharetitle;
    private String shareContent;
    private String shareUrl;
    private String imageUrl;
    private LinearLayout share_popwindow_layout;


    /**
     * 分享面板呈现方式：1:GridView方式排列;2:水平单行排列
     */
    private int type = 1;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ShareBean shareBean = null;

    private int columns = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CheckInstallShareApp.canShare(this)){
            Toast.makeText(this,"您的手机没有安装支持分享的应用哦",Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(0,0);
        }
        setContentView(R.layout.share_transparent_activity);
        initView();
        initData();
    }

    private void initView(){
        mRecyclerView = (RecyclerView)this.findViewById(R.id.share_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewAdapter = new RecyclerViewAdapter(null, this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                shareItemClick(position);
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

    private void initData(){
        try{
            Intent intent = this.getIntent();
            if (intent == null || intent.getExtras() == null) {
                return;
            }
            Bundle bundle = intent.getExtras();
            sharetitle = bundle.getString(ShareConstant.SHARE_TITLE);
            shareContent = bundle.getString(ShareConstant.SHARE_CONTENT);
            shareUrl = bundle.getString(ShareConstant.SHARE_URL);
            imageUrl = bundle.getString(ShareConstant.SHARE_IMAGE_URL);

            shareBean = BizportShare.getInstance().getShareData(this, sharetitle, shareContent, shareUrl, imageUrl);

            setRecyclerViewStyle(shareBean.getPlatforms().size());
            mRecyclerViewAdapter.notifyDataChage(shareBean);

        }catch (Exception e){

        }
    }


    private void setRecyclerViewStyle(int size){
        int grid_item_horizontal_space = getResources().getDimensionPixelSize(R.dimen.grid_item_horizontal_space);
        int grid_item_vertical_space = getResources().getDimensionPixelSize(R.dimen.grid_item_vertical_space);

        int grid_padding_left = getResources().getDimensionPixelSize(R.dimen.grid_padding_left);
        int grid_padding_top = getResources().getDimensionPixelSize(R.dimen.grid_padding_top);
        int grid_padding_right = getResources().getDimensionPixelSize(R.dimen.grid_padding_right);
        int grid_padding_bottom = getResources().getDimensionPixelSize(R.dimen.grid_padding_bottom);

        if (type == 1 ){
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, columns));

            mRecyclerView.addItemDecoration(new GridLayoutItemDecoration(grid_item_horizontal_space, grid_item_vertical_space, columns));
            mRecyclerView.setPadding(grid_padding_left, grid_padding_top, grid_padding_right, grid_padding_bottom);
        }else if (type == 2 ){
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);

            mRecyclerView.addItemDecoration(new LinearLayoutItemDecoration(size,grid_item_horizontal_space, grid_padding_left, grid_padding_right));
            mRecyclerView.setPadding(0, grid_padding_top, 0, grid_padding_bottom);
        }
    }


    public void shareItemClick(int position) {
        try{
            if (!ShareTool.isNetworkConnected(this)){
                Toast.makeText(this, ShareTool.getString(this, R.string.share_network_disabled),Toast.LENGTH_SHORT).show();
                return;
            }
            if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0){
                Toast.makeText(this,"分享数据为空",Toast.LENGTH_SHORT).show();
                return;
            }
            final SharePlatform platform = shareBean.getPlatforms().get(position);

            if (platform.shareMedia == null){
                Toast.makeText(this,"分享平台为空",Toast.LENGTH_SHORT).show();
                return;
            }
            LogManager.e("pyj", "---------------------------------------------------------------------------------"
                    + "\n"
                    + "---------------------------------------------------------------------------------"+ "\n"
                    + "---------------------------------------------------------------------------------");
            LogManager.e("pyj", "ShareTransparentActivity shareItemClick sharetitle = "+shareBean.getSharetitle()
                    + ","+ "\n"+" shareContent = " + shareBean.getShareContent()
                    + ","+ "\n"+" shareUrl = " + shareBean.getShareUrl()
                    + ","+ "\n"+" shareImageUrl = " + shareBean.getImageUrl()
                    + ","+ "\n"+" shareUMImage = " + shareBean.getUmImage());
            if (shareBean.getUmImage() != null){
                LogManager.e("pyj", "ShareTransparentActivity shareItemClick UmImage不为空 share");
                share(platform, shareBean.getUmImage());

            }else if (shareBean.getUmImage() == null && !TextUtils.isEmpty(shareBean.getImageUrl())){
                LogManager.e("pyj", "ShareTransparentActivity shareItemClick UmImage为空， getImageBitmap");
                BizportShare.getInstance().getImageBitmap(this, shareBean.getImageUrl(), shareBean, 100, 100, new IDownloadBitmapCallBack() {
                    @Override
                    public void downloadBitmapCallBack(int code, byte[] bytes, String message) {
                        if (code == 0){
                            //下载成功
                            LogManager.e("pyj", "ShareTransparentActivity shareItemClick downloadBitmapCallBack 下载成功");
                            shareBean.setUmImage(new ShareImage(ShareTransparentActivity.this, bytes));
                            share(platform, shareBean.getUmImage());
                        }else {
                            //下载失败
                            Toast.makeText(ShareTransparentActivity.this,"分享图片下载失败，请稍后重试",Toast.LENGTH_SHORT).show();
                            LogManager.e("pyj", "ShareTransparentActivity shareItemClick downloadBitmapCallBack 下载失败 message = "+message);
                        }

                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void share(SharePlatform platform, ShareImage umImage){
        LogManager.e("pyj", "ShareTransparentActivity share");
        share_popwindow_layout.setVisibility(View.GONE);
        if (ShareMedia.WEIXIN.equals(platform.shareMedia) || ShareMedia.WEIXIN_CIRCLE.equals(platform.shareMedia)){
            isNeedClose = true;
        }else {
            isNeedClose = false;
        }
        BizportShare.getInstance().share(this, platform.shareMedia,shareBean.getShareUrl(),
                shareBean.getSharetitle(), umImage,shareBean.getShareContent(), this);
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
    protected void onRestart() {
        super.onRestart();
        if (isNeedClose){
            close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
