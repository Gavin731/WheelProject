package com.rzm.socialsecurity.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.metrics.Event;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.common.wheel.admanager.InformationFlowAdCallback;
import com.common.wheel.admanager.RewardAdCallBack;
import com.common.wheel.mvp.MvpFragment;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.CalculateZXFJKCActivity;
import com.rzm.socialsecurity.activity.GSCalculateResultActivity;
import com.rzm.socialsecurity.activity.MainActivity;
import com.rzm.socialsecurity.activity.SBCalculateActivity;
import com.rzm.socialsecurity.activity.SBFunctionActivity;
import com.rzm.socialsecurity.activity.SBManageOrSuperviseActivity;
import com.rzm.socialsecurity.activity.TaxGuideActivity;
import com.rzm.socialsecurity.activity.YLBXCalculateActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.entity.ShowInfoAdEvent;
import com.rzm.socialsecurity.presenter.HomePresenter;
import com.rzm.socialsecurity.util.ADUtil;
import com.rzm.socialsecurity.view.IAView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public class HomeFragment extends MvpFragment<HomePresenter> implements IAView {

    private static final String ARG_C = "content";

    public TextView tvTabGszxjs, tvTabSbjnjs;
    public LinearLayout llSb, llGs, tvStartCalculate, tvGsCalculate, ivYanglao, ivYiliao, ivShiye, tvGoPage1, tvGoPage2;
    public ImageView ivTop;
    public EditText etMonthMoney, etSbMoney, etGsMonthMoney, etGsSbMoney, etGsZxkcMoney;
    public FrameLayout flInfoAd;

    public static HomeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("zhangsanzhangsa");
        showInfoAd();
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView() {
        ivTop = view.findViewById(R.id.iv_top);
        Glide.with(getActivity()).load(R.mipmap.sy_banner).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                ivTop.setBackground(resource);
            }
        });

        ivYanglao = view.findViewById(R.id.iv_yanglao);
        ivYiliao = view.findViewById(R.id.iv_yiliao);
        ivShiye = view.findViewById(R.id.iv_top_shiye);
        tvTabSbjnjs = view.findViewById(R.id.tv_sbjnjs);
        tvTabGszxjs = view.findViewById(R.id.tv_gszxjs);
        llSb = view.findViewById(R.id.ll_sb);
        llGs = view.findViewById(R.id.ll_gs);
        etMonthMoney = view.findViewById(R.id.et_month_money);
        etSbMoney = view.findViewById(R.id.et_sb_money);
        etGsMonthMoney = view.findViewById(R.id.et_gs_month_money);
        etGsSbMoney = view.findViewById(R.id.et_gs_sb_money);
        etGsZxkcMoney = view.findViewById(R.id.et_gs_zxkc_money);


        ivYanglao.setOnClickListener(v -> jumpCalculatePage(1));
        ivYiliao.setOnClickListener(v -> jumpCalculatePage(2));
        ivShiye.setOnClickListener(v -> jumpCalculatePage(3));

        Drawable bgHomeTabLeftSelect =getActivity().getDrawable(R.drawable.bg_home_tab_left_select);
        Drawable bgHomeTabLeftDefault =getActivity().getDrawable(R.drawable.bg_home_tab_left_default);
        Drawable bgHomeTabRightSelect =getActivity().getDrawable(R.drawable.bg_home_tab_right_select);
        Drawable bgHomeTabRightDefault =getActivity().getDrawable(R.drawable.bg_home_tab_right_default);
        tvTabSbjnjs.setOnClickListener(v -> {
            llSb.setVisibility(VISIBLE);
            llGs.setVisibility(GONE);
            tvTabSbjnjs.setBackground(bgHomeTabLeftSelect);
            tvTabSbjnjs.setTextColor(getActivity().getColor(R.color.white));

            tvTabGszxjs.setBackground(bgHomeTabRightDefault);
            tvTabGszxjs.setTextColor(getActivity().getColor(R.color.color_3875F6));
        });
        tvTabGszxjs.setOnClickListener(v -> {
            llSb.setVisibility(GONE);
            llGs.setVisibility(VISIBLE);
            tvTabGszxjs.setBackground(bgHomeTabRightSelect);
            tvTabGszxjs.setTextColor(getActivity().getColor(R.color.white));

            tvTabSbjnjs.setBackground(bgHomeTabLeftDefault);
            tvTabSbjnjs.setTextColor(getActivity().getColor(R.color.color_3875F6));
        });
        tvStartCalculate = view.findViewById(R.id.tv_start_calculate);
        tvStartCalculate.setOnClickListener(v -> {
            String monthMoney=etMonthMoney.getText().toString().trim();
            String sbMoney=etSbMoney.getText().toString().trim();
            if(TextUtils.isEmpty(monthMoney)){
                showToast("请先填写本月工资收入");
                return;
            }
            if(TextUtils.isEmpty(sbMoney)){
                showToast("请先填写社保个人部分");
                return;
            }

            float monthMoneybl = 0;
            try {
                monthMoneybl = Float.parseFloat(monthMoney);
            }catch (Exception ignored){

            }
            float sbMoneybl = 0;
            try {
                sbMoneybl = Float.parseFloat(sbMoney);
            }catch (Exception ignored){

            }

            Intent intent = new Intent(getActivity(), SBCalculateActivity.class);
            intent.putExtra(ConstantConfig.monthMoney, monthMoneybl);
            intent.putExtra(ConstantConfig.sbgrMoney, sbMoneybl);

            ADUtil.showRewardAd(getActivity(), ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {
                    startActivity(intent);
                }

                @Override
                public void onVideoComplete() {

                }

                @Override
                public void onAdVideoBarClick() {

                }

                @Override
                public void onVideoError() {

                }

                @Override
                public void onRewardArrived() {

                }

                @Override
                public void onSkippedVideo() {

                }

                @Override
                public void onAdShow() {

                }

                @Override
                public void onError() {

                }
            });
        });
        tvGoPage1 = view.findViewById(R.id.tv_goPage1);
        tvGoPage1.setOnClickListener(v -> startActivity(new Intent(getActivity(), SBFunctionActivity.class)));
        tvGoPage2 = view.findViewById(R.id.tv_goPage2);
        tvGoPage2.setOnClickListener(v -> startActivity(new Intent(getActivity(), SBManageOrSuperviseActivity.class)));


        tvGsCalculate = view.findViewById(R.id.tv_gs_calculate);
        tvGsCalculate.setOnClickListener(v -> {
            String monthMoney=etGsMonthMoney.getText().toString().trim();
            String sbMoney=etGsSbMoney.getText().toString().trim();
            String zxkcMoney=etGsZxkcMoney.getText().toString().trim();
            if(TextUtils.isEmpty(monthMoney)){
                showToast("请先填写本月工资收入");
                return;
            }
            if(TextUtils.isEmpty(sbMoney)){
                sbMoney="0";
//                showToast("请先填写五险一金");
//                return;
            }
            if(TextUtils.isEmpty(zxkcMoney)){
                zxkcMoney = "0";
//                showToast("请先填写专项附加扣除");
//                return;
            }
            float monthMoneybl = 0;
            try {
                monthMoneybl = Float.parseFloat(monthMoney);
            }catch (Exception ignored){

            }
            float sbMoneybl = 0;
            try {
                sbMoneybl = Float.parseFloat(sbMoney);
            }catch (Exception ignored){

            }
            float zxkcMoneybl = 0;
            try {
                zxkcMoneybl = Float.parseFloat(zxkcMoney);
            }catch (Exception ignored){

            }

            Intent intent = new Intent(getActivity(), GSCalculateResultActivity.class);
            intent.putExtra(ConstantConfig.monthMoney, monthMoneybl);
            intent.putExtra(ConstantConfig.sbgrMoney, sbMoneybl);
            intent.putExtra(ConstantConfig.zxkcMoney, zxkcMoneybl);

            ADUtil.showRewardAd(getActivity(), ConstantConfig.AD_Reward, new RewardAdCallBack() {
                @Override
                public void onAdClose() {
                    startActivity(intent);
                }

                @Override
                public void onVideoComplete() {

                }

                @Override
                public void onAdVideoBarClick() {

                }

                @Override
                public void onVideoError() {

                }

                @Override
                public void onRewardArrived() {

                }

                @Override
                public void onSkippedVideo() {

                }

                @Override
                public void onAdShow() {

                }

                @Override
                public void onError() {

                }
            });
        });

        view.findViewById(R.id.tv_zxfjkc).setOnClickListener(v -> startActivity(new Intent(getActivity(), CalculateZXFJKCActivity.class)));
        view.findViewById(R.id.tv_jump_fragment3).setOnClickListener(v -> {
           startActivity(new Intent(getActivity(), TaxGuideActivity.class));
        });

        flInfoAd=view.findViewById(R.id.fl_info_ad_home);
    }

    public void jumpCalculatePage(int type) {
        Intent intent = new Intent(getActivity(), YLBXCalculateActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showInfoAd(ShowInfoAdEvent showInfoAdEvent){
        if(!showInfoAdEvent.isShowAd()){
            return;
        }
        showInfoAd();
    }

    public void showInfoAd(){
        ADUtil.showInfoFlowAd(getActivity(), ConstantConfig.AD_INFO, flInfoAd, ScreenUtils.getScreenWidth(), 0, false, new InformationFlowAdCallback() {
            @Override
            public void onError() {

            }

            @Override
            public void onFeedAdLoad() {

            }

            @Override
            public void onRenderSuccess() {

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onRenderFail() {

            }
        });
    }

}
