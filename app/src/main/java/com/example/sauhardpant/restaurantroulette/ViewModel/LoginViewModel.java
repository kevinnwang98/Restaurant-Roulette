package com.example.sauhardpant.restaurantroulette.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();

    private FirebaseAuth mAuth;

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> loggedIn = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getLoggedIn() {
        return loggedIn;
    }

    public void init() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }

    public void onLogin(String email, String password) {
        isLoading.postValue(true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isLoading.postValue(false);
                        if (task.isSuccessful()) {
                            loggedIn.postValue(true);
                        } else {
                            loggedIn.postValue(false);
                        }
                    }
                });
    }
}
