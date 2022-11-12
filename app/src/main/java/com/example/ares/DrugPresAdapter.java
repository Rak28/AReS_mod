package com.example.ares;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrugPresAdapter extends RecyclerView.Adapter<DrugPresAdapter.DrugPresHolder> {

    private Context context;
    private ArrayList<DrugClass> drugs;

    public DrugPresAdapter(Context context, ArrayList<DrugClass> drugs) {
        Log.e("Drugs is empty", drugs.toString());
        this.context = context;
        this.drugs = drugs;
    }

    @NonNull
    @Override
    public DrugPresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_layout, parent, false);

        return new DrugPresHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugPresHolder holder, int position) {
        DrugClass drug = drugs.get(position);
        holder.SetDetails(drug);

    }


    @Override
    public int getItemCount() {
        return drugs.size();
    }

    class DrugPresHolder extends RecyclerView.ViewHolder {

        private TextView morning, afternoon, night;
        private EditText route, days;
        private TextView drugName;

        public DrugPresHolder(@NonNull View itemView) {
            super(itemView);
            morning = itemView.findViewById(R.id.morningSwitch);
            afternoon = itemView.findViewById(R.id.afternoonSwitch);
            night = itemView.findViewById(R.id.nightSwitch);
            route = (EditText) itemView.findViewById(R.id.route);
            days = (EditText) itemView.findViewById(R.id.days);
            drugName = (TextView) itemView.findViewById(R.id.drugNameItem);
        }

        void SetDetails(DrugClass drug) {
            if (drug.isMorning()) {
                morning.setText("Morning");
            }
            if (drug.isAfternoon()) {
                afternoon.setText("Afternoon");
            }
            if (drug.isNight()) {
                night.setText("Night");
            }
            route.setText(drug.getRoute());
            days.setText((drug.getDays()));
            drugName.setText(drug.getDrugName());


        }
    }

}
