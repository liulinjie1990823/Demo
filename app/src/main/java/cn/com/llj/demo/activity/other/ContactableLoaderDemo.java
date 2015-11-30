package cn.com.llj.demo.activity.other;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/11/30.
 */
public class ContactableLoaderDemo extends DemoActivity {
    @Override
    public void getIntentData() {
        if (getIntent() != null) {
            handleIntent(getIntent());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.contactable_loader_demo;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    /**
     * Assuming this activity was started with a new intent, process the incoming information and
     * react accordingly.
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {
        // Special processing of the incoming intent only occurs if the if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // SearchManager.QUERY is the key that a SearchManager will use to send a query string
            // to an Activity.
            String query = intent.getStringExtra(SearchManager.QUERY);

            // We need to create a bundle containing the query string to send along to the
            // LoaderManager, which will be handling querying the database and returning results.
            Bundle bundle = new Bundle();
            bundle.putString(QUERY_KEY, query);

            ContactablesLoaderCallbacks loaderCallbacks = new ContactablesLoaderCallbacks(this);

            // Start the loader with the new query, and an object that will handle all callbacks.
            getLoaderManager().restartLoader(CONTACT_QUERY_LOADER, bundle, loaderCallbacks);
        }
    }

    public static final int CONTACT_QUERY_LOADER = 0;
    public static final String QUERY_KEY = "query";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView( menu.findItem(R.id.search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
