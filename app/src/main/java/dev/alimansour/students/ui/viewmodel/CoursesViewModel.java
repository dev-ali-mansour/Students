package dev.alimansour.students.ui.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import dev.alimansour.students.data.AppDatabase;
import dev.alimansour.students.model.Course;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
public class CoursesViewModel extends AndroidViewModel {
    private static AppDatabase database;
    private static MutableLiveData<Boolean> isAdded = new MutableLiveData<>();
    private static MutableLiveData<List<Course>> courseList = new MutableLiveData<>();

    public CoursesViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getInstance(application.getApplicationContext());
    }

    public LiveData<Boolean> addCourse(Course course) {
        new AddCourse().execute(course);
        return isAdded;
    }

    public LiveData<List<Course>> getCourses() {
        new GetCourses().execute();
        return courseList;
    }

    private static class AddCourse extends AsyncTask<Course, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAdded.setValue(false);
        }

        @Override
        protected Void doInBackground(Course... courses) {
            database.courseDao().insert(courses[0]);
            isAdded.postValue(true);
            return null;
        }
    }

    private static class GetCourses extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            courseList.postValue(database.courseDao().getCourses());
            return null;
        }
    }
}
