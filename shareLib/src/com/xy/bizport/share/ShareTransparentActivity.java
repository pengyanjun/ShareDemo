package com.xy.bizport.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.xy.bizport.share.net.NetStateUtils;
import com.xy.bizport.share.recyclerview.RecyclerViewAdapter;
import com.xy.bizport.share.recyclerview.SpacingItemDecoration;
import com.xy.bizport.share.utils.CheckInstallShareApp;
import com.xy.bizport.share.utils.LogManager;

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
    /**
     * 分享面板实现方式：1:GridView;2:RecyclerView
     */
    private int tag = 1;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private int columns = 4;//列数
    private ShareBean shareBean = null;


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
        shareGridView = (ShareNoScroolGridView)this.findViewById(R.id.share_gv);
        if (tag ==1){
            shareGridView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            sharePopAdapter = new SharePopAdapter(null, this);
            shareGridView.setAdapter(sharePopAdapter);
            shareGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dealShareClick(position);
                }
            });
        }else if (tag ==2){
            shareGridView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);

            mRecyclerViewAdapter = new RecyclerViewAdapter(null, this);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
            mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    dealShareClick(position);
                }
            });
        }


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
            if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0){
                Toast.makeText(this,"分享数据为空",Toast.LENGTH_SHORT).show();
                return;
            }
            final SnsPlatform platform = shareBean.getPlatforms().get(position);

            if (platform.mPlatform == null){
                Toast.makeText(this,"分享平台为空",Toast.LENGTH_SHORT).show();
                return;
            }
            LogManager.e("pyj", "---------------------------------------------------------------------------------"
                    + "\n"
                    + "---------------------------------------------------------------------------------"+ "\n"
                    + "---------------------------------------------------------------------------------");
            LogManager.e("pyj", "ShareActivity dealShareClick sharetitle = "+shareBean.getSharetitle()
                    + ","+ "\n"+" shareContent = " + shareBean.getShareContent()
                    + ","+ "\n"+" shareUrl = " + shareBean.getShareUrl()
                    + ","+ "\n"+" shareImageUrl = " + shareBean.getImageUrl()
                    + ","+ "\n"+" shareUMImage = " + shareBean.getUmImage());
            if (shareBean.getUmImage() != null){
                LogManager.e("pyj", "ShareActivity dealShareClick UmImage不为空 share");
                share(platform, shareBean.getUmImage());

            }else if (shareBean.getUmImage() == null && !TextUtils.isEmpty(shareBean.getImageUrl())){
                LogManager.e("pyj", "ShareActivity dealShareClick UmImage为空， getImageBitmap");
                ShareTool.getInstance().getImageBitmap(this, shareBean.getImageUrl(), shareBean, 100, 100, new IDownloadBitmapCallBack() {
                    @Override
                    public void downloadBitmapCallBack(int code, byte[] bytes, String message) {
                        if (code == 0){
                            //下载成功
                            LogManager.e("pyj", "ShareActivity dealShareClick downloadBitmapCallBack 下载成功");
                            shareBean.setUmImage(new UMImage(ShareTransparentActivity.this, bytes));
                            share(platform, shareBean.getUmImage());
                        }else {
                            //下载失败
                            Toast.makeText(ShareTransparentActivity.this,"分享图片下载失败，请稍后重试",Toast.LENGTH_SHORT).show();
                            LogManager.e("pyj", "ShareActivity dealShareClick downloadBitmapCallBack 下载失败 message = "+message);
                        }

                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void share(SnsPlatform platform, UMImage umImage){
        LogManager.e("pyj", "ShareActivity share");
        share_popwindow_layout.setVisibility(View.GONE);
        if (SHARE_MEDIA.WEIXIN.equals(platform.mPlatform) || SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform.mPlatform)){
            isNeedClose = true;
        }else {
            isNeedClose = false;
        }
        ShareTool.getInstance().share(this, platform.mPlatform,shareBean.getShareUrl(),
                shareBean.getSharetitle(), umImage,shareBean.getShareContent(), this);
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

            shareBean = ShareTool.getInstance().getShareData(this, sharetitle, shareContent, shareUrl, imageUrl);

            if (tag ==1){
                sharePopAdapter.notifyDataChage(shareBean);
            }else if (tag ==2){
                setItemPadding(shareBean.getPlatforms().size());
                mRecyclerViewAdapter.notifyDataChage(shareBean);
            }

        }catch (Exception e){

        }
    }

    private void setItemPadding(int size){
//        int grid_item_padding_left = getResources().getDimensionPixelSize(R.dimen.grid_item_padding_left);
//        int grid_item_padding_top = getResources().getDimensionPixelSize(R.dimen.grid_item_padding_top);
//
//        int grid_padding_left = getResources().getDimensionPixelSize(R.dimen.grid_padding_left);
//        int grid_padding_top = getResources().getDimensionPixelSize(R.dimen.grid_padding_top);
//        int grid_padding_right = getResources().getDimensionPixelSize(R.dimen.grid_padding_right);
//        int grid_padding_bottom = getResources().getDimensionPixelSize(R.dimen.grid_padding_bottom);
//
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(size, columns, grid_item_padding_left, grid_item_padding_top, grid_padding_left, grid_padding_top, grid_padding_right, grid_padding_bottom));

        int grid_item_padding_left = getResources().getDimensionPixelSize(R.dimen.grid_item_padding_left);
        int grid_padding_left = getResources().getDimensionPixelSize(R.dimen.grid_padding_left);
        int grid_padding_top = getResources().getDimensionPixelSize(R.dimen.grid_padding_top);
        int grid_padding_right = getResources().getDimensionPixelSize(R.dimen.grid_padding_right);
        int grid_padding_bottom = getResources().getDimensionPixelSize(R.dimen.grid_padding_bottom);
        mRecyclerView.addItemDecoration(new SpacingItemDecoration(size,grid_item_padding_left, grid_padding_left,grid_padding_top,  grid_padding_right, grid_padding_bottom));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
        if (!SHARE_MEDIA.WEIXIN.equals(platform) && !SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform)){
            //微信分享取消分享之后的回调仍是分享成功，斌哥说微信分享干脆不弹提示了
            Toast.makeText(this,"成功了", Toast.LENGTH_LONG).show();
        }
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
//        Toast.makeText(this,"取消了",Toast.LENGTH_LONG).show();
//        Intent data=new Intent();
//        data.putExtra("result", "取消了");
//        setResult(MainActivity.SHARE_RESULT_CODE, data);
        close();

    }
}
