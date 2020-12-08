package dev.alimansour.students.ui.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import dev.alimansour.students.data.AppDatabase;
import dev.alimansour.students.model.Student;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
public class StudentsViewModel extends AndroidViewModel {
    private static AppDatabase database;
    private static Student currentStudent;
    private static MutableLiveData<Boolean> isAddedLiveData = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isUpdatedLiveData = new MutableLiveData<>();
    private static MutableLiveData<Student> studentLiveData = new MutableLiveData<>();
    private static MutableLiveData<List<Student>> studentListLiveData = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isDeletedLiveData = new MutableLiveData<>();

    public StudentsViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<Boolean> addNewStudent(Student student) {
        new AddNewStudent().execute(student.getFirstName()
                , student.getLastName(),
                String.valueOf(student.getLevel()),
                String.valueOf(student.getDegree()))
        ;
        return isAddedLiveData;
    }

    public LiveData<Boolean> updateStudent(Student student) {
        currentStudent = student;
        new UpdateStudent().execute();
        return isUpdatedLiveData;
    }

    public LiveData<Student> findStudent(int id) {
        new FindStudent().execute(id);
        return studentLiveData;
    }

    public LiveData<List<Student>> getStudents() {
        new GetStudents().execute();
        return studentListLiveData;
    }

    public LiveData<Boolean> deleteStudent(Student student) {
        new DeleteStudent().execute(student);
        return isDeletedLiveData;
    }

    public LiveData<List<Student>> getFilteredStudentsByLevel(byte level){
        new FilterByLevel().execute(level);
        return studentListLiveData;
    }

    static class AddNewStudent extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAddedLiveData.setValue(false);
        }

        @Override
        protected Void doInBackground(String... strings) {
            Student student = new Student(
                    strings[0],
                    strings[1],
                    Byte.parseByte(strings[2]),
                    Double.parseDouble(strings[3])
            );
            database.studentDao().addStudent(student);
            isAddedLiveData.postValue(true);
            return null;
        }

    }

    private static class UpdateStudent extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isUpdatedLiveData.setValue(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.studentDao().updateStudent(currentStudent);
            isUpdatedLiveData.postValue(true);
            return null;
        }
    }

    private static class FindStudent extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            studentLiveData.postValue(database.studentDao().findStudent(integers[0]));
            return null;
        }
    }

    private static class GetStudents extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            studentListLiveData.postValue(database.studentDao().getStudents());
            return null;
        }
    }

    private static class DeleteStudent extends AsyncTask<Student, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isDeletedLiveData.setValue(false);
        }

        @Override
        protected Void doInBackground(Student... students) {
            database.studentDao().deleteStudent(students[0]);
            isDeletedLiveData.postValue(true);
            return null;
        }
    }

    public static class FilterByLevel extends AsyncTask<Byte, Void, Void> {

        @Override
        protected Void doInBackground(Byte... bytes) {
            studentListLiveData.postValue(database.studentDao().getStudentsByLevel(bytes[0]));
            return null;
        }
    }
}
