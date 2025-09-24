package com.rzm.socialsecurity.presenter;

import com.common.wheel.mvp.MvpPresenter;
import com.rzm.socialsecurity.view.ITaxGuideDetailView;

public class TaxGuideDetailPresenter extends MvpPresenter<ITaxGuideDetailView> {
    @Override
    public void initView() {
        getView().initView();
    }

    public String getTitleName(int type){
        String result = "";
        switch (type){
            case 1:
                result="住房租金页面";
                break;
            case 2:
                result="赡养老人页面";
                break;
            case 3:
                result="继续教育页面";
                break;
            case 4:
                result="大病医疗页面";
                break;
            case 5:
                result="子女教育页面";
                break;
            case 6:
                result="住房贷款页面";
                break;
            case 7:
                result="3岁以下婴幼儿照顾页面";
                break;
        }
        return result;
    }
    public String getBXName(int type){
        String result = "";
        switch (type){
            case 1:
                result="住房租金";
                break;
            case 2:
                result="赡养老人";
                break;
            case 3:
                result="继续教育";
                break;
            case 4:
                result="大病医疗";
                break;
            case 5:
                result="子女教育";
                break;
            case 6:
                result="住房贷款";
                break;
            case 7:
                result="3岁以下婴幼儿照顾";
                break;
        }
        return result;
    }

    public String getKcfw(int type){
        String result = "";
        switch (type){
            case 1:
                result="在主要工作城市没有自有住房的纳税人发生的住房租金支出";
                break;
            case 2:
                result="赡养一位及以上年满60岁的父母，及子女均已去世的年满60岁的祖父母、外祖父母的支出";
                break;
            case 3:
                result=" 1、纳税人在中国境内接受学历(学位)继续教育的支出:在境内学历(学位)教育期间<br/><br/>" +
                        "2、技能人员职业资格继续教育支出:取得证书的年度<br/><br/>" +
                        "3、专业技术人员职业资格继续教育支出:取得证书的年度";
                break;
            case 4:
                result="在一个纳税年度内,纳税人发生的与基本医保相关的医药费用支出，扣除医保报销后个人负担累计超过<font color='red'>15000</font>元的部分";
                break;
            case 5:
                result="1、学前教育支出:满3岁至小学入学前<br/><br/>" +
                        "2、学历教育支出:小、初、高、中职、技工、专、本、硕、博";
                break;
            case 6:
                result="首套住房贷款利息支出:在实际发生贷款利息期间(不超过<font color='red'>240</font>个月)";
                break;
            case 7:
                result="3岁以下婴幼儿照顾";
                break;
        }
        return result;
    }
    public String getKcbz(int type){
        String result = "";
        switch (type){
            case 1:
                result="1、直辖市、省会(首府)、计划单列市及经国务院确定的其他城市:<font color='red'>1500</font>元/月<br/><br/>" +
                        "2、除第一项所列城市以外，市辖区户籍人口超过100万的城市:<font color='red'>1100</font>元/月<br/><br/>" +
                        "3、第一项所列城市以外，市辖区户籍人口不超过100万的城市:<font color='red'>800</font>元/月";
                break;
            case 2:
                result="1、独生子女:<font color='red'>3000</font>元/月<br/><br/>" +
                        "2、非独生子女:在兄弟姐妹之间分摊每月<font color='red'>3000</font>元的扣除额度，每人分摊额度不得超过1500元";
                break;
            case 3:
                result=" 1、学历教育<font color='red'>400</font>元/月最长不超过<font color='red'>48</font>个月<br/><br/>" +
                        "2、资格证书<font color='red'>3600</font>元/年";
                break;
            case 4:
                result="每年在不超过<font color='red'>80000</font>元标准限额内据实扣除";
                break;
            case 5:
                result="<font color='red'>2000</font>元/月/每个子女";
                break;
            case 6:
                result="<font color='red'>1000</font>元/月";
                break;
            case 7:
                result="每个婴幼儿每月<font color='red'>2000</font>元的标准定额扣除.";
                break;
        }
        return result;
    }

