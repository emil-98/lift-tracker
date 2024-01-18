package com.example.workoutlog.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workoutlog.R;
import com.example.workoutlog.controller.CardViewAdapter;
import com.example.workoutlog.model.AppDB;
import com.example.workoutlog.model.Lift;

import java.util.ArrayList;
import java.util.List;

public class TodayFragment extends Fragment {
    private int stateValue;
    private final String FRAG_KEY = "todayFragment";

    View view;
    ViewPager2 viewPager2;
    ArrayList<CardViewAdapter.ViewPagerItemData> viewPagerItemDataArrayList;
    List<Lift> liftList;

    Context activityContext;

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.activityContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_today, container, false);

        if(savedInstanceState != null){
            stateValue = savedInstanceState.getInt(FRAG_KEY);
        }

        viewPager2 = view.findViewById(R.id.todayViewPager);

        AppDB db = AppDB.getInstance(activityContext);
        liftList = db.liftDAO().getAllLifts();

        System.out.println(liftList);

        viewPagerItemDataArrayList = new ArrayList<>();

        for(int i = 0; i < liftList.size(); i ++){
            Lift currLift = liftList.get(i);
            CardViewAdapter.ViewPagerItemData item = new CardViewAdapter.ViewPagerItemData(currLift.liftName,
                                                   currLift.goalReps,
                                                   currLift.sets,
                                                   0.0);
            viewPagerItemDataArrayList.add(item);
        }

        System.out.println("VP arrayList: " + viewPagerItemDataArrayList);

        CardViewAdapter cardViewAdapter = new CardViewAdapter(activityContext, viewPagerItemDataArrayList);
        viewPager2.setAdapter(cardViewAdapter);

        viewPager2.setClipToPadding(false);

        viewPager2.setClipChildren(false);

        viewPager2.setOffscreenPageLimit(2);

        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putInt(FRAG_KEY, stateValue);
        super.onSaveInstanceState(outState);
    }
}