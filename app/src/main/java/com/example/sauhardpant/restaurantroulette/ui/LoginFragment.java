package com.example.sauhardpant.restaurantroulette.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sauhardpant.restaurantroulette.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginFragment extends Fragment {
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.btn_signUp)
    Button signUpButton;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new SignUpFragment())
                            .commit();
                }
            }
        });

        return rootView;
    }
}
