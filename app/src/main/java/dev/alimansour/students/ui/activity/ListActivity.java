package dev.alimansour.students.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.alimansour.students.R;
import dev.alimansour.students.adapter.StudentsAdapter;
import dev.alimansour.students.model.Student;
import dev.alimansour.students.ui.viewmodel.StudentsViewModel;
import dev.alimansour.students.ui.viewmodel.StudentsViewModelFactory;

public class ListActivity extends AppCompatActivity implements OnCLickListener {
    private RecyclerView studentsRecyclerView;
    private StudentsViewModel studentsViewModel;
    private EditText levelFilterEditText;
    private ImageButton filterButton, viewAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        levelFilterEditText = findViewById(R.id.levelFilterEditText);
        filterButton = findViewById(R.id.filterButton);
        viewAllButton = findViewById(R.id.viewAllButton);
        studentsRecyclerView = findViewById(R.id.studentsRecyclerView);

        loadStudents();

        filterButton.setOnClickListener(v -> {
            try {
                if (levelFilterEditText.getText().toString().isEmpty()) {
                    levelFilterEditText.setError("Please enter level to filter!");
                    return;
                }
                byte level = Byte.parseByte(levelFilterEditText.getText().toString());
                if (level > 5) {
                    levelFilterEditText.setError("Please enter correct level! it is up to 5.");
                    return;
                }
                studentsViewModel.getFilteredStudentsByLevel(level)
                        .observe(this, students -> initRecyclerView(students));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        viewAllButton.setOnClickListener(v -> {
            levelFilterEditText.setText("");
            loadStudents();
        });

    }

    private void loadStudents() {
        studentsViewModel = new ViewModelProvider(this,
                new StudentsViewModelFactory(getApplication()))
                .get(StudentsViewModel.class);
        studentsViewModel.getStudents().observe(this, students -> initRecyclerView(students));
    }

    private void initRecyclerView(List<Student> students) {
        studentsRecyclerView.setHasFixedSize(true);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StudentsAdapter adapter = new StudentsAdapter();
        adapter.setDataSource(students);
        adapter.setListener(this);
        studentsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isEdit", true);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDelete(Student student) {
        studentsViewModel.deleteStudent(student).observe(this, isDeleted -> {
            if (isDeleted) loadStudents();
        });
    }
}