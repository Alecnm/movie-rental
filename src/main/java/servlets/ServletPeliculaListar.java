
package servlets;

import controller.PeliculaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletPeliculaListar", urlPatterns = {"/ServletPeliculaListar"})
public class ServletPeliculaListar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ServletPeliculaListar() {
        super();
        
    }
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PeliculaController pelicula = new PeliculaController();
        
        boolean ordenar = Boolean.parseBoolean(request.getParameter("ordenar"));
        String orden = request.getParameter("orden");

        String peliculaStr = pelicula.listar(ordenar, orden);
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
