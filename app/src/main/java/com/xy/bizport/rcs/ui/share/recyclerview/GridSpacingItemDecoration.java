package com.xy.bizport.rcs.ui.share.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int columns;//列数
    private int size;//item size
    private int rows;//item rows
    private int grid_item_padding_left;
    private int grid_item_padding_top;

    private int grid_padding_left;
    private int grid_padding_top;
    private int grid_padding_right;
    private int grid_padding_bottom;

    public GridSpacingItemDecoration(int size,int columns, int grid_item_padding_left, int grid_item_padding_top
            , int grid_padding_left, int grid_padding_top, int grid_padding_right, int grid_padding_bottom) {
        this.size = size;
        this.columns = columns;
        if (size % columns == 0){
            this.rows = size / columns;
        }else {
            this.rows = size / columns + 1;
        }


        this.grid_item_padding_left = grid_item_padding_left;
        this.grid_item_padding_top = grid_item_padding_top;

        this.grid_padding_left = grid_padding_left;
        this.grid_padding_top = grid_padding_top;
        this.grid_padding_right = grid_padding_right;
        this.grid_padding_bottom = grid_padding_bottom;


    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % columns; // item column
        int row = position / columns; // item row

        if (row == 0) {
            outRect.top = grid_padding_top;
        }else {
            outRect.top = grid_item_padding_top;
        }

        if(column == 0){
            outRect.left = grid_padding_left;
        }else {
            outRect.left = grid_item_padding_left;
        }

        if (column == (columns - 1)){
            outRect.right = grid_padding_right;
        }

        if(row == (rows - 1)){
            outRect.bottom = grid_padding_bottom;
        }

    }
}