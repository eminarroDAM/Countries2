package org.ecaib.countries2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("select * from Country")
    LiveData<List<Country>> getCountries();

    @Query("select * from Country where region IN (:region)")
    LiveData<List<Country>> getCountriesRegion(List<String> region);

    @Insert
    void addCountries(List<Country> countries);

    @Delete
    void deleteCountry(Country country);

    @Query("DELETE FROM Country")
    void deleteCountries();
}
