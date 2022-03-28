package org.ecaib.countries2.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ecaib.countries2.Country;
import org.ecaib.countries2.Mnemotecnique;
import org.ecaib.countries2.IncidenciesInfoWindowAdapter;
import org.ecaib.countries2.R;
import org.ecaib.countries2.SharedViewModel;
import org.ecaib.countries2.ui.countries.MainViewModel;

import java.util.List;
//import org.ecaib.incivisme.databinding.FragmentMapaBinding;

public class MapaFragment extends Fragment {

    private MapaViewModel mapaViewModel;
    private SharedViewModel model;
    //private FragmentMapaBinding binding;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private List<Country> countriesList;
    private MainViewModel mainViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapaViewModel =
                new ViewModelProvider(this).get(MapaViewModel.class);

        //binding = FragmentMapaBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        View root = inflater.inflate(R.layout.fragment_mapa,container,false);

        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);




        mapFragment.getMapAsync(map -> {

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]
                                {Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
            }


            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            map.setMyLocationEnabled(true);

            MutableLiveData<LatLng> currentLatLng = model.getCurrentLatLng();
            LifecycleOwner owner = getViewLifecycleOwner();
            currentLatLng.observe(owner, latLng -> {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                map.animateCamera(cameraUpdate);
                currentLatLng.removeObservers(owner);
            });


            mainViewModel.getCountries().observe(getViewLifecycleOwner(), countries -> {
                countriesList = countries;

                for (Country country : countriesList){

                    LatLng aux = new LatLng(
                            country.getLatitud(),
                            country.getLongitud()
                    );

                    map.addMarker(new MarkerOptions()

                            .title(country.getName())
                            .snippet(country.getCapital())
                            .position(aux)
                            .icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_GREEN)));

                    try {
                        // Customize the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = map.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        getActivity(), R.raw.map_style));

                        if (!success) {
                            Log.e(null, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(null, "Can't find style. Error: ", e);
                    }
                }
            });



        });




        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}