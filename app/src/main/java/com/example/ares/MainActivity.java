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
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addFAB;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText name, age, weight, bp, sickness;
    private Button add, save, cancel;
    ArrayAdapter adapter;
    private Switch morning, afternoon, night;
    private EditText route, days;

    private DrugAdapter drugAdapter;
    private ArrayList<DrugClass> drugClassArrayList;

    RecyclerView recyclerView;
    AutoCompleteTextView drugName;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    private File pdfFile;

    PdfDocument document;

    Bitmap bmp, scaledBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFAB = findViewById(R.id.floatingActionButton);



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
        String[] drugs = {"Dolo", "doo", "ddadasda", "dadsd", "drwerex", "dathdfgfg", "aasdasd", "bsdfsdf"};

        String[] drugs1 = {"Zithromax 500 Tablet","Avil 25 Tablet","Allegra 120mg","Augmentin 625 Duo ","Aciloc 150","Alex","Avomine","Asthalin 100mcg ","Atarax 25mg Tablet","Benadryl ","Buscopan 20mg","Betnesol Tablet","Chymoral Forte Tablet","Combiflam Tablet","Cetrizine Tablet","Ciplox ","Crocin Advance Tablet","Calpol 650mg Tablet","Dolo 650 Tablet","Duphaston 10mg Tablet","Drotin-M Tablet","Dytor 10 Tablet","Dexona Tablet","Duraplus Tablet","Ecosprin 75 Tablet","Evion LC Tablet","Emeset 4 Tablet","Enzomac Plus Tablet","Etizola 0.25 Tablet","Eldoper 2mg Capsule","Folvite 5mg Tablet","Flexon Tablet","Fluka 150 Tablet","Febrex Plus Tablet","Flexura D Tablet","Forxiga 10mg Tablet","Gabapin NT Tablet","Gudcef 200 Tablet","Glycomet 500","Glizid-M Tablet","Glynase-MF Tablet","Hifenac-P Tablet","Hetrazan 100 Tablet","Hifenac-MR Tablet",};
        String[] rxcode = {"226827","1037115","1190331","562251","1090518","1048075","108942","2053818","1484091","1049889","412723","105387","452454","1151552","1011482","103943","104908","1049270","1049270","201946","2609415","198369","105392","402096","103954","1008227","104894","2592914","2592243","1036807","1008064","1151552","105328","1052679","105802","1486977","2609465","309078","1043578","199825","199825","2608541","2609404","2608541"};


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, drugs1);

        drugName.setAdapter(adapter);
        drugName.setThreshold(1);

//        Log.e("Index", "is : " + Arrays.binarySearch(drugs1, drugName.toString()));
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        recyclerView = contactPopupView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        drugClassArrayList = new ArrayList<>();
        drugAdapter = new DrugAdapter(this, drugClassArrayList);
        recyclerView.setAdapter(drugAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

//        createListData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drugClassArrayList.add(new DrugClass(drugClassArrayList.size() + 1 + " " + drugName.getText().toString(), route.getText().toString(), days.getText().toString(), morning.isChecked(), afternoon.isChecked(), night.isChecked()));

                drugAdapter.notifyDataSetChanged();
//                Log.e("Index", "is : " + Arrays.binarySearch(drugs1, drugName.getText().toString()));

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdf();
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

    private void createListData() {

        DrugClass drug = new DrugClass("Dolo 650", "Tablet", "5", true, false, true);
        drugClassArrayList.add(drug);
    }

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