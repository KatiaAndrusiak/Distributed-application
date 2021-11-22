package com.example.myapplication;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.entity.Problem;
import com.example.myapplication.manager.DataManagement;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment {

    private static final String  PROBLEM_DATA= "param1";
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
        Problem problem = MapsFragment.newInstance().getArguments().getParcelable(PROBLEM_DATA);
        addressTextView = view.findViewById(R.id.addressTextView);
        addressTextView.setText(problem.getAddress());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_item, DataManagement.categories);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        dynamicSpinner.setAdapter(adapter);
        String selectedProblem = ProblemsFragment.newInstance().getArguments().getString(SELECTED_PROBLEM);
        Spinner dynamicSpinner2 = view.findViewById(R.id.problem_spinner);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_item, DataManagement.problemsByCategories.get(selectedProblem));
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);

        dynamicSpinner2.setAdapter(adapter2);

        dynamicSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        dynamicSpinner.setSelection(getPositionByValue(problems, selectedProblem));
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selItem = (String)parent.getSelectedItem();
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                        getActivity(), R.layout.spinner_item, DataManagement.problemsByCategories.get(selItem));
                adapter3.setDropDownViewResource(R.layout.spinner_dropdown_item);

                dynamicSpinner2.setAdapter(adapter3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        Button sendButton = view.findViewById(R.id.send_button);

        sendButton.setOnClickListener(
                v -> getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new CompleteFragment())
                        .commit()
        );

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