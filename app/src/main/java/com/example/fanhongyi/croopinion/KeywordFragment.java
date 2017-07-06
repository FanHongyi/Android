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
    private  LineChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.keyword_fragment, container,false);
        mTopic = (EditText)view.findViewById(R.id.editTopic);
        userTags = (TagCloudLinkView) view.findViewById(R.id.userTags);
        userTags.add(new Tag(1,"TAG TEXT 1"));
        userTags.add(new Tag(1,"TAG TEXT 2"));
        userTags.add(new Tag(1,"TAG TEXT 3"));
        userTags.drawTags();
        userTags.setOnTagSelectListener(new TagCloudLinkView.OnTagSelectListener(){
            @Override
            public void onTagSelected(Tag tag, int i) {
                // write something
            }
        });
        userTags.setOnTagDeleteListener(new TagCloudLinkView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(Tag tag, int i) {
                // write something
            }
        });

        mChart = (LineChart) view.findViewById(lineChart);
        mChart.setData(getLineData());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = (Button) getActivity().findViewById(R.id.selectButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTopic.getText().toString().equals("")){
                    Toast.makeText(getActivity(), mTopic.getText().toString(), Toast.LENGTH_LONG).show();
                    userTags.add(new Tag(1,mTopic.getText().toString()));
                    userTags.drawTags();
                    mTopic.setText("");
                }
            }
        });
    }

    private LineData getLineData(){
        final int DATA_COUNT = 5;  //设置折线图横跨距离
        LineDataSet dataSetA = new LineDataSet( getChartData(DATA_COUNT, 1), "A");
        //设置折线数据 getChartData返回一个List<Entry>键值对集合标识 折线点的横纵坐标，"A"代表折线标识
        LineDataSet dataSetB = new LineDataSet( getChartData(DATA_COUNT, 2), "B");

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA);
        dataSets.add(dataSetB);
        LineData data = new LineData( getLabels(DATA_COUNT), dataSets);
        return data;
        // 返回LineData类型数据，该类型由标识X轴单位 List<String>的 集合和一个标识折线数据的List<ILineDataSet>组成
    }

    private List<Entry> getChartData(int count, int ratio){
        List<Entry> chartData = new ArrayList<>();
        for(int i=0;i<count;i++){
            chartData.add(new Entry( i*2*ratio, i));
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
}
