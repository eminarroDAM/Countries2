package org.ecaib.countries2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LlistatSharedViewModel extends ViewModel {
    private final MutableLiveData<org.ecaib.countries2.Country> selected = new MutableLiveData<org.ecaib.countries2.Country>();

    public void select(org.ecaib.countries2.Country country) {
        selected.setValue(country);
    }

    public LiveData<org.ecaib.countries2.Country> getSelected() {
        return selected;
    }

}
