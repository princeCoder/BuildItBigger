package com.udacity.gradle.builditbigger;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements IMainFragment{

    public MainActivity mActivity;
    public EndpointsAsyncTask mMyTask;
    private ProgressBar spinner;
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
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_main, container, false);
        spinner=(ProgressBar) rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        mBtnJoke= (Button) rootView.findViewById(R.id.btn_getJoke);
        mBtnJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });

        if (mMyTask != null && mMyTask.getStatus() == AsyncTask.Status.RUNNING) {// If the task is running
                spinner.setVisibility(View.VISIBLE);
            } else { //The task is not running any more
                afterFetching();
        }
        return rootView;


    }


    public void tellJoke(){

//        1st Step
//        Toast.makeText(this, MyClass.tellJoke(), Toast.LENGTH_SHORT).show();

//        2nd Step
//        Intent intent=new Intent(this, Home.class);
//        intent.putExtra(Home.messageTag, MyClass.tellJoke());
//        startActivity(intent);
        new EndpointsAsyncTask(mActivity, this).execute();
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



}
