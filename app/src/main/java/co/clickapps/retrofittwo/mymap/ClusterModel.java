package co.clickapps.retrofittwo.mymap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by clickapps on 21/11/17.
 */

public class ClusterModel implements ClusterItem {

    private LatLng position;
    private String title;
    private String snippet;

    public ClusterModel(double lat,double lng ) {
        position =  new LatLng(lat,lng);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }
}
