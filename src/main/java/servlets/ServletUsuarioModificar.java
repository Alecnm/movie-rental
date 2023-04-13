package servlets;

import controller.UsuarioController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletUsuarioModificar", urlPatterns = {"/ServletUsuarioModificar"})
public class ServletUsuarioModificar extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ServletUsuarioModificar() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioController usuario = new UsuarioController();

        String username = request.getParameter("username");
        String contrasena = request.getParameter("contrasena");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        double saldo = Double.parseDouble(request.getParameter("saldo"));
        boolean premium = Boolean.parseBoolean(request.getParameter("premium"));

        String usuarioStr = usuario.modificar(username, contrasena, nombre, apellidos,
                email, saldo, premium);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(usuarioStr);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doGet(request, response);
    }
}
