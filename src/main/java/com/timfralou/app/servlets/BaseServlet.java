package com.timfralou.app.servlets;

import java.io.*;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/serlvets")
public class BaseServlet extends HttpServlet {

        public void doGet(HttpServletRequest servRequest, HttpServletResponse servResponse)
                        throws IOException, ServletException {
                Dotenv dotenv = Dotenv
                                .configure()
                                .directory("/usr/local/tomcat/webapps")
                                .filename("env")
                                .load();
                System.out.println("Hello");
        }
}