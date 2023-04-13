package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import beans.Peliculas;
import connection.DBConnection;

public class PeliculaController implements IPeliculaController {

    @Override
    public String listar(boolean ordenar, String orden) {
        Gson gson = new Gson();

        DBConnection con = new DBConnection();

        String sql = "Select * from peliculas";

        if (ordenar == true) {

            sql += " order by genero " + orden;

        }

        List<String> peliculas = new ArrayList<String>();

        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                String autor = rs.getString("autor");
                int copias = rs.getInt("copias");
                boolean novedad = rs.getBoolean("novedad");

                Peliculas pelicula = new Peliculas(id, titulo, genero, autor, copias, novedad);

                peliculas.add(gson.toJson(pelicula));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return gson.toJson(peliculas);
    }

    @Override
    public String alquilar(int id, String username) {

        Timestamp fecha = new Timestamp(new Date().getTime());
        DBConnection con = new DBConnection();
        String sql = "Insert into alquiler values ('" + id + "', '" + username + "', '" + fecha + "')";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            String modificar = modificar(id);

            if (modificar.equals("true")) {
                return "true";
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }
        return "false";
    }
    
     @Override
    public String modificar(int id) {

        DBConnection con = new DBConnection();
        String sql = "Update peliculas set copias = (copias - 1) where id = " + id;

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
    @Override
    public String devolver(int id, String username) {

        DBConnection con = new DBConnection();
        String sql = "Delete from alquiler where id= " + id + " and username = '" 
                + username + "' limit 1";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            this.sumarCantidad(id);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
    @Override
    public String sumarCantidad(int id) {

        DBConnection con = new DBConnection();

        String sql = "Update peliculas set copias = (Select copias from peliculas where id = " 
                + id + ") + 1 where id = " + id;

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            con.desconectar();
        }

        return "false";

    }
}
