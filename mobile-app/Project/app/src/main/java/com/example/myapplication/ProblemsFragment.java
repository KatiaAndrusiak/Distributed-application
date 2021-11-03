package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProblemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProblemsFragment extends Fragment {

    List<String> problems = new ArrayList<>(Arrays.asList("Woda", "Ogień", "Zniszczenia", "Śmiecie i segregacja", "Komunikacja publiczna", "Sąsiad"));
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProblemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProblemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProblemsFragment newInstance(String param1, String param2) {
        ProblemsFragment fragment = new ProblemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_problems, container, false);

        int mar = (((getResources().getDisplayMetrics().heightPixels)/(problems.size()+1))* 20)/100;
        for( String button: problems){
            LinearLayout ll = view.findViewById(R.id.problems_layout);

            Button btn = new Button(view.getContext());
            btn.setText(button);
            btn.setBackgroundColor(Color.parseColor("#4285F4"));
            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
                    (int)(getResources().getDisplayMetrics().widthPixels * 0.9),
                    (int)((getResources().getDisplayMetrics().heightPixels * 0.8) / problems.size())-mar);
            params.setMargins(0,mar/2,0,mar/2);
            btn.setLayoutParams(
                   params);
            btn.setOnClickListener(
                    v ->{
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, new FormFragment())
                                .commit();
                    }
            );
            ll.addView(btn);
        }

        return view;
    }
}