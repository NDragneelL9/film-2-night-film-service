package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.BasicTest;
import com.timfralou.app.seeds.CountrySeed;

public class CountryTest extends BasicTest {
    private Country country = new CountrySeed().country();

    @Test
    public void constructsCountry() {
        Country country = new Country("United States");
        boolean isCorrectName = country.toString().contains("United States");
        assertEquals(true, isCorrectName);
    }

    @Test
    public void writesToString() {
        boolean printsToString = country.toString().startsWith("Country [country=");
        assertEquals(true, printsToString);
    }

    @Test
    public void savesCountryToDB() throws SQLException {
        int insertedRows = country.saveToDB(dbTEST.connect());
        assertEquals(1, insertedRows);
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        dbTEST.updateQuery("DELETE FROM countries");
    }
}
