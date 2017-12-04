package co.clickapps.retrofittwo.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import co.clickapps.retrofittwo.R;

public class FragmentActivity extends AppCompatActivity {

    TextView textView;
    final String TAG = "mud";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        textView = findViewById(R.id.text_view_activtity);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: text view actvity");
                openFragmentOne();
            }
        });
    }




    private void openFragmentOne(){
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.act_container,new Fragment1())
                .addToBackStack("frag1")
                .commit();
    }


    /*
    * open frag one then frag two
    * */

    private void openFragmentTowThrowOne(){
        //open frag one
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.act_container,new Fragment1())
                .addToBackStack("frag1")
                .setReorderingAllowed(true)
                .commit();

        //open frag two
        getSupportFragmentManager().beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(android.R.id.content,new Fragment2())
                .addToBackStack("frag2")
                .setReorderingAllowed(true)
                .commit();
    }
}
