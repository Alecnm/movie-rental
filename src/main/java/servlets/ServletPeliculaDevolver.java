package servlets;

import controller.PeliculaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletPeliculaDevolver", urlPatterns = {"/ServletPeliculaDevolver"})
public class ServletPeliculaDevolver extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ServletPeliculaDevolver() {
        super();
    }
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PeliculaController pelicula = new PeliculaController();

        String username = request.getParameter("username");
        int id = Integer.parseInt(request.getParameter("id"));

        String libroStr = pelicula.devolver(id, username);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(libroStr);
        out.flush();
        out.close();
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doGet(request, response);
    }
}
