package dev.alimansour.students.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.alimansour.students.R;
import dev.alimansour.students.model.Student;
import dev.alimansour.students.ui.activity.OnCLickListener;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {
    private List<Student> students;
    private OnCLickListener listener;

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.student_list_item, parent, false);
        StudentViewHolder viewHolder = new StudentViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student, listener);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setDataSource(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public void setListener(OnCLickListener listener) {
        this.listener = listener;
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView fullNameTextView, degreeTextView, levelTextView;
        private ImageView resultImageView;
        private ImageButton deleteButton;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            fullNameTextView = itemView.findViewById(R.id.fullNameTextView);
            degreeTextView = itemView.findViewById(R.id.degreeTextView);
            levelTextView = itemView.findViewById(R.id.levelTextView);
            resultImageView = itemView.findViewById(R.id.resultImageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        void bind(Student student, OnCLickListener listener) {
            String fullName = student.getFirstName() + " " + student.getLastName();
            fullNameTextView.setText("Full Name: " + fullName);
            levelTextView.setText("Level: " + student.getLevel());
            degreeTextView.setText("Degree: " + student.getDegree());

            if (student.getDegree() > 350) {
                resultImageView.setImageResource(R.drawable.right);
            } else {
                resultImageView.setImageResource(R.drawable.wrong);
            }

            itemView.setOnClickListener(v -> {
                listener.onClick(student.getId());
            });
            deleteButton.setOnClickListener(v -> {
                listener.onDelete(student);
            });
        }
    }
}
