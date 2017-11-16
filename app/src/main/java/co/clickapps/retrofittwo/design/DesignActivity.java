package co.clickapps.retrofittwo.design;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import co.clickapps.retrofittwo.R;

public class DesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_app_layout);


    }

    /**
     * use with R.layout.activity_design
     * */
    private void drawerLayoutWithMenu() {
        final DrawerLayout drawerLayout =  findViewById(R.id.drawer_lay);
        NavigationView view =  findViewById(R.id.vavigate_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Snackbar.make(drawerLayout, item.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
//                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

}


