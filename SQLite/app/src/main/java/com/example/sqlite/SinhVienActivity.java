package com.example.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SinhVienActivity extends AppCompatActivity {
    private Button major;
    Database database;
    Database majorTable;

    ListView lv_sinhvien;
    List<Sinhvien> arraySinhvien;
    AdapterSinhvien adapter;
    List<Note> arrayNganh;
    private int idSelected = -1;

    Button addStudent, edit, delete;


    Spinner nganh;

    EditText name;
    EditText date;
    EditText address;
    EditText gender; // gender
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sinh_vien_activity);
        nganh = findViewById(R.id.nganh);
        lv_sinhvien = findViewById(R.id.lv_sinhvien);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.editTextText3);
        addStudent = findViewById(R.id.addStudent);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);

        arraySinhvien = new ArrayList<>();
        adapter = new AdapterSinhvien(this, R.layout.student_layout, arraySinhvien);
        lv_sinhvien.setAdapter(adapter);

        database = new Database(this, "sinhvien.sqlite", null, 1);

        majorTable = new Database(this, "note.sqlite", null, 1);


        getMajor();

        List<String> majorList = new ArrayList<>();
        for (Note note : arrayNganh) {
            majorList.add(note.getContent());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, majorList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nganh.setAdapter(adapter);








//        database.QueryData("DROP TABLE Sinhvien");

        database.QueryData("CREATE TABLE IF NOT EXISTS Sinhvien(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(50), Ngaysinh VARCHAR(50), Diachi VARCHAR(50), IdNganh VARCHAR(50), Gioitinh VARCHAR(50))");

        getData();

        // create 1 sinh vien

//         database.QueryData("INSERT INTO Sinhvien VALUES(null, 'Nguyen Van A', '01/01/2000', 'Ha Noi', '1', 'Nam')");


        major = findViewById(R.id.major);

        major.setOnClickListener(v -> {
            Intent intent = new Intent(SinhVienActivity.this, MainActivity.class);
            startActivity(intent);
        });

        addStudent.setOnClickListener(v -> {
            String ten = name.getText().toString();
            String ngaysinh = date.getText().toString();
            String diachi = address.getText().toString();
            String gioitinh = gender.getText().toString();
            String Nganh = nganh.getSelectedItem().toString();
            database.QueryData("INSERT INTO Sinhvien VALUES(null, '"+ten+"', '"+ngaysinh+"', '"+diachi+"', '"+Nganh+"', '"+gioitinh+"')");
            getData();
        });

        lv_sinhvien.setOnItemClickListener((parent, view, position, id) -> {
            name.setText(arraySinhvien.get(position).getTen());
            date.setText(arraySinhvien.get(position).getNgaysinh());
            address.setText(arraySinhvien.get(position).getDiachi());
            gender.setText(arraySinhvien.get(position).getGioitinh());
            idSelected = arraySinhvien.get(position).getId();
        });

        edit.setOnClickListener(v -> {
            String ten = name.getText().toString();
            String ngaysinh = date.getText().toString();
            String diachi = address.getText().toString();
            String gioitinh = gender.getText().toString();
            String Nganh = nganh.getSelectedItem().toString();
            database.QueryData("UPDATE Sinhvien SET Ten = '"+ten+"', Ngaysinh = '"+ngaysinh+"', Diachi = '"+diachi+"', IdNganh = '"+Nganh+"', Gioitinh = '"+gioitinh+"' WHERE Id = '"+idSelected+"'");
            getData();
        });
        delete.setOnClickListener(v -> {
            if(idSelected == -1){
                return;
            }
            database.QueryData("DELETE FROM Sinhvien WHERE Id = '"+idSelected+"'");
            getData();
        });




    }
    private void getData(){
        Cursor dataSinhvien = database.GetData("SELECT * FROM Sinhvien");
        arraySinhvien.clear();
        while (dataSinhvien.moveToNext()){
            int id = dataSinhvien.getInt(0);
            String ten = dataSinhvien.getString(1);
            String ngaysinh = dataSinhvien.getString(2);
            String diachi = dataSinhvien.getString(3);
            String idNganh = dataSinhvien.getString(4);
            String gioitinh = dataSinhvien.getString(5);
//            arraySinhvien.add(new Sinhvien(id, ten, ngaysinh, diachi, gioitinh, Integer.parseInt(idNganh)));
            Sinhvien sinhvien = new Sinhvien(id, ten, ngaysinh, diachi, gioitinh, idNganh);
            arraySinhvien.add(sinhvien);


        }
        adapter.notifyDataSetChanged();
    }

    private void getMajor(){
        Cursor data = majorTable.GetData("SELECT * FROM Note");
        arrayNganh = new ArrayList<>();
        while (data.moveToNext()){
            int id = data.getInt(0);
            String content = data.getString(1);
            arrayNganh.add(new Note (id, content));
        }
    }
}
