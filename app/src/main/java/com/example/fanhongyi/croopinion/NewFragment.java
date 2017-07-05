package com.example.fanhongyi.croopinion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by FANHONGYI on 2017/6/27.
 */

public class NewFragment extends Fragment{
    private TextView hot0,hot1,hot2,hot3,hot4,hot5,hot6,hot7,hot8,hot9;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_fragment, container,false);

        View list0=view.findViewById(R.id.new_list0);
        View list1=view.findViewById(R.id.new_list1);
        View list2=view.findViewById(R.id.new_list2);
        View list3=view.findViewById(R.id.new_list3);
        View list4=view.findViewById(R.id.new_list4);
        View list5=view.findViewById(R.id.new_list5);
        View list6=view.findViewById(R.id.new_list6);
        View list7=view.findViewById(R.id.new_list7);
        View list8=view.findViewById(R.id.new_list8);
        View list9=view.findViewById(R.id.new_list9);

        hot0=(TextView)list0.findViewById(R.id.hot);
        hot1=(TextView)list1.findViewById(R.id.hot);
        hot2=(TextView)list2.findViewById(R.id.hot);
        hot3=(TextView)list3.findViewById(R.id.hot);
        hot4=(TextView)list4.findViewById(R.id.hot);
        hot5=(TextView)list5.findViewById(R.id.hot);
        hot6=(TextView)list6.findViewById(R.id.hot);
        hot7=(TextView)list7.findViewById(R.id.hot);
        hot8=(TextView)list8.findViewById(R.id.hot);
        hot9=(TextView)list9.findViewById(R.id.hot);

        hot0.setText("topic 0");
        hot1.setText("topic 1");
        hot2.setText("topic 2");
        hot3.setText("topic 3");
        hot4.setText("topic 4");
        hot5.setText("topic 5");
        hot6.setText("topic 6");
        hot7.setText("topic 7");
        hot8.setText("topic 8");
        hot9.setText("topic 9");

        return view;
    }

}
