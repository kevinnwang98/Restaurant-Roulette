package com.example.sauhardpant.restaurantroulette.ViewModel.Utils;

import java.util.List;

public interface BusinessResultListener {
    void onResult(List businesses);
    void onError();
}
