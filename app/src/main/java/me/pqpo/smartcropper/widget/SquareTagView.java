package me.pqpo.smartcropper.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import me.pqpo.smartcropper.R;

public class SquareTagView extends RelativeLayout {

    private Context context;
    private View loTag;

    private static final int ViewWidth = 50;
    private static final int ViewHeight = 50;

    public SquareTagView(Context context) {
        super(context);
        this.context = context;
        initViews();
        init();
    }

    /**
     * 初始化视图
     **/
    protected void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.picturetagview, this, true);
        loTag = view.findViewById(R.id.loTag);
    }

    /**
     * 初始化
     **/
    protected void init() {
//        directionChange();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View parent = (View) getParent();
        int halfParentW = (int) (parent.getWidth() * 0.5);
        int center = (int) (l + (this.getWidth() * 0.5));
//        directionChange();
    }

    private void directionChange() {
        loTag.setBackgroundResource(R.drawable.shape_square);
    }

    public static int getViewWidth() {
        return ViewWidth;
    }

    public static int getViewHeight() {
        return ViewHeight;
    }
}
