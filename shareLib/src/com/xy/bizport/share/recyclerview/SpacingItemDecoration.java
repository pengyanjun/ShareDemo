package com.xy.bizport.share.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int item_padding_left;

    private int grid_padding_top;
    private int grid_padding_left;
    private int grid_padding_right;
    private int grid_padding_bottom;
    private int size;
    private int top;
    private int right;
    private int bottom;

    public SpacingItemDecoration(int size, int item_padding_left, int grid_padding_left, int grid_padding_top, int grid_padding_right, int grid_padding_bottom) {
        this.size = size;
        this.item_padding_left = item_padding_left;

        this.grid_padding_left = grid_padding_left;
        this.grid_padding_top = grid_padding_top;
        this.grid_padding_right = grid_padding_right;
        this.grid_padding_bottom = grid_padding_bottom;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        //竖直方向的
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            //最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.bottom = bottom;
            }
            outRect.top = top;
            outRect.left = item_padding_left;
            outRect.right = right;
        } else {
            int position = parent.getChildAdapterPosition(view); // item position
            if (position == 0){
                outRect.left = grid_padding_left;
            }else {
                outRect.left = item_padding_left;
            }

            if (position == (size - 1)){
                outRect.right = grid_padding_right;
            }

            outRect.top = grid_padding_top;
            outRect.bottom = grid_padding_bottom;

        }
    }
}