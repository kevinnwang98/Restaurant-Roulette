package com.example.sauhardpant.restaurantroulette.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sauhardpant.restaurantroulette.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFragment extends Fragment {
    @BindView(R.id.link_login) TextView tvLoginLink;

    public SignUpFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);

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
}
