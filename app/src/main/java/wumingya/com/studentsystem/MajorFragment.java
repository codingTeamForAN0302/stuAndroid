package wumingya.com.studentsystem;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import foldingmenu.FoldingLayout;
import foldingmenu.OnFoldListener;

public class MajorFragment extends Fragment
{
	private LinearLayout mTrafficLayout;
	private RelativeLayout mTrafficBarLayout;
	private FoldingLayout mTrafficFoldingLayout;

	private View myView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		myView = inflater.inflate(R.layout.tab02, container, false);
		mTrafficLayout = (LinearLayout)  myView.findViewById(R.id.traffic_layout);
		mTrafficBarLayout = (RelativeLayout)  myView.findViewById(R.id.traffic_bar_layout);
		mTrafficFoldingLayout = (FoldingLayout)  myView.findViewById(R.id.traffic_item);

		final View mBottomView =  myView.findViewById(R.id.bottom_view);
		mTrafficBarLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				Log.i("Tag","触发点击事件");
				handleAnimation(v, mTrafficFoldingLayout, mTrafficLayout, mBottomView);
//				Log.i("Tag","点击事件over");
			}
		});
		return myView;
	}

//	@Override
//	public void onClick(View v) {
//		Log.i("Tag","判别点击事件");
//		// TODO Auto-generated method stub
//		int id = v.getId();
//		switch (id) {
//			case R.id.traffic_bar_layout:
//				final View mBottomView =  myView.findViewById(R.id.bottom_view);
//				handleAnimation(v, mTrafficFoldingLayout, mTrafficLayout, mBottomView);
//				break;
//			default:
//				Log.i("Tag","触发未知点击事件");
//				break;
//		}
//
//	}


	private void handleAnimation(final View bar, final FoldingLayout foldingLayout, View parent, final View nextParent) {

		final ImageView arrow = (ImageView) parent.findViewById(R.id.traffic_arrow);

		foldingLayout.setFoldListener(new OnFoldListener() {

			@Override
			public void onStartFold(float foldFactor) {

//                bar.setClickable(true);
//                arrow.setBackgroundResource(R.drawable.service_arrow_up);
//                resetMarginToTop(foldingLayout, foldFactor, nextParent);
			}

			@Override
			public void onFoldingState(float foldFactor, float foldDrawHeight) {
//                bar.setClickable(false);
//                resetMarginToTop(foldingLayout, foldFactor, nextParent);
			}

			@Override
			public void onEndFold(float foldFactor) {

//                bar.setClickable(true);
//                arrow.setBackgroundResource(R.drawable.service_arrow_down);
//                resetMarginToTop(foldingLayout, foldFactor, nextParent);
			}
		});

		animateFold(foldingLayout, 1000);

	}
	@SuppressLint("NewApi")
	public void animateFold(FoldingLayout foldLayout, int duration) {
		float foldFactor = foldLayout.getFoldFactor();

		ObjectAnimator animator = ObjectAnimator.ofFloat(foldLayout, "foldFactor", foldFactor, foldFactor > 0 ? 0 : 1);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.setRepeatCount(0);
		animator.setDuration(duration);
		animator.setInterpolator(new AccelerateInterpolator());
		animator.start();
	}
//    private void resetMarginToTop(View view, float foldFactor, View nextParent) {
//
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) nextParent.getLayoutParams();
//        lp.topMargin =(int)( - view.getMeasuredHeight() * foldFactor) + dp2px(MainActivity.this, 10);
//        nextParent.setLayoutParams(lp);
//    }
//    public final static int dp2px(Context context, float dpValue) {
//        float density = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * density + 0.5f);
//    }
}
