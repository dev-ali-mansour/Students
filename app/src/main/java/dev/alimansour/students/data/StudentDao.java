package dev.alimansour.students.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import dev.alimansour.students.model.Student;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
@Dao
public interface StudentDao {

    @Insert
    public void addStudent(Student student);

    @Update
    public void updateStudent(Student student);

    @Delete
    public void deleteStudent(Student student);

    @Query("SELECT * FROM students")
    public List<Student> getStudents();

    @Query("SELECT * FROM students WHERE id = :id")
    public Student findStudent(int id);

    @Query("SELECT * FROM students WHERE level = :level")
    public List<Student> getStudentsByLevel(byte level);

}
