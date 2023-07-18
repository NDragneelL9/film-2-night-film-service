package com.timfralou.app.servlets;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.timfralou.app.models.Country;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/countries")
public class CountriesServlet extends BaseServlet {

    public void doPut(HttpServletRequest servRequest, HttpServletResponse servResponse) {
        try {
            String responseJSON = "";
            String jsonFilters = super.knpApi().getFilmFilters();
            JSONObject jsonObj = new JSONObject(jsonFilters);
            if (jsonObj.has("message")) {
                responseJSON = super.objMapper().writeValueAsString(jsonObj.getString("message"));
            } else {
                JSONArray jsonArrCountries = jsonObj.getJSONArray("countries");
                Country[] countries = super.objMapper().readValue(jsonArrCountries.toString(), Country[].class);
                for (Country country : countries) {
                    country.saveToDB(super.dbConn());
                }
                responseJSON = super.objMapper().writeValueAsString(countries);
            }
            handleResponse(servResponse, responseJSON);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
