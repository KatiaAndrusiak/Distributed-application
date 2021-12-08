package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteFragment extends Fragment {

    public CompleteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complete, container, false);
        Button completeBtn = view.findViewById(R.id.complete_button);
        Button newProblemBtn = view.findViewById(R.id.new_problem_button);
        completeBtn.setOnClickListener(
                v -> {
                    getActivity().finishAndRemoveTask();

                }
        );

        newProblemBtn.setOnClickListener(
                v -> {
                    Fragment fragment = new MapsFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, fragment)
                            .commit();
                }
        );
        return view;
    }
}