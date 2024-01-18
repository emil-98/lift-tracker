package com.example.workoutlog.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.example.workoutlog.R;
import com.example.workoutlog.model.AppDB;
import com.example.workoutlog.model.LiftRecord;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
*
* Query each lift by name from db, make date and weight an Entry obj
*
*/
public class ProgressFragment extends Fragment {
    private int stateValue;
    private final String FRAG_KEY = "progressFragment";
    public String[] colorList = {
            "#FC0505",
            "#FC7805",
            "#FCE705",
            "#05FC57",
            "#059DFC",
            "#0553FC",
            "#6805FC",
            "#FC0547"
    };

    Context activityContext;

    LineChart progressChart;

    ImageButton prevBtn;
    ImageButton nextBtn;
    TextView selectedLiftTxt;

    List<LiftRecord> liftRecordList;

    List<String> recordNameList;

    Spinner liftSpinner;

    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        if(savedInstanceState != null){
            stateValue = savedInstanceState.getInt(FRAG_KEY);
        }

        progressChart = view.findViewById(R.id.lineGraphView);
        progressChart.setTouchEnabled(true);
        progressChart.setPinchZoom(true);

        liftSpinner = view.findViewById(R.id.liftRecordSpinner);

        AppDB db = AppDB.getInstance(activityContext);

        liftRecordList = db.liftRecordDAO().getAllRecords();

        recordNameList.add("All Lifts");

        for(int i = 0; i < liftRecordList.size(); i ++){
            if(!recordNameList.contains(liftRecordList.get(i).nameRecord)){
                recordNameList.add(liftRecordList.get(i).nameRecord);
            }
        }

        drawGraph(recordNameList.get(0), db, progressChart);

        ArrayAdapter<String> liftNameAdapter = new ArrayAdapter<>(activityContext, android.R.layout.simple_spinner_item, recordNameList);
        liftNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.activityContext = context;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(FRAG_KEY, stateValue);
        super.onSaveInstanceState(outState);
    }

    void drawGraph(String liftName, AppDB dbInstance, LineChart chart){
        chart.clear();

        List<LiftRecord> recordData;
        ArrayList<Entry> chartData;

        LineDataSet dataSet;

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        if(liftName.equals("All Lifts")){

            recordData = dbInstance.liftRecordDAO().getRecordsByLift(recordNameList.get(0));
            chartData = new ArrayList<>();

            float xVal = 0;
            for(LiftRecord record : recordData){
                xVal ++;
                float yVal = (float)record.weight;
                chartData.add(new Entry(xVal, yVal));
            }

            dataSet = new LineDataSet(chartData, recordNameList.get(0));

            int dataColor = Color.parseColor(colorList[colorList.length - 3]);

            dataSet.setColor(dataColor);
            dataSet.setCircleColor(dataColor);

            dataSet.setLineWidth(4f);
            dataSet.setCircleRadius(8f);

            dataSet.setDrawCircleHole(true);
            dataSet.setCircleHoleColor(Color.TRANSPARENT);
            dataSet.setCircleHoleRadius(6f);

            dataSet.setDrawValues(false);

            dataSet.enableDashedHighlightLine(10f, 5f, 0f);

            dataSets.add(dataSet);

            LineData data = new LineData(dataSets);

            Legend legend = chart.getLegend();
            legend.setWordWrapEnabled(true);
            legend.setTextSize(12);
            //legend.setTextColor(getResources());
            chart.setData(data);
        }else{
            recordData = dbInstance.liftRecordDAO().getRecordsByLift(liftName);
            chartData = new ArrayList<>();

            float xVal = 0;

            for(LiftRecord record : recordData){
                xVal ++;
                float yVal = (float)record.weight;
                chartData.add(new Entry(xVal, yVal));
            }

            dataSet = new LineDataSet(chartData, selectedLiftTxt.getText().toString());

            Random rand = new Random();

            int dataColor = Color.parseColor(colorList[rand.nextInt(colorList.length)]);

            dataSet.setColor(dataColor);
            dataSet.setCircleColor(dataColor);

            dataSet.setLineWidth(4f);
            dataSet.setCircleRadius(8f);

            dataSet.setDrawCircleHole(true);
            dataSet.setCircleHoleColor(Color.TRANSPARENT);
            dataSet.setCircleHoleRadius(4f);

            dataSet.setDrawValues(false);

            dataSet.enableDashedHighlightLine(10f, 5f, 0f);

            dataSet.setHighlightEnabled(true);

            dataSets.add(dataSet);

            LineData data = new LineData(dataSets);

            chart.setData(data);
        }
    }

//    void setGraphAttributes(LineDataSet inputDataSet){
//        inputDataSet.setColor();
//    }
}