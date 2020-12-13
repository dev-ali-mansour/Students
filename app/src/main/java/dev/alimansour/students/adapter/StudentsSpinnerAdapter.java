package dev.alimansour.students.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dev.alimansour.students.model.Student;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
public class StudentsSpinnerAdapter extends ArrayAdapter<Student> {
    private List<Student> students;

    public StudentsSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Student> students) {
        super(context, resource, students);
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Nullable
    @Override
    public Student getItem(int position) {
        return students.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
         String fullName = getItem(position).getFirstName() + " " + getItem(position).getLastName();
        label.setText(fullName);
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        String fullName = students.get(position).getFirstName() + " " + students.get(position).getLastName();
        label.setText(fullName);
        return label;
    }
}
