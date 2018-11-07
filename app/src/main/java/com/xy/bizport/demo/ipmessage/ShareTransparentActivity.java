package com.xy.bizport.demo.ipmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.umeng.message.lib.ShareBean;
import com.umeng.message.lib.ShareNoScroolGridView;
import com.umeng.message.lib.SharePopAdapter;
import com.umeng.message.lib.ShareTool;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import org.json.JSONObject;

public class ShareTransparentActivity extends Activity implements UMShareListener{
    private TextView cancelBtn;
    private ShareNoScroolGridView shareGridView;
    private SharePopAdapter sharePopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_transparent_activity);
        initView();
        initData();
    }

    private void initView(){
        shareGridView = (ShareNoScroolGridView)this.findViewById(com.umeng.message.lib.R.id.share_gv);
        sharePopAdapter = new SharePopAdapter(null, this);
        shareGridView.setAdapter(sharePopAdapter);
        shareGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    ShareBean shareBean = sharePopAdapter.getShareBean();
                    if (shareBean == null || shareBean.getPlatforms() == null || shareBean.getPlatforms().size() == 0){
                        return;
                    }
                    SnsPlatform platform = (SnsPlatform)sharePopAdapter.getItem(position);

                    if (platform.mPlatform == null){
                        return;
                    }
                    ShareTool.getInstance().share(ShareTransparentActivity.this, platform.mPlatform,shareBean.getShareUrl(),
                            shareBean.getSharetitle(), shareBean.getUmImage(),shareBean.getShareContent(), ShareTransparentActivity.this);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        cancelBtn = (TextView) this.findViewById(com.umeng.message.lib.R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    private void initData(){
        try{
            JSONObject shareData = new JSONObject();
            shareData.put(ShareTool.SHARE_TITLE, "title-特稿｜陆家嘴金融骗子和P2P“爆雷潮”，全是自作孽不可活！");
            shareData.put(ShareTool.SHARE_CONTENT, "content-位于上海浦东新区黄浦江畔的陆家嘴金融中心，是众多跨国银行的大中华区或东亚总部所在地，是中国最具影响力的金融中心之一。");
            shareData.put(ShareTool.SHARE_URL, "http://gz.feixin.10086.cn/I8LG36_eu2QVr");
            shareData.put(ShareTool.SHARE_IMAGE_URL, "http://pa.rcscdn.fetionpic.com//Public/Uploads/user/4/7/62/2/497834762/imgs/5b6015a52540d.jpg");
            sharePopAdapter.notifyDataChage(ShareTool.getInstance().getShareData(this, shareData));
        }catch (Exception e){

        }
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
                finish();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        ShareTool.getInstance().clearCache();
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
//        Toast.makeText(this,"成功了", Toast.LENGTH_LONG).show();
        Intent data=new Intent();
        data.putExtra("result", "成功了");
        setResult(MainActivity.SHARE_RESULT_CODE, data);
        finish();
    }

    /**
     * @descrption 分享失败的回调
     * @param platform 平台类型
     * @param t 错误原因
     */
    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
//        Toast.makeText(this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        Intent data=new Intent();
        data.putExtra("result", "失败"+t.getMessage());
        setResult(MainActivity.SHARE_RESULT_CODE, data);
        finish();
    }

    /**
     * @descrption 分享取消的回调
     * @param platform 平台类型
     */
    @Override
    public void onCancel(SHARE_MEDIA platform) {
//        Toast.makeText(this,"取消了",Toast.LENGTH_LONG).show();
        Intent data=new Intent();
        data.putExtra("result", "取消了");
        setResult(MainActivity.SHARE_RESULT_CODE, data);
        finish();

    }
}
