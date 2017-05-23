package cn.com.llj.demo.activity.other;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.common.library.llj.utils.LogUtilLj;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 16/4/28.
 */
public class RulersActivity extends DemoActivity {
    @Bind(R.id.rv_rulers)
    RecyclerView recyclerView;

    @Bind(R.id.tv_show)
    TextView tv_show;

    @Bind(R.id.wrap)
    FrameLayout wrap;

    @Override
    public int getLayoutId() {
        return R.layout.rulers_activity;
    }

    private float halfScreeen;
    private float current;
    private int padding;

    @Override
    public void initViews() {
        super.initViews();

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                halfScreeen = (int) ((findViewById(android.R.id.content).getHeight()) / 2.0);

//                padding = (int) (halfScreeen - 14.0f * getResources().getDisplayMetrics().density * 15);
//                recyclerView.setPadding(0, padding, 0, padding);


                current = 141.5f - ((halfScreeen / (14.0f * getResources().getDisplayMetrics().density)) / 10.0f);
//                current = 140;

                tv_show.setText(current + "");

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    private int totalDy = 0;

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        totalDy -= dy;
                        LogUtilLj.LLJe("recyclerView.getScrollY():" + totalDy);
                        float adadadad = current - ((Math.abs(totalDy) / (14.0f * getResources().getDisplayMetrics().density)) / 10.0f);
                        LogUtilLj.LLJe("adad:" + adadadad);
                        tv_show.setText(adadadad + "");
                    }
                });
//        recyclerView.addItemDecoration(null);
                recyclerView.setLayoutManager(new LinearLayoutManager(mBaseFragmentActivity));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new RulersAdapter());
            }
        });

//        recyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                int adadaff = wrap.getHeight();
//                int asdad = findViewById(android.R.id.content).getHeight();
//                int asdadadad = BaseApplication.STATUS_BAR_HEIGHT;
//                int asdadadadasdad = BaseApplication.DISPLAY_HEIGHT;
//            }
//        });
    }

    private class RulersAdapter extends RecyclerView.Adapter<RulersAdapter.RulersHolder> {
        @Override
        public RulersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RulersHolder(LayoutInflater.from(mBaseFragmentActivity).inflate(R.layout.rulers_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RulersHolder holder, int position) {
            holder.item_big_ruler_ver_show.setText((141 - position * 1) + "");
        }

        @Override
        public int getItemCount() {
            return 102;
        }

        class RulersHolder extends RecyclerView.ViewHolder {
            private TextView item_big_ruler_ver_show;

            public RulersHolder(View itemView) {
                super(itemView);
                item_big_ruler_ver_show = (TextView) itemView.findViewById(R.id.item_big_ruler_ver_show);
            }
        }
    }
}
