package menuview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import wumingya.com.studentsystem.R;


/**
 * Created by 13271 on 2018/4/6.
 */

public class slidingmenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private  ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuWidth;
    private  boolean once = false;
    private  boolean isOpen;
    //dp
    private  int mMenuRightPadding =50;

    //未使用自定义属性时，调用
    public slidingmenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
 }
    /**
     * 当使用了自定义属性时，会调用此构造方法
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public slidingmenu(Context context, AttributeSet attrs,int defStyle) {
        super(context, attrs,defStyle);
        // 获取我们定义的属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,defStyle,0);

        int n = a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 50, context
                                    .getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();

        WindowManager wm = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth =outMetrics.widthPixels;

    }

    public slidingmenu(Context context)
    {
        this(context, null);
    }



    //设置子View的宽和高
    //设置自己的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if(!once)
        {
            mWapper = (LinearLayout)getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent =(ViewGroup)mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth -mMenuRightPadding;
            mContent.getLayoutParams().width =mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    //通过设置偏移量，将menu隐藏
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed)
        {
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();//隐藏在左边的宽度
                if(scrollX >= mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen = false;
                }
                else {
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }


    /**
     * 打开菜单
     */
    public void openMenu()
    {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    public void closeMenu()
    {
        if (!isOpen)
            return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    /**
     * 切换菜单
     */
    public void toggle()
    {
        if (isOpen)
        {
            closeMenu();
        } else
        {
            openMenu();
        }
    }
}
