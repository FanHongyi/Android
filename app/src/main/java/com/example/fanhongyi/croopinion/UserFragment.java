package com.example.fanhongyi.croopinion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.fanhongyi.croopinion.R.id.textView1;

/**
 * Created by FANHONGYI on 2017/6/27.
 */

public class UserFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container,false);
        TextView t = (TextView) view.findViewById(R.id.userTextView);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            t.setText(bundle.getString("str"));
        }
        return view;
    }

}
