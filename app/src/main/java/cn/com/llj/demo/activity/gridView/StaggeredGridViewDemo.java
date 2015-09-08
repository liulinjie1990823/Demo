package cn.com.llj.demo.activity.gridView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.okhttp.OkHttpUtil;
import com.common.library.llj.okhttp.OkhttpResponseHandle;
import com.common.library.llj.utils.FitY;
import com.common.library.llj.views.AutoScrollViewPager;
import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.github.lzyzsd.randomcolor.RandomColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.netbean.ImageListResponse;
import cn.com.llj.demo.R;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by liulj on 15/9/1.
 */
public class StaggeredGridViewDemo extends DemoActivity {
    private final String TAG = StaggeredGridViewDemo.this.getClass().getSimpleName();
    private StaggeredGridView mGridView;
    private boolean mHasRequestedMore;
    private GridAdapter mAdapter;
    private AutoScrollViewPager mAutoScrollViewPager;
    public final int SAMPLE_DATA_ITEM_COUNT = 30;
    private List<Image> mImages = new ArrayList<Image>();
    private PtrFrameLayout mPtrFrameLayout;
    private List<String> images = new ArrayList<String>();
    String url = "http://cube-server.liaohuqiu.net/api_demo/image-list.php";
    Handler handler = new Handler() {
    };

    @Override
    public int getLayoutId() {
        return R.layout.staggered_gridview_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        initRefreshHeader();
        setTitle("SGV");
        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        View header = getLayoutInflater().inflate(R.layout.staggered_gridview_header, null);
        mAutoScrollViewPager = (AutoScrollViewPager) header.findViewById(R.id.vp_auto);

        mGridView.addHeaderView(header);
    }

    private void initRefreshLayout() {
        mPtrFrameLayout.setResistance(1.7f);
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrameLayout.setDurationToClose(200);
        mPtrFrameLayout.setDurationToCloseHeader(800);
        // default is false
        mPtrFrameLayout.setPullToRefresh(false);
        // default is true
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
    }

    private String mHeadString = "fuck";

    private void initRefreshHeader() {
        // header
//        final RentalsSunHeaderView header = new RentalsSunHeaderView(mBaseFragmentActivity);
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, 30, 0, 20);
//        header.setUp(mPtrFrameLayout);
//
//        mPtrFrameLayout.setLoadingMinTime(1000);
//        mPtrFrameLayout.setDurationToCloseHeader(1500);
//        mPtrFrameLayout.setHeaderView(header);
//        mPtrFrameLayout.addPtrUIHandler(header);
        final StoreHouseHeader header = new StoreHouseHeader(mBaseFragmentActivity);
        header.initWithString(mHeadString);
        header.setTextColor(getResources().getColor(R.color.black));

        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        mPtrFrameLayout.setLoadingMinTime(2000);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
    }

    @Override
    public void addListeners() {
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                Log.d(TAG, "onScrollStateChanged:" + arg1);

            }

            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                Log.d(TAG, "onScroll arg1:" + arg1 + " visibleItemCount:" + arg2 + " totalItemCount:" + arg3);
                // our handling
                if (!mHasRequestedMore) {
                    int lastInScreen = arg1 + arg2;
                    if (lastInScreen >= arg3) {
                        Log.d(TAG, "onScroll lastInScreen - so load more");
                        mHasRequestedMore = true;
                        // onLoadMoreItems();
                    }
                }

            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(mBaseFragmentActivity, "Item Clicked: " + arg2, Toast.LENGTH_SHORT).show();

            }
        });
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(mBaseFragmentActivity, "Item Long Clicked: " + arg2, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                OkHttpUtil.get().get(mBaseFragmentActivity, url, ImageListResponse.class, new OkhttpResponseHandle<ImageListResponse>() {
                    @Override
                    public void onSuccess(ImageListResponse response) {
                    }

                    @Override
                    public void onSuccessByOtherStatus(ImageListResponse response) {
                        mAutoScrollViewPager.setAdapter(new AutoImagesAdapter(images));
                        mAutoScrollViewPager.startAutoScroll();

                        mAdapter.replaceAll(response.getData().getList());
                        mPtrFrameLayout.refreshComplete();
                    }
                });
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    @Override
    public void initViews() {
        images.add("http://gb.cri.cn/mmsource/images/2011/01/19/74/17961619270109147574.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=217309171,2137821995&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1338996410,2084536095&fm=21&gp=0.jpg");
        images.add("http://img4.duitang.com/uploads/item/201406/30/20140630161551_xuLJ4.jpeg");
        images.add("http://img5.imgtn.bdimg.com/it/u=965839582,674998855&fm=21&gp=0.jpg");
        images.add("http://img.zybus.com/uploads/allimg/131221/1-131221161552-50.jpg");

        mAdapter = new GridAdapter(this);
        mGridView.setAdapter(mAdapter);

        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 500);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sgv_dynamic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.col1:
                mGridView.setColumnCount(1);
                break;
            case R.id.col2:
                mGridView.setColumnCount(2);
                break;
            case R.id.col3:
                mGridView.setColumnCount(3);
                break;
        }
        return true;
    }

    class GridAdapter extends QuickAdapter<ImageListResponse.ImageListData.Image> {
        private final Random mRandom = new Random();
        private final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
        private RandomColor randomColor = new RandomColor();
        private int[] colors;

        public GridAdapter(Context context) {
            super(context, R.layout.list_item_sample);
            colors = randomColor.randomColor(10);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, View convertView, ImageListResponse.ImageListData.Image item, int position) {
            if (item != null && item.getPic() != null) {
                int arg0 = helper.getPosition();
                final DynamicHeightImageView imageView = helper.getView(R.id.txt_line1);
                imageView.setHeightRatio(getPositionRatio(arg0));
                Glide.with(mBaseFragmentActivity).load(item.getPic()).crossFade().placeholder(new ColorDrawable(colors[position % 10])).error(R.drawable.progress).override(250, 250).centerCrop()
                        .into(imageView);
            }
        }

        private double getPositionRatio(final int position) {
            // 先从map中获取随机数
            double ratio = sPositionHeightRatios.get(position, 0.0);
            // if not yet done generate and stash the columns height
            // in our real world scenario this will be determined by
            // some match based on the known height and width of the image
            // and maybe a helpful way to get the column height!
            if (ratio == 0) {
                // height will be 1.0 -1.5 the width
                ratio = (mRandom.nextDouble() / 2.0) + 1.0;
                sPositionHeightRatios.append(position, ratio);
            }
            return ratio;
        }
    }


    class AutoImagesAdapter extends PagerAdapter {
        private List<String> images;

        public AutoImagesAdapter(List<String> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size() == 0 ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String url = images.get(position % 6);
            ImageView imageView = null;
            if (url != null) {
                imageView = new ImageView(mBaseFragmentActivity);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mBaseFragmentActivity).load(url).crossFade().placeholder(new ColorDrawable()).error(R.drawable.progress).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .transform(new FitY(mBaseFragmentActivity)).into(imageView);
                container.addView(imageView);
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((ImageView) object));
        }
    }
}
