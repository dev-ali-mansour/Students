package dev.alimansour.students.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import dev.alimansour.students.R;

public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
//        Navigation.findNavController(view).navigate(R.id.signUpFragment);
        new Handler().postDelayed(() -> {
            Bundle bundle = new Bundle();
            bundle.putString("fullName","Adel Shahin");
            bundle.putInt("age",18);
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment,bundle);
        },3000);
        return view;
    }
}