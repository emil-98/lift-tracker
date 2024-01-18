package com.example.workoutlog.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workoutlog.R;
import com.example.workoutlog.model.AppDB;
import com.example.workoutlog.model.Lift;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.workoutlog.controller.LiftListAdapter;

import java.util.List;

public class AddLiftsFragment extends Fragment {

    LiftListAdapter liftListAdapter;
    View view;
    List<Lift> liftList;

    Context activityContext;

    FloatingActionButton addLiftBtn;

    public AddLiftsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_add_lifts, container, false);

        addLiftBtn = view.findViewById(R.id.addLiftButton);

        addLiftBtn.setOnClickListener(view -> {
            FragmentTransaction fragTrans = getParentFragmentManager().beginTransaction();
            fragTrans.setCustomAnimations(androidx.navigation.ui.R.animator.nav_default_enter_anim, androidx.navigation.ui.R.animator.nav_default_exit_anim);
            fragTrans.replace(R.id.mainFrameLayout, new AddLiftUI(activityContext));
            fragTrans.commit();
        });

        initRecyclerView(view);
        loadLiftList(this.activityContext);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.activityContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }

    private void initRecyclerView(View view){
        Context context = this.activityContext;

        RecyclerView liftListRecycler = view.findViewById(R.id.addLiftsRecycler);
        liftListRecycler.setLayoutManager(new LinearLayoutManager(context));

        liftListAdapter = new LiftListAdapter(activityContext);

        liftListRecycler.setAdapter(liftListAdapter);
    }

    private void loadLiftList(Context context){
        AppDB db = AppDB.getInstance(context);
        liftList = db.liftDAO().getAllLifts();
        liftListAdapter.setLiftList(liftList);
    }

}