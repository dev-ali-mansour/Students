package dev.alimansour.students;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import dev.alimansour.students.adapter.CoursesSpinnerAdapter;
import dev.alimansour.students.adapter.StudentsSpinnerAdapter;
import dev.alimansour.students.model.Course;
import dev.alimansour.students.model.Student;
import dev.alimansour.students.ui.viewmodel.CouresesViewModelFactory;
import dev.alimansour.students.ui.viewmodel.CoursesViewModel;
import dev.alimansour.students.ui.viewmodel.StudentsViewModel;
import dev.alimansour.students.ui.viewmodel.StudentsViewModelFactory;

public class CourseReservation extends AppCompatActivity {
    private Spinner studentsSpinner, coursesSpinner;
    private Button reserveButton;
    private CoursesViewModel coursesViewModel;
    private StudentsViewModel studentsViewModel;
    private Student selectedStudent;
    private Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_reservation);

        setTitle("Course Reservation");

        studentsSpinner = findViewById(R.id.studentsSpinner);
        coursesSpinner = findViewById(R.id.coursesSpinner);
        reserveButton = findViewById(R.id.reserveButton);
        coursesViewModel = new ViewModelProvider(this,
                new CouresesViewModelFactory(getApplication()))
                .get(CoursesViewModel.class);
        studentsViewModel = new ViewModelProvider(this,
                new StudentsViewModelFactory(getApplication()))
                .get(StudentsViewModel.class);

        reserveButton.setOnClickListener(v -> {
            Toast.makeText(this, selectedStudent.getFirstName() + " " + selectedStudent.getLastName() + " has reserved Course: " + selectedCourse.getName(), Toast.LENGTH_LONG).show();
        });

        coursesViewModel.getCourses().observe(this, courses -> {
            CoursesSpinnerAdapter adapter =
                    new CoursesSpinnerAdapter(this, android.R.layout.simple_spinner_item, courses);
            coursesSpinner.setAdapter(adapter);

            coursesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCourse = courses.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        });

        studentsViewModel.getStudents().observe(this, students -> {
            StudentsSpinnerAdapter adapter =
                    new StudentsSpinnerAdapter(this, android.R.layout.simple_spinner_item, students);
            studentsSpinner.setAdapter(adapter);
            studentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedStudent = adapter.getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        });
    }
}