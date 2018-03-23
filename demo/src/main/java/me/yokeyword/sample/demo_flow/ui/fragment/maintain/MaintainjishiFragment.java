package me.yokeyword.sample.demo_flow.ui.fragment.maintain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.yokeyword.sample.R;
import me.yokeyword.sample.demo_flow.base.BaseBackFragment;

/**
 * 维修纪实
 * Created by YoKeyword on 16/2/7.
 */
public class MaintainjishiFragment extends BaseBackFragment {
    private static final String ARG_NUMBER = "arg_number";
    private Toolbar mToolbar;
    private String mTitle;

    public static MaintainjishiFragment newInstance(String title) {
        MaintainjishiFragment fragment = new MaintainjishiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NUMBER, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTitle = args.getString(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jishi, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        initToolbarNav(mToolbar);
    }
}
