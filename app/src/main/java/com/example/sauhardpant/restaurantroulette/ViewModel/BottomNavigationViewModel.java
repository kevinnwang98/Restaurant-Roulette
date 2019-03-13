package com.example.sauhardpant.restaurantroulette.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.MenuItem;

import com.example.sauhardpant.restaurantroulette.R;
import com.example.sauhardpant.restaurantroulette.ViewModel.Interfaces.CustomBottomNavClickListener;

import java.util.List;

public class BottomNavigationViewModel extends AndroidViewModel {
    private static final String TAG = BottomNavigationViewModel.class.getSimpleName();

    private List<CustomBottomNavClickListener> menuClickListeners;

    public BottomNavigationViewModel(Application application) {
        super(application);
    }

    public void addMenuClickListener(CustomBottomNavClickListener listener) {
        menuClickListeners.add(listener);
    }

    public void onMenuItemClicked(MenuItem menuItem) {
        menuItem.setChecked(true);
    }
}
