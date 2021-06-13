package com.ahmed.parks.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmed.parks.R;
import com.ahmed.parks.adapter.ViewPagerAdapter;
import com.ahmed.parks.model.ParkViewModel;

public class DetailsFragment extends Fragment {

    private ParkViewModel parkViewModel;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view , savedInstanceState);

        viewPager = view.findViewById(R.id.details_view_pager);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);

        TextView parkName = view.findViewById(R.id.details_park_name);
        TextView parkDesignation = view.findViewById(R.id.details_park_designation);
        TextView parkDescription = view.getRootView().findViewById(R.id.details_description);
        TextView parkActivities = view.getRootView().findViewById(R.id.details_activities);
        TextView parkEntranceFees = view.getRootView().findViewById(R.id.details_entrancefees);
        TextView parkOperatingHours = view.getRootView().findViewById(R.id.details_operatinghours);
        TextView parkDetailsTopics = view.getRootView().findViewById(R.id.details_topics);
        TextView parkDirections = view.getRootView().findViewById(R.id.details_directions);


        parkViewModel.getSelectedPark().observe(this , park -> {

            parkName.setText(park.getName());
            parkDesignation.setText(park.getDesignation());
            parkDescription.setText(park.getDescription());

            StringBuilder activitiesBuilder = new StringBuilder();
            StringBuilder topicsBuilder = new StringBuilder();
            StringBuilder operatingHoursBuilder = new StringBuilder();


            for(int i = 0; i <park.getActivities().size() ; i++) {

                activitiesBuilder.append(park.getActivities().get(i).getName())
                        .append(" | ");
            }

            for(int i = 0; i < park.getTopics().size() ; i++) {

                topicsBuilder.append(park.getTopics().get(i).getName())
                        .append(" | ");
            }

            for(int i = 0; i < park.getOperatingHours().size() ; i++) {

                operatingHoursBuilder.append("Wednesday: ").append(park.getOperatingHours().get(i)
                        .getStandardHours().getWednesday()).append("\n")
                        .append("Monday: ").append(park.getOperatingHours().get(i)
                        .getStandardHours().getMonday()).append("\n")
                        .append("Thursday: ").append(park.getOperatingHours().get(i)
                        .getStandardHours().getThursday()).append("\n")
                        .append("Sunday: ").append(park.getOperatingHours().get(i)
                        .getStandardHours().getSunday()).append("\n")
                        .append("Tuesday: ").append(park.getOperatingHours().get(i)
                        .getStandardHours().getTuesday()).append("\n")
                        .append("Friday: ").append(park.getOperatingHours().get(i)
                        .getStandardHours().getFriday()).append("\n")
                        .append("Saturday: ").append(park.getOperatingHours().get(i)
                        .getStandardHours().getSaturday()).append("\n");
            }

            if(park.getEntranceFees().size() > 0){

                parkEntranceFees.setText(String.format("Cost: $ %s" , park.getEntranceFees().get(0).getCost()));

            }else {
                parkEntranceFees.setText(R.string.info_text);
            }

            if(!TextUtils.isEmpty(park.getDirectionsInfo())){

                parkDirections.setText(park.getDirectionsInfo());

            }else {
                parkDirections.setText(R.string.direcations_text);
            }

            parkActivities.setText(activitiesBuilder);
            parkDetailsTopics.setText(topicsBuilder);
            parkOperatingHours.setText(operatingHoursBuilder);

            viewPagerAdapter = new ViewPagerAdapter(park.getImages());

            viewPager.setAdapter(viewPagerAdapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details , container , false);
    }
}