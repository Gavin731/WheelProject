package com.rzm.socialsecurity.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener;
import com.github.gzuliyujiang.wheelview.widget.WheelView;
import com.rzm.socialsecurity.R;

import java.util.ArrayList;
import java.util.List;

public class DialogUtil {

    public static void showBottomWheelDialog(Context context, List<String> datas, SelectBottomCallback callback) {
        // 创建Dialog并设置样式
        final Dialog dialog = new Dialog(context, R.style.BottomDialog);
        View dialogView = View.inflate(context, R.layout.view_single_selected_xml, null);

        // 设置Dialog的窗口属性
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setWindowAnimations(R.style.BottomDialog_Animation);
        }

        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        // 初始化视图
        WheelView wheelView = dialogView.findViewById(R.id.wheel_view);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView tvConfirm = dialogView.findViewById(R.id.tv_confirm);
        // 设置数据
        wheelView.setData(datas);
        // 设置默认选中项
        wheelView.setDefaultPosition(0);

        // 监听滚轮变化
        wheelView.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onWheelScrolled(WheelView view, int offset) {

            }

            @Override
            public void onWheelSelected(WheelView view, int position) {

            }

            @Override
            public void onWheelScrollStateChanged(WheelView view, int state) {

            }

            @Override
            public void onWheelLoopFinished(WheelView view) {

            }
        });

        // 取消按钮点击事件
        tvCancel.setOnClickListener(v -> dialog.dismiss());

        // 确定按钮点击事件
        tvConfirm.setOnClickListener(v -> {
            int selectedIndex = wheelView.getCurrentPosition();
            String selectedItem = datas.get(selectedIndex);
            if (callback != null) {
                callback.onConfirm(selectedIndex, selectedItem);
            }
            dialog.dismiss();
        });

        // 显示Dialog
        dialog.show();
    }

    public interface SelectBottomCallback {
        void onConfirm(int position, String value);
    }
}
