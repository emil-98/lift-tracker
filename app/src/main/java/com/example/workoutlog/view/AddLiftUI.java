package com.example.workoutlog.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.workoutlog.R;
import com.example.workoutlog.model.AppDB;
import com.example.workoutlog.model.Lift;

public class AddLiftUI extends Fragment {
    View view;
    EditText liftInput;
    EditText setsInput;
    EditText repsInput;
    Button cancelBtn;
    Button saveBtn;

    Context activityContext;

    //used when editing entry
    Integer liftID;
    String liftName;
    int sets;
    int reps;

    public AddLiftUI() {
        // Required empty public constructor
    }

    public AddLiftUI(Context context){
        this.activityContext = context;
    }

    public AddLiftUI(Context context, int id, String name, int sets, int reps){
        activityContext = context;
        liftID = id;
        liftName = name;
        this.sets = sets;
        this.reps = reps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_lift_ui, container, false);

        liftInput = view.findViewById(R.id.liftNameInput);
        setsInput = view.findViewById(R.id.setsInput);
        repsInput = view.findViewById(R.id.goalRepsInput);

        cancelBtn = view.findViewById(R.id.cancelTxtBtn);
        saveBtn = view.findViewById(R.id.submitTxtBtn);
        saveBtn.setEnabled(false);

        if(liftID != null){
            liftInput.setText(liftName);
        }

        TextWatcher liftTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String liftNameStr = liftInput.getText().toString();
                String setsStr = setsInput.getText().toString();
                String repsStr = repsInput.getText().toString();

                saveBtn.setEnabled(!liftNameStr.isEmpty() && !setsStr.isEmpty() && !repsStr.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        liftInput.addTextChangedListener(liftTextWatcher);
        repsInput.addTextChangedListener(liftTextWatcher);
        setsInput.addTextChangedListener(liftTextWatcher);

        cancelBtn.setOnClickListener(view -> {
            FragmentTransaction fragTrans = getParentFragmentManager().beginTransaction();
            fragTrans.replace(R.id.mainFrameLayout, new AddLiftsFragment()).commit();
        });

        saveBtn.setOnClickListener(view -> {

            if(liftID != null){
                saveEditInput(liftID, liftInput.getText().toString(),
                        Integer.parseInt(setsInput.getText().toString()),
                        Integer.parseInt(repsInput.getText().toString()));
            }else{
                saveInput(liftInput.getText().toString(),
                        Integer.parseInt(setsInput.getText().toString()),
                        Integer.parseInt(repsInput.getText().toString()));
            }

            FragmentTransaction fragTrans = getParentFragmentManager().beginTransaction();
            fragTrans.replace(R.id.mainFrameLayout, new AddLiftsFragment()).commit();

        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.activityContext = context;
    }

    public void saveInput(String liftName, int numSets, int goalReps){
        AppDB db = AppDB.getInstance(this.activityContext);

        Lift liftObj = new Lift();
        liftObj.liftName = liftName;
        liftObj.sets = numSets;
        liftObj.goalReps = goalReps;

        db.liftDAO().insertLift(liftObj);
    }

    public void saveEditInput(int id, String liftName, int numSets, int goalReps){
        AppDB db = AppDB.getInstance(this.activityContext);

        Lift liftObj = new Lift();
        liftObj.liftID = id;
        liftObj.liftName = liftName;
        liftObj.sets = numSets;
        liftObj.goalReps = goalReps;

        db.liftDAO().updateLift(liftObj);
    }
}