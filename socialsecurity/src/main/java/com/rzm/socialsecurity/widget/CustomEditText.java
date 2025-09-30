package com.rzm.socialsecurity.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);
    }
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 保留光标，但禁用选择
        setCursorVisible(true); // 重要：保留光标
        setTextIsSelectable(false);
        setLongClickable(false);

        // 禁用选择操作
        setCustomSelectionActionModeCallback(disabledActionModeCallback);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            setCustomInsertionActionModeCallback(disabledActionModeCallback);
        }
    }

    private final ActionMode.Callback disabledActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 处理触摸事件，防止长按显示选择手柄
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 清除任何可能的选择
            setSelection(getText().length());
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        // 如果检测到选择范围（selStart != selEnd），立即取消选择
        if (selStart != selEnd) {
            post(new Runnable() {
                @Override
                public void run() {
                    setSelection(getText().length());
                }
            });
        }
    }
}
