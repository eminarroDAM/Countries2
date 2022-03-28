package org.ecaib.countries2;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class LlistatRefreshDataTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        org.ecaib.countries2.CountriesApiClient api = new org.ecaib.countries2.CountriesApiClient();

        ArrayList<org.ecaib.countries2.Country> countries = api.getCountries();

        Log.d(null, countries.toString());
        return null;
    }
}
