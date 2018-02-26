package co.clickapps.retrofittwo.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeTransform;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.concurrent.ExecutionException;

import co.clickapps.retrofittwo.R;
import co.clickapps.retrofittwo.glide.GlideApp;

import static android.support.transition.TransitionSet.ORDERING_TOGETHER;

/**
 * Created by clickapps on 30/11/17.
 */

public class Fragment1 extends Fragment {

    TextView textView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView;

    final String TAG = "mud";
    View view;
    volatile boolean downloaded = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            Log.d(TAG, "onCreateView: fragment one view == null");
            view = inflater.inflate(R.layout.fragment1, container, false);
            textView = view.findViewById(R.id.text_view_one);

            imageView = view.findViewById(R.id.image_view_one);
            imageView2 = view.findViewById(R.id.image_view_2);
            imageView3 = view.findViewById(R.id.image_view_3);
            imageView4 = view.findViewById(R.id.image_view_4);
            imageView5 = view.findViewById(R.id.image_view_5);
            imageView6 = view.findViewById(R.id.image_view_6);
            imageView7 = view.findViewById(R.id.image_view_7);
            imageView8 = view.findViewById(R.id.image_view_8);
            imageView9 = view.findViewById(R.id.image_view_9);



//            ViewCompat.setTransitionName(imageView,"targe");

            textView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "textView one onClick: ");
                    /*getActivity().getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .replace(R.id.fragment_one_container,new Fragment2())
                            .addToBackStack("frag2")
                    .commit();*/

//                    openFragmentTwoWIthShardElement();
                    downloadAndSaveImageToDiskUsingGlide();
                }
            });
        }
        Log.d(TAG, "onCreateView: fragment one view has been initialize");
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openFragmentTwoWIthShardElement(){
        Fragment2 fragment2 = new Fragment2();
        fragment2.setSharedElementEnterTransition(new DetailsTransition());
        fragment2.setEnterTransition(new android.transition.Fade());
        setExitTransition(new android.transition.Fade());
        fragment2.setSharedElementReturnTransition(new DetailsTransition());

        /*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)*/
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(imageView, "target")
                .replace(R.id.act_container, fragment2)
                .addToBackStack(null)
                .commit();
    }




    private void downloadAndSaveImageToDiskUsingGlide(){
        final String []urls = {"http://www.wallpaperawesome.com/wallpapers-awesome/wallpapers-full-hd-1920-1080-widescreen-awesome/wallpaper-beautiful-city-1920-x-1080-full-hd.jpg",
                "http://7-themes.com/data_images/collection/3/4445181-beautiful-wallpapers.jpg",
                "http://7-themes.com/data_images/collection/3/4445193-beautiful-wallpapers.jpg",
                "http://wallpaperget.com/images/full-hd-wallpapers-for-pc-2.jpg",
                "http://4.bp.blogspot.com/-5vHbSYnqyHU/UEhgqCnRL6I/AAAAAAAABAY/88A-WyYetZU/s1600/hearty-cloud-beautiful-full-HD-nature-background-wallpaper-for-laptop-widescreen.jpg",
                "https://www.planwallpaper.com/static/images/1080p-wallpaper-hd.jpg",
                "https://www.planwallpaper.com/static/images/crysis_hd_1080p-HD.jpg",
                "https://www.planwallpaper.com/static/images/63906_1_BdhSun5_vKBCXSe.jpg",
                "https://www.planwallpaper.com/static/images/3D-Computer-Wallpaper-Built-by-Robot-1024x640_1E8N52q.jpg",

                "https://www.planwallpaper.com/static/images/3D_Wallpapers-881_PlIyPRj.jpg",
                "https://1.bp.blogspot.com/-dJ88gv-U0QE/Vn1vah51IuI/AAAAAAAALqw/cB7nl-HnBAM/s1600/BIG-TEDDY-BEAR-SMALL-MICKEY-MOUSE.jpg",
                "https://3.bp.blogspot.com/-z_-cHv5vFdE/UAkooQMU3vI/AAAAAAAAHus/nVvji_IUUrk/s1600/Hdhut.blogspot.com+%25285%2529.jpg",
        };


        GlideApp.with(this)
                .load(urls[0])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.snapchat)
                .error(R.drawable.sd)
                .into(imageView);

        GlideApp.with(this)
                .load(urls[1])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.snapchat)
                .error(R.drawable.sd)
                .into(imageView2);

        GlideApp.with(this)
                .load(urls[2])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.snapchat)
                .error(R.drawable.sd)
                .into(imageView3);

        GlideApp.with(this)
                .load(urls[3])
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView4);

        GlideApp.with(this)
                .load(urls[4])
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView5);

        GlideApp.with(this)
                .load(urls[5])
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView6);

        GlideApp.with(this)
                .load(urls[6])
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView7);

        GlideApp.with(this)
                .load(urls[7])
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView8);

        GlideApp.with(this)
                .load(urls[9])
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView9);
    }


    //life cycle


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: Fragment one");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Fragment one");
        super.onDestroy();
    }


    //classes
//    public class DetailsTransition extends TransitionSet {
//
//        public DetailsTransition() {
//            setOrdering(ORDERING_TOGETHER);
//            addTransition(new ChangeBounds()).
//                    addTransition(new ChangeTransform()).
//                    addTransition(new ChangeImageTransform());
//        }
//    }
}
