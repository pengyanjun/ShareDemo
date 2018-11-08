package com.xy.bizport.share;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * GridView自适应Item高度,不出现滚动条
 */
public class ShareNoScroolGridView extends GridView {

	public ShareNoScroolGridView(Context context) {
		super(context);
	}

	public ShareNoScroolGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShareNoScroolGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 不出现滚动条
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}