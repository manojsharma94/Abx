package com.kot.mylibrary123;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.kot.mylibrary123.Interface.Checkiner;

public class Sample {

    public Sample(Context context) {


    }

    MutableLiveData<String>liveData=new MutableLiveData<>();

    @Nullable
    public static MultiPermissionListener getActi(Activity activity){
        activity.startActivity(new Intent(activity, Dash.class));
        return null;
    }



    public interface MultiPermissionListener {
       public Sample withListener(Checkiner var1);
    }



    public MutableLiveData<String> getLiveData() {
        return liveData;
    }


}