package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
     TextView tvcases ,tvr,tvc,tva,tvtc,tvtd,tod,tvac;
     Button b;
     SimpleArcLoader sac;
     ScrollView sv;
     PieChart pc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvcases=findViewById(R.id.tvcases);
        tvc=findViewById(R.id.tvC);
        tvr=findViewById(R.id.tvR);
        tva=findViewById(R.id.tvA);
        tvtc=findViewById(R.id.tvTC);
        tvtd=findViewById(R.id.tvTD);
        tod=findViewById(R.id.tvTOD);
        tvac=findViewById(R.id.TVAC);
        sac=findViewById(R.id.loader);
        sv=findViewById(R.id.scrollSats);
        pc=findViewById(R.id.piechart);
        b=findViewById(R.id.btnTrack);

        fetchData();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AffectedCountries.class);
                startActivity(i);

            }
        });

    }
    private void fetchData() {

        String url  = "https://corona.lmao.ninja/v2/all/";

        sac.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            tvcases.setText(jsonObject.getString("cases"));
                            tvr.setText(jsonObject.getString("recovered"));
                            tvc.setText(jsonObject.getString("critical"));
                            tva.setText(jsonObject.getString("active"));
                            tvtc.setText(jsonObject.getString("todayCases"));
                            tvtd.setText(jsonObject.getString("deaths"));
                            tod.setText(jsonObject.getString("todayDeaths"));
                            tvac.setText(jsonObject.getString("affectedCountries"));


                            pc.addPieSlice(new PieModel("Cases",Integer.parseInt(tvcases.getText().toString()), Color.parseColor("#B71C1C")));
                            pc.addPieSlice(new PieModel("Recoverd",Integer.parseInt(tvr.getText().toString()), Color.parseColor("#FFD600")));
                            pc.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvtd.getText().toString()), Color.parseColor("#03DAC5")));
                            pc.addPieSlice(new PieModel("Active",Integer.parseInt(tva.getText().toString()), Color.parseColor("#FF6D00")));
                            pc.startAnimation();

                           sac.stop();
                            sac.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            sac.stop();
                            sac.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               sac.stop();
               sac.setVisibility(View.GONE);
                sv.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }





}