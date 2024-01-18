package com.example.workoutlog.controller;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutlog.R;
import com.example.workoutlog.model.AppDB;
import com.example.workoutlog.model.LiftRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    Context context;
    ArrayList<ViewPagerItemData> vpItemArrayList;

    public CardViewAdapter(Context context, ArrayList<ViewPagerItemData> vpItemArrayList){
        this.context = context;
        this.vpItemArrayList = vpItemArrayList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lift_page_item, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        holder.nextSetBtn.setEnabled(false);

        ViewPagerItemData viewPagerItemData = vpItemArrayList.get(position);
        TextWatcher cardTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String weightStr = holder.weightInput.getText().toString();
                String repsStr = holder.repsInput.getText().toString();

                holder.nextSetBtn.setEnabled(!weightStr.isEmpty() && !repsStr.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String weightStr = holder.weightInput.getText().toString();
                String repsStr = holder.repsInput.getText().toString();

                holder.nextSetBtn.setEnabled(!weightStr.isEmpty() && !repsStr.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        AppDB db = AppDB.getInstance(context);

        List<LiftRecord> currLiftRecord = db.liftRecordDAO().getRecordsByLift(viewPagerItemData.cardTitle);

        holder.cardTitleTxt.setText(viewPagerItemData.cardTitle);
        if(currLiftRecord.size() > 0){
            LiftRecord lastRecord = currLiftRecord.get(currLiftRecord.size() - 1);
            double prevWeightRecord = lastRecord.weight;
            holder.prevWeightTxt.setText(String.format(context.getResources().getString(R.string.prevWeightLbl), prevWeightRecord));
        }

        if(viewPagerItemData.currSet <= viewPagerItemData.sets){
            holder.setNumTxt.setText(String.format(context.getResources().getString(R.string.setNumLbl), viewPagerItemData.currSet, viewPagerItemData.sets));
        }
        holder.nextSetBtn.setText(context.getResources().getString(R.string.nextSetLbl));
        if(viewPagerItemData.currSet == viewPagerItemData.sets){
            holder.nextSetBtn.setText(context.getResources().getString(R.string.finalSetLbl));
        }

        if(viewPagerItemData.currSet > viewPagerItemData.sets){
            holder.nextSetBtn.setText(context.getResources().getString(R.string.completedLbl));
            holder.nextSetBtn.setEnabled(false);
        }

        holder.weightInput.addTextChangedListener(cardTextWatcher);
        holder.repsInput.addTextChangedListener(cardTextWatcher);

        holder.nextSetBtn.setOnClickListener(view -> {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());

            LiftRecord liftRecordObj = new LiftRecord();

            liftRecordObj.dateRecord = date;
            liftRecordObj.nameRecord = viewPagerItemData.cardTitle;
            liftRecordObj.weight = Double.parseDouble(holder.weightInput.getText().toString());
            liftRecordObj.reps = Integer.parseInt(holder.repsInput.getText().toString());

            if(viewPagerItemData.currSet <= viewPagerItemData.sets){
                viewPagerItemData.incSetNum();
            }

            if(viewPagerItemData.currSet == viewPagerItemData.sets){
                holder.nextSetBtn.setText(context.getResources().getString(R.string.finalSetLbl));
            }

            holder.weightInput.setText("");
            holder.repsInput.setText("");
            db.liftRecordDAO().insertRecord(liftRecordObj);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return vpItemArrayList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        TextView cardTitleTxt;
        TextView prevWeightTxt;
        TextView setNumTxt;

        EditText weightInput;
        EditText repsInput;

        Button nextSetBtn;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTitleTxt = itemView.findViewById(R.id.liftCardTitle);
            prevWeightTxt = itemView.findViewById(R.id.prevWeightTextView);
            setNumTxt = itemView.findViewById(R.id.setCountTextView);

            weightInput = itemView.findViewById(R.id.weightInput);
            repsInput = itemView.findViewById(R.id.repsInput);

            nextSetBtn = itemView.findViewById(R.id.nextSetBtn);

        }
    }

    public static class ViewPagerItemData {

        //stored data
        String cardTitle;
        int goalReps;
        double prevWeight;

        //created data
        int sets;
        int currSet = 1;

        void incSetNum(){
            currSet += 1;
        }

        public ViewPagerItemData(String cardTitle, int goalReps, int sets, double prevWeight){
            this.cardTitle = cardTitle;
            this.goalReps = goalReps;
            this.sets = sets;
            this.prevWeight = prevWeight;
        }
    }
}
