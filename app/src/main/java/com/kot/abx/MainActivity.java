package com.kot.abx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kot.mylibrary123.Dashboard;
import com.kot.mylibrary123.Interface.Checkiner;
import com.kot.mylibrary123.Sample;

public class MainActivity extends AppCompatActivity implements Checkiner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sample.getActi(MainActivity.this);



       // startActivity(new Intent(MainActivity.this, Dashboard.class).putExtra("key","MainActivity"));
        Dashboard.liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("saa",s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getdata(String name) {
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
    }


}