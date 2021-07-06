package com.example.vaccineslotcheck;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private Button searchButton;
    EditText pincodeEdt;
    RecyclerView centersRV;
    CenterRVAdapter centerRVAdapter;
    ArrayList<CenterRvModal> centerlist;
    ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = findViewById(R.id.idBtnSearch);
        pincodeEdt = (EditText) findViewById(R.id.idEdtPinCode);
        centersRV =(RecyclerView) findViewById(R.id.centersRV);
        centerlist = new ArrayList<CenterRvModal>();
        loadingPB= findViewById(R.id.idPBLoading);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pincode = pincodeEdt.getText().toString();
                if (pincode.length() != 6) {
                    Toast.makeText(MainActivity.this, "Please enter Valid Pincode"+pincode.length(), Toast.LENGTH_SHORT).show();

                } else {
                    centerlist.clear();

                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {


                                    String txtDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    getAppointments(pincode, txtDate);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }

            }

        });
    }


    private void getAppointments(String pinCode, String date) {
        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=" + pinCode + "&date=" + date;
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Tag", "Success is response"+ response);
                        loadingPB.setVisibility(View.GONE);

                        try {
                            JSONArray centerArray = response.getJSONArray("centers");

                            if (centerArray.length()==0) {
                                Toast.makeText(MainActivity.this, "No Center Found", Toast.LENGTH_SHORT).show();


                            }
                            for (int i = 0; i < centerArray.length(); i++) {
                                JSONObject centerObj = centerArray.getJSONObject(i);
                                String CenterName = centerObj.getString("name");
                                String CenterAddress = centerObj.getString("address");
                                String CenterFromTime = centerObj.getString("from");
                                String CenterToTime = centerObj.getString("to");
                                String fee_type = centerObj.getString("fee_type");

                                JSONObject sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0);
                                int ageLimit = sessionObj.getInt("min_age_limit");
                                String vaccineName = sessionObj.getString(("vaccine"));
                                int availableCapacity = sessionObj.getInt("available_capacity");

                                CenterRvModal center = new CenterRvModal(CenterName, CenterAddress, CenterFromTime, CenterToTime, fee_type, ageLimit, vaccineName, availableCapacity);
                                centerlist.add(center);

                            }
                            // on the below line we are passing this list to our adapter class.
                            centerRVAdapter = new CenterRVAdapter(centerlist, MainActivity.this);

                            // on the below line we are setting layout manager to our recycler view.
                             LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                             centersRV.setLayoutManager(layoutManager);
                             centersRV.setHasFixedSize(true);
                            // on the below line we are setting an adapter to our recycler view.

                            centersRV.setAdapter(centerRVAdapter);
                            // on the below line we are notifying our adapter as the data is updated.
                            centerRVAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Tag", "response is error"+error);
                        Toast.makeText(MainActivity.this, "Fail to get response", Toast.LENGTH_SHORT).show();

                    }
                });

        queue.add(jsonObjectRequest);
    }
}