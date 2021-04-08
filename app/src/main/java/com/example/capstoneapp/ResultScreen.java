package com.example.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ResultScreen extends AppCompatActivity  {

    //AnyChartView polaritychart;
    String[] polarity = {"Positive", "Negative"};
    int[] polarityrating = {60,40};

    LineChart predictionChart;
    PieChart polarityChart;
    PieChart objectivityChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        // stevdza-San
      //  polaritychart = (AnyChartView) findViewById(R.id.polaritysentiment);
        //  setupPolarityChart();
        objectivityChart = (PieChart) findViewById(R.id.objectivityChart);
        polarityChart = (PieChart) findViewById(R.id.polarityChart);
        polarityChart.setDrawHoleEnabled(true);
        polarityChart.setUsePercentValues(true);
        polarityChart.getDescription().setEnabled(false);
        polarityChart.setDragDecelerationFrictionCoef(0.99f);
        polarityChart.setHoleColor(Color.TRANSPARENT);
        polarityChart.setTransparentCircleRadius(31f);

        objectivityChart.setDrawHoleEnabled(true);
        objectivityChart.setUsePercentValues(true);
        objectivityChart.getDescription().setEnabled(false);
        objectivityChart.setDragDecelerationFrictionCoef(0.99f);
        objectivityChart.setHoleColor(Color.TRANSPARENT);
        objectivityChart.setTransparentCircleRadius(31f);





        ArrayList<PieEntry> myvalues = new ArrayList<>();
        myvalues.add(new PieEntry(60f,"Positive"));
        myvalues.add(new PieEntry(30f,"Negative"));
        myvalues.add(new PieEntry(10f,"Neutral"));


        PieDataSet poldata = new PieDataSet(myvalues,"Polarity");
        poldata.setSliceSpace(1f);
        poldata.setSelectionShift(2f);
        poldata.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData polchartdata = new PieData(poldata);
        polchartdata.setValueTextSize(12f);
        polchartdata.setValueTextColor(Color.BLACK);

        polarityChart.animateY(1000, Easing.EaseInOutCubic);
        objectivityChart.animateY(1000, Easing.EaseInOutCubic);

        polarityChart.setData(polchartdata);
        objectivityChart.setData(polchartdata);


        ////////////////////////////////////////
        predictionChart = (LineChart) findViewById(R.id.predictionChart);
        predictionChart.setDragEnabled(true);
        predictionChart.setScaleEnabled(false);

        ArrayList<String> XValues = new ArrayList<>();
        ArrayList<Entry> actualyValues = new ArrayList<>();
        ArrayList<Entry> predictyValues = new ArrayList<>();
        int x = 0;
        float price = 50;
        int datapoints = 10;
        for(int i=0; i<datapoints;i++){
             actualyValues.add(new Entry(i,price+2));
             predictyValues.add(new Entry(i,price+3));
        }

        ArrayList<ILineDataSet> lineDatasets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(actualyValues,"actual");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(predictyValues,"predicted");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.GREEN);

        lineDatasets.add(lineDataSet1);
        lineDatasets.add(lineDataSet2);

        //////////////

        LineData data = new LineData(lineDataSet1,lineDataSet2);


        predictionChart.setData(data);



    }

/*
    public void setupPolarityChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<DataEntry>();
        for(int i = 0;i<polarity.length;i++){
            dataEntries.add(new ValueDataEntry(polarity[i],polarityrating[i]));
        }
        pie.data(dataEntries);
        pie.title("Polarity");
        polaritychart.setChart(pie);

    }
*/

}

