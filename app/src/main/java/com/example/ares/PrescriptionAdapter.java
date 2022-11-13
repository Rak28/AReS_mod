package com.example.ares;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionHolder> {
    private Context context;
    private ArrayList<Prescription> prescriptions;

    public PrescriptionAdapter(Context context, ArrayList<Prescription> prescriptions) {
        this.context = context;
        this.prescriptions = prescriptions;
    }

    @NonNull
    @Override
    public PrescriptionAdapter.PrescriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prescription_item, parent, false);

        return new PrescriptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionAdapter.PrescriptionHolder holder, int position) {
        Prescription prescription = prescriptions.get(position);
        holder.SetDetails(prescription);
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    class PrescriptionHolder extends RecyclerView.ViewHolder {
        private TextView name, date, sickness;

        public PrescriptionHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.patientName);
            date = itemView.findViewById(R.id.dateText);
            sickness = itemView.findViewById(R.id.sicknessText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    final Dialog invoicedDialog = new Dialog(context);
                    invoicedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    invoicedDialog.setContentView(R.layout.pdf_layout);
                    invoicedDialog.setCancelable(true);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(invoicedDialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    invoicedDialog.getWindow().setAttributes(lp);

                    TextView namePdf = invoicedDialog.findViewById(R.id.nameText);
                    TextView agePdf = invoicedDialog.findViewById(R.id.ageText);
                    TextView weightPdf = invoicedDialog.findViewById(R.id.weightText);
                    TextView bpPdf = invoicedDialog.findViewById(R.id.bpText);
                    TextView sickPdf = invoicedDialog.findViewById(R.id.sickText);
                    TextView datePdf = invoicedDialog.findViewById(R.id.datePdf);

                    ArrayList<String> tempDrugName = new ArrayList<>();

                    RecyclerView listView = invoicedDialog.findViewById(R.id.listView);
                    ArrayList<DrugClass> drugClassArrayList = new ArrayList<>();
                    drugClassArrayList.add(new DrugClass(1, "Dolo", "Tablet", "3", "231564123", true, true, true, "11-11-22"));
                    drugClassArrayList.add(new DrugClass(2, "Alex", "Syrup", "2", "231564123", true, true, true, "11-11-22"));
//        int index, String drugName, String route, String days, String rxcode, boolean morning, boolean afternoon, boolean night, String date

                    ArrayList<Prescription> prescriptionArrayList;
                    prescriptionArrayList = new ArrayList<>();
                    prescriptionArrayList.add(new Prescription("Dr. Ramesh", "11-11-22", "20", "120/80", "80", "Fever", drugClassArrayList));
                    prescriptionArrayList.add(new Prescription("Dr. Suresh", "11-11-22", "20", "120/80", "80", "Fever", drugClassArrayList));

                    listView.setLayoutManager(new LinearLayoutManager(context));
                    DrugPresAdapter drugPresAdapter = new DrugPresAdapter(context, drugClassArrayList);
                    listView.setAdapter(drugPresAdapter);
                    listView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

                    namePdf.setText("Rajesh");
                    agePdf.setText(prescriptionArrayList.get(pos).getAge());
                    weightPdf.setText(prescriptionArrayList.get(pos).getWeight());
                    bpPdf.setText(prescriptionArrayList.get(pos).getBp());
                    sickPdf.setText((prescriptionArrayList.get(pos).getSickness()));
                    datePdf.setText(prescriptionArrayList.get(pos).getDate());




                    invoicedDialog.show();
                }
            });

        }

        void SetDetails(Prescription prescription) {
            name.setText(prescription.getName());
            date.setText(prescription.getDate());
            sickness.setText(prescription.getSickness());
        }
    }
}
