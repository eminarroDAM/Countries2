package org.ecaib.countries2.ui.mnemotecniques;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ecaib.countries2.Mnemotecnique;
import org.ecaib.countries2.R;
import org.ecaib.countries2.SharedViewModel;
import org.ecaib.countries2.databinding.FragmentMnemotecniquesBinding;

public class MnemotecniquesFragment extends Fragment {

    private MnemotecniquesViewModel mnemotecniquesViewModel;
    private FragmentMnemotecniquesBinding binding;
    private SharedViewModel shared_model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mnemotecniquesViewModel =
                new ViewModelProvider(this).get(MnemotecniquesViewModel.class);


        binding = FragmentMnemotecniquesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference base = FirebaseDatabase.getInstance("https://countries2-a5d3e-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        DatabaseReference users = base.child("users");

        shared_model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        shared_model.getUser().observe(getViewLifecycleOwner(), user -> {
            Log.e("UID", String.valueOf(users.child(user.getUid())));
            DatabaseReference uid = users.child(user.getUid());
            DatabaseReference mnemotecniques = uid.child("mnemotecniques");

            // --Hardcoded listview entries
            Mnemotecnique mnemotecnique1 = new Mnemotecnique();
            mnemotecnique1.setTitle("Cabo Verde");
            mnemotecnique1.setText("Desde el Cabo Verde se ve una bonita playa: Praia");
            DatabaseReference reference = mnemotecniques.push();

            Mnemotecnique mnemotecnique2 = new Mnemotecnique();
            mnemotecnique2.setTitle("Eritrea");
            mnemotecnique2.setText("La Eritrosis me dio Asma: Asmana");
            DatabaseReference reference2 = mnemotecniques.push();


            reference.setValue(mnemotecnique1);
            reference2.setValue(mnemotecnique2);

            // --Until here





            FirebaseListOptions<Mnemotecnique> options = new FirebaseListOptions.Builder<Mnemotecnique>()
                    .setQuery(mnemotecniques, Mnemotecnique.class)
                    .setLayout(R.layout.mnemotecnique)
                    .setLifecycleOwner(this)
                    .build();


            FirebaseListAdapter<Mnemotecnique> adapter = new FirebaseListAdapter<Mnemotecnique>(options) {
                @Override
                protected void populateView(View v, Mnemotecnique model, int position) {
                    TextView txtTitle = v.findViewById(R.id.txtTitle);
                    TextView txtText = v.findViewById(R.id.txtText);

                    txtTitle.setText(model.getTitle());
                    txtText.setText(model.getText());
                }
            };

            ListView lvIncidencies = view.findViewById(R.id.lvMnemotecniques);
            lvIncidencies.setAdapter(adapter);
        });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}