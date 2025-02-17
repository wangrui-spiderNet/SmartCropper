package me.pqpo.smartcropper.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;


@SuppressLint("NewApi")
public class TagContainerLayout extends RelativeLayout implements OnTouchListener {
    private static final int CLICKRANGE = 5;
    int startX = 0;
    int startY = 0;
    int startTouchViewLeft = 0;
    int startTouchViewTop = 0;
    private View touchView, clickView;

    public TagContainerLayout(Context context) {
        super(context, null);
    }

    public TagContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchView = null;
                if (clickView != null) {
                    clickView = null;
                }
                startX = (int) event.getX();
                startY = (int) event.getY();
                if (hasView(startX, startY)) {
                    startTouchViewLeft = touchView.getLeft();
                    startTouchViewTop = touchView.getTop();
                } else {
//                    addItem(startX, startY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveView((int) event.getX(),
                        (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getX();
                int endY = (int) event.getY();
                //如果挪动的范围很小，则判定为单击
                if (touchView != null && Math.abs(endX - startX) < CLICKRANGE && Math.abs(endY - startY) < CLICKRANGE) {
                    clickView = touchView;
                }
                touchView = null;
                break;
        }
        return true;
    }

    public void addItem(int x, int y) {
        View view = null;
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = x - SquareTagView.getViewWidth();
        view = new SquareTagView(getContext());

        params.topMargin = y;
        //上下位置在视图内
        if (params.topMargin < 0) params.topMargin = 0;
        else if ((params.topMargin + SquareTagView.getViewHeight()) > getHeight())
            params.topMargin = getHeight() - SquareTagView.getViewHeight();


        this.addView(view, params);
    }

    private void moveView(int x, int y) {
        if (touchView == null) return;
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = x - startX + startTouchViewLeft;
        params.topMargin = y - startY + startTouchViewTop;
        //限制子控件移动必须在视图范围内
        if (params.leftMargin < 0 || (params.leftMargin + touchView.getWidth()) > getWidth())
            params.leftMargin = touchView.getLeft();
        if (params.topMargin < 0 || (params.topMargin + touchView.getHeight()) > getHeight())
            params.topMargin = touchView.getTop();
        touchView.setLayoutParams(params);
    }

    private boolean hasView(int x, int y) {
        //循环获取子view，判断xy是否在子view上，即判断是否按住了子view
        for (int index = 0; index < this.getChildCount(); index++) {
            View view = this.getChildAt(index);
            int left = (int) view.getX();
            int top = (int) view.getY();
            int right = view.getRight();
            int bottom = view.getBottom();
            Rect rect = new Rect(left, top, right, bottom);
            boolean contains = rect.contains(x, y);
            //如果是与子view重叠则返回真,表示已经有了view不需要添加新view了
            if (contains) {
                touchView = view;
                touchView.bringToFront();
                return true;
            }
        }
        touchView = null;
        return false;
    }
}
