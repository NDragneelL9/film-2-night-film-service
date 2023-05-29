package com.timfralou.app.servlets;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/servlet")
public class BaseServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws IOException, ServletException {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Hello servlet World!</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Hello servlet World!</h1>");
                out.println("</body>");
                out.println("</html>");
        }
}