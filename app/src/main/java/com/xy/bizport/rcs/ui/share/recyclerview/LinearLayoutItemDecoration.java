package com.xy.bizport.rcs.ui.share.recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {
    private int grid_item_horizontal_space;

    private int grid_padding_left;
    private int grid_padding_right;
    private int size;

    public LinearLayoutItemDecoration(int size, int grid_item_horizontal_space, int grid_padding_left, int grid_padding_right) {
        this.size = size;
        this.grid_item_horizontal_space = grid_item_horizontal_space;

        this.grid_padding_left = grid_padding_left;
        this.grid_padding_right = grid_padding_right;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
            int position = parent.getChildAdapterPosition(view); // item position
            if (position == 0){
                outRect.left = grid_padding_left;
            }else {
                outRect.left = grid_item_horizontal_space;
            }

            if (position == (size - 1)){
                outRect.right = grid_padding_right;
            }
        }
    }
}