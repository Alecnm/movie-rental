package servlets;

import controller.AlquilerController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletAlquilerListar", urlPatterns = {"/ServletAlquilerListar"})
public class ServletAlquilerListar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ServletAlquilerListar() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AlquilerController alquiler = new AlquilerController();
        String username = request.getParameter("username");

        String alquilerStr = alquiler.listarAlquileres(username);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(alquilerStr);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doGet(request, response);
    }
}
