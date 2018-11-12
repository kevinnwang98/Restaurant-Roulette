package com.example.sauhardpant.restaurantroulette;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sauhardpant.restaurantroulette.ui.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, new LoginFragment())
                .commit();
    }
}
