package cn.com.llj.demo.activity.titlebar;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.library.llj.utils.ScrollUtilLj;
import com.common.library.llj.views.KenBurnsView;

import java.util.ArrayList;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

public class NoBoringActionBarDemo extends DemoActivity {
    private int mActionBarTitleColor;
    private int mHeaderHeight;// 头部
    private int mMinHeaderTranslation;// 向上移动的距离，负数
    private static final String TAG = "NoBoringActionBarActivity";
    private ListView mListView;
    private ImageView mHeaderLogo;
    private View mHeader;
    private View mPlaceHolderView;

    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;
    private KenBurnsView mHeaderPicture;
    private ImageView icon;

    @Override
    public void getIntentData() {
        mHeaderHeight = 500;
        mMinHeaderTranslation = -mHeaderHeight + 112;
    }

    @Override
    public int getLayoutId() {
        return R.layout.no_boring_actionbar_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        icon = (ImageView) findViewById(R.id.iv_image);
        mListView = (ListView) findViewById(R.id.listview);
        mHeader = findViewById(R.id.header);
        mHeaderPicture = (KenBurnsView) findViewById(R.id.header_picture);
        mHeaderPicture.setResourceIds(R.drawable.picture0, R.drawable.picture1);
        mHeaderLogo = (ImageView) findViewById(R.id.header_logo);

        mActionBarTitleColor = getResources().getColor(android.R.color.white);

        mSpannableString = new SpannableString("Beautiful picture");
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);

        setupListView();

    }

    private void setupListView() {
        ArrayList<String> FAKES = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            FAKES.add("entry " + i);
        }
        mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
        mListView.addHeaderView(mPlaceHolderView);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FAKES));
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int scrollY = getScrollY();
                // header向上移动
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
                float ratio = ScrollUtilLj.getFloat(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
                calculate(mHeaderLogo, icon, ratio);
                setTitleAlpha(ScrollUtilLj.getFloat(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            }
        });
    }

    private void setTitleAlpha(float alpha) {
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) findViewById(R.id.title)).setText(mSpannableString);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        calculate(mHeaderLogo, icon, 0);
    }

    /**
     * @param headLogo
     * @param actionIcon
     * @param ratio
     */
    private void calculate(View headLogo, View actionIcon, float ratio) {
        if (headLogo.getMeasuredWidth() != 0) {
            // 用 RectF把int转换成float,方便后面float计算
            getOnScreenRect(mRect1, headLogo);
            getOnScreenRect(mRect2, actionIcon);
            float hTranslation = (mRect1.left + mRect1.right - mRect2.left - mRect2.right) / 2;
            float vTranslation = (mRect1.top + mRect1.bottom - mRect2.top - mRect2.bottom) / 2;
            float scaleX = 1.0F + ratio * (mRect2.width() / mRect1.width() - 1.0F);
            float scaleY = 1.0F + ratio * (mRect2.width() / mRect1.width() - 1.0F);
            float translationX = -ratio * hTranslation;
            float translationY = -ratio * vTranslation;
            // 向左移动
            headLogo.setTranslationX(translationX);
            // 向上移动
            headLogo.setTranslationY(translationY - mHeader.getTranslationY());
            headLogo.setScaleX(scaleX);
            headLogo.setScaleY(scaleY);
        }
    }

    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mPlaceHolderView.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    public class AlphaForegroundColorSpan extends ForegroundColorSpan {

        private float mAlpha;

        public AlphaForegroundColorSpan(int color) {
            super(color);
        }

        public AlphaForegroundColorSpan(Parcel src) {
            super(src);
            mAlpha = src.readFloat();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(mAlpha);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getAlphaColor());
        }

        public void setAlpha(float alpha) {
            mAlpha = alpha;
        }

        public float getAlpha() {
            return mAlpha;
        }

        private int getAlphaColor() {
            int foregroundColor = getForegroundColor();
            return Color.argb((int) (mAlpha * 255), Color.red(foregroundColor), Color.green(foregroundColor), Color.blue(foregroundColor));
        }
    }
}
