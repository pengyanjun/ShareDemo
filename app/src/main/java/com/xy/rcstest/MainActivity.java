package com.xy.rcstest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.xy.bizport.share.lib.ShareConstant;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        MainActivityPermissionsDispatcher.showSTORAGEWithPermissionCheck(this);
        initView();
    }
    void initView(){
        findViewById(R.id.share_activity_btn).setOnClickListener(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showSTORAGE(){
        Toast.makeText(this, "访问文件权限已获取", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForSTORAGE(final PermissionRequest request){
        new AlertDialog.Builder(this)
                .setMessage("申请访问文件权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();//取消执行请求
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForSTORAGE() {
        Toast.makeText(this, "访问文件权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForSTORAGE() {
        Toast.makeText(this, "已拒绝访问文件权限，并不再询问", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.share_activity_btn:
                share();
                break;
        }

    }

    void share(){
        try{
            Bundle bundle = new Bundle();
            bundle.putString(ShareConstant.SHARE_TITLE, "title-特稿｜陆家嘴金融骗子和P2P“爆雷潮”，全是自作孽不可活！");
            bundle.putString(ShareConstant.SHARE_CONTENT, "content-位于上海浦东新区黄浦江畔的陆家嘴金融中心，是众多跨国银行的大中华区或东亚总部所在地，是中国最具影响力的金融中心之一。");
            bundle.putString(ShareConstant.SHARE_URL, "http://gz.feixin.10086.cn/I8LG36_eu2QVr");
            bundle.putString(ShareConstant.SHARE_IMAGE_URL, "http://pa.rcscdn.fetionpic.com//Public/Uploads/user/4/7/62/2/497834762/imgs/5b6015a52540d.jpg");
            Intent intent = new Intent("com.xy.bizport.ACTION_SHARE");
            intent.putExtras(bundle);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
