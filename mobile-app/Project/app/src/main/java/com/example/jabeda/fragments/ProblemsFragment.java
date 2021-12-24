package com.example.jabeda.fragments;

import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.jabeda.R;
import com.example.jabeda.data.DiModule;
import com.example.jabeda.data.ProblemRepository;
import com.example.jabeda.data.ProblemResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProblemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProblemsFragment extends Fragment {
    List<String> categories = new ArrayList<>();
    private static final String ARG_SELECTED_PROBLEM = "selectedProblem";

    private static String selectedProblem;
    ProblemRepository repository = DiModule.getRepository();

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
        args.putString(ARG_SELECTED_PROBLEM, selectedProblem);
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

        categories = repository.getCategories();
        renderCategories(view);

        if(categories == null) {
            fetchCategories(view);
        }

        return view;
    }

    private void fetchCategories(View view) {
        repository.getProblems(new Callback<List<ProblemResponse>>() {
            @Override
            public void onResponse(Call<List<ProblemResponse>> call, Response<List<ProblemResponse>> response) {
                if (response.body() != null) {
                    renderCategories(view);
                }
            }

            @Override
            public void onFailure(Call<List<ProblemResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void renderCategories(View view) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int mar = displayMetrics.heightPixels /(categories.size()+2) * 20 /100;
        int width = (int)(displayMetrics.widthPixels * 0.9);
        int height = (int)(displayMetrics.heightPixels * 0.75 / categories.size() + 1)-mar;
        for(String problem: categories){
            LinearLayout ll = view.findViewById(R.id.problems_layout);

            Button btn = new Button(view.getContext());
            btn.setText(problem);
            btn.setBackgroundColor(Color.parseColor("#ebebeb"));
            btn.setTextColor(Color.parseColor("#FF000000"));
            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(
                    width, height);
            params.setMargins(0,mar/2,0,mar/2);
            btn.setLayoutParams(params);
            btn.setOnClickListener(
                    (View v) -> {
                        selectedProblem = btn.getText().toString();
                        System.out.println(selectedProblem);
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_layout, new FormFragment())
                                .commit();
                    }
            );
            ll.addView(btn);
        }
    }


}