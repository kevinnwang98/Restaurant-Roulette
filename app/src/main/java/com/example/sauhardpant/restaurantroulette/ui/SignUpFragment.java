package com.example.sauhardpant.restaurantroulette.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sauhardpant.restaurantroulette.R;
import com.example.sauhardpant.restaurantroulette.ViewModel.SignUpViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFragment extends Fragment {
    @BindView(R.id.link_login)
    TextView tvLoginLink;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_password)
    EditText etPassword;
    @BindView(R.id.input_name)
    EditText etName;
    @BindView(R.id.main_linear_layout)
    LinearLayout linearLayout;

    ProgressDialog progressBar;

    SignUpViewModel viewModel;

    public SignUpFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        viewModel.init();
        subscribeToViewModel();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    viewModel.onSignUp(etEmail.getText().toString(), etPassword.getText().toString());
                }
            }
        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, new LoginFragment())
                            .commit();
                }
            }
        });

        return rootView;
    }

    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Email cannot be empty");
            valid = false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Password needs to be at least 6 characters long");
            valid = false;

        }
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Name cannot be empty");
            valid = false;
        }

        return valid;
    }

    private void subscribeToViewModel() {
        viewModel.getUserSignedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean signedIn) {
                if (signedIn !=null && signedIn) {
                    Toast.makeText(getActivity(), "User successfully signed in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                if (loading != null && loading) {
                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(false);
                    progressBar.setMessage("Creating account for " + etName.getText().toString());
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                } else {
                    progressBar.hide();
                }
            }
        });

        viewModel.getUserSignedIn().observe(this, new Observer<Boolean>() {
            int numOfSignUpAttempts = 0;
            @Override
            public void onChanged(@Nullable Boolean signedIn) {
                if (signedIn != null && signedIn) {
                    if (getActivity() != null) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_fragment_container, new BaseResultsFragment())
                                .commit();
                    }
                } else {
                    if (numOfSignUpAttempts == 0) {
                        Snackbar.make(linearLayout,
                                R.string.sign_up_fail,
                                BaseTransientBottomBar.LENGTH_INDEFINITE)
                                .setAction("Try Again", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        viewModel.onSignUp(etEmail.getText().toString(),
                                                etPassword.getText().toString());
                                    }
                                }).show();
                        numOfSignUpAttempts++;
                    } else {
                        Snackbar.make(linearLayout,
                                "Check connection and try again later",
                                BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
