package dev.alimansour.students.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import dev.alimansour.students.R;
import dev.alimansour.students.model.Student;
import dev.alimansour.students.ui.viewmodel.StudentsViewModel;
import dev.alimansour.students.ui.viewmodel.StudentsViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private ImageView logoImageView;
    private EditText firstNameEditText, lastNameEditText, levelEditText, degreeEditText;
    private Button saveButton, listButton;
    private boolean isEdit;
    private Student currentStudent;
    private StudentsViewModel studentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();

        Glide.with(this).load(getString(R.string.logo_url)).into(logoImageView);

        loadData();

        logoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", "https://www.yatlearning.com/Front/English/Global/Home.aspx");
            startActivity(intent);
        });

        saveButton.setOnClickListener(v -> saveStudent());

        listButton.setOnClickListener(v ->
                startActivity(new Intent(this, ListActivity.class)));
    }

    private void initVariables() {
        logoImageView = findViewById(R.id.logoImageView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        levelEditText = findViewById(R.id.levelEditText);
        degreeEditText = findViewById(R.id.degreeEditText);
        saveButton = findViewById(R.id.saveButton);
        listButton = findViewById(R.id.listButton);

        studentsViewModel = new ViewModelProvider(this,
                new StudentsViewModelFactory(getApplication()))
                .get(StudentsViewModel.class);
    }

    private void saveStudent() {
        if (firstNameEditText.getText().toString().isEmpty()) {
            firstNameEditText.setError("Please enter first name!");
        } else if (lastNameEditText.getText().toString().isEmpty()) {
            lastNameEditText.setError("Please enter last name!");
        } else if (levelEditText.getText().toString().isEmpty()) {
            levelEditText.setError("Please enter level!");
        } else if (degreeEditText.getText().toString().isEmpty()) {
            degreeEditText.setError("Please enter degree!");
        } else {
            if (isEdit) updateStudent();
            else addNewStudent();
            resetForm();
        }
    }

    private void updateStudent() {
        currentStudent.setFirstName(firstNameEditText.getText().toString());
        currentStudent.setLastName(lastNameEditText.getText().toString());
        currentStudent.setLevel(Byte.parseByte(levelEditText.getText().toString()));
        currentStudent.setDegree(Double.parseDouble(degreeEditText.getText().toString()));
        studentsViewModel.updateStudent(currentStudent).observe(this, isUpdated -> {
            if (isUpdated) Toast.makeText(this,
                    "Student's data was updated successfully",
                    Toast.LENGTH_LONG).show();
        });
    }

    private void addNewStudent() {
        Student student = new Student(firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(),
                Byte.parseByte(levelEditText.getText().toString()),
                Double.parseDouble(degreeEditText.getText().toString())
        );
        studentsViewModel.addNewStudent(student).observe(this, isAdded -> {
            if (isAdded) Toast.makeText(this,
                    "Student's data was added successfully",
                    Toast.LENGTH_LONG).show();
        });
    }

    private void resetForm() {
        isEdit = false;
        saveButton.setText("Save");

        // Empty screen inputs
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        levelEditText.setText("");
        degreeEditText.setText("");
    }

    private void loadData() {
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        int id = getIntent().getIntExtra("id", -1);
        if (isEdit) {
            studentsViewModel.findStudent(id).observe(this, student -> {
                currentStudent = student;
                firstNameEditText.setText(student.getFirstName());
                lastNameEditText.setText(student.getLastName());
                levelEditText.setText(String.valueOf(student.getLevel()));
                degreeEditText.setText(String.valueOf(student.getDegree()));
                saveButton.setText("Update");
            });
        }
    }
}