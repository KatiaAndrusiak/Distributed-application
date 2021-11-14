package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String  SELECTED_ADDRESS= "param1";
    private static final String SELECTED_PROBLEM = "selectedProblem";
    TextView addressTextView;
    String [] problems = new String[]{"Woda", "Ogień", "Zniszczenia", "Śmiecie i segregacja", "Komunikacja publiczna", "Sąsiad"};

    public FormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance(String param1, String param2) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        Spinner dynamicSpinner = view.findViewById(R.id.category_spinner);
        // TODO: Rename and change types of parameters
        String selectedAddress = MapsFragment.newInstance().getArguments().getString(SELECTED_ADDRESS);
        addressTextView = view.findViewById(R.id.addressTextView);
        addressTextView.setText(selectedAddress);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_item, problems);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        dynamicSpinner.setAdapter(adapter);
        String selectedProblem = ProblemsFragment.newInstance().getArguments().getString(SELECTED_PROBLEM);
        dynamicSpinner.setSelection(getPositionByValue(problems, selectedProblem));
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        return view;
    }

    public static int getPositionByValue(String[] strList, String value){
        for (int i = 0; i < strList.length; i++){
            if(strList[i].equals(value)){
                return i;
            }
        }
        return -1;
    }


}