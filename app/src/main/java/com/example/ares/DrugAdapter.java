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

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.DrugHolder> {

    private Context context;
    private ArrayList<DrugClass> drugs;

    public DrugAdapter(Context context, ArrayList<DrugClass> drugs) {
        Log.e("Drugs is empty", drugs.toString());
        this.context = context;
        this.drugs = drugs;
    }

    @NonNull
    @Override
    public DrugHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_layout, parent, false);

        return new DrugHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugAdapter.DrugHolder holder, int position) {
        DrugClass drug = drugs.get(position);
        holder.SetDetails(drug);
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    class DrugHolder extends RecyclerView.ViewHolder {

        private TextView morning, afternoon, night, rxcode, route, days, drugName, index;

        public DrugHolder(@NonNull View itemView) {
            super(itemView);
            morning = itemView.findViewById(R.id.morningSwitch);
            afternoon = itemView.findViewById(R.id.afternoonSwitch);
            night = itemView.findViewById(R.id.nightSwitch);
            route = itemView.findViewById(R.id.route);
            rxcode = itemView.findViewById(R.id.rxcodeText);
            days = itemView.findViewById(R.id.days);
            drugName = itemView.findViewById(R.id.drugNameItem);
            index = itemView.findViewById(R.id.indexText);
        }

        void SetDetails(DrugClass drug) {
//            morning.setText("0");
//            afternoon.setText("0");
//            night.setText("0");

            if (drug.isMorning()) {
                morning.setText("Morning");
            }
            if (drug.isAfternoon()) {
                afternoon.setText("Afternoon");
            }
            if (drug.isNight()) {
                night.setText("Night");
            }
            rxcode.setText(drug.getRxcode());
            route.setText(drug.getRoute());
            days.setText((drug.getDays()));
            drugName.setText(drug.getDrugName());
            index.setText(drug.getIndex()+".");

        }
    }

}
