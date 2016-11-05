package bc.liaoningu.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bc.liaoningu.R;
import bc.liaoningu.activities.CourseActivity;
import bc.liaoningu.activities.ScoreActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {


    public MainPageFragment() {
        // Required empty public constructor
    }
    public static MainPageFragment newInstance(String index) {
        MainPageFragment f = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_main_page, container, false);
        v.findViewById(R.id.course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScoreActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
