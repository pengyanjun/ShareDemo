package com.xy.bizport.rcs.ui.share.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_COLUMN = Integer.MAX_VALUE;
    private int horizontalSpace;
    private int verticalSpace;
    private int column;//列数
    private float avg;//每个item水平方向之间的平均间距,对于 column = n 的grid，假设两个item间距是M：水平方向的内边距为：(n-1)*M,平均到每一个item后，平均是：A=(n-1)*M/n

    public GridLayoutItemDecoration(int horizontalSpace, int verticalSpace, int column) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.column = column;
        avg = (column - 1) * horizontalSpace * 1.0f / column;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        /*
         * 除了第一行，每一行的每个item，距离顶部的item距离值space
         * 水平间距略复杂：对于grid方式垂直滚动的recyclerview，假设有4列
         * 排列如下：
         * ———————————————————————————————————————————
         * |    1    | |   2   | |   3    | |   4    |
         * |    5    | |   6   | |   7    | |   8    |
         * -------------------------------------------
         * 如果给不是第一列的设置左边距，第一列的会比其他item宽一个边距
         * 如果给不是最后一列的设置右边距，同理，最后一列比其他item宽一个边距
         * 也就是说，边距也是item的一部分，所以，这种方法，会导致四个item 不一样宽
         *
         * 下面的方法，通过算法，让每一列的所有item平均分担边距
         * 对于 column = n 的grid，假设两个item间距是M：水平方向的内边距为：(n-1)*M
         * 平均到每一个item后，平均是：A=(n-1)*M/n
         * 则有如下规律：
         * 1、第一列item的左边距为零，所以右边距只能是A
         * 2、相邻左itemL，和右itemR，的左右边距和等于一个间距M
         * 类推：
         *      L       R
         *   0  0       A
         *   1  M-A     A-(M-A)
         *   2  2(M-A)  A-2(M-A)
         *   3  3(M-A)  A-3(M-A)
         *   ...
         *   n  n(M-A)  A-n(M-A)
         *   n<=column
         */
        outRect.top = verticalSpace;
        int pos = parent.getChildLayoutPosition(view);
        if (isFirstRow(pos)) {
            outRect.top = 0;
        }
        if (column != DEFAULT_COLUMN) {
            outRect.left = (int) (pos%column * (horizontalSpace - avg));
            outRect.right = (int) (avg - (pos%column * (horizontalSpace - avg)));
        }
    }

    boolean isFirstRow(int pos) {
        return pos < column;
    }

}
