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

    public EndpointsAsyncTask(Context c){
        context=c;
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
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        //Open a new activity with the message
        Intent intent=new Intent(context, Home.class);
        intent.putExtra(Home.messageTag, result);
        context.startActivity(intent);
    }
}