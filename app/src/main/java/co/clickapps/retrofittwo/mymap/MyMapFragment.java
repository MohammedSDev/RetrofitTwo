package co.clickapps.retrofittwo.mymap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import co.clickapps.retrofittwo.R;

/**
 * Created by clickapps on 21/11/17.
 */

public class MyMapFragment extends Fragment {

    GoogleMap mMap;
    private final int DIALOG_ERROR_CODE = 1048;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.my_map_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isMapServiceOk())
            Toast.makeText(getContext(), "Map okay!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getContext(), "Map not okay", Toast.LENGTH_LONG).show();
    }

    private boolean isMapServiceOk(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if (available == ConnectionResult.SUCCESS)
            return true;
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(),available,DIALOG_ERROR_CODE).show();
            return false;
        }
        else
            return false;
    }
}
