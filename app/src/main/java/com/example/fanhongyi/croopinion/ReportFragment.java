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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static com.example.fanhongyi.croopinion.R.id.lineChart;

/**
 * Created by FANHONGYI on 2017/6/27.
 */

public class ReportFragment extends Fragment{

    private PieChart mPieChart;
    private LineChart mLineChart;
    private Context context;
    private TagCloudLinkView cloud;
    private final int DATA_COUNT = 7;  //设置折线图横跨距离
    private List<ILineDataSet> dataSets = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_fragment, container,false);

        float pos=66.6f,neg=13.4f,neu=20f;

        this.context = getActivity();
        mPieChart = (PieChart) view.findViewById(R.id.pieChart);
        PieData mPieData = getPieData(pos,neg,neu);
        showPieChart(mPieChart, mPieData);

        mLineChart = (LineChart) view.findViewById(lineChart);
        mLineChart.setData(getLineData("test"));
        mLineChart.notifyDataSetChanged();
        showLineChart();

        cloud = (TagCloudLinkView) view.findViewById(R.id.tagCloud);
        cloud.add(new Tag(1,"TAG TEXT 1"));
        cloud.add(new Tag(1,"TAG TEXT 2"));
        cloud.add(new Tag(1,"TAG TEXT 3"));
        cloud.drawTags();
        cloud.setOnTagSelectListener(new TagCloudLinkView.OnTagSelectListener(){
            @Override
            public void onTagSelected(Tag tag, int i) {
                // write something
            }
        });

        return view;
    }

    private void showPieChart(PieChart pieChart, PieData pieData) {

        //pieChart.setHoleRadius(30f);  //半径
        pieChart.setTransparentCircleRadius(0); // 半透明圈
        pieChart.setHoleRadius(50f);  //实心圆

        pieChart.setDescription("");

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

    private PieData getPieData(float pos,float neg,float neu) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

//        for (int i = 0; i < count; i++) {
//            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
//        }
        xValues.add("正面");
        xValues.add("负面");
        xValues.add("中性");


        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
//        /**
//         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
//         * 所以 14代表的百分比就是14%
//         */
//        float quarterly1 = 40;
//        float quarterly2 = 40;
//        float quarterly3 = 20;

        yValues.add(new Entry(pos, 0));
        yValues.add(new Entry(neg, 1));
        yValues.add(new Entry(neu, 2));

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

    private void showLineChart() {
        mLineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        mLineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        mLineChart.setDrawGridBackground(true); // 是否显示表格颜色
        mLineChart.setGridBackgroundColor(Color.WHITE); // 表格的的颜色，在这里是是给颜色设置一个透明度

        // enable touch gestures
        mLineChart.setTouchEnabled(false); // 设置是否可以触摸

        // enable scaling and dragging
        mLineChart.setDragEnabled(false);// 是否可以拖拽
        mLineChart.setScaleEnabled(false);// 是否可以缩放

        XAxis xAxis = mLineChart.getXAxis();
        // 不显示y轴
        xAxis.setDrawAxisLine(false);
        // 不从y轴发出横向直线
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mLineChart.getAxisLeft();
        // 不显示y轴
        leftAxis.setDrawAxisLine(false);
        // 不从y轴发出横向直线
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = mLineChart.getAxisRight();
        // 不显示y轴
        rightAxis.setDrawAxisLine(false);
        // 不从y轴发出横向直线
        rightAxis.setDrawGridLines(false);

        mLineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴

        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(false);//

        //lineChart.setBackgroundColor(Color.WHITE);// 设置背景

        // add data
        //mLineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = mLineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setEnabled(false);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色
//      mLegend.setTypeface(mTf);// 字体

        mLineChart.animateX(2500); // 立即执行的动画,x轴
    }

//    /**
//     * 生成一个数据
//     * @param count 表示图表中有多少个坐标点
//     * @param range 用来生成range以内的随机数
//     * @return
//     */
//    private LineData getLineData(int count, float range) {
//        ArrayList<String> xValues = new ArrayList<String>();
//        for (int i = 0; i < count; i++) {
//            // x轴显示的数据，这里默认使用数字下标显示
//            xValues.add("" + i);
//        }
//
//        // y轴的数据
//        ArrayList<Entry> yValues = new ArrayList<Entry>();
//        for (int i = 0; i < count; i++) {
//            float value = (float) (Math.random() * range) + 3;
//            yValues.add(new Entry(value, i));
//        }
//
//        // create a dataset and give it a type
//        // y轴的数据集合
//        LineDataSet lineDataSet = new LineDataSet(yValues, "测试折线图" /*显示在比例图上*/);
//        // mLineDataSet.setFillAlpha(110);
//        // mLineDataSet.setFillColor(Color.RED);
//
//        //用y轴的集合来设置参数
//        lineDataSet.setLineWidth(1.75f); // 线宽
//        lineDataSet.setCircleSize(3f);// 显示的圆形大小
//        lineDataSet.setColor(Color.BLACK);// 显示颜色
//        lineDataSet.setCircleColor(Color.BLACK);// 圆形的颜色
//        lineDataSet.setHighLightColor(Color.BLACK); // 高亮的线的颜色
//
//        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
//        lineDataSets.add(lineDataSet); // add the datasets
//
//        // create a data object with the datasets
//        LineData lineData = new LineData(xValues, lineDataSet);
//
//        return lineData;
//    }
    private LineData getLineData(String s){
        //Log.i("testStrings",s);
        LineDataSet dataSet=new LineDataSet(getChartData(DATA_COUNT), s);
        //设置折线数据 getChartData返回一个List<Entry>键值对集合标识 折线点的横纵坐标，"A"代表折线标识
        dataSet.setColor(Color.GRAY);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setDrawCircleHole(false);
        dataSets.add(dataSet);
        LineData data = new LineData(getLabels(DATA_COUNT), dataSets);
        return data;
        // 返回LineData类型数据，该类型由标识X轴单位 List<String>的 集合和一个标识折线数据的List<ILineDataSet>组成
    }

        private List<Entry> getChartData(int count){
            List<Entry> chartData = new ArrayList<>();
            for(int i=0;i<count;i++){
                chartData.add(new Entry((float) (Math.random() * 20) + 3, i));
            }
            return chartData;
        }

        private List<String> getLabels(int count){
            List<String> chartLabels = new ArrayList<>();
            for(int i=0;i<count;i++) {
                chartLabels.add(i+"");
            }
            return chartLabels;
        }
}
