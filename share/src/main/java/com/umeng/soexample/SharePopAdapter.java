package com.umeng.soexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.List;

public class SharePopAdapter extends BaseAdapter {
	private List<SnsPlatform> shareMediaList;
	private Context mContext;
	private LayoutInflater mInflater;

	public SharePopAdapter(List<SnsPlatform> shareMediaList, Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		if (shareMediaList == null) {
			this.shareMediaList = new ArrayList<>();
		} else {
			this.shareMediaList = shareMediaList;
		}
	}

	public void notifyDataChage(List<SnsPlatform> shareMediaList) {
		if (shareMediaList == null) {
			this.shareMediaList = new ArrayList<>();
		} else {
			this.shareMediaList = shareMediaList;
		}
		this.notifyDataSetChanged();
	}

	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (shareMediaList == null) {
			return 0;
		}
		return shareMediaList.size();
	}

	@Override
	public Object getItem(int position) {
		return shareMediaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.share_popwindow_item, null);
			viewHolder.share_name_tv = (TextView) convertView.findViewById(R.id.share_name_tv);
			viewHolder.share_logo_iv = (ImageView) convertView.findViewById(R.id.share_logo_iv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			if (shareMediaList != null && shareMediaList.size() > 0) {
				SnsPlatform SnsPlatform = shareMediaList.get(position);
				if (SnsPlatform != null) {
					viewHolder.share_name_tv.setText(SnsPlatform.mShowWord);
					if (SHARE_MEDIA.WEIXIN_CIRCLE.equals(SnsPlatform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_wx_circle_logo));
					} else if (SHARE_MEDIA.WEIXIN.equals(SnsPlatform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_wx_logo));
					} else if (SHARE_MEDIA.QQ.equals(SnsPlatform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_qq_logo));
					} else if (SHARE_MEDIA.QZONE.equals(SnsPlatform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_qq_logo));
					} else if (SHARE_MEDIA.SINA.equals(SnsPlatform.mPlatform)){
						viewHolder.share_logo_iv.setImageDrawable(ShareTool.getDrawable(mContext, R.drawable.share_sina_logo));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		public ImageView share_logo_iv;
		public TextView share_name_tv;
	}
}