package com.rzm.socialsecurity.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.entity.MedicalCalculationEntity;

public class MedicalCalculationAdapter extends BaseQuickAdapter<MedicalCalculationEntity, BaseViewHolder> {

    public MedicalCalculationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MedicalCalculationEntity item) {
        ((TextView) helper.getView(R.id.tv_type)).setText(item.getType());
        ((TextView) helper.getView(R.id.tv_amount)).setText(item.getAmount());
        ((TextView) helper.getView(R.id.tv_rate)).setText(item.getRate());
    }
}
