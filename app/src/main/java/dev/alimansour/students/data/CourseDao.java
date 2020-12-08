package dev.alimansour.students.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import dev.alimansour.students.model.Course;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
@Dao
public interface CourseDao {

    @Insert
    public void insert(Course course);

    @Update
    public void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses")
    public List<Course> getCourses();

    @Query("SELECT * FROM courses WHERE id = :id")
    public Course findCourse(int id);
}
