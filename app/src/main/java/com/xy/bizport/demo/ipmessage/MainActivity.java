package com.xy.bizport.demo.ipmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


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
                //在当前页面打开分享透明activity
                startActivityForResult(new Intent(this,ShareTransparentActivity.class), SHARE_REQUEST_CODE);
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
