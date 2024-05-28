package com.common.wheelproject.test.activity;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.common.wheelproject.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/12/4     zenglinggui       v1.0.0        create
 **/
public class CardViewActivity extends com.common.base.base.BaseActivity {

    @BindView(R.id.cv_shadow)
    CardView cardView;
    @BindView(R.id.pv_content)
    PhotoView photoView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load("http://a3.topitme.com/2/19/82/1127998324d2682192o.jpg").into(photoView);
    }
}
