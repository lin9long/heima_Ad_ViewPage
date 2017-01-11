package com.linsaya.heima_ad_viewpage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp_ad;
    private int[] mViewPageIdList;
    private String[] mDesIdList;
    private List<ImageView> mViewPageItemList;
    private ImageView mImageview;
    private TextView tv_des;
    private LinearLayout ll_point_container;
    private View mPointview;
    private List<View> mPointViews;
    private int lastposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        //将0号的点位可用状态设置为true，初始化界面
        ll_point_container.getChildAt(0).setEnabled(true);
        vp_ad.setAdapter(new MyAdapter());
        vp_ad.setOnPageChangeListener(this);
        vp_ad.setCurrentItem(5000000);
    }

    private void initData() {
        //添加图片资源id
        mPointViews = new ArrayList<>();
        mViewPageIdList = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
        mDesIdList = new String[]{"图片1", "图片2", "图片3", "图片4", "图片5"};
        mViewPageItemList = new ArrayList<>();
        for (int i = 0; i < mViewPageIdList.length; i++) {
            mImageview = new ImageView(this);
            mImageview.setImageResource(mViewPageIdList[i]);
            mViewPageItemList.add(mImageview);

            mPointview = new View(this);
            mPointview.setBackgroundResource(R.drawable.selector_bg_point);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i != 0)
                layoutParams.leftMargin = 20;
            //将所有的点位可用状态设置为false
            mPointview.setEnabled(false);
            ll_point_container.addView(mPointview, layoutParams);
        }

    }

    private void initUi() {
        vp_ad = (ViewPager) findViewById(R.id.vp_ad);
        tv_des = (TextView) findViewById(R.id.tv_des);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_pointContainer);
    }

    //pageadapter的固定用法
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //固定写法
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //container时imageview的容器，需要往里面添加child
            int newPosition = position % mViewPageItemList.size();
            ImageView imageView = mViewPageItemList.get(newPosition);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁child的方法必须要重写
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % mViewPageItemList.size();
        tv_des.setText(mDesIdList[newPosition]);
        //将前一个位置的点禁用，记录当前点的位置，将下一个点位设置为可用
        ll_point_container.getChildAt(lastposition).setEnabled(false);
        lastposition = newPosition;
        ll_point_container.getChildAt(newPosition).setEnabled(true);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
