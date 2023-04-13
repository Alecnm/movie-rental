/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controller.PeliculaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletPeliculaAlquilar", urlPatterns = {"/ServletPeliculaAlquilar"})
public class ServletPeliculaAlquilar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ServletPeliculaAlquilar() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PeliculaController pelicula = new PeliculaController();
        
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");

        String peliculaStr = pelicula.alquilar(id, username);
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println(peliculaStr);
        out.flush();
        out.close();
    }

    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            doGet(request, response);

    }
}
