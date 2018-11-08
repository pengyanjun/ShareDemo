package com.xy.bizport.demo.ipmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xy.bizport.share.ShareTool;


public class MainActivity extends Activity implements View.OnClickListener{

    public static final int SHARE_REQUEST_CODE = 1001;
    public static final int SHARE_RESULT_CODE = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViewById(R.id.pop_btn).setOnClickListener(this);
        findViewById(R.id.share_activity_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.pop_btn:
                startActivity(new Intent(this,SharePopWindowActivity.class));
                break;
            case R.id.share_activity_btn:
                try{
                    //在当前页面打开分享透明activity
                    Bundle bundle = new Bundle();
                    bundle.putString(ShareTool.SHARE_TITLE, "title-特稿｜陆家嘴金融骗子和P2P“爆雷潮”，全是自作孽不可活！");
                    bundle.putString(ShareTool.SHARE_CONTENT, "content-位于上海浦东新区黄浦江畔的陆家嘴金融中心，是众多跨国银行的大中华区或东亚总部所在地，是中国最具影响力的金融中心之一。");
                    bundle.putString(ShareTool.SHARE_URL, "http://gz.feixin.10086.cn/I8LG36_eu2QVr");
                    bundle.putString(ShareTool.SHARE_IMAGE_URL, "http://pa.rcscdn.fetionpic.com//Public/Uploads/user/4/7/62/2/497834762/imgs/5b6015a52540d.jpg");
                    Intent intent = new Intent("com.xy.bizport.share.ACTION_SHARE");
                    intent.putExtras(bundle);
                    startActivity(intent);
//                startActivityForResult(intent, SHARE_REQUEST_CODE);

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case SHARE_RESULT_CODE:
                String result = data.getExtras().getString("result");
                Toast.makeText(this,result, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