    public String getKcfs(int type){
        String result = "";
        switch (type){
            case 1:
                result="纳税人的配偶在纳税人的主要工作城市有自有住房的，视同纳税人在主要工作城市有自有住房。夫妻双方婚前分别购买住房发生的首套住房贷款利息:选择一套房由购买方按扣除标准的100%扣除或对各自购买住房分别按扣除标准的50%扣除。<br/><br/>" +
                        "注:不得与住房贷款利息专项附加扣除同时享受";
                break;
            case 2:
                result="1、独生子女本人扣除<br/><br/>" +
                        "2、平均分摊:赡养人平均分摊<br/><br/>" +
                        "3、约定分摊:赡养人自行约定分摊比例4、指定分摊:由被赡养人指定分摊比例<br/><br/>" +
                        "注:非独生子女指定分摊及约定分摊须签订书面协议。非独生子女指定分摊与约定分摊不一致的，以指定分摊为准。非独生子女具体分摊方式和额度在一个纳税年度内不能变更";
                break;
            case 3:
                result="1、本人扣除个人接受本科(含)以下学历(学位)继续教育，可以选择由其父母扣除.<br/><br/>" +
                        "2、本人扣除<br/><br/>" +
                        "注:学历(学位)继续教育支出的同一教育事项，不得重复扣除。";
                break;
            case 4:
                result="1、本人医药费用可以选择由本人或其配偶扣除。<br/><br/>" +
                        "2、未成年子女医药费用可以选择由其父母一方扣除。<br/><br/>" +
                        "注:次年汇算清缴时享受扣除。个人负担部分是指医保目录范围内的自付部分。";
                break;
            case 5:
                result="父母(法定监护人)各扣除50%或选择一方全额扣除:<br/><br/>" +
                        "1、子女在境内或境外接受学历(学位)教育，接受公办或民办教育均可享受.<br/><br/>" +
                        "2、子女接受学历教育需为全日制学历教育.";
                break;
            case 6:
                result="1、纳税人本人或者配偶单独或者共同使用商业银行或者住房公积金个人住房贷款为本人或者其配偶购买中国境内住房，在实际发生贷款利息的年度扣除。<br/><br/>" +
                        "2、经夫妻双方约定，可以选择由其中一方扣除，具体扣除方式在一个纳税年度内不能变更<br/><br/>" +
                        "3、夫妻双方婚前分别购买住房发生的首套住房贷款利息:选择一套房由购买方按扣除标准的100%扣除或对各自购买住房分别按扣除标准的50%扣除。<br/><br/>" +
                        "注:不得与住房租金专项附加扣除同时享受。纳税人只能享受一次首套住房贷款的利息扣除。";
                break;
            case 7:
                result="父母(监护人)可以选择由其中一方按扣除标准的100%扣除也可以选择由双方分别按扣除标准的50%扣除。<br/><br/>" +
                        "注:政策自2022年起实施，不适用于当前进行的2021年度个税综合所得汇算清缴。非独生子女指定分摊与约定分摊不一致的，以指定分摊为准。非独生子女具体分摊方式和额度在一个纳税年度内不能变更。";
                break;
        }
        return result;
    }

