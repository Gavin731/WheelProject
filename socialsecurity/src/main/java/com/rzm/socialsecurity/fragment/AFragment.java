package com.rzm.socialsecurity.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.common.wheel.mvp.MvpFragment;
import com.rzm.socialsecurity.R;
import com.rzm.socialsecurity.activity.SBCalculateActivity;
import com.rzm.socialsecurity.activity.SBFunctionActivity;
import com.rzm.socialsecurity.activity.SBManageOrSuperviseActivity;
import com.rzm.socialsecurity.activity.YLBXCalculateActivity;
import com.rzm.socialsecurity.constant.ConstantConfig;
import com.rzm.socialsecurity.presenter.APresenter;
import com.rzm.socialsecurity.util.DialogUtil;
import com.rzm.socialsecurity.util.TablelayoutUtil;
import com.rzm.socialsecurity.view.IAView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zenglinggui
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018/11/30     zenglinggui       v1.0.0        create
 **/
public class AFragment extends MvpFragment<APresenter> implements IAView {

    private static final String ARG_C = "content";

    public TextView tvTabGszxjs,tvTabSbjnjs, tvStartCalculate,tvGoPage1,tvGoPage2,tvGsCalculate;
    public LinearLayout llSb, llGs;
    public ImageView ivYanglao, ivYiliao, ivShiye;
    public TableLayout tbLDetail;

    public static AFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C, content);
        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.initView();
    }

    @Override
    public APresenter createPresenter() {
        return new APresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_home;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void initView() {
        ivYanglao = view.findViewById(R.id.iv_yanglao);
        ivYiliao = view.findViewById(R.id.iv_yiliao);
        ivShiye = view.findViewById(R.id.iv_top_shiye);
        tvTabSbjnjs = view.findViewById(R.id.tv_sbjnjs);
        tvTabGszxjs = view.findViewById(R.id.tv_gszxjs);
        llSb = view.findViewById(R.id.ll_sb);
        llGs = view.findViewById(R.id.ll_gs);

        ivYanglao.setOnClickListener(v -> jumpCalculatePage(1));
        ivYiliao.setOnClickListener(v -> jumpCalculatePage(2));
        ivShiye.setOnClickListener(v -> jumpCalculatePage(3));
        tvTabSbjnjs.setOnClickListener(v -> {
            llSb.setVisibility(VISIBLE);
            llGs.setVisibility(GONE);
        });
        tvTabGszxjs.setOnClickListener(v -> {
            llSb.setVisibility(GONE);
            llGs.setVisibility(VISIBLE);
        });
        tvStartCalculate= view.findViewById(R.id.tv_start_calculate);
        tvStartCalculate.setOnClickListener(v -> startActivity(new Intent(getActivity(), SBCalculateActivity.class)));
        tvGoPage1=view.findViewById(R.id.tv_goPage1);
        tvGoPage1.setOnClickListener(v -> startActivity(new Intent(getActivity(), SBFunctionActivity.class)));
        tvGoPage2=view.findViewById(R.id.tv_goPage2);
        tvGoPage2.setOnClickListener(v -> startActivity(new Intent(getActivity(), SBManageOrSuperviseActivity.class)));

        List<String> data = new ArrayList<>();
        data.add("食品生鲜");
        data.add("家用电器");
        data.add("家居生活");
        data.add("医疗保健");
        data.add("酒水饮料");
        data.add("图书音像");
        tvGsCalculate =view.findViewById(R.id.tv_gs_calculate);
        tvGsCalculate.setOnClickListener(v -> DialogUtil.showBottomWheelDialog(getActivity(), data, new DialogUtil.SelectBottomCallback() {
            @Override
            public void onConfirm(int position, String value) {
                LogUtils.e("选择的索引:"+position+",值："+value);
            }
        }));

        tbLDetail = view.findViewById(R.id.tbL_detail);
        List<String> titles=new ArrayList<>();
        titles.add("明细");
        titles.add("明细2");
        titles.add("明细3");
        titles.add("明细4");

        List<List<String>> tableData=new ArrayList<>();
        List<String> row1=new ArrayList<>();
        row1.add("1");
        row1.add("2");
        row1.add("3");
        row1.add("4");
        tableData.add(row1);

        List<String> row2=new ArrayList<>();
        row2.add("a");
        row2.add("b");
        row2.add("c");
        row2.add("d");
        tableData.add(row2);

        List<String> row3=new ArrayList<>();
        row3.add("a1");
        row3.add("a2");
        row3.add("a3");
        row3.add("a4");
        tableData.add(row3);

        TablelayoutUtil.addTableRow(getActivity(), tbLDetail,titles, tableData,getActivity().getColor(R.color.gray), getActivity().getColor(R.color.white));
    }

    public void jumpCalculatePage(int type){
        Intent intent = new Intent(getActivity(), YLBXCalculateActivity.class);
        intent.putExtra(ConstantConfig.bxKey, type);
        startActivity(intent);
    }



}
