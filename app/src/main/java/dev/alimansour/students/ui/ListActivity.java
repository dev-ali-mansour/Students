package dev.alimansour.students.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.alimansour.students.R;
import dev.alimansour.students.adapter.StudentsAdapter;
import dev.alimansour.students.data.DataSource;

public class ListActivity extends AppCompatActivity implements OnCLickListener {
    private RecyclerView studentsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        studentsRecyclerView = findViewById(R.id.studentsRecyclerView);

        initRecyclerView();

    }

    private void initRecyclerView() {
        studentsRecyclerView.setHasFixedSize(true);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StudentsAdapter adapter = new StudentsAdapter();
        adapter.setDataSource(DataSource.getStudents());
        adapter.setListener(this);
        studentsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int id) {
        Intent intent =new Intent(this, MainActivity.class);
        intent.putExtra("isEdit",true);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }
}