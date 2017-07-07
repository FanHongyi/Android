package com.example.fanhongyi.croopinion;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

public class KeywordFragment extends Fragment{
    private EditText mTopic=null;
    private TagCloudLinkView userTags;
    private LineChart mChart;
    private List<ILineDataSet> dataSets = new ArrayList<>();
    private final int DATA_COUNT = 7;  //设置折线图横跨距离

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keyword_fragment, container,false);
        mTopic = (EditText)view.findViewById(R.id.editTopic);
        userTags = (TagCloudLinkView) view.findViewById(R.id.userTags);
        userTags.drawTags();
        userTags.setOnTagSelectListener(new TagCloudLinkView.OnTagSelectListener(){
            @Override
            public void onTagSelected(Tag tag, int i) {
                Toast.makeText(getActivity(), tag.getText()+"点击", Toast.LENGTH_LONG).show();
                System.out.println(dataSets.get(0));
            }
        });
        userTags.setOnTagDeleteListener(new TagCloudLinkView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                Toast.makeText(getActivity(), tag.getText()+"删除", Toast.LENGTH_LONG).show();
                //LineDataSet dataSet=new LineDataSet(getChartData(DATA_COUNT), s);
                for(int j=0;j<dataSets.size();j++){
                    if(dataSets.get(j).getLabel().equals(tag.getText()))
                        dataSets.remove(j);
                }

                LineData data = new LineData(getLabels(DATA_COUNT), dataSets);
                mChart.setData(data);
                mChart.notifyDataSetChanged();
                showChart();
            }
        });

        mChart = (LineChart) view.findViewById(lineChart);
        showChart();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.selectButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTag();
            }
        });
    }

    private LineData getLineData(String s){
        Log.i("testStrings",s);
        LineDataSet dataSet=new LineDataSet(getChartData(DATA_COUNT), s);
        //设置折线数据 getChartData返回一个List<Entry>键值对集合标识 折线点的横纵坐标，"A"代表折线标识
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
    //利用循环生成了(0,0) (2,1) (4,2) (6,3) (8,4)
    //(0,0) (4,1) (8,2) (12,3) (16,4) 这两组折线坐标 组成List<Entry>返回

    private List<String> getLabels(int count){
        List<String> chartLabels = new ArrayList<>();
        for(int i=0;i<count;i++) {
            chartLabels.add("X" + i);
        }
        return chartLabels;
    }
    //生成横坐标的单位显示，(x0 x1 x2 x3 x4)这样的List<String>集合返回

    
    private void addTag(){
        if(!mTopic.getText().toString().equals("")){
            Toast.makeText(getActivity(), "\""+mTopic.getText().toString()+"\""+"已添加", Toast.LENGTH_LONG).show();
            userTags.add(new Tag(1,mTopic.getText().toString()));
            userTags.drawTags();
            mChart.setData(getLineData(mTopic.getText().toString()));
            mTopic.setText("");
        }
    }
    private void showChart(){
        mChart.setDrawBorders(false);  //是否在折线图上添加边框
        // no description text
        mChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mChart.setNoDataTextDescription("You need to provide data for the chart.");
        // enable / disable grid background
        mChart.setDrawGridBackground(true); // 是否显示表格颜色
        mChart.setGridBackgroundColor(Color.WHITE); // 表格的的颜色，在这里是是给颜色设置一个透明度
        // enable touch gestures
        mChart.setTouchEnabled(false); // 设置是否可以触摸
        // enable scaling and dragging
        mChart.setDragEnabled(false);// 是否可以拖拽
        mChart.setScaleEnabled(false);// 是否可以缩放

        XAxis xAxis = mChart.getXAxis();
        // 不显示y轴
        xAxis.setDrawAxisLine(false);
        // 不从y轴发出横向直线
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mChart.getAxisLeft();
        // 不显示y轴
        leftAxis.setDrawAxisLine(false);
        // 不从y轴发出横向直线
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = mChart.getAxisRight();
        // 不显示y轴
        rightAxis.setDrawAxisLine(false);
        // 不从y轴发出横向直线
        rightAxis.setDrawGridLines(false);

        mChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴

        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);//

        //lineChart.setBackgroundColor(Color.WHITE);// 设置背景

        // add data
//            mChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = mChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setEnabled(true);
        mLegend.setForm(Legend.LegendForm.SQUARE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色
//      mLegend.setTypeface(mTf);// 字体

        mChart.animateX(2500); // 立即执行的动画,x轴
    }
}
