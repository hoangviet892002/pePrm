package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterSinhvien extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Sinhvien> sinhviens;

    public AdapterSinhvien(Context context, int layout, List<Sinhvien> sinhviens) {
        this.context = context;
        this.layout = layout;
        this.sinhviens = sinhviens;
    }

    @Override
    public int getCount() {
        return sinhviens.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.nameStudent = convertView.findViewById(R.id.nameStudent);
            holder.dateStudent = convertView.findViewById(R.id.dateStudent);
            holder.addressStudent = convertView.findViewById(R.id.addressStudent);
            holder.majorStudent = convertView.findViewById(R.id.majorStudent);
            holder.genderStudent = convertView.findViewById(R.id.genderStudent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Sinhvien sinhvien = sinhviens.get(position);
        holder.nameStudent.setText(sinhvien.getTen());
        holder.dateStudent.setText(sinhvien.getNgaysinh());
        holder.addressStudent.setText(sinhvien.getDiachi());
        holder.majorStudent.setText(sinhvien.getNganh());
        holder.genderStudent.setText(sinhvien.getGioitinh());
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView nameStudent;
        TextView dateStudent;
        TextView addressStudent;
        TextView majorStudent;
        TextView genderStudent;
    }
}
