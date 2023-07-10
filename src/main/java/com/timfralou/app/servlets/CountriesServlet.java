package com.timfralou.app.servlets;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.timfralou.app.models.Country;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/countries")
public class CountriesServlet extends BaseServlet {

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse)
            throws IOException, ServletException {

        String jsonFilters = super.knpApi().getFilmFilters();
        JSONArray jsonArrCountries = new JSONObject(jsonFilters).getJSONArray("countries");
        Country[] countries = super.objMapper().readValue(jsonArrCountries.toString(), Country[].class);
        for (Country country : countries) {
            country.saveToDB(super.postgres().conn());
        }
        String responseJSON = super.objMapper().writeValueAsString(countries);
        handleResponse(servResponse, responseJSON);
    }
}
