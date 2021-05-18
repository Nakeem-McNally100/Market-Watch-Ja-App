package com.example.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.APIlib;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultScreen extends AppCompatActivity  {

    AnyChartView polaritychart;
    AnyChartView subjectivitychart;

    //String[] polarity = {"Positive", "Negative"};
    //int[] polarityrating = {60,40};

    LineChart predictionChart;
    //PieChart polarityChart;
    //PieChart objectivityChart;
    TextView freecashflow;
    TextView operatingcashflow;
    TextView currentratio;
    TextView currentassets;
    TextView currentliabilities;
    TextView noncurrentliabilities;
    TextView noncurrentassets;
    TextView acidtest;
    TextView assestturnover;
    TextView grossmargin;
    TextView debtratio;
    TextView debtcoverage;
    TextView workingcapital;

    TextView companyname;

    int sentimentpositive;
    int sentimentnegative;
    int sentimentneutral;
    int sentimentsubjectivity;
    int sentimentobjectivity;
    int sentimenttotaltweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        // stevdza-San


        //  setupPolarityChart();
       // objectivityChart = (PieChart) findViewById(R.id.objectivityChart);
      //  polarityChart = (PieChart) findViewById(R.id.polarityChart);
        Bundle myitemBundle = getIntent().getExtras();
        if(myitemBundle!=null) {

            String companySearch = myitemBundle.getString("CompanyName");

            // #3A3939 nice dark grey
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(ResultScreen.this);
            String url = "http://192.168.1.7:5000/company/" + companySearch;
            // String url ="https://10.0.2.2:5000/user/"+logInEmail.getText().toString();
            // Request a string response from the provided URL.
/*
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Toast.makeText(ResultScreen.this, response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(ResultScreen.this, "Oops didn't work. Try again later", Toast.LENGTH_SHORT).show();

                }
            });

            queue.add(stringRequest);


*/

            final String companydisplayname = companySearch;
            if(companySearch.contains(" ")){
                companySearch = companySearch.replace(" ","-");

            }
            RequestQueue jsonqueue = Volley.newRequestQueue(this);
            String sentimenturl = "http://192.168.1.7:5000/companyresult/" + companySearch;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, sentimenturl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                companyname = (TextView)findViewById(R.id.companyname);
                                companyname.setText("Company: "+ companydisplayname);
                                JSONArray jsonarray = response.getJSONArray("companydata");

                                JSONObject sentiments = jsonarray.getJSONObject(0);
                                JSONObject health = jsonarray.getJSONObject(1);
                                JSONObject prediction = jsonarray.getJSONObject(2);

                                ///////////////////////////////////////

                                sentimentpositive = sentiments.getInt("positive");
                                sentimentnegative = sentiments.getInt("negative");
                                sentimentneutral = sentiments.getInt("neutral");
                                sentimentsubjectivity = sentiments.getInt("subjective");
                                sentimentobjectivity = sentiments.getInt("objective");
                                sentimenttotaltweets = sentiments.getInt("Totaltweets");
                                
                               ////////////////////////
                                double freecashflowint = health.getDouble("free cashflow");
                                double operatingcashflowint = health.getDouble("operating cashflow");
                                double currentratioint = health.getDouble("current ratio");
                                double currentassetsint = health.getDouble("current assets");
                                double currentliabilitiesint = health.getDouble("current liailities");
                                double noncurrentliabilitiesint = health.getDouble("non current liailities");
                                double noncurrentassetsint = health.getDouble("non current assets");
                                double acidtestint = health.getDouble("acid test");
                                double assestturnoverint = health.getDouble("asset turnover");
                                double grossmarginint = health.getDouble("grossmargin");
                                double debtratioint = health.getDouble("debt ratio");
                                double debtcoverageint = health.getDouble("debt coverage");
                                double workingcapitalint = health.getDouble("working capital");
                                ////////////////////////////////////
                                String sevenfuture = prediction.getString("sevendayprediction");
                                String thirtyprediction = prediction.getString("thirtydayprediction");
                                String thirtyactual = prediction.getString("thirtydayactual");

                                freecashflow = (TextView)findViewById(R.id.freecashflow13);
                                operatingcashflow = (TextView)findViewById(R.id.operatingcashflow);
                                currentratio = (TextView)findViewById(R.id.currentratio);
                                currentassets = (TextView)findViewById(R.id.currentassests);
                                currentliabilities = (TextView)findViewById(R.id.currentliabilities);
                                noncurrentliabilities = (TextView)findViewById(R.id.noncurrentliabilities);
                                noncurrentassets = (TextView)findViewById(R.id.noncurrentassests);
                                acidtest = (TextView)findViewById(R.id.acidtest);
                                assestturnover = (TextView)findViewById(R.id.assestturnover);
                                grossmargin = (TextView)findViewById(R.id.grossmargin);
                                debtratio = (TextView)findViewById(R.id.debtratio);
                                debtcoverage = (TextView)findViewById(R.id.debtcoverage);
                                workingcapital = (TextView)findViewById(R.id.workingcapital);


                                freecashflow.setText("Free Cashflow(USD): "+String.valueOf(freecashflowint));
                                operatingcashflow.setText("Operating Cashflow(USD): "+String.valueOf(operatingcashflowint));
                                currentratio.setText("Current Ratio: "+String.valueOf(currentratioint));
                                currentassets.setText("Current Assets(USD): "+String.valueOf(currentassetsint));
                                currentliabilities.setText("Current Liabilities(USD): "+String.valueOf(currentliabilitiesint));
                                noncurrentliabilities.setText("Non-current Liabilities(USD): "+String.valueOf(noncurrentliabilitiesint));
                                noncurrentassets.setText("Non-current Assets(USD): "+String.valueOf(noncurrentassetsint));
                                acidtest.setText("Acid Test: "+String.valueOf(acidtestint));
                                assestturnover.setText("Assets Turnover: "+String.valueOf(assestturnoverint));
                                grossmargin.setText("Gross Margin: "+String.valueOf(grossmarginint));
                                debtratio.setText("Debt Ratio: "+String.valueOf(debtratioint));
                                debtcoverage.setText("Debt Coverage: "+String.valueOf(debtcoverageint));
                                workingcapital.setText("Working Capital(USD): "+String.valueOf(workingcapitalint));


                                ////////////////////////////////

                               /*
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
                                myvalues.add(new PieEntry(sentimentpositive, "Positive"));
                                myvalues.add(new PieEntry(sentimentnegative, "Negative"));
                                myvalues.add(new PieEntry(sentimentneutral, "Neutral"));


                                PieDataSet poldata = new PieDataSet(myvalues, "Polarity");
                                poldata.setSliceSpace(1f);
                                poldata.setSelectionShift(2f);
                                poldata.setColors(ColorTemplate.JOYFUL_COLORS);

                                PieData polchartdata = new PieData(poldata);
                                polchartdata.setValueTextSize(12f);
                                polchartdata.setValueTextColor(Color.BLACK);


                                polarityChart.animateY(1000, Easing.EaseInOutCubic);
                                objectivityChart.animateY(1000, Easing.EaseInOutCubic);



                                ArrayList<PieEntry> myvalues2 = new ArrayList<>();
                                myvalues2.add(new PieEntry(sentimentsubjectivity, "Subjective"));
                                myvalues2.add(new PieEntry(sentimentobjectivity, "Objective"));

                                PieDataSet objdata = new PieDataSet(myvalues2, "Subjectivity");
                                objdata.setSliceSpace(1f);
                                objdata.setSelectionShift(2f);
                                objdata.setColors(ColorTemplate.JOYFUL_COLORS);

                                PieData objchartdata = new PieData(objdata);
                                objchartdata .setValueTextSize(12f);
                                objchartdata .setValueTextColor(Color.BLACK);

                                objectivityChart.setData(objchartdata);
                                polarityChart.setData(polchartdata);

                                */

                                String[] subjectLabels = {"Subjective", "Objective"};
                                int[] subjectvalues = {sentimentsubjectivity,sentimentobjectivity};

                                List<DataEntry> dataEntries2 = new ArrayList<DataEntry>();
                                for(int w = 0;w<subjectLabels.length;w++){
                                    dataEntries2.add(new ValueDataEntry(subjectLabels[w],subjectvalues[w]));
                                }

                                subjectivitychart = (AnyChartView) findViewById(R.id.subjectivityChart1);
                                APIlib.getInstance().setActiveAnyChartView(subjectivitychart);

                                Pie pie2 = AnyChart.pie();
                                pie2.data(dataEntries2);
                                pie2.title("Subjectivity");

                                subjectivitychart.setChart(pie2);


                                //////////////////////ANYCHART TEST/////////////////////
                                String[] polarityLabels = {"Positive", "Negative","Neutral"};
                                int[] polarityvalues = {sentimentpositive,sentimentnegative,sentimentneutral};

                                List<DataEntry> dataEntries = new ArrayList<DataEntry>();
                                for(int i = 0;i<polarityLabels.length;i++){
                                    dataEntries.add(new ValueDataEntry(polarityLabels[i],polarityvalues[i]));
                                }

                                polaritychart = (AnyChartView) findViewById(R.id.polarityChart1);
                                APIlib.getInstance().setActiveAnyChartView(polaritychart);

                                Pie pie = AnyChart.pie();
                                pie.data(dataEntries);
                                pie.title("Polarity");

                                polaritychart.setChart(pie);

                                //////////////////////////////////////////////////

                                //String[] polarity =
                                String [] sevenfutureArray = sevenfuture.split("-");
                                String [] thirtypredictionArray = thirtyprediction.split("-");
                                String [] thirtyactualArray = thirtyactual.split("-");


                                ////////////////////////////////////////
                                predictionChart = (LineChart) findViewById(R.id.predictionChart);
                                //predictionChart.setDragEnabled(true);
                                //predictionChart.setScaleEnabled(false);
                                predictionChart.animateX(1000);
                                predictionChart.getDescription().setText("Closing Stock price vs Days");

                                ArrayList<Entry> futureValues = new ArrayList<>();
                                ArrayList<Entry> actualyValues = new ArrayList<>();
                                ArrayList<Entry> predictyValues = new ArrayList<>();

                                int datapoints = 30;
                                int datapoints2 = 37;
                                for (int i = 0; i < datapoints; i++) {
                                    actualyValues.add(new Entry(i, Float.valueOf(thirtyactualArray[i])));
                                }

                                for (int i = 0; i < datapoints; i++) {

                                        predictyValues.add(new Entry(i, Float.valueOf(thirtypredictionArray[i])));


                                }
                                futureValues.add(new Entry(31, Float.valueOf(sevenfutureArray[0])));
                                futureValues.add(new Entry(32, Float.valueOf(sevenfutureArray[1])));
                                futureValues.add(new Entry(33, Float.valueOf(sevenfutureArray[2])));
                                futureValues.add(new Entry(34, Float.valueOf(sevenfutureArray[3])));
                                futureValues.add(new Entry(35, Float.valueOf(sevenfutureArray[4])));
                                futureValues.add(new Entry(36, Float.valueOf(sevenfutureArray[5])));
                                futureValues.add(new Entry(37, Float.valueOf(sevenfutureArray[6])));


                                ArrayList<ILineDataSet> lineDatasets = new ArrayList<>();

                                LineDataSet lineDataSet1 = new LineDataSet(actualyValues, "Actual");
                                lineDataSet1.setDrawCircles(false);
                                lineDataSet1.setColor(Color.BLUE);
                                lineDataSet1.setLineWidth(3f);
                                lineDataSet1.setDrawValues(false);
                                //lineDataSet1

                                LineDataSet lineDataSet2 = new LineDataSet(predictyValues, "Past Prediction");
                                lineDataSet2.setDrawCircles(false);
                                lineDataSet2.setColor(Color.RED);
                                lineDataSet2.setLineWidth(3f);
                                lineDataSet2.setDrawValues(false);


                                LineDataSet lineDataSet3 = new LineDataSet(futureValues, "Future prediction");
                                lineDataSet3.setDrawCircles(false);
                                lineDataSet3.setColor(Color.GREEN);
                                lineDataSet3.setLineWidth(3f);
                                lineDataSet3.setDrawValues(false);

                                lineDatasets.add(lineDataSet1);
                                lineDatasets.add(lineDataSet2);
                                lineDatasets.add(lineDataSet3);

                                //////////////

                                LineData data = new LineData(lineDataSet1, lineDataSet2, lineDataSet3);



                                predictionChart.setData(data);





                            }catch(JSONException e){
                                Toast.makeText(ResultScreen.this, "Oops invalid response. Try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResultScreen.this, "Oops invalid request. Try again later", Toast.LENGTH_SHORT).show();

                }
            });
            jsonqueue.add(request);



        }
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

/*
*
*
*
*  <com.anychart.AnyChartView
                android:id="@+id/polarityChart1"
                android:layout_width="300dp"
                android:layout_height="300dp"
                android:layout_marginTop="30sp"
                />
*
*
* */