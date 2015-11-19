package com.udacity.gradle.builditbigger; /**
 * Created by Prinzly Ngotoum on 11/18/15.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements IMainFragment{

    public MainActivity mActivity;
    public EndpointsAsyncTask mMyTask;
    private ProgressBar spinner;
    InterstitialAd mInterstitialAd;
    Button mBtnJoke;

    public MainActivityFragment() {
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=(MainActivity) activity;
        // Required empty public constructor
        if (mMyTask == null) {// if the task is activ, I attach the reference of the new activity, because the activity will get destroyed
            // If there is no task, then I initialize my task.
            mMyTask = new EndpointsAsyncTask(mActivity,this);
        }
    }

    public ProgressBar getSpinner(){
        return spinner;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        spinner=(ProgressBar) root.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        mBtnJoke= (Button) root.findViewById(R.id.btn_getJoke);
        mBtnJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                new EndpointsAsyncTask(mActivity,MainActivityFragment.this).execute();
            }
        });

        requestNewInterstitial();

        return root;
    }



    public void tellJoke(){

//        1st Step
//        Toast.makeText(this, MyClass.tellJoke(), Toast.LENGTH_SHORT).show();

//        2nd Step
//        Intent intent=new Intent(this, Home.class);
//        intent.putExtra(Home.messageTag, MyClass.tellJoke());
//        startActivity(intent);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            new EndpointsAsyncTask(mActivity, this).execute();
        }

    }

    @Override
    public void beforeFetching() {
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterFetching() {
        if(spinner!=null){
            spinner.setVisibility(View.GONE);
        }
    }

    @Override
    public void Detach() {
        if (spinner != null && spinner.getVisibility()==View.VISIBLE) {
            spinner.setVisibility(View.GONE);
        }
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}