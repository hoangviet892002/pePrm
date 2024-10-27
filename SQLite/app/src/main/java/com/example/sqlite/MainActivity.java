package com.example.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;

    ListView lv_note;
    ArrayList<Note> arrayNote;
    Adapter adapter;
    EditText edt_content;
    Button btn_add, btn_edit, btn_delete, sinhvien;
    private int idSelected = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv_note = findViewById(R.id.lv_note);
        edt_content = findViewById(R.id.edt_content);
        btn_add = findViewById(R.id.btn_add);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);
        sinhvien = findViewById(R.id.sinhvien);
        arrayNote = new ArrayList<>();

        adapter = new Adapter(this, R.layout.note_layout, arrayNote);
        lv_note.setAdapter(adapter);
        //tao database
        database = new Database(this, "note.sqlite", null, 1);

        //tao bang
        database.QueryData("CREATE TABLE IF NOT EXISTS Note(Id INTEGER PRIMARY KEY AUTOINCREMENT, Content VARCHAR(500))");

        //insert

//        database.QueryData("INSERT INTO Note VALUES(null, 'Hello')");

        //select
        getData();

        lv_note.setOnItemClickListener((parent, view, position, id) -> {
            edt_content.setText(arrayNote.get(position).getContent());
            idSelected = arrayNote.get(position).getId();
        });

        btn_add.setOnClickListener(v -> {
            String content = edt_content.getText().toString();
            if(content.equals("")){
                Toast.makeText(this, "Please enter major", Toast.LENGTH_SHORT).show();
            }else{
                database.QueryData("INSERT INTO Note VALUES(null, '"+content+"')");
                idSelected = -1;
                edt_content.setText("");
                getData();
            }
        });
        btn_edit.setOnClickListener(v -> {
            String content = edt_content.getText().toString();
            if(content.equals("")){
                Toast.makeText(this, "Please enter content", Toast.LENGTH_SHORT).show();
            }else{
                database.QueryData("UPDATE Note SET Content = '"+content+"' WHERE Id = '"+idSelected+"'");
                getData();
            }
        });
        btn_delete.setOnClickListener(v -> {
            if(idSelected == -1){
                Toast.makeText(this, "Please choose a major to delete", Toast.LENGTH_SHORT).show();
            }else{
                database.QueryData("DELETE FROM Note WHERE Id = '"+idSelected+"'");
                idSelected = -1;
                edt_content.setText("");
                getData();
            }
        });
        sinhvien.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SinhVienActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void getData(){
        Cursor data = database.GetData("SELECT * FROM Note");
        arrayNote.clear();
        while (data.moveToNext()){
            String content = data.getString(1);
            int id = data.getInt(0);
            arrayNote.add(new Note(id, content));
        }
        adapter.notifyDataSetChanged();
    }
}