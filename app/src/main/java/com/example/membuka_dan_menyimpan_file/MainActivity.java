package com.example.membuka_dan_menyimpan_file;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputCatatan, inputJudul;
    Button tombolSimpan, tombolBaca, tombolFileBaru;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputCatatan = findViewById(R.id.input_text);
        tombolSimpan = findViewById(R.id.simpanFile);
        tombolBaca = findViewById(R.id.lihatFile);
        tombolFileBaru = findViewById(R.id.newFile);
        inputCatatan = findViewById(R.id.input_text);
        inputJudul = findViewById(R.id.judulFile);

        tombolFileBaru.setOnClickListener(this);
        tombolBaca.setOnClickListener(this);
        tombolSimpan.setOnClickListener(this);
        path = getFilesDir();
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.newFile:
                newFile();
                break;
            case R.id.lihatFile:
                openFile();
                break;
            case R.id.simpanFile:
                saveFile();
                break;
        }
    }

        //membuat file baru
        public void newFile(){
            inputJudul.setText("");
            inputCatatan.setText("");
            Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
        }


    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        inputJudul.setText(title);
        inputCatatan.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    //membuka file yang sudah disimpan
    public void openFile() {
            showList();
        }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang ingin dibaca");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //menyimpan file
    public void saveFile() {
        if (inputJudul.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = inputJudul.getText().toString();
            String text = inputCatatan.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Menyimpan " + "file" + " " + inputJudul.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    

}