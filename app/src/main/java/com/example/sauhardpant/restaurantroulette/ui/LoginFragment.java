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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.sauhardpant.restaurantroulette.R;
import com.example.sauhardpant.restaurantroulette.ViewModel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends Fragment {
    @BindView(R.id.btn_signup_link)
    Button signUpButton;
    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_password)
    EditText etPassword;
    @BindView(R.id.login_linear_layout)
    LinearLayout linearLayout;
    ProgressDialog progressBar;

    LoginViewModel viewModel;

    @OnClick(R.id.btn_login)
    public void onLoginClicked() {
        if (validateForm()) {
            viewModel.onLogin(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.init();

        subscribeToViewModel();

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

    private void subscribeToViewModel() {
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                if (loading != null && loading) {
                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(false);
                    progressBar.setMessage("Logging In");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                } else {
                    progressBar.hide();
                }
            }
        });

        viewModel.getLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loggedIn) {
                if (loggedIn != null && loggedIn) {
                    // show new screen
                } else {
                    Snackbar.make(linearLayout,
                            R.string.login_failed,
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (etEmail.getText().toString().isEmpty()) {
            isValid = false;
        }

        if (etPassword.getText().toString().isEmpty()) {
            isValid = false;
        }

        return isValid;
    }
}
