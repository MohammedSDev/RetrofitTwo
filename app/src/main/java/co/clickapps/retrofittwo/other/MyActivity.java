package co.clickapps.retrofittwo.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import co.clickapps.retrofittwo.R;

/**
 * Created by clickapps on 25/9/17.
 */

public class MyActivity extends AppCompatActivity {


    FrameLayout frameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity_layout);

        frameLayout = findViewById(R.id.frame_loading);
    }

    /* failed*/
    public void showHideFrameLoading(boolean show){
        if (show)
            frameLayout.setVisibility(View.VISIBLE);
        else
            frameLayout.setVisibility(View.GONE);
    }
}
