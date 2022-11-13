package com.example.ares;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addFAB;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText name, age, weight, bp, sickness;
    private Button add, save, cancel;
    ArrayAdapter adapter;
    private Switch morning, afternoon, night;
    private EditText route, days;
    private int index;

    private String date;

    private DrugAdapter drugAdapter;
    private ArrayList<DrugClass> drugClassArrayList;
    private PrescriptionAdapter prescriptionAdapter;
    private ArrayList<Prescription> prescriptionArrayList;

    private RecyclerView recyclerView,prescriptionsRecyclerView;
    private AutoCompleteTextView drugName;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    private File pdfFile;

    PdfDocument document;

    Bitmap bmp, scaledBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFAB = findViewById(R.id.floatingActionButton);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        date = df.format(c);


        prescriptionsRecyclerView = findViewById(R.id.patientRecyclerView);
        prescriptionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        prescriptionArrayList = new ArrayList<>();
        prescriptionAdapter = new PrescriptionAdapter(this, prescriptionArrayList);
        Log.e("Prescriptions", prescriptionArrayList.toString());
        prescriptionsRecyclerView.setAdapter(prescriptionAdapter);
        prescriptionsRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        addFAB.setOnClickListener(view -> {
            createNewContactDialog();
        });


    }

    public void createNewContactDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.new_prescription, null);
        name = contactPopupView.findViewById(R.id.name);
        age = contactPopupView.findViewById(R.id.age);
        bp = contactPopupView.findViewById(R.id.bp);
        weight = contactPopupView.findViewById(R.id.weight);
        sickness = contactPopupView.findViewById(R.id.sickness);
        drugName = contactPopupView.findViewById(R.id.drugName);

        morning = contactPopupView.findViewById(R.id.morningSwitch2);
        afternoon = contactPopupView.findViewById(R.id.afternoonSwitch2);
        night = contactPopupView.findViewById(R.id.nightSwitch2);
        route = (EditText) contactPopupView.findViewById(R.id.route2);
        days = (EditText) contactPopupView.findViewById(R.id.days2);

        add = contactPopupView.findViewById(R.id.addButton);
        save = contactPopupView.findViewById(R.id.saveButton);
        cancel = contactPopupView.findViewById(R.id.cancelButton);
//        drugName = findViewById(R.id.drugName);

        String[] drugs = {"Zithromax 500","Avil 25","Allegra 120mg","Augmentin 625 Duo","Aciloc 150","Alex","Avomine","Asthalin 100mcg ","Atarax 25mg","Benadryl","Buscopan 20mg","Betnesol","Chymoral Forte","Combiflam","Cetrizine","Ciplox ","Crocin Advance","Calpol 650mg","Dolo 650","Duphaston 10mg","Drotin-M","Dytor 10","Dexona","Duraplus","Ecosprin 75","Evion LC","Emeset 4","Enzomac Plus","Etizola 0.25","Eldoper 2mg","Folvite 5mg","Flexon","Fluka 150","Febrex Plus","Flexura D","Forxiga 10mg","Gabapin NT","Gudcef 200","Glycomet 500","Glizid-M","Glynase-MF","Hifenac-P","Hetrazan 100","Hifenac-MR","Indocap SR","Inderal 10","Levocet M","Lubrex ","Manforce 100mg","Monocef-O 200","Maxtra","Omnacortil 10","Omee","Ovral L","Otogesic Ear Drop","PAN 40","Phenergan ","Pipzo 4.5gm ","Rantac 150","Reswas","Sucral-O","Taxim-O 200","T-Bact 2%","Taxim 1gm","Ultracet","Urimax 0.4","Vigore Spray","Vomikind","Zytee RB","Drotin"};
        String[] rxcode = {"1","226827","1037115","1190331","562251","1090518","1048075","108942","2053818","1484091","1049889","412723","105387","452454","1151552","1011482","103943","104908","1049270","1049270","201946","2609415","198369","105392","402096","103954","1008227","104894","2592914","2592243","1036807","1008064","1151552","105328","1052679","105802","1486977","2609465","309078","1043578","199825","199825","2608541","2609404","2608541","103156","1132265","2609433","1188426","213271","309078","1052679","1007257","104099","1112687","107416","284400","330144","1659149","1090517","52014","568825","1043030","106346","1656313","1152238","863669","573184","328448","2114017","23684"};


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, drugs);

        drugName.setAdapter(adapter);
        drugName.setThreshold(1);

        drugName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                int index=drugs1.indexOf(drugName.getText().toString());
                String item = adapterView.getItemAtPosition(i).toString();
                index = Arrays.asList(drugs).indexOf(item);
                Log.e("Selected", "drug is: " + item + " " + index);
            }
        });

