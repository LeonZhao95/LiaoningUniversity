package bc.liaoningu.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bc.liaoningu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {


    public MessageFragment() {
        // Required empty public constructor
    }
    public static MessageFragment newInstance(String index) {
        MessageFragment f = new MessageFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

}
