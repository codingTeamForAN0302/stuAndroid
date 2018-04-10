package wumingya.com.studentsystem;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;


import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import wumingya.com.studentsystem.ReFlashListView.IReflashListener;

import menuview.slidingmenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IReflashListener {
    ArrayList<ApkEntity> apk_list;
    private slidingmenu mLeftMenu ;
    //声明ViewPager
    private ViewPager viewPager;
    //声明ViewPager的适配器
    private PagerAdapter Madapter;
    //用于装载四个Tab的List
    private List<View> mViews = new ArrayList<View>();

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFriends;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;

    private ImageButton mWeixinImg;
    private ImageButton mFriendsImg;
    private ImageButton mAddressImg;
    private ImageButton mSettingsImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();//初始化控件
        initData();//初始化数据
        initEvent();//初始化事件


        mLeftMenu = (slidingmenu) findViewById(R.id.id_menu);

    }
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

    private void initView() {
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        mTabWeixin = (LinearLayout)findViewById(R.id.id_tab_chat);
        mTabAddress = (LinearLayout)findViewById(R.id.id_tab_address);
        mTabFriends = (LinearLayout)findViewById(R.id.id_tab_friend);
        mTabSettings = (LinearLayout)findViewById(R.id.id_tab_settings);

        mWeixinImg = (ImageButton)findViewById(R.id.id_tab_chat_btn);
        mFriendsImg = (ImageButton)findViewById(R.id.id_tab_friend_btn);
        mAddressImg = (ImageButton)findViewById(R.id.id_tab_address_btn);
        mSettingsImg = (ImageButton)findViewById(R.id.id_tab_settings_btn);

        LayoutInflater inflater = LayoutInflater.from(this);
        View tab01 = inflater.inflate(R.layout.tab01, null);
        View tab02 = inflater.inflate(R.layout.tab02, null);
        View tab03 = inflater.inflate(R.layout.tab03, null);
        View tab04 = inflater.inflate(R.layout.tab04, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mViews.add(tab04);
    }



    public void toggleMenu(View view)
    {
        mLeftMenu.toggle();
    }

    private void initEvent() {
        // 设置事件
        mTabAddress.setOnClickListener(this);
        mTabFriends.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);
        mTabWeixin.setOnClickListener(this);

        //添加ViewPager的切换Tab的监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //ViewPager 改变时改变图标的颜色
            @Override
            public void onPageSelected(int arg0) {
                //获取ViewPager的当前Tab
                int currentItem = viewPager.getCurrentItem();
                //将所以的ImageButton设置成灰色
                resetImg();
                //将当前Tab对应的ImageButton设置成绿色
                switch (currentItem) {
                    case 0:
                        mWeixinImg.setImageResource(R.mipmap.icon_blue_1);
                        break;
                    case 1:
                        mFriendsImg.setImageResource(R.mipmap.icon_blue_2);
                        break;
                    case 2:
                        mAddressImg.setImageResource(R.mipmap.icon_blue_3);
                        break;
                    case 3:
                        mSettingsImg.setImageResource(R.mipmap.icon_blue_4);
                        break;

                    default:
                        mWeixinImg.setImageResource(R.mipmap.icon_blue_1);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initData() {
        //初始化ViewPager的适配器
        Madapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }
        };
        //设置ViewPager的适配器
        viewPager.setAdapter(Madapter);
    }


    @Override
    public void onClick(View v) {
        //先将四个ImageButton都设置成灰色
        resetImg();
        switch (v.getId()) {
            case R.id.id_tab_chat:
                //设置viewPager的当前Tab
                viewPager.setCurrentItem(0);
                mWeixinImg.setImageResource(R.mipmap.icon_blue_1);
                setData();
                showList(apk_list);
                break;
            case R.id.id_tab_friend:
                viewPager.setCurrentItem(1);
                mFriendsImg.setImageResource(R.mipmap.icon_blue_2);
                break;
            case R.id.id_tab_address:
                viewPager.setCurrentItem(2);
                mAddressImg.setImageResource(R.mipmap.icon_blue_3);
                break;
            case R.id.id_tab_settings:
                viewPager.setCurrentItem(3);
                mSettingsImg.setImageResource(R.mipmap.icon_blue_4);
                break;
            default:
                break;
        }
    }

    //将所有的图片都变暗
    private void resetImg(){
        mWeixinImg.setImageResource(R.mipmap.icon_1);
        mFriendsImg.setImageResource(R.mipmap.icon_2);
        mAddressImg.setImageResource(R.mipmap.icon_3);
        mSettingsImg.setImageResource(R.mipmap.icon_4);
    }
}
