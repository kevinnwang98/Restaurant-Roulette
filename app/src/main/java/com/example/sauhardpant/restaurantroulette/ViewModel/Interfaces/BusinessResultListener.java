package com.example.sauhardpant.restaurantroulette.ViewModel.Interfaces;

import java.util.List;

public interface BusinessResultListener {
    void onResult(List businesses);
    void onError();
}
