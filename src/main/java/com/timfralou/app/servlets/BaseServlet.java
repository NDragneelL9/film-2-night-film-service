package com.timfralou.app.servlets;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.*;

public class BaseServlet extends HttpServlet {
        public static final Dotenv dotenv = Dotenv.configure()
                        .directory("/usr/local/")
                        .filename("env")
                        .load();
        public static final int BATCH_SIZE = 20;
}