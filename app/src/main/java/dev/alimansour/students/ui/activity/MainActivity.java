package dev.alimansour.students.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProvider;
import dev.alimansour.students.CourseReservation;
import dev.alimansour.students.R;
import dev.alimansour.students.model.Student;
import dev.alimansour.students.ui.viewmodel.StudentsViewModel;
import dev.alimansour.students.ui.viewmodel.StudentsViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private ImageView logoImageView;
    private EditText firstNameEditText, lastNameEditText, levelEditText, degreeEditText;
    private Button saveButton, listButton, reservationButton, fragmentsButton;
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
            /*Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", "https://www.yatlearning.com/Front/English/Global/Home.aspx");
            startActivity(intent);*/

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse("https://www.yatlearning.com/Front/English/Global/Home.aspx"));
        });

        registerForContextMenu(logoImageView);

        saveButton.setOnClickListener(v -> saveStudent());

        listButton.setOnClickListener(v ->
                startActivity(new Intent(this, ListActivity.class)));

        reservationButton.setOnClickListener(v ->
                startActivity(new Intent(this, CourseReservation.class)));

        fragmentsButton.setOnClickListener(v ->
                startActivity(new Intent(this, AuthenticationActivity.class)));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_copy:
                Toast.makeText(this, "You select to copy", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_paste:
                Toast.makeText(this, "You select to paste", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Application settings", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "About Us", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_contact:
                Toast.makeText(this, "Contact Us", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initVariables() {
        logoImageView = findViewById(R.id.logoImageView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        levelEditText = findViewById(R.id.levelEditText);
        degreeEditText = findViewById(R.id.degreeEditText);
        saveButton = findViewById(R.id.saveButton);
        listButton = findViewById(R.id.listButton);
        reservationButton = findViewById(R.id.reservationButton);
        fragmentsButton = findViewById(R.id.fragmentsButton);

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