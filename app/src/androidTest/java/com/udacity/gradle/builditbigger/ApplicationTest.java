package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    String mResult = null;
    Exception mError = null;
    CountDownLatch signal = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testEndPointAsyncTask() throws InterruptedException {

        EndpointsAsyncTask task = new EndpointsAsyncTask(getApplication());
        task.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result, Exception e) {
                mResult = result;
                mError = e;
                signal.countDown();
            }
        }).execute();
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mResult));
        assertTrue(mResult.equalsIgnoreCase("This is the Java Joke Library"));
    }

}