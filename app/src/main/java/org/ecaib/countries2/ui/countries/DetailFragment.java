package org.ecaib.countries2.ui.countries;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.ecaib.countries2.Country;
import org.ecaib.countries2.LlistatSharedViewModel;
import org.ecaib.countries2.databinding.DetailFragmentBinding;

public class DetailFragment extends Fragment {

    private org.ecaib.countries2.ui.countries.DetailViewModel mViewModel;
    private DetailFragmentBinding binding;
    /*private TextView nameDetail;
    private TextView rarityDetail;
    private TextView manaCostDetail;
    private TextView textDetail;
    private ImageView imageDetail;*/

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailFragmentBinding.inflate(inflater);
        View view = binding.getRoot();

        /*nameDetail = view.findViewById(R.id.txtNameDetail);
        rarityDetail = view.findViewById(R.id.txtRarityDetail);
        manaCostDetail = view.findViewById(R.id.txtManaCostDetail);
        textDetail = view.findViewById(R.id.txtTextDetail);
        imageDetail = view.findViewById(R.id.imgDetail);*/


        Intent intent = getActivity().getIntent();

        if(intent != null) {
            Country country = (Country)intent.getSerializableExtra("country");

            if (country != null){
                showData(country);
            }
        }

        LlistatSharedViewModel sharedViewModel = new ViewModelProvider(getActivity()).get(LlistatSharedViewModel.class);
        sharedViewModel.getSelected().observe(getViewLifecycleOwner(), this::showData);

        return view;
    }

    private void showData(Country country) {
        binding.txtNameDetail.setText(country.getName());
        binding.txtCapitalDetail.setText(country.getCapital());
        binding.txtPopulationDetail.setText(String.valueOf(country.getPopulation()));
        String region = country.getSubregion() + "   -   " + country.getRegion();
        binding.txtSubregionRegionDetail.setText(region);
        //binding.txtTextDetail.setText(country.getId());


        Glide.with(getContext()
        ).load(country.getFlagUrl()
        ).into(binding.imgDetail);





    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(org.ecaib.countries2.ui.countries.DetailViewModel.class);
        // TODO: Use the ViewModel
    }

}