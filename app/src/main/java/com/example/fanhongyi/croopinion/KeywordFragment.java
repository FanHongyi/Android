package com.example.fanhongyi.croopinion;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;

import java.util.ArrayList;
import java.util.List;

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

//        userTags.add(new Tag(1,"TAG TEXT 1"));
//        userTags.add(new Tag(1,"TAG TEXT 2"));
//        userTags.add(new Tag(1,"TAG TEXT 3"));
        userTags.drawTags();
//        userTags.setOnTagSelectListener(new TagCloudLinkView.OnTagSelectListener(){
//            @Override
//            public void onTagSelected(Tag tag, int i) {
//                // write something
//            }
//        });
//        userTags.setOnTagDeleteListener(new TagCloudLinkView.OnTagDeleteListener() {
//            @Override
//            public void onTagDeleted(Tag tag, int i) {
//                // write something
//            }
//        });

        mChart = (LineChart) view.findViewById(lineChart);
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
            Toast.makeText(getActivity(), mTopic.getText().toString()+"已添加", Toast.LENGTH_LONG).show();
            userTags.add(new Tag(1,mTopic.getText().toString()));
            userTags.drawTags();
            mTopic.setText("");
            mChart.setData(getLineData(mTopic.getText().toString()));
        }
    }
}
