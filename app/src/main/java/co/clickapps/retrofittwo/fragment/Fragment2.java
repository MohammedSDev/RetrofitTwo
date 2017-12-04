package co.clickapps.retrofittwo.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.clickapps.retrofittwo.R;

/**
 * Created by clickapps on 30/11/17.
 */

public class Fragment2 extends Fragment {


    TextView textView;
    ImageView imageView;

    final String TAG = "mud";
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment2, container, false);
            textView = view.findViewById(R.id.text_view_tow);
            imageView = view.findViewById(R.id.image_view_tow);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "textView two onClick: ");
                    //remove all fragment on backStack till 'frag1', including 'frag1'
                    /*getActivity().getSupportFragmentManager().popBackStack("frag1", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/

                    //remove last fragment from back stack
                    getActivity().getSupportFragmentManager().popBackStackImmediate();

                    //remove this fragment,event if not added to back stack
                    /*getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(Fragment2.this)
                            .commit();*/
                }
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        imageView.setImageResource(R.drawable.boy);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: Fragment two");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: Fragment two");
        super.onDestroy();
    }
}
