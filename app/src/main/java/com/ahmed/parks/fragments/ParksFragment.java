package com.ahmed.parks.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmed.parks.R;
import com.ahmed.parks.adapter.OnParkClickListener;
import com.ahmed.parks.adapter.ParkRecyclerViewAdapter;
import com.ahmed.parks.model.Park;
import com.ahmed.parks.model.ParkViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParksFragment extends Fragment implements OnParkClickListener{

    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;
    private ParkViewModel parkViewModel;

    public ParksFragment() {
        // Required empty public constructor
    }

    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parkList = new ArrayList <>();
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view , savedInstanceState);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);

        if(parkViewModel.getParks().getValue() != null){

            parkList = parkViewModel.getParks().getValue();

            parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parkList,  this);
            recyclerView.setAdapter(parkRecyclerViewAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parks , container , false);

        recyclerView = view.findViewById(R.id.park_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onParkClicked(Park park) {

//        Log.i("TAG" , "onParkClicked: " + park.getName());

        parkViewModel.setSelectedPark(park);
        getFragmentManager().beginTransaction()
                .replace(R.id.park_fragment, DetailsFragment.newInstance())
                .commit();
    }
}