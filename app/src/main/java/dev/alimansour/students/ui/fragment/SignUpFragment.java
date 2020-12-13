package dev.alimansour.students.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import dev.alimansour.students.R;

public class SignUpFragment extends Fragment {
    private String fullName;
    private int age;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fullName = getArguments().getString("fullName", "user");
            age = getArguments().getInt("age", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Toast.makeText(requireContext(), "Welcome back " + fullName, Toast.LENGTH_LONG).show();
        if (age < 9) {
            new Handler().postDelayed(() -> {
                Toast.makeText(requireContext(),
                        "You need your parents permission to use the app!",
                        Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
            }, 5000);
        }
        return view;
    }
}