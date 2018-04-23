package wumingya.com.studentsystem;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import reflash.ApkEntity;
import reflash.ReFlashListView.IReflashListener;

import menuview.slidingmenu;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    ArrayList<ApkEntity> apk_list;
    private slidingmenu mLeftMenu ;

    //声明ViewPager
    private ViewPager viewPager;
    //声明ViewPager的适配器
    private FragmentPagerAdapter mAdapter;

    private List<Fragment> mFragments;

    private LinearLayout mTabHome;
    private LinearLayout mTabMajor;
    private LinearLayout mTabStudent;
    private LinearLayout mTabCollage;

    private RelativeLayout mItem;

    private ImageButton mHomeImg;
    private ImageButton mMajorImg;
    private ImageButton mStudentImg;
    private ImageButton mCollageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化控件
        initEvent();//初始化事件
        initData();//初始化数据
        setTab(0);
    }
    //侧滑菜单功能
    public void toggleMenu(View view)
    {
        mLeftMenu.toggle();
    }

    /*
    * 初始化控件
    * */
    private void initView() {
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        mTabHome = (LinearLayout)findViewById(R.id.id_tab_home);
        mTabMajor = (LinearLayout)findViewById(R.id.id_tab_student);
        mTabStudent = (LinearLayout)findViewById(R.id.id_tab_major);
        mTabCollage = (LinearLayout)findViewById(R.id.id_tab_collage);

        mHomeImg = (ImageButton)findViewById(R.id.id_tab_home_btn);
        mMajorImg = (ImageButton)findViewById(R.id.id_tab_major_btn);
        mStudentImg = (ImageButton)findViewById(R.id.id_tab_student_btn);
        mCollageImg = (ImageButton)findViewById(R.id.id_tab_collage_btn);

        mItem = (RelativeLayout)findViewById(R.id.item4);

        mLeftMenu = (slidingmenu) findViewById(R.id.id_menu);

    }

    private void initEvent() {
        // 设置事件
        mTabStudent.setOnClickListener(this);
        mTabMajor.setOnClickListener(this);
        mTabCollage.setOnClickListener(this);
        mTabHome.setOnClickListener(this);
        mItem.setOnClickListener(this);

    }

    private void initData() {

        mFragments = new ArrayList<Fragment>();
        Fragment mTab01 = new HomeFragment();
        Fragment mTab02 = new MajorFragment();
        Fragment mTab03 = new ClassFragment();
        Fragment mTab04 = new CollageFragment();
        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);
        mFragments.add(mTab04);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments.size();
            }
            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        viewPager.setAdapter(mAdapter);
        //添加ViewPager的切换Tab的监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //ViewPager 改变时改变图标的颜色
            @Override
            public void onPageSelected(int arg0) {
                //获取ViewPager的当前Tab
                int currentItem = viewPager.getCurrentItem();
                resetImg();
                setTab(currentItem);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImg();
        switch (v.getId()) {
            case R.id.id_tab_home:
                setTab(0);
                break;
            case R.id.id_tab_major:
                setTab(1);
                break;
            case R.id.id_tab_student:
                setTab(2);
//                setData();
//                showList(apk_list);
                break;
            case R.id.id_tab_collage:
                setTab(3);
                break;
            case R.id.item4:
                Intent intent = new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
            default:
                break;
        }

    }

    private void setTab(int i)
    {
        //将当前Tab对应的ImageButton设置成绿色
        switch (i) {
            case 0:
                mHomeImg.setImageResource(R.mipmap.icon_blue_1);
                break;
            case 1:
                mMajorImg.setImageResource(R.mipmap.icon_blue_2);
                break;
            case 2:
                mStudentImg.setImageResource(R.mipmap.icon_blue_3);
                break;
            case 3:
                mCollageImg.setImageResource(R.mipmap.icon_blue_4);
                break;
        }
        viewPager.setCurrentItem(i);
    }
    //将所有的图片都变暗
    private void resetImg(){
        mHomeImg.setImageResource(R.mipmap.icon_1);
        mMajorImg.setImageResource(R.mipmap.icon_2);
        mStudentImg.setImageResource(R.mipmap.icon_3);
        mCollageImg.setImageResource(R.mipmap.icon_4);
    }

}



