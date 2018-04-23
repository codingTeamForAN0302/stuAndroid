package wumingya.com.studentsystem;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import common.http.GetRequest_Interface;
import common.http.Translation;
import reflash.ApkEntity;
import reflash.MyAdapter;
import reflash.ReFlashListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassFragment extends Fragment implements ReFlashListView.IReflashListener
{
	ArrayList<ApkEntity> apk_list;
	private View myView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{    myView = inflater.inflate(R.layout.tab03, container, false);
		if (myView != null) {
            ViewGroup parent = (ViewGroup) myView.getParent();
            if (parent != null) {
                parent.removeView(myView);
            }

			this.setData();
			this.showList(apk_list);
            return myView;
        }
		return myView;
	}

	/*
   * 下拉刷新功能
   * */
	MyAdapter adapter;
	ReFlashListView listview;
	private void showList(ArrayList<ApkEntity> apk_list) {

		if (adapter == null) {
			listview = (ReFlashListView) myView.findViewById(R.id.listview);
			listview.setInterface(this);
			adapter = new MyAdapter(this.getContext(), apk_list);
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
}
