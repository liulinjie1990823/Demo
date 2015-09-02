package cn.com.llj.demo.activity.animation;

import android.view.View;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/9/1.
 */
public class NiftyDialogDemo extends DemoActivity {
    private Effectstype effect;

    @Override
    public int getLayoutId() {
        return R.layout.nifty_dialog_demo;
    }

    public void dialogShow(View v) {
        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

        switch (v.getId()) {
            case R.id.fadein:
                effect = Effectstype.Fadein;
                break;
            case R.id.slideright:
                effect = Effectstype.Slideright;
                break;
            case R.id.slideleft:
                effect = Effectstype.Slideleft;
                break;
            case R.id.slidetop:
                effect = Effectstype.Slidetop;
                break;
            case R.id.slideBottom:
                effect = Effectstype.SlideBottom;
                break;
            case R.id.newspager:
                effect = Effectstype.Newspager;
                break;
            case R.id.fall:
                effect = Effectstype.Fall;
                break;
            case R.id.sidefall:
                effect = Effectstype.Sidefill;
                break;
            case R.id.fliph:
                effect = Effectstype.Fliph;
                break;
            case R.id.flipv:
                effect = Effectstype.Flipv;
                break;
            case R.id.rotatebottom:
                effect = Effectstype.RotateBottom;
                break;
            case R.id.rotateleft:
                effect = Effectstype.RotateLeft;
                break;
            case R.id.slit:
                effect = Effectstype.Slit;
                break;
            case R.id.shake:
                effect = Effectstype.Shake;
                break;
        }

        dialogBuilder.withTitle("Modal Dialog") // .withTitle(null) no title
                .withTitleColor("#FFFFFF") // def
                .withDividerColor("#11000000") // def
                .withMessage("This is a modal Dialog.") // .withMessage(null) no Msg
                .withMessageColor("#FFFFFFFF") // def | withMessageColor(int resid)
                .withDialogColor("#3367d6") // def | withDialogColor(int resid) //def
                .withIcon(getResources().getDrawable(R.drawable.ic_launcher)).isCancelableOnTouchOutside(true) // def | isCancelable(true)
                .withDuration(700) // def
                .withEffect(effect) // def Effectstype.Slidetop
                .withButton1Text("OK") // def gone
                .withButton2Text("Cancel") // def gone
                .setCustomView(R.layout.custom_view, v.getContext()) // .setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
            }
        }).show();

    }
}
