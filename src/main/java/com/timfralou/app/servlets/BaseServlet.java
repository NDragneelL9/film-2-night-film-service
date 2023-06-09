package com.timfralou.app.servlets;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.models.Film;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/films/top-250")
public class BaseServlet extends HttpServlet {
        private static final String BASE_URL = "https://kinopoiskapiunofficial.tech/";
        private static final String TOP_250_URL = "api/v2.2/films/top?type=TOP_250_BEST_FILMS";

        public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
                        throws IOException, ServletException {
                Dotenv dotenv = Dotenv
                                .configure()
                                .directory("/usr/local/tomcat/webapps")
                                .filename("env")
                                .load();
                String API_KEY = dotenv.get("KNPSK_API_KEY");
                String top250url = BASE_URL + TOP_250_URL;

                try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        // to prevent exception when encountering unknown property:
                        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                        String jsonSTR = getTopFilms(top250url, API_KEY);
                        JSONObject json = new JSONObject(jsonSTR);
                        JSONArray jsonFilms = json.getJSONArray("films");

                        Film[] filmList = objectMapper.readValue(jsonFilms.toString(), Film[].class);
                        for (Film film : filmList) {
                                System.out.println(film);
                        }

                        servResponse.setCharacterEncoding("UTF-8");
                        servResponse.setContentType("application/json");
                        PrintWriter out = servResponse.getWriter();
                        out.println(json.toString());
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
        }

        private String getTopFilms(String url, String API_KEY) throws IOException, InterruptedException {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                                .GET()
                                .header("X-API-KEY", API_KEY)
                                .uri(URI.create(url))
                                .build();

                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

                return response.body();
        }
}