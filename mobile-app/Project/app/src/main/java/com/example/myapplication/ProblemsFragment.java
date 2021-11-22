package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.myapplication.manager.DataManagement;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProblemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProblemsFragment extends Fragment {
    List<String> problems = new ArrayList<>();
//    List<String> problems = new ArrayList<>(Arrays.asList("Woda", "Ogień", "Zniszczenia", "Śmiecie i segregacja", "Komunikacja publiczna", "Sąsiad"));
    private static final String ARG_PARAM1 = "selectedProblem";

    private static String selectedProblem;
    public ProblemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProblemsFragment.
     */
    public static ProblemsFragment newInstance() {
        ProblemsFragment fragment = new ProblemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, selectedProblem);
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_problems, container, false);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    JSONArray arr =DataManagement.getJSONObjectFromURL(
                            DataManagement.URL+DataManagement.PROBLEMS_PATH
                    );
                   problems = DataManagement.getCategoriesFromJsonObject(arr);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        while (true){
            if (problems.size() > 0) break;
        }

        int mar = (((getResources().getDisplayMetrics().heightPixels)/(problems.size()+1))* 20)/100;
        for( String button: problems){
            LinearLayout ll = view.findViewById(R.id.problems_layout);

            Button btn = new Button(view.getContext());
            btn.setText(button);
            btn.setBackgroundColor(Color.parseColor("#ebebeb"));
            btn.setTextColor(Color.parseColor("#FF000000"));
            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
                    (int)(getResources().getDisplayMetrics().widthPixels * 0.9),
                    (int)((getResources().getDisplayMetrics().heightPixels * 0.8) / problems.size())-mar);
            params.setMargins(0,mar/2,0,mar/2);
            btn.setLayoutParams(
                   params);
            btn.setOnClickListener(
                    v ->{
                        selectedProblem = btn.getText().toString();
                        System.out.println(selectedProblem);
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