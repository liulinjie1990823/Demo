package cn.com.llj.demo.activity.gridView;

import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.library.llj.base.BaseApplication;
import com.common.library.llj.base.BasePopupWindow;
import com.common.library.llj.utils.DensityUtils;
import com.common.library.llj.utils.PhotoUtilLj;
import com.common.library.llj.utils.PhotoUtilLj.ExternalImageInfo;
import com.common.library.llj.utils.TimeUitlLj;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by liulj on 15/9/7.
 */
public class StickyGridHeadersDemo extends DemoActivity {
    private StickyGridHeadersGridView mGridView;
    private ImageView mTakePhotoIv;
    private LinearLayout mPhotoSelectLi, mBottomLi;
    private BasePopupWindow mWindow;// 显示目录
    private ListView mListView;// 显示目录结构的列表
    private ImageAdapter mGridAdapter;
    private FileDirAdapter mFileDirAdapter;
    private List<PhotoUtilLj.ExternalImageInfo> mExternalImageInfos = new ArrayList<PhotoUtilLj.ExternalImageInfo>();// 从sdcard上获取的所有图片文件
    private HashMap<String, List<ExternalImageInfo>> mDirMap = new HashMap<String, List<ExternalImageInfo>>();// 安日期分模块存放
    private List<String> mDirKeyList = new ArrayList<String>();// 存放目录key的集合
    private List<DataMap> mPosition = new ArrayList<DataMap>();// 存放不同日期的开始项
    private SmoothProgressBar mSmoothProgressBar;

    class DataMap {
        private String date;
        private int position;
        private boolean selected;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.sticky_grid_headers_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        mSmoothProgressBar = (SmoothProgressBar) findViewById(R.id.google_now);
        mTakePhotoIv = (ImageView) findViewById(R.id.iv_take_photo);
        mTakePhotoIv.getLayoutParams().width = (int) ((BaseApplication.DISPLAY_WIDTH - DensityUtils.dp2px(mBaseFragmentActivity, 34)) / 3.0);
        mTakePhotoIv.getLayoutParams().height = (int) ((BaseApplication.DISPLAY_WIDTH - DensityUtils.dp2px(mBaseFragmentActivity, 34)) / 3.0);
        mGridView = (StickyGridHeadersGridView) findViewById(R.id.gridview);
        mGridView.setAreHeadersSticky(false);
        mPhotoSelectLi = (LinearLayout) findViewById(R.id.li_photo_select);
        mBottomLi = (LinearLayout) findViewById(R.id.li_bottom);
    }

    @Override
    public void addListeners() {
        mPhotoSelectLi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showWindow();
            }
        });
    }

    private void showWindow() {
        if (mDirKeyList.size() != 0 && mWindow != null) {
            mFileDirAdapter.notifyDataSetChanged();
            mWindow.showAtLocation(mBottomLi, Gravity.BOTTOM, 0, mBottomLi.getMeasuredHeight());
        }
    }

    @Override
    public void initViews() {
        Thread.currentThread().getId();
        mSmoothProgressBar.setVisibility(View.VISIBLE);
        mGridAdapter = new ImageAdapter();
        mGridView.setAdapter(mGridAdapter);
        Task.callInBackground(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Thread.currentThread().getId();
                List<ExternalImageInfo> externalImageInfos = PhotoUtilLj.getMediaStoreImages(mBaseFragmentActivity);
                // 获得所有图片文件
                if (externalImageInfos != null) {
                    mExternalImageInfos.clear();
                    mExternalImageInfos.addAll(externalImageInfos);
                }
                // 根据文件日期进行分模块存放在map中
                if (mExternalImageInfos.size() != 0) {
                    Map<String, List<ExternalImageInfo>> map = PhotoUtilLj.getAllDirByImages(mExternalImageInfos);
                    if (map != null) {
                        mDirMap.clear();
                        mDirMap.putAll(map);
                        mDirKeyList.clear();
                        // 遍历所有的key，存放在目录集合中
                        for (String key : mDirMap.keySet()) {
                            mDirKeyList.add(key);
                        }
                    }
                }
                return true;
            }
        }).continueWith(new Continuation<Boolean, Void>() {

            @Override
            public Void then(Task<Boolean> task) throws Exception {
                if (task.getResult()) {
                    mGridAdapter.notifyDataSetChanged();
                    updatePosition(mExternalImageInfos.size());
                }
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
        //
        initPopupWindow();
    }

    @Override
    public void requestOnCreate() {

    }

    private void initPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.image_file_dialog_layout, null);
        mListView = (ListView) view.findViewById(R.id.lv_list);
        mFileDirAdapter = new FileDirAdapter();
        mListView.setAdapter(mFileDirAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mWindow.dismiss();
                if (mDirKeyList.get(position) != null) {
                    if (mDirMap.get(mDirKeyList.get(position)) != null) {
                        mExternalImageInfos.clear();
                        mPosition.clear();
                        mExternalImageInfos.addAll(mDirMap.get(mDirKeyList.get(position)));
                        mGridAdapter.notifyDataSetChanged();
                        updatePosition(mExternalImageInfos.size());
                    }
                }

            }
        });
        mWindow = new BasePopupWindow(view, BaseApplication.DISPLAY_WIDTH, (int) (BaseApplication.DISPLAY_HEIGHT / 2.0), this);
    }

    private void updatePosition(int count) {
        for (int i = 0; i < count; i++) {
            String dateStr = null;
            dateStr = TimeUitlLj.millisecondsToString(9, Long.parseLong(mExternalImageInfos.get(i).getDate().trim() + "000"));
            if (mPosition.size() == 0) {
                DataMap dataMap = new DataMap();
                dataMap.setDate(dateStr);
                dataMap.setPosition(i);
                mPosition.add(dataMap);
            } else {
                DataMap dataMap = mPosition.get(mPosition.size() - 1);
                if (dataMap.getDate() != null && !dateStr.equals(dataMap.getDate())) {
                    DataMap dataMap2 = new DataMap();
                    dataMap2.setDate(dateStr);
                    dataMap2.setPosition(i);
                    mPosition.add(dataMap2);
                }
            }
        }
    }

    /**
     * 图片文件夹适配器
     *
     * @author liulj
     */
    class FileDirAdapter extends BaseAdapter {
        private int width = (int) (BaseApplication.DISPLAY_WIDTH / 5.0);

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.file_dir_item, null);
                holder = new ViewHolder();
                holder.dirIv = (ImageView) convertView.findViewById(R.id.iv_dir);
                holder.dirIv.getLayoutParams().width = width;
                holder.dirIv.getLayoutParams().height = width;
                holder.dirName = (TextView) convertView.findViewById(R.id.tv_dir_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (mDirKeyList.get(position) != null) {
                holder.dirName.setText(mDirKeyList.get(position));
                if (mDirMap.get(mDirKeyList.get(position)) != null) {
                    List<ExternalImageInfo> imageInfos = mDirMap.get(mDirKeyList.get(position));
                    holder.dirName.append("(" + imageInfos.size() + ")");
                    if (imageInfos.get(0) != null && imageInfos.get(0).getPath() != null) {
                        Glide.with(mBaseFragmentActivity).load(new File(imageInfos.get(0).getPath())).centerCrop().crossFade().override(width, width).into(holder.dirIv);
                    }
                }
            }
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return mDirKeyList.size();
        }

        class ViewHolder {
            private ImageView dirIv;
            private TextView dirName;
        }

    }

    /**
     * 分日期设置图片适配器
     *
     * @author liulj
     */
    class ImageAdapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter {
        private int width = (int) ((BaseApplication.DISPLAY_WIDTH - DensityUtils.dp2px(mBaseFragmentActivity, 34)) / 3.0);

        @Override
        public int getCount() {
            return mExternalImageInfos == null ? 0 : mExternalImageInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public long getHeaderId(int position) {
            long date = 0;
            String dateStr = null;
            dateStr = TimeUitlLj.millisecondsToString(9, Long.parseLong(mExternalImageInfos.get(position).getDate().trim() + "000"));
            try {
                date = TimeUitlLj.stringToMilliseconds(9, dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        @Override
        public View getHeaderView(final int position, View convertView, ViewGroup parent) {
            ViewHolderH viewHolderH;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.photo_select_header_item, null);
                viewHolderH = new ViewHolderH();
                viewHolderH.date = (TextView) convertView.findViewById(R.id.tv_date);
                viewHolderH.all = (ImageView) convertView.findViewById(R.id.iv_select_all);
                convertView.setTag(viewHolderH);
            } else {
                viewHolderH = (ViewHolderH) convertView.getTag();
            }
            if ("ArchivesPublishActivity".equals(getIntent().getStringExtra("from"))) {
                viewHolderH.all.setVisibility(View.GONE);
            } else if ("ManageActivity".equals(getIntent().getStringExtra("from"))) {
                viewHolderH.all.setVisibility(View.VISIBLE);
            }
            final ExternalImageInfo imageInfo = mExternalImageInfos.get(position);
            if (imageInfo != null) {
                String date = null;
                if (imageInfo.getDate() != null) {
                    date = TimeUitlLj.millisecondsToString(9, Long.parseLong(imageInfo.getDate().trim() + "000"));
                }
                viewHolderH.date.setText(date == null ? "" : date.trim());
                viewHolderH.all.setOnClickListener(new MyClickListener(date, mExternalImageInfos));
            }
            return convertView;
        }

        class MyClickListener implements View.OnClickListener {
            private String date;
            private List<ExternalImageInfo> externalImageInfos;

            public MyClickListener(String date, List<ExternalImageInfo> externalImageInfos) {
                this.date = date;
                this.externalImageInfos = externalImageInfos;
            }

            @Override
            public void onClick(View v) {
                DataMap dataMap = null;
                int from = 0;
                int to = 0;
                for (int i = 0; i < mPosition.size(); i++) {
                    if (mPosition.get(i) != null && mPosition.get(i).getDate().equals(date)) {
                        dataMap = mPosition.get(i);
                        from = mPosition.get(i).getPosition();
                        if (i + 1 != mPosition.size()) {
                            to = mPosition.get(i + 1).getPosition();
                        } else {
                            to = externalImageInfos.size();
                        }
                        break;
                    }
                }
                for (int i = 0; i < externalImageInfos.size(); i++) {
                    if (i >= from && i < to) {
                        if (!dataMap.isSelected()) {
                            if (externalImageInfos.get(i).getStatus() == 0) {
                                externalImageInfos.get(i).setStatus(1);
                                // YoujiameiApplication.mSeleteImageInfos.add(imageInfos.get(i));
                            }
                        } else {
                            if (externalImageInfos.get(i).getStatus() == 1) {
                                externalImageInfos.get(i).setStatus(0);
                                // YoujiameiApplication.mSeleteImageInfos.remove(imageInfos.get(i));
                            }
                        }
                    }
                }
                dataMap.setSelected(!dataMap.isSelected());
                mGridAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.photo_select_item, null);
                viewHolder = new ViewHolder();
                viewHolder.imageBackRl = (RelativeLayout) convertView.findViewById(R.id.rl_photo_item);
                viewHolder.imageBackRl.getLayoutParams().height = (int) ((BaseApplication.DISPLAY_WIDTH - DensityUtils.dp2px(mBaseFragmentActivity, 34)) / 3.0);
                viewHolder.bgIv = (ImageView) convertView.findViewById(R.id.iv_bg);
                viewHolder.tagIv = (ImageView) convertView.findViewById(R.id.iv_tag);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final ExternalImageInfo imageInfo = mExternalImageInfos.get(position);
            if (imageInfo != null && imageInfo.getPath() != null) {
                // if ("ArchivesPublishActivity".equals(getIntent().getStringExtra("from"))) {
                // if (imageInfo.getStatus() == 0) {
                // // 可以选中的状态
                // viewHolder.tagIv.setImageDrawable(null);
                // } else if (imageInfo.getStatus() == 1) {
                // // 可以选中的状态
                // viewHolder.tagIv.setImageDrawable(getResources().getDrawable(R.drawable.public_photo_select_img));
                // }
                // } else if ("ManageActivity".equals(getIntent().getStringExtra("from"))) {
                // if (imageInfo.getStatus() == 2 || imageInfo.getStatus() == 3 || imageInfo.getStatus() == 4) {
                // // 已经插入数据库
                // viewHolder.tagIv.setImageDrawable(getResources().getDrawable(R.drawable.photo_select_has_publish_img));
                // } else if (imageInfo.getStatus() == 0) {
                // // 可以选中的状态
                // viewHolder.tagIv.setImageDrawable(null);
                // } else if (imageInfo.getStatus() == 1) {
                // // 可以选中的状态
                // viewHolder.tagIv.setImageDrawable(getResources().getDrawable(R.drawable.public_photo_select_img));
                // }
                // }
                File file = new File(imageInfo.getPath());
                if (file.exists()) {
                    Log.i("llj", "file.getAbsolutePath()" + file.getAbsolutePath());
                    Glide.with(mBaseFragmentActivity).load(file).centerCrop().crossFade().override(width, width).into(viewHolder.bgIv);
                }
                viewHolder.imageBackRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if ("ArchivesPublishActivity".equals(getIntent().getStringExtra("from"))) {
                        // if (imageInfo.getStatus() == 0) {
                        // if (YoujiameiApplication.mSeleteImageInfos2.size() >= mCanSelectInt) {
                        // Toast.makeText(PhotoSelectActivity.this, "您一次最多只能选择" + mCanSelectInt + "张图片！", Toast.LENGTH_SHORT).show();
                        // return;
                        // }
                        // // 可以选中的状态下设置选中（0->1）
                        // YoujiameiApplication.mSeleteImageInfos2.add(imageInfo);
                        // viewHolder.tagIv.setImageDrawable(getResources().getDrawable(R.drawable.public_photo_select_img));
                        // imageInfo.setStatus(1);
                        // } else if (imageInfo.getStatus() == 1) {
                        // // 选中状态下去掉勾选（1->0）
                        // YoujiameiApplication.mSeleteImageInfos2.remove(imageInfo);
                        // viewHolder.tagIv.setImageDrawable(null);
                        // imageInfo.setStatus(0);
                        // }
                        // } else if ("ManageActivity".equals(getIntent().getStringExtra("from"))) {
                        // if (imageInfo.getStatus() == 0) {
                        // // 可以选中的状态下设置选中（0->1）
                        // YoujiameiApplication.mSeleteImageInfos.add(imageInfo);
                        // viewHolder.tagIv.setImageDrawable(getResources().getDrawable(R.drawable.public_photo_select_img));
                        // imageInfo.setStatus(1);
                        // } else if (imageInfo.getStatus() == 1) {
                        // // 选中状态下去掉勾选（1->0）
                        // YoujiameiApplication.mSeleteImageInfos.remove(imageInfo);
                        // viewHolder.tagIv.setImageDrawable(null);
                        // imageInfo.setStatus(0);
                        // } else {
                        // // 2,3,4状态
                        // Toast.makeText(PhotoSelectActivity.this, "该照片已经上传！", Toast.LENGTH_SHORT).show();
                        // }
                        //
                        // }

                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            private RelativeLayout imageBackRl;
            private ImageView tagIv;
            private ImageView bgIv;
        }

        class ViewHolderH {
            private TextView date;
            private ImageView all;
        }
    }
}
