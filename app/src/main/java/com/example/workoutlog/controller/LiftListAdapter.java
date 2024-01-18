package com.example.workoutlog.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutlog.view.AddLiftUI;
import com.example.workoutlog.R;
import com.example.workoutlog.model.AppDB;
import com.example.workoutlog.model.Lift;

import java.util.List;

public class LiftListAdapter extends RecyclerView.Adapter<LiftListAdapter.LiftListViewHolder> {

    Context context;
    List<Lift> liftList;

    AppDB db;

    public LiftListAdapter(Context context){
        this.context = context;
        db = AppDB.getInstance(context);
    }

    public void setLiftList(List<Lift> liftList){
        this.liftList = liftList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lift_list_layout, parent, false);

        return new LiftListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiftListViewHolder holder, int position) {
        int sets = this.liftList.get(position).sets;
        int reps = this.liftList.get(position).goalReps;

        holder.titleView.setText(this.liftList.get(position).liftName);
        holder.subtitleView.setText(String.format(context.getResources().getString(R.string.liftSubtitle), sets, reps));

        holder.editBtn.setOnClickListener(view -> {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Lift liftItem = liftList.get(position);
            AddLiftUI addLiftUIFrag = new AddLiftUI(activity, liftItem.liftID, liftItem.liftName, liftItem.sets, liftItem.goalReps);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, addLiftUIFrag).commit();
        });

        holder.deleteBtn.setOnClickListener(view -> {
            Lift liftItem = liftList.get(position);
            db.liftDAO().delete(liftItem);
            liftList.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return liftList.size();
    }

    public static class LiftListViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        TextView subtitleView;

        ImageButton editBtn;
        ImageButton deleteBtn;

        public LiftListViewHolder(View view){
            super(view);
            titleView = view.findViewById(R.id.listItemTitle);
            subtitleView = view.findViewById(R.id.listItemSubtitle);

            editBtn = view.findViewById(R.id.editLiftBtn);
            deleteBtn = view.findViewById(R.id.deleteLiftBtn);
        }
    }
}
