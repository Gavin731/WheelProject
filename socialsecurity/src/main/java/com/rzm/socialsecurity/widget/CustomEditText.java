package com.rzm.socialsecurity.widget;

import android.content.Context;
import android.os.Build;
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
        // 确保光标可见
        setCursorVisible(true);

        // 禁用选择功能
        setTextIsSelectable(false);
        setLongClickable(false);

        // 禁用选择操作模式
        setCustomSelectionActionModeCallback(disabledActionModeCallback);

        // 高版本API处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        // 防止出现选择范围
        if (selStart != selEnd) {
            setSelection(getText().length());
        }
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        // 拦截文本操作菜单
        if (id == android.R.id.selectAll ||
                id == android.R.id.copy ||
                id == android.R.id.cut) {
            return true; // 消费事件，不执行操作
        }
        return super.onTextContextMenuItem(id);
    }
}
