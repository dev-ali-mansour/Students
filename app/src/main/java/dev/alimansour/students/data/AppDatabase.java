package dev.alimansour.students.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import dev.alimansour.students.model.Course;
import dev.alimansour.students.model.Student;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
@Database(entities = {Student.class, Course.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract StudentDao studentDao();

    public abstract CourseDao courseDao();

    public static  AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,AppDatabase.class,"students-database.dp")
                    .build();
        }
        return instance;
    }

}
