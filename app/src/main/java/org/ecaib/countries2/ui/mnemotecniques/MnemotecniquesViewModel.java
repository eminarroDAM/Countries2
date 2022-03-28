package org.ecaib.countries2.ui.mnemotecniques;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MnemotecniquesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MnemotecniquesViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}