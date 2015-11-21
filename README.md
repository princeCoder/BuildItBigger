# BuildItBigger

**Synopsis**
<br/>
 In this project I show how to configure a project. I have Created a Java Library providing a joke, an Android Library which display a
 a given joke inside an activity, and a GME Library which get the joke and display it using the Android library.
 
 I have made configurations inside the build.gradle file.<br/>
**Motivation**

This project is one of the task of the Udacity Android Nanodegree
<br/>
**Tests**
<br/>
Here I have created a test case class to retreive the joke from the java Library
<br/>

public void testEndPointAsyncTask() throws InterruptedException {
        MainActivityFragment fragment=new MainActivityFragment();
        EndpointsAsyncTask task = new EndpointsAsyncTask(getApplication(),fragment);
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
