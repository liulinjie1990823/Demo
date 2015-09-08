package cn.com.llj.demo.activity.viewpager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.common.library.llj.pagetransformer.AccordionPageTransformer;
import com.common.library.llj.views.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 *
 */
public class PagerSlidingTabStripDemo extends DemoActivity {
    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;
    private List<Drawable> mDrawables = new ArrayList<Drawable>();

    @Override
    public int getLayoutId() {
        return R.layout.pager_sliding_tabstrip_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setUnderlineHeight(10);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setPageTransformer(true, new AccordionPageTransformer());
        // pager.setPageTransformer(true, new DepthPageTransformer());
        // pager.setPageTransformer(true, new FlipPageTransformer());
        // pager.setPageTransformer(true, new RotationPageTransformer());
        // pager.setPageTransformer(true, new ScalePageTransformer());
        // pager.setPageTransformer(true, new ZoomOutPageTransformer());
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        changeColor(currentColor);
//        mDrawables.add(getResources().getDrawable(R.drawable.photo1));
//        mDrawables.add(getResources().getDrawable(R.drawable.photo2));
//        mDrawables.add(getResources().getDrawable(R.drawable.photo3));
//        mDrawables.add(getResources().getDrawable(R.drawable.photo4));
//        mDrawables.add(getResources().getDrawable(R.drawable.photo5));
//        mDrawables.add(getResources().getDrawable(R.drawable.photo6));
    }

    @Override
    public void addListeners() {
        // pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
        // @Override
        // public void onPageSelected(int position) {
        // Bitmap bitmap = BitmapUtilLj.drawableToBitmap(mDrawables.get(position));
        // Palette.generateAsync(bitmap, new PaletteAsyncListener() {
        //
        // @Override
        // public void onGenerated(Palette arg0) {
        // Palette.Swatch vibrant = arg0.getVibrantSwatch();
        // if (vibrant != null) {
        // changeColor(vibrant.getRgb());
        // }
        //
        // }
        // });
        // }
        // });
        tabs.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Log.i("test", "onPageSelected:page" + arg0);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                Log.i("test", "onPageScrolled:" + "arg0:" + arg0 + "," + "arg1:" + arg1 + "," + "arg2:" + arg2);

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case 0:
                        Log.i("test", "onPageScrollStateChanged:" + "idle");
                        break;
                    case 1:
                        Log.i("test", "onPageScrollStateChanged:" + "dragging");
                        break;
                    case 2:
                        Log.i("test", "onPageScrollStateChanged:" + "setting");
                        break;

                    default:
                        Log.i("test", "onPageScrollStateChanged:" + arg0);
                        break;
                }

            }
        });
    }


    private void changeColor(int newColor) {
        tabs.setIndicatorColor(newColor);
        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    getSupportActionBar().setBackgroundDrawable(ld);
                }

            } else {
                TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    getSupportActionBar().setBackgroundDrawable(td);
                }

                td.startTransition(200);

            }

            oldBackground = ld;

            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }

        currentColor = newColor;

    }

    public void onColorClicked(View v) {

        int color = Color.parseColor(v.getTag().toString());
        changeColor(color);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getSupportActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Categories", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid", "Top New Free", "Trending"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return SuperAwesomeCardFragment.newInstance(position);
        }

    }

    public static class SuperAwesomeCardFragment extends Fragment {

        private static final String ARG_POSITION = "position";

        private int position;

        public static SuperAwesomeCardFragment newInstance(int position) {
            SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
            Bundle b = new Bundle();
            b.putInt(ARG_POSITION, position);
            f.setArguments(b);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            position = getArguments().getInt(ARG_POSITION);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

            FrameLayout fl = new FrameLayout(getActivity());
            fl.setLayoutParams(params);

            final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

            TextView v = new TextView(getActivity());
            params.setMargins(margin, margin, margin, margin);
            v.setLayoutParams(params);
            v.setLayoutParams(params);
            v.setGravity(Gravity.CENTER);
            v.setBackgroundResource(R.drawable.background_card);
            v.setText("CARD " + (position + 1));

            fl.addView(v);
            return fl;
        }

    }

}
