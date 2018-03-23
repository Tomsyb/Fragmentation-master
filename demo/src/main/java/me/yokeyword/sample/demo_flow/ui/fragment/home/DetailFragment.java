package me.yokeyword.sample.demo_flow.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.sample.R;
import me.yokeyword.sample.demo_flow.adapter.SortButtonAdapter;
import me.yokeyword.sample.demo_flow.base.BaseBackFragment;
import me.yokeyword.sample.demo_flow.entity.ButtonModel;
import me.yokeyword.sample.demo_flow.ui.fragment.maintain.MaintainjishiFragment;
import me.yokeyword.sample.demo_flow.view.sortbtn.SoreButton;
import me.yokeyword.sample.demo_flow.view.sortbtn.ViewControl;

/**
 * 功能界面
 * Created by YoKeyword on 16/2/3.
 */
public class DetailFragment extends BaseBackFragment implements ViewControl {
    public static final String TAG = DetailFragment.class.getSimpleName();
    private static final int REQ_MODIFY_FRAGMENT = 100;

    private static final String ARG_TITLE = "arg_title";
    static final String KEY_RESULT_TITLE = "title";

    private Toolbar mToolbar;
    private TextView mTvContent;
    private String mTitle;
    private SoreButton soreButton;
    private List<Integer> list;

    public static DetailFragment newInstance(String title) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(ARG_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        soreButton = (SoreButton)view.findViewById(R.id.soreButton);
        soreButton.setViewControl(this);
        //设置界面监听
        mToolbar.setTitle(mTitle);
        initToolbarNav(mToolbar);
    }

    /**
     * 这里演示:
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        initDelayView();
    }

    private void initDelayView() {
        //添加界面到list
        list = new ArrayList<>();
        list.add(R.layout.viewpager_page);
        list.add(R.layout.viewpager_page);
        list.add(R.layout.viewpager_page_text);
        soreButton.setView(list).init();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MODIFY_FRAGMENT && resultCode == RESULT_OK && data != null) {
            mTitle = data.getString(KEY_RESULT_TITLE);
            mToolbar.setTitle(mTitle);
            // 保存被改变的 title
            getArguments().putString(ARG_TITLE, mTitle);
            Toast.makeText(_mActivity, R.string.modify_title, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setView(View view, final int type) {
        switch (type) {
            case 0://第一个界面
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                SortButtonAdapter adapter = new SortButtonAdapter(getActivity(),setData());
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                start(MaintainjishiFragment.newInstance("维修纪实"));
                                break;
                            default:
                                break;
                        }
                        Toast.makeText(getActivity(),"第"+type+"页"+position,Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 1://第二个界面
                GridView gridView2 = (GridView) view.findViewById(R.id.gridView);
                SortButtonAdapter adapter2 = new SortButtonAdapter(getActivity(),setData2());
                gridView2.setAdapter(adapter2);
                gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(),"第"+type+"页"+position,Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 2://第三个界面
                TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                tvTitle.setText("可高度定制，可设置任意layout,并且在回调中获取该layout内的所有控件");
                tvTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"点击了该文字",Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

    private List<ButtonModel> setData(){
        List<ButtonModel> data = new ArrayList<>();
        ButtonModel buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_1);
        buttonModel.setName("维修纪实");
        data.add(buttonModel);

        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_2);
        buttonModel.setName("维修历史");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_3);
        buttonModel.setName("性能参数");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_4);
        buttonModel.setName("连通测试");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_5);
        buttonModel.setName("实时曲线");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_6);
        buttonModel.setName("观测环境");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_7);
        buttonModel.setName("仪器原件");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_8);
        buttonModel.setName("更多");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_9);
        buttonModel.setName("更多");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_10);
        buttonModel.setName("更多");
        data.add(buttonModel);
        return data;
    }

    private List<ButtonModel> setData2(){
        List<ButtonModel> data = new ArrayList<>();
        ButtonModel buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_11);
        buttonModel.setName("品质酒店");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_12);
        buttonModel.setName("生活服务");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_13);
        buttonModel.setName("足疗按摩");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_14);
        buttonModel.setName("母婴亲子");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_15);
        buttonModel.setName("结婚");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_16);
        buttonModel.setName("景点");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_17);
        buttonModel.setName("温泉");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_18);
        buttonModel.setName("学习培训");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_19);
        buttonModel.setName("洗浴/汗蒸");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.drawable.icon_20);
        buttonModel.setName("全部分类");
        data.add(buttonModel);
        return data;
    }
}
