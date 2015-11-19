package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.prinzlyngotoum.myapplication.backend.myApi.MyApi;

import java.io.IOException;

import princecoder.androidlibrary.Home;

/**
 * Created by Prinzly Ngotoum on 11/11/15.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private IMainFragment mFragment;
    private EndpointsAsyncTaskListener mListener = null;
    private Exception mError = null;

    public EndpointsAsyncTask(Context c, IMainFragment fragment){
        context=c;
        mFragment=fragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mFragment != null) {
            if(((MainActivityFragment)mFragment).getSpinner()!=null)
            mFragment.beforeFetching();
        }
    }


    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-1120.appspot.com/_ah/api/");
            myApiService = builder.build();
        }

        try {
            return myApiService.sayHi().execute().getData();
        } catch (IOException e) {
            mError = e;
            return e.getMessage();
        }
    }

    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onPostExecute(String result) {

        if (this.mListener != null){
            //Open a new activity with the message
            this.mListener.onComplete(result, mError);
        }

        startNewActivity(result);

    }

    public  void startNewActivity(String result) {

        if (mFragment != null) {// I make the test because for some reasons, it can happen that when the activity get destroy, we don't have the reference of the new one, just for some milliseconds
            Intent intent=new Intent(context, Home.class);
            intent.putExtra(Home.messageTag, result);
            context.startActivity(intent);
            if((((MainActivityFragment)mFragment).getSpinner()!=null))
            mFragment.afterFetching();

        }

    }

    public static interface EndpointsAsyncTaskListener {
        public void onComplete(String jsonString, Exception e);
    }
}