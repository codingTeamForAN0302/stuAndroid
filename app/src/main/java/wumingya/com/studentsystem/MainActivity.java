package wumingya.com.studentsystem;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import common.http.GetRequest_Interface;
import common.http.Translation;
import reflash.ApkEntity;
import reflash.MyAdapter;
import reflash.ReFlashListView;
import reflash.ReFlashListView.IReflashListener;

import menuview.slidingmenu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends FragmentActivity implements View.OnClickListener,IReflashListener{
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
    * 下拉刷新功能
    * */
    MyAdapter adapter;
    ReFlashListView listview;
    private void showList(ArrayList<ApkEntity> apk_list) {
        if (adapter == null) {
            listview = (ReFlashListView) findViewById(R.id.listview);
            listview.setInterface(this);
            adapter = new MyAdapter(this, apk_list);
            listview.setAdapter(adapter);
        } else {
            adapter.onDateChange(apk_list);
        }
    }
    private void setData() {
        apk_list = new ArrayList<ApkEntity>();
        for (int i = 0; i < 5; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("默认数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(entity);
        }
    }
    private void setReflashData() {
        for (int i = 0; i < 2; i++) {
            ApkEntity entity = new ApkEntity();
            entity.setName("刷新数据");
            entity.setDes("这是一个神奇的应用");
            entity.setInfo("50w用户");
            apk_list.add(0,entity);
        }
    }
    @Override
    public void onReflash() {

        request();

        // TODO Auto-generated method stub\
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                setReflashData();
                //通知界面显示
                showList(apk_list);
                //通知listview 刷新数据完毕；
                listview.reflashComplete();
            }
        }, 2000);
    }

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                response.body().show();
                Log.i("tag","接受成功");
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
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
        Fragment mTab03 = new StudentFragment();
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
                setData();
                showList(apk_list);
                break;
            case R.id.id_tab_major:
                setTab(1);
                break;
            case R.id.id_tab_student:
                setTab(2);
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



