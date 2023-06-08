package com.timfralou.app.servlets;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
                URL obj = new URL(BASE_URL + TOP_250_URL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                Dotenv dotenv = Dotenv
                                .configure()
                                .directory("/usr/local/tomcat/webapps")
                                .filename("env")
                                .load();

                String API_KEY = dotenv.get("KNPSK_API_KEY");

                con.setRequestMethod("GET");
                con.setRequestProperty("X-API-KEY", API_KEY);

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        in.close();

                        servResponse.setCharacterEncoding("UTF-8");
                        servResponse.setContentType("application/json");
                        PrintWriter out = servResponse.getWriter();
                        out.println(response.toString());
                } else {
                        System.out.println("GET request did not work.");
                        System.out.println("Response code: " + responseCode);
                }
        }
}