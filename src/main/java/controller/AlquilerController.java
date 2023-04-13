package controller;

import beans.Alquiler;
import com.google.gson.Gson;
import connection.DBConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlquilerController implements IAlquierController {

    @Override
    public String listarAlquileres(String username) {
        Gson gson = new Gson();

        DBConnection con = new DBConnection();

        String sql = "Select l.id, l.titulo, l.genero, l.novedad, a.fecha from peliculas l "
                + "inner join alquiler a on l.id = a.id inner join usuarios u on a.username = u.username "
                + "where a.username = '" + username + "'";

        List<String> alquileres = new ArrayList<String>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                boolean novedad = rs.getBoolean("novedad");
                Date fechaAlquiler = rs.getDate("fecha");

                Alquiler alquiler = new Alquiler(id, fechaAlquiler, novedad, genero, titulo);

                alquileres.add(gson.toJson(alquiler));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return gson.toJson(alquileres);
    }
}
