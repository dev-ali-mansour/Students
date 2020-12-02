package dev.alimansour.students.data;

import java.util.ArrayList;
import java.util.List;

import dev.alimansour.students.model.Student;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
public class DataSource {
    private static int id = 0;
    private static final List<Student> students = new ArrayList<>();

    public static void addStudent(Student student) {
        Student mStudent = new Student(id++,
                student.getFirstName(),
                student.getLastName(),
                student.getLevel(),
                student.getDegree()
        );

        students.add(mStudent);
    }

    public static void updateStudent(int id, Student updated) {
        Student selected = getStudent(id);
        if (selected != null) {
            selected.setFirstName(updated.getFirstName());
            selected.setLastName(updated.getLastName());
            selected.setLevel(updated.getLevel());
            selected.setDegree(updated.getDegree());
        }
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static Student getStudent(int id) {
        Student student = null;
        for (Student mStudent : students) {
            if (mStudent.getId() == id) {
                student = mStudent;
                break;
            }
        }
        return student;
    }
}
