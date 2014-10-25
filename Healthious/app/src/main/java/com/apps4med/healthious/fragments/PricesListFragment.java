package com.apps4med.healthious.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.apps4med.healthious.R;
import com.apps4med.healthious.adapters.CustomPriceListArrayAdapter;
import com.apps4med.healthious.adapters.ShowListener;
import com.apps4med.healthious.model.HealthPrice;
import com.apps4med.healthious.parsers.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iskitsas on 26/10/2014.
 */
public class PricesListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<HealthPrice>>, ShowListener {
    CustomPriceListArrayAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initially there is no data
        setEmptyText("No Data Here");
        getListView().setItemsCanFocus(true);

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new CustomPriceListArrayAdapter(getActivity(), this);
        setListAdapter(mAdapter);

        // Start out with a progress indicator.
        setListShown(false);


        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<HealthPrice>> onCreateLoader(int i, Bundle bundle) {
        Log.i("xanthiLOG", "DataListFragment.onCreateLoader");
        return new DataListLoader(getActivity());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behavior here.

    }


    @Override
    public void onLoadFinished(Loader<List<HealthPrice>> arg0, List<HealthPrice> data) {
        mAdapter.setData(data);
        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<HealthPrice>> arg0) {
        mAdapter.setData(null);
    }

    public void reloadList() {
        // Start out with a progress indicator.
        setListShown(false);
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void show(int position) {

    }

    public static class DataListLoader extends AsyncTaskLoader<List<HealthPrice>> {

        List<HealthPrice> mModels;
        private List<HealthPrice> list;

        public DataListLoader(Context context) {
            super(context);
        }

        @Override
        public List<HealthPrice> loadInBackground() {

            // You should perform the heavy task of getting data from
            // Internet or database or other source

            // Create corresponding array of entries and load with data.
            JsonParser jsonParser=new JsonParser(getContext());

            list=jsonParser.getPrices();//from local storage
            //list=jsonParser.getPricesFromUrl("http://www.mobistuff.net/apps/apps4med/healthPrices.json");//from web
            List<HealthPrice> entries = new ArrayList<HealthPrice>();

            for(HealthPrice xp:list){
                entries.add(new HealthPrice(xp.getId(), xp.getDescription(),xp.getHospitalization(),xp.getPrice()));
            }
            return entries;
        }

        /**
         * Called when there is new data to deliver to the client.  The
         * super class will take care of delivering it; the implementation
         * here just adds a little more logic.
         */
        @Override public void deliverResult(List<HealthPrice> listOfData) {
            if (isReset()) {
                // An async query came in while the loader is stopped.  We
                // don't need the result.
                if (listOfData != null) {
                    onReleaseResources(listOfData);
                }
            }
            List<HealthPrice> oldApps = listOfData;
            mModels = listOfData;

            if (isStarted()) {
                // If the Loader is currently started, we can immediately
                // deliver its results.
                super.deliverResult(listOfData);
            }

            // At this point we can release the resources associated with
            // 'oldApps' if needed; now that the new result is delivered we
            // know that it is no longer in use.
            if (oldApps != null) {
                onReleaseResources(oldApps);
            }
        }

        /**
         * Handles a request to start the Loader.
         */
        @Override protected void onStartLoading() {
            if (mModels != null) {
                // If we currently have a result available, deliver it
                // immediately.
                deliverResult(mModels);
            }

            if (takeContentChanged() || mModels == null) {
                // If the data has changed since the last time it was loaded
                // or is not currently available, start a load.
                forceLoad();
            }
        }

        /**
         * Handles a request to stop the Loader.
         */
        @Override protected void onStopLoading() {
            // Attempt to cancel the current load task if possible.
            cancelLoad();
        }

        /**
         * Handles a request to cancel a load.
         */
        @Override public void onCanceled(List<HealthPrice> apps) {
            super.onCanceled(apps);

            // At this point we can release the resources associated with 'apps'
            // if needed.
            onReleaseResources(apps);
        }

        /**
         * Handles a request to completely reset the Loader.
         */
        @Override protected void onReset() {
            super.onReset();

            // Ensure the loader is stopped
            onStopLoading();

            // At this point we can release the resources associated with 'apps'
            // if needed.
            if (mModels != null) {
                onReleaseResources(mModels);
                mModels = null;
            }
        }

        /**
         * Helper function to take care of releasing resources associated
         * with an actively loaded data set.
         */
        protected void onReleaseResources(List<HealthPrice> apps) {}

    }

}
