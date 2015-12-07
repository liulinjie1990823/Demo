package cn.com.llj.demo.activity.other;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.common.library.llj.utils.ContactsUtil;

import java.util.List;

import butterknife.Bind;
import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/24.
 */
public class CursorLoaderDemo extends DemoActivity {
    @Bind(R.id.content)
    TextView textView;
    LoaderManager manager = this.getLoaderManager();

    @Override
    public int getLayoutId() {
        return R.layout.cursor_loader_demo;
    }

    @Override
    public void initViews() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                //以后每次都弹出自己的ui来显示
                new ConfirmationDialog().show(getFragmentManager(), FRAGMENT_DIALOG);
            } else {
                //第一次会直接请求权限，弹出系统的对话框
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CAMERA_PERMISSION);
            }
            return;
        }
        List<ContactsUtil.ContactInfo> contactInfos = ContactsUtil.getAllContacts(this);
        for (ContactsUtil.ContactInfo contactInfo : contactInfos) {
            textView.setText("name:" + contactInfo.getName() + "phone:" + contactInfo.getPhoneNumber() + "----------------");
        }
//        manager.initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
//            @Override
//            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//                return new CursorLoader(CursorLoaderDemo.this, ContactsContract.Contacts.CONTENT_URI, null, null, null,
//                        null);
//            }
//
//            @Override
//            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//                while (cursor.moveToNext()) {
//                    Map<String, String> map = new HashMap<String, String>();
//                    int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                    LogUtilLj.LLJi("id:" + id + "name:" + name + "----------------");
//                }
//            }
//
//            @Override
//            public void onLoaderReset(Loader<Cursor> loader) {
//
//            }
//        });
    }

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //拒绝了之后回调到这里
                ErrorDialog.newInstance(getString(R.string.request_permission)).show(getFragmentManager(), FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Shows an error message dialog.
     */
    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity()).setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    }).create();
        }

    }

    /**
     * Shows OK/Cancel confirmation dialog about camera permission.
     */
    public static class ConfirmationDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity()).setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    }).create();
        }
    }
}