    public String getcjwt(int type){
        String result = "";
        switch (type){
            case 1:
                result="问1:合租住房可以分别享受扣除政策吗?<br/><br/>" +
                        "答1:住房租金支出由签订租赁合同的承租人扣除。因此，合租租房的个人(非夫妻关系)，若都与出租方签署了规范租房合同，可根据租金定额标准各自扣除。<br/><br/>" +
                        "问2:公租房是公司与保障房公司签的协议，但员工是需要付房租的，这种情况下员工是否可以享受专项附加扣除?需要留存什么资料备查?<br/><br/>" +
                        "答2:纳税人在主要工作城市没有自有住房而发生的住房租金支出，可以按照标准定额扣除。员工租用公司与保障房公司签订的保障房，并支付租金的，可以申报扣除住房租金专项附加扣除。纳税人应当留存与公司签订的公租房合同或协议等相关资料备查。";
                break;
            case 2:
                result="问1:父母均要满60岁还是只要一位满60岁即可享受扣除?<br/><br/>" +
                        "答1:父母中有一位年满60周岁，纳税人就可以按照规定标准享受赡养老人专项附加扣除。<br/><br/>" +
                        "问2:非独生子女，父母指定或兄弟协商，能否以某位子女按每月<font color='red'>2000</font>元扣除?<br/><br/>" +
                        "答2:不可以。按照规定，纳税人为非独生子女的，在兄弟姐妹之间分摊每月<font color='red'>2000</font>元的扣除额度，每人分摊额度不能超过每月<font color='red'>1000</font>元。";
                break;
            case 3:
                result="问1:纳税人处于本硕博连读的博士阶段，父母已经申报享受了子女教育专项附加扣除,纳税人如果在博士读书时取得律师资格证书，可以申报继续教育扣除吗?<br/><br/>" +
                        "答1:如果纳税人有综合所得或经营所得，在取得证书的当年，可以享受职业资格继续教育扣除。<br/><br/>" +
                        "问2:回纳税人参加夜大、函授、现代远程教育、广播电视大学等学习，是否可以享受继续教育扣除?<br/><br/>" +
                        "答2:纳税人参加夜大、函授、现代远程教育广播电视大学等学习，所读学校为其建立学籍档案的，可以享受学历(学位)继续教育扣除";
                break;
            case 4:
                result="问1:四大病医疗的扣除主体、范围和扣除标准是什么?<br/><br/>" +
                        "答1:在一个纳税年度内，纳税人发生的与基本医保相关的医药费用支出，扣除医保报销后个人负担(指医保目录范围内的自付部分)累计超过<font color='red'>15000</font>元的部分，由纳税人在办理年度汇算时在<font color='red'>80000</font>元限额内据实扣除。纳税人发生的医药费用支出可以选择由本人或其配偶一方扣除，未成年子女发生的医药费用支出可以选择由其父母一方扣除。纳税人及其配偶、未成年子女发生的医药费用支出，可按规定分别计算扣除额。<br/><br/>" +
                        "问2:在私立医院就诊是否可以享受大病医疗扣除?<br/><br/>" +
                        "答2:对于纳入医疗保障结算系统的私立医院只要纳税人看病的支出在医保系统可以体现和归集，则纳税人发生的与基本医保相关的支出，可以按照规定享受大病医疗扣除。";
                break;
            case 5:
                result="问:有多子女的父母，可以对不同子女选择不同扣除方式?<br/><br/>" +
                        "答:可以。有多子女的父母，可以对不同的子女选择不同的扣除方式，即对于女甲可以选择由一方按照每月<font color='red'>1000</font>元的标准扣除,对于女乙可以选择由双方分别按照每月<font color='red'>500</font>元的标准扣除。";
                break;
            case 6:
                result="问1:如何理解纳税人只能享受一次住房贷款利息扣除?<br/><br/>" +
                        "答1:只要纳税人申报扣除过一套住房贷款利息，在个人所得税专项附加扣除的信息系统中就存有扣除住房贷款利息的记录，无论扣除时间长短、也无论该住房的产权归属情况，纳税人就不得再就其他房屋享受住房贷款利息扣除。<br/><br/>" +
                        "问2:我用贷款买了一套房，由于工作需要将该房屋贷款还清后置换了另一套房，第二套房贷银行依旧给我的是首套房贷款利率，第一套房时我没享受过贷款利息政策，那么第二套房贷利息可以享受住房贷款利息扣除政策吗?<br/><br/>" +
                        "答2:可以。根据现行政策规定，如果纳税人没有申报过住房贷款利息扣除，那么其按照首套住房贷款利率购买的第二套住房，可以享受住房贷款利息扣除。";
                break;
            case 7:
                result="问1:享受3岁以下婴幼儿照护专项，附加扣除的起算时间是什么?<br/><br/>" +
                        "答1:从婴幼儿出生的当月至满3周岁的前一个月，纳税人可以享受这项专项附加扣除。比如:2022年5月出生的婴幼儿，一直到2025年4月，其父母都可以按规定享受此项专项附加扣除政策。<br/><br/>" +
                        "问2:婴幼儿子女的范围包括哪些?<br/><br/>" +
                        "答2:婴幼儿子女包括婚生子女、非婚生子女养子女、继子女等受到本人监护的3岁以下婴幼儿。";
                break;
        }
        return result;
    }
}
