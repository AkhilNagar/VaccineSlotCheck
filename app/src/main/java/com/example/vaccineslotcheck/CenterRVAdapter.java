package com.example.vaccineslotcheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CenterRVAdapter extends RecyclerView.Adapter<CenterRVAdapter.CenterRVViewHolder> {
    private ArrayList<CenterRvModal> centerList;
    private Context mContext;

    public CenterRVAdapter(ArrayList<CenterRvModal> centerList, Context context) {
        this.centerList = centerList;
        this.mContext = context;
    }




    @NonNull
        @Override
        public CenterRVAdapter.CenterRVViewHolder onCreateViewHolder (@NonNull ViewGroup parent,
        int viewType){

            LayoutInflater layoutinflater= LayoutInflater.from(parent.getContext());
            View view= layoutinflater.inflate(R.layout.center_rv_item,parent, false);
            return new CenterRVViewHolder(view);

        }

        @Override
        public void onBindViewHolder (@NonNull CenterRVViewHolder holder,
        int position){
            final CenterRvModal currentItem = centerList.get(position);

            // Set the data to the views here
            holder.setCenterName(currentItem.getCenterName());
            holder.setCenterAddress(currentItem.getCenterAddress());
            holder.setCenterTimings("From:"+currentItem.getCenterFromTime()+"To:"+currentItem.getCenterToTime());
            holder.setCentergetFee_type(currentItem.getFee_type());
            holder.setAgeLimit("Age Limit"+Integer.toString(currentItem.getAgeLimit()));
            holder.setVaccineName(currentItem.getVaccineName());
            holder.setAvailableCapacity("Availability"+ Integer.toString(currentItem.getAvailableCapacity()));

        }

        @Override
        public int getItemCount () {
            return centerList.size();
        }
    public class CenterRVViewHolder extends RecyclerView.ViewHolder {


        private TextView centerName1;
        private TextView centerAddress1;
        private TextView centerTimings1;
        private TextView vaccineName1;
        private TextView centerAgeLimit;
        private TextView centerFeeType;
        private TextView availability;


        public CenterRVViewHolder(@NonNull View itemView) {
            super(itemView);
            centerName1=  itemView.findViewById(R.id.idTVCenterName);
            centerAddress1=itemView.findViewById(R.id.idTVCenterAddress);
            centerTimings1 = itemView.findViewById(R.id.idTVCenterTimings);
            vaccineName1=itemView.findViewById(R.id.idTVVaccineName);
            centerAgeLimit=itemView.findViewById(R.id.idTVAgeLimit);
            centerFeeType=itemView.findViewById(R.id.idTVFeeType);
            availability=itemView.findViewById(R.id.idTVAvaliablity);
        }


        public void setCenterName(String centerName) {
            centerName1.setText(centerName);

        }


        public void setCenterAddress(String centerAddress) {
            centerAddress1.setText(centerAddress);
        }



        public void setCentergetFee_type(String fee_type) {
            centerFeeType.setText(fee_type);
        }



        public void setAgeLimit(String ageLimit) {
            centerAgeLimit.setText(ageLimit);
        }

        public void setVaccineName(String vaccineName) {
            vaccineName1.setText(vaccineName);
        }

        public void setAvailableCapacity(String available) {
            availability.setText(available);
        }

        public void setCenterTimings(String timings) {
            centerTimings1.setText(timings);
        }
    }
}


