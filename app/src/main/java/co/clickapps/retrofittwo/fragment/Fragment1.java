package co.clickapps.retrofittwo.fragment;

import android.os.Build;
import android.os.Bundle;
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

import co.clickapps.retrofittwo.R;

import static android.support.transition.TransitionSet.ORDERING_TOGETHER;

/**
 * Created by clickapps on 30/11/17.
 */

public class Fragment1 extends Fragment {

    TextView textView;
    ImageView imageView;
    final String TAG = "mud";
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            Log.d(TAG, "onCreateView: fragment one view == null");
            view = inflater.inflate(R.layout.fragment1, container, false);
            textView = view.findViewById(R.id.text_view_one);
            imageView = view.findViewById(R.id.image_view_one);
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

                    openFragmentTwoWIthShardElement();
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
