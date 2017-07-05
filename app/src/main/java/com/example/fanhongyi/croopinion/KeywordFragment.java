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

import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;

/**
 * Created by FANHONGYI on 2017/6/27.
 */

public class KeywordFragment extends Fragment{
    private EditText mTopic=null;
    private TagCloudLinkView userTags;

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
}
