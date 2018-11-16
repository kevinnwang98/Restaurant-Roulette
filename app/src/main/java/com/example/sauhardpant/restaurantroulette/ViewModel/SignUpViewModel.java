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
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends ViewModel {
    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private MutableLiveData<Boolean> isUserSignedIn = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private FirebaseAuth mAuth;

    @NonNull
    public LiveData<Boolean> getUserSignedIn() {
        return isUserSignedIn;
    }

    @NonNull
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void init() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }

    private FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void onSignUp(String email, String password) {
        isLoading.setValue(true);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        isLoading.postValue(false);
                        if (task.isSuccessful()) {
                            isUserSignedIn.postValue(true);
                            Log.d(TAG, "onComplete: SUCCESS");
                        } else {
                            isUserSignedIn.postValue(false);
                            Log.d(TAG, "onComplete: " + task.getException());
                        }
                    }
                });
    }
}
