package com.common.wheelproject.test.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.common.wheelproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/12/11     zenglinggui       v1.0.0        create
 **/
public class CommonStyleHolder extends BaseViewHolder {

    @BindView(R.id.tv_item)
    public TextView textView;

    public CommonStyleHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
