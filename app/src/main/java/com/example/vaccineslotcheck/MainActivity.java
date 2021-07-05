package com.example.vaccineslotcheck;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
        pincodeEdt= findViewById(R.id.idEdtPinCode);
        centersRV =findViewById(R.id.centersRV);

        ArrayList<CenterRvModal> centerlist = new ArrayList<CenterRvModal>();
        searchButton.setOnClickListener((View.OnClickListener) this);
    }
    public DatePickerDialog onClick(View v){
        String pincode =pincodeEdt.toString();
        if(pincode.length()!=6){
            Toast.makeText(this, "Please enter Valid Pincode", Toast.LENGTH_SHORT).show();
        }
        else{
            centerlist.clear();
            Calendar cal= Calendar.getInstance();
            int year= cal.get(Calendar.YEAR);
            int month= cal.get(Calendar.MONTH);
            int day= cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener)
                    this, year, month, day);

            //getAppointments(pincode,dateStr);
        }

        return null;
    }
    private void getAppointments(String pinCode, String date){
        String url ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=" + pinCode + "&date=" + date;
        RequestQueue queue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    Log.e("Tag","Success is $response");
                    loadingPB.setVisibility(View.GONE);
                    try{
                        JSONArray centerArray = response.getJSONArray("Centers");
                        if(centerArray.length()==0){
                            //Toast.makeText(this, "No Center Found", Toast.LENGTH_SHORT).show();

                        }
                        for(int i=0;i<centerArray.length();i++){
                            JSONObject centerObj = centerArray.getJSONObject(i);
                            String CenterName=centerObj.getString("name");
                            String CenterAddress =centerObj.getString("address");
                            String CenterFromTime=centerObj.getString("from");
                            String CenterToTime=centerObj.getString("to");
                            String fee_type=centerObj.getString("fee_type'");

                            JSONObject sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0);
                            int ageLimit = sessionObj.getInt("min_age_limit");
                            String vaccineName= sessionObj.getString(("vaccine"));
                            int availableCapacity=sessionObj.getInt("available_capacity");

                             CenterRvModal center = new CenterRvModal(CenterName, CenterAddress, CenterFromTime, CenterToTime, fee_type, ageLimit, vaccineName, availableCapacity);
                            centerlist =centerlist + center;
                        }
                        // on the below line we are passing this list to our adapter class.
                        CenterRVAdapter centerRVAdapter = new CenterRVAdapter(centerlist);

                        // on the below line we are setting layout manager to our recycler view.
                        centersRV.layoutManager = LinearLayoutManager(this);

                        // on the below line we are setting an adapter to our recycler view.
                        centersRV.adapter = centerRVAdapter;

                        // on the below line we are notifying our adapter as the data is updated.
                        centerRVAdapter.notifyDataSetChanged();

                    } catch(JSONException e){
                        e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Tag", "response is $error");
                        //Toast.makeText(this, "Fail to get response", Toast.LENGTH_SHORT).show();

                    }
                });

                queue.add(request)
    }
}