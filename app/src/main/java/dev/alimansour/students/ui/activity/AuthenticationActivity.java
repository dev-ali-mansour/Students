package dev.alimansour.students.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import dev.alimansour.students.R;

public class AuthenticationActivity extends AppCompatActivity {
private ImageView logoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        logoImageView = findViewById(R.id.logoImageView);

        Glide.with(this).load(getString(R.string.logo_url)).into(logoImageView);
    }
}