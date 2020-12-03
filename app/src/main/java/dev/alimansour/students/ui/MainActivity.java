package dev.alimansour.students.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import dev.alimansour.students.R;
import dev.alimansour.students.data.DataSource;
import dev.alimansour.students.model.Student;

public class MainActivity extends AppCompatActivity {
    private EditText firstNameEditText, lastNameEditText, levelEditText, degreeEditText;
    private Button saveButton, listButton;
    private boolean isEdit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        levelEditText = findViewById(R.id.levelEditText);
        degreeEditText = findViewById(R.id.degreeEditText);
        saveButton = findViewById(R.id.saveButton);
        listButton = findViewById(R.id.listButton);

        isEdit = getIntent().getBooleanExtra("isEdit", false);
        id = getIntent().getIntExtra("id", -1);

        loadData();

        saveButton.setOnClickListener(v -> {
            if (firstNameEditText.getText().toString().isEmpty()) {
                firstNameEditText.setError("Please enter first name!");
            } else if (lastNameEditText.getText().toString().isEmpty()) {
                lastNameEditText.setError("Please enter last name!");
            } else if (levelEditText.getText().toString().isEmpty()) {
                levelEditText.setError("Please enter level!");
            } else if (degreeEditText.getText().toString().isEmpty()) {
                degreeEditText.setError("Please enter degree!");
            } else {
                Student student = new Student(
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        Byte.parseByte(levelEditText.getText().toString()),
                        Double.parseDouble(degreeEditText.getText().toString())
                );
                if (isEdit) {
                    DataSource.updateStudent(id, student);
                } else {
                    DataSource.addStudent(student);
                }

                isEdit = false;
                saveButton.setText("Save");

                Toast.makeText(this, "Student data was saved successfully", Toast.LENGTH_LONG).show();

                // Empty screen inputs
                firstNameEditText.setText("");
                lastNameEditText.setText("");
                levelEditText.setText("");
                degreeEditText.setText("");
            }
        });

        listButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ListActivity.class));
        });
    }

    private void loadData() {
        if (isEdit) {
            Student currentStudent = DataSource.getStudent(id);
            firstNameEditText.setText(currentStudent.getFirstName());
            lastNameEditText.setText(currentStudent.getLastName());
            levelEditText.setText(String.valueOf(currentStudent.getLevel()));
            degreeEditText.setText(String.valueOf(currentStudent.getDegree()));
            saveButton.setText("Update");
        }
    }

}