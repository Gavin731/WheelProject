package com.common.wheel.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.wheel.R;


public class ToastUtil {

    public static void showToast(Activity context, String message) {
        // 创建一个自定义的布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.view_toast, context.findViewById(R.id.toast_root));

        // 设置你的文本或其他元素
        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        // 创建 Toast 对象
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, -400); // 设置居中显示
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout); // 设置自定义视图
        toast.show();
    }
}