//        Log.e("Index", "is : " + Arrays.binarySearch(drugs1, drugName.toString()));
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        recyclerView = contactPopupView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        drugClassArrayList = new ArrayList<>();
        DrugAdapter drugAdapter = new DrugAdapter(this, drugClassArrayList);
        recyclerView.setAdapter(drugAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

//        createListData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                drugClassArrayList.add(new DrugClass(drugClassArrayList.size() + 1 ,drugName.getText().toString(), route.getText().toString(), days.getText().toString(), rxcode[index+1], morning.isChecked(), afternoon.isChecked(), night.isChecked(), date));


                drugAdapter.notifyDataSetChanged();
//                Log.e("Index", "is : " + Arrays.binarySearch(drugs1, drugName.getText().toString()));

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdf();
                    prescriptionArrayList.add(new Prescription(name.getText().toString(), date, age.getText().toString(), bp.getText().toString(), weight.getText().toString(), sickness.getText().toString(), drugClassArrayList));
                    prescriptionAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void createPdfWrapper() throws FileNotFoundException{
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }

    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

//    private void createListData() {
//
//        DrugClass drug = new DrugClass("Dolo 650", "Tablet", "5", true, false, true);
//        drugClassArrayList.add(drug);
//    }

    private void createPdf() throws FileNotFoundException{


        final Dialog invoicedDialog = new Dialog(this);
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


        for (int i = 0; i < drugClassArrayList.size(); i++) {
            tempDrugName.add(drugClassArrayList.get(i).getDrugName());
        }


//        listView = contactPopupView.findViewById(R.id.listView);

        listView.setLayoutManager(new LinearLayoutManager(this));
        DrugPresAdapter drugPresAdapter = new DrugPresAdapter(this, drugClassArrayList);
        listView.setAdapter(drugPresAdapter);
        listView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        namePdf.setText(name.getText().toString());
        agePdf.setText(age.getText().toString());
        weightPdf.setText(weight.getText().toString());
        bpPdf.setText(bp.getText().toString());
        sickPdf.setText((sickness.getText().toString()));
        datePdf.setText(date);




        invoicedDialog.show();

        Button button = invoicedDialog.findViewById(R.id.download);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = getBitmapFromView(invoicedDialog.findViewById(R.id.pdf_prescription));
                document = new PdfDocument();
                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
                PdfDocument.Page myPage1 = document.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();


                canvas.drawBitmap(bitmap, 0, 0, null);
                document.finishPage(myPage1);
                createFile();
                invoicedDialog.dismiss();
            }
        });





//
//
//
//        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
//        if (!docsFolder.exists()) {
//            docsFolder.mkdir();
//            Log.i("Rakshith", "Created a new directory for PDF");
//        }
//
//        String pdfname = "GiftItem.pdf";
//        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
//        OutputStream output = new FileOutputStream(pdfFile);
//        DocumentsContract.Document document = new Document(Page.);
//
//        PdfDocument myPdfDocument = new PdfDocument();
//        Paint myPaint = new Paint();
//        Paint titlePaint = new Paint();
//
//        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder.(1200, 2010, 1).create();
//        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
//        Canvas canvas = myPage1.getCanvas();
//
//        canvas.drawBitmap(scaledBmp, 0, 0, myPaint);
//
//        titlePaint.setTextAlign(Paint.Align.CENTER);
//        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        titlePaint.setTextSize(70);
//        canvas.drawText("Prescription", 600, 270,titlePaint);
//        myPaint.setColor(Color.rgb(0, 113, 188));
//
//        myPaint.setTextSize(30f);
//        myPaint.setTextAlign(Paint.Align.RIGHT);
//
//        myPdfDocument.finishPage(myPage1);
//
//        File file = new File(Environment.getExternalStorageDirectory(), "/hello.pdf");
//        try {
//            myPdfDocument.writeTo(new FileOutputStream(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        myPdfDocument.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 01 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                if (document != null) {
                    ParcelFileDescriptor pfd = null;
                    try {
                        pfd = getContentResolver().openFileDescriptor(uri, "w");
                        FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
                        document.writeTo(fileOutputStream);
                        document.close();
                        Toast.makeText(this, "Pdf saved successfully", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        try {
                            DocumentsContract.deleteDocument(getContentResolver(), uri);
                        } catch (FileNotFoundException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void createFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf");
        startActivityForResult(intent,01);

    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}