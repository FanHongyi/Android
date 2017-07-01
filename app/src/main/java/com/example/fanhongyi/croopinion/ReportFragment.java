package com.example.fanhongyi.croopinion;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by FANHONGYI on 2017/6/27.
 */

public class ReportFragment extends Fragment{

    private PieChart mPieChart;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_fragment, container,false);

        this.context = getActivity();
        mPieChart = (PieChart) view.findViewById(R.id.pieChart);

        PieData mPieData = getPieData(3, 100);
        showChart(mPieChart, mPieData);

        return view;
    }

    private void showChart(PieChart pieChart, PieData pieData) {

        //pieChart.setHoleRadius(30f);  //半径
        pieChart.setTransparentCircleRadius(0); // 半透明圈
        pieChart.setHoleRadius(50f);  //实心圆

        pieChart.setDescription("测试");

        // mChart.setDrawYValues(true);
        //pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(180); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("舆论倾向");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setFormSize(15f);//比例块大小
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        //mLegend.setEnabled(false);//设置禁用比例块

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

//        for (int i = 0; i < count; i++) {
//            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
//        }
        xValues.add("正面");
        xValues.add("负面");
        xValues.add("模糊");


        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
//        /**
//         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
//         * 所以 14代表的百分比就是14%
//         */
        float quarterly1 = 40;
        float quarterly2 = 40;
        float quarterly3 = 20;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(205, 205, 205));
        //colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 10 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);
        // 设置成PercentFormatter将追加%号
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(15f);

        return pieData;
    }
}
