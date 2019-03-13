package com.example.sauhardpant.restaurantroulette.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.apollographql.apollo.yelp.SearchYelpQuery;
import com.example.sauhardpant.restaurantroulette.R;
import com.example.sauhardpant.restaurantroulette.ViewModel.BaseResultsViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessResultsListViewFragment extends Fragment {
    private static final String TAG = BusinessResultsListViewFragment.class.getSimpleName();

    @BindView(R.id.results_list_view)
    ListView resultsListView;

    private List<SearchYelpQuery.Business> businessList = new ArrayList<>();

    private BaseResultsViewModel parentViewModel;

    public BusinessResultsListViewFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentViewModel = ViewModelProviders.of(getParentFragment()).get(BaseResultsViewModel.class);

        subscribeToViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        ButterKnife.bind(this, rootView);

        resultsListView.setAdapter(new ResultsAdapter());

        return rootView;
    }

    private void subscribeToViewModel() {
        parentViewModel.getBusinessList().observe(this, new Observer<List<SearchYelpQuery.Business>>() {
            @Override
            public void onChanged(@Nullable List<SearchYelpQuery.Business> businesses) {
                for (int i = 0; i < businesses.size(); i++) {
                    Log.d(TAG, "This is from guess where: " + businesses.get(i) + "\n");
                    businessList = businesses;
                    resultsListView.setAdapter(new ResultsAdapter());
                }
            }
        });
    }

    private class ResultsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return businessList.size();
        }

        @Override
        public Object getItem(int position) {
            return businessList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_view_item, parent, false);
            }

            ((TextView)convertView.findViewById(R.id.business_name))
                    .setText(((SearchYelpQuery.Business) getItem(position)).name());

            Picasso.get().load(
                    ((SearchYelpQuery.Business) getItem(position)).photos().get(0)
            ).into((ImageView) convertView.findViewById(R.id.business_image));

            return convertView;
        }
    }
}
