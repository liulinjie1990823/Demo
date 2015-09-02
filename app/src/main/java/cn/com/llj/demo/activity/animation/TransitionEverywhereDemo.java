package cn.com.llj.demo.activity.animation;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionInflater;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/1.
 */
public class TransitionEverywhereDemo extends DemoActivity implements RadioGroup.OnCheckedChangeListener {
    private Scene mScene1;
    private Scene mScene2;
    private Scene mScene3;
    private TransitionManager mTransitionManagerForScene3;
    @Bind(R.id.scene_root)
    ViewGroup mSceneRoot;
    @Bind(R.id.select_scene)
    RadioGroup mRadioGroup;

    @Override
    public int getLayoutId() {
        return R.layout.transition_everywhere_demo;
    }

    @Override
    public void findViews(Bundle savedInstanceState) {
        mRadioGroup.setOnCheckedChangeListener(this);
        mScene1 = new Scene(mSceneRoot, mSceneRoot.findViewById(R.id.container));
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene2, this);
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene3, this);
        mTransitionManagerForScene3 = TransitionInflater.from(this).inflateTransitionManager(R.anim.scene3_transition_manager, mSceneRoot);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.select_scene_1: {
                TransitionManager.go(mScene1);
                break;
            }
            case R.id.select_scene_2: {
                TransitionSet set = new TransitionSet();
                Slide slide = new Slide(Gravity.LEFT);
                slide.addTarget(R.id.transition_title);
                set.addTransition(slide);
                set.addTransition(new ChangeBounds());
                set.addTransition(new ChangeImageTransform());
                set.setOrdering(TransitionSet.ORDERING_TOGETHER);
                set.setDuration(350);
                TransitionManager.go(mScene2, set);
                break;
            }
            case R.id.select_scene_3: {
                // You can also start a transition with a custom TransitionManager.
                mTransitionManagerForScene3.transitionTo(mScene3);
                break;
            }
            case R.id.select_scene_4: {
                // Alternatively, transition can be invoked dynamically without a Scene.
                // For this, we first call TransitionManager.beginDelayedTransition().
                TransitionManager.beginDelayedTransition(mSceneRoot, new ChangeBounds());
                // Then, we can just change view properties as usual.
                View square = mSceneRoot.findViewById(R.id.transition_square);
                ViewGroup.LayoutParams params = square.getLayoutParams();
                int newSize = 200;
                params.width = newSize;
                params.height = newSize;
                square.setLayoutParams(params);
                square.invalidate();
                break;
            }
        }
    }
}
