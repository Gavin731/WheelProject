package com.rzm.socialsecurity.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rzm.socialsecurity.R;

import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class HomeTabItemView extends BaseTabItem {

    Context context;
    ImageView ivIcon;
    TextView tvTitle;
    Drawable mCheckedDrawable, mDefaultDrawable;

    public HomeTabItemView(@NonNull Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_home_tab, this, true);
        ivIcon = findViewById(R.id.iv_icon);
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            ivIcon.setImageDrawable(mCheckedDrawable);
            tvTitle.setTextColor(context.getColor(R.color.color_407AEC));
        } else {
            ivIcon.setImageDrawable(mDefaultDrawable);
            tvTitle.setTextColor(context.getColor(R.color.color_999999));
        }
    }

    @Override
    public void setMessageNumber(int number) {

    }

    @Override
    public void setHasMessage(boolean hasMessage) {

    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setDefaultDrawable(Drawable drawable) {
        this.mDefaultDrawable = drawable;
    }

    @Override
    public void setSelectedDrawable(Drawable drawable) {
        this.mCheckedDrawable = drawable;
    }

    @Override
    public String getTitle() {
        return "";
    }
}
