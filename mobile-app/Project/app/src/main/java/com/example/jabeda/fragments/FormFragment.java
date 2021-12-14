package com.example.jabeda.fragments;

import static java.net.HttpURLConnection.HTTP_OK;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;


import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jabeda.entity.Problem;
import com.example.jabeda.R;
import com.example.jabeda.manager.DataManagement;
import com.sdsmdg.tastytoast.TastyToast;

import org.apache.http.client.HttpResponseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {

    private static final String PROBLEM_DATA = "param1";
    private static final String SELECTED_PROBLEM = "selectedProblem";
    TextView addressTextView;
    public FormFragment() {
        // Required empty public constructor
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
        Button changeBtn = view.findViewById(R.id.change_button);
        Spinner dynamicSpinner = view.findViewById(R.id.category_spinner);
        Problem problem = MapsFragment.newInstance().getArguments().getParcelable(PROBLEM_DATA);
        addressTextView = view.findViewById(R.id.addressTextView);
        addressTextView.setText(problem.getAddress());

        changeBtn.setOnClickListener(
                v -> {
                    Fragment fragment = new MapsFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, fragment)
                            .commit();
                }
        );

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
                String selItem = (String) parent.getSelectedItem();
                problem.setProblem(selItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        dynamicSpinner.setSelection(getPositionByValue(DataManagement.categories, selectedProblem));
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String selItem = (String) parent.getSelectedItem();
                problem.setCategory(selItem);
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
        EditText description = view.findViewById(R.id.editTextTextMultiLine);
        description.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(description.getText().toString().length() < 2) {
                    sendButton.setEnabled(false);
                    sendButton.setBackgroundColor(Color.parseColor("#b8b8b8"));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(description.getText().toString().length() > 2){
                    sendButton.setEnabled(true);
                    sendButton.setBackgroundColor(Color.parseColor("#FF3700B3"));
                }

            }
        });


        sendButton.setOnClickListener(
                v -> {
                    problem.setDescription(String.valueOf(description.getText()));
                    problem.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    System.out.println(problem.toString());
                    Thread thread = new Thread(new Runnable() {
                        int output = 0;

                        @Override
                        public void run() {
                            try {
                                output = DataManagement.sendProblemPostRequest(DataManagement.URL + DataManagement.REQUEST_PROBLEMS_PATH, problem);
                                if (output == HTTP_OK) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            sendButton.setEnabled(false);
                                            sendButton.setBackgroundColor(Color.parseColor("#b8b8b8"));
                                            Toast toast = TastyToast.makeText(
                                                    getContext(),
                                                    "Zgłoszenie zostało wysłane!",
                                                    50,
                                                    TastyToast.SUCCESS
                                            );
                                            new CountDownTimer(2000, 1000) {

                                                public void onTick(long millisUntilFinished) {

                                                }

                                                public void onFinish() {

                                                    getFragmentManager()
                                                            .beginTransaction()
                                                            .replace(R.id.frame_layout, new CompleteFragment())
                                                            .commit();
                                                }
                                            }.start();

                                        }
                                        });
                                    DataManagement.responseOK = true;

                                }
                                else{
                                    throw new HttpResponseException(output, "Nie udało się wysłać zgłoszenia. Strawdź poprawność danych lub sprobuj póżniej");
                                }
                            } catch (HttpResponseException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        TastyToast.makeText(
                                                getContext(),
                                                e.getMessage(),
                                                TastyToast.LENGTH_LONG,
                                                TastyToast.ERROR
                                        );

                                    }
                                });
                                System.out.println(e.toString());
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });

                    thread.start();
                }
        );

        return view;
    }

    public static int getPositionByValue(List<String> strList, String value) {
        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }


}