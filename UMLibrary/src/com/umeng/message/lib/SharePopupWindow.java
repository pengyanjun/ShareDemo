package com.umeng.message.lib;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.List;


public class SharePopupWindow extends PopupWindow {

	private View mMenuView;
	private Context mContext;
	private UMShareListener shareListener;
	private Button cancelBtn;
	private ShareNoScroolGridView shareGridView;
	private SharePopAdapter sharePopAdapter;


	/**
	 * 分享面板
	 * @param context 传Activity的Context
	 * @param shareListener 分享结果回调
	 */
	public SharePopupWindow(Context context, UMShareListener shareListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context ;
		this.shareListener = shareListener ;

        mMenuView = inflater.inflate(R.layout.share_popwindow, null);
        initView(mMenuView);
       
        //设置SelectPicPopupWindow的View  
        this.setContentView(mMenuView);  
        //设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        //设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.anim.share_pop_anim);
        //实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景  
        this.setBackgroundDrawable(dw);  
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        mMenuView.setOnTouchListener(new OnTouchListener() {
              
            public boolean onTouch(View v, MotionEvent event) {
                  
              int height = mMenuView.findViewById(R.id.share_content_layout).getTop();  
                int y=(int) event.getY();  
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){  
                        dismiss();  
                    }  
                }                 
                return true;  
            }  
        });  
  
    }

	public void setShareMediaList(List<ShareBean> shareMediaList) {
		if (sharePopAdapter != null){
			sharePopAdapter.notifyDataChage(shareMediaList);
		}
	}

	private void initView(View view){
		shareGridView = (ShareNoScroolGridView)mMenuView.findViewById(R.id.share_gv);
		sharePopAdapter = new SharePopAdapter(null, mContext);
		shareGridView.setAdapter(sharePopAdapter);
		shareGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try{
					ShareBean shareBean = (ShareBean)sharePopAdapter.getItem(position);
					if (shareBean == null || shareBean.getPlatform() == null){
						return;
					}
					SnsPlatform platform = shareBean.getPlatform();
					if (platform.mPlatform == null){
						return;
					}
					dismiss();
					ShareTool.getInstance(mContext).share(platform.mPlatform,shareBean.getShareUrl(),
							shareBean.getSharetitle(), shareBean.getImage(),shareBean.getShareContent(), shareListener);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});

		cancelBtn = (Button) mMenuView.findViewById(R.id.cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					dismiss();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		
	}

}
