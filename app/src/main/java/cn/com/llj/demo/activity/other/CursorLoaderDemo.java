package cn.com.llj.demo.activity.other;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.common.library.llj.utils.LogUtilLj;

import java.util.HashMap;
import java.util.Map;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/24.
 */
public class CursorLoaderDemo extends DemoActivity {
    LoaderManager manager = this.getLoaderManager();

    @Override
    public int getLayoutId() {
        return R.layout.cursor_loader_demo;
    }

    @Override
    public void initViews() {
        manager.initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(CursorLoaderDemo.this, ContactsContract.Contacts.CONTENT_URI, null, null, null,
                        null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                while (cursor.moveToNext()) {
                    Map<String, String> map = new HashMap<String, String>();
                    int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    LogUtilLj.LLJi("id:"+id+"name:"+name+"----------------");
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });
    }
}
