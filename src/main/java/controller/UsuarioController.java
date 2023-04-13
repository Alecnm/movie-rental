/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import beans.Usuario;
import com.google.gson.Gson;
import connection.DBConnection;

public class UsuarioController implements IUsuarioController {

    @Override
    public String login(String username, String contrasena) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();

        String sql = "Select * from usuarios where username = '" + username
                + "' and contrasena = '" + contrasena + "'";
        try {
            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String email = rs.getString("email");
                double saldo = rs.getDouble("saldo");
                boolean premium = rs.getBoolean("premium");

                Usuario usuario
                        = new Usuario(username, contrasena, nombre, apellidos, email, saldo, premium);
                return gson.toJson(usuario);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }

    @Override
    public String register(String username, String contrasena, String nombre, String apellidos, String email, double saldo, boolean premium) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();

        String sql = "Insert into usuarios values('" + username + "', '" + contrasena + "', '" + nombre
                + "', '" + apellidos + "', '" + email + "', " + saldo + ", " + premium + ")";

        try {
            Statement st = con.getConnection().createStatement();

            st.executeUpdate(sql);

            Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, email, saldo, premium);
            st.close();
            
            return gson.toJson(usuario);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }

    @Override
    public String pedir(String username) {

        Gson gson = new Gson();

        DBConnection con = new DBConnection();

        String sql = "SELECT * FROM usuarios where username = '" + username + "'";

        try {
            Statement st = con.getConnection().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {                
                
    
            String contrasena = rs.getString("contrasena");
            String nombre = rs.getString("nombre");
            String apellidos =  rs.getString("apellidos");
            String email = rs.getString("email");
            double saldo = rs.getDouble("saldo");
            boolean premium = rs.getBoolean("premium");

            Usuario usuario = new Usuario(username, contrasena, nombre, apellidos, email, saldo, premium);
            
            return gson.toJson(usuario);
            
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
    @Override
    public String restarDinero(String username, double nuevoSaldo) {

        DBConnection con = new DBConnection();
        String sql = "Update usuarios set saldo = " + nuevoSaldo + " where username = '" + username + "'";

        try {

            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }

    @Override
    public String modificar(String username, String nuevaContrasena, 
            String nuevoNombre, String nuevosApellidos,
            String nuevoEmail, double nuevoSaldo, boolean nuevoPremium) {   

        DBConnection con = new DBConnection();

        String sql = "Update usuarios set contrasena = '" + nuevaContrasena +
                "', nombre = '" + nuevoNombre + "', "
                + "apellidos = '" + nuevosApellidos + "', email = '" 
                + nuevoEmail + "', saldo = " + nuevoSaldo + ", premium = ";

        if (nuevoPremium == true) {
            sql += " 1 ";
        } else {
            sql += " 0 ";
        }

        sql += " where username = '" + username + "'";

        try {

            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";

    }

    @Override
    public String verCopias(String username) {

        DBConnection con = new DBConnection();
        String sql = "Select id,count(*) as num_copias from alquiler where username = '" 
                + username + "' group by id;";

        Map<Integer, Integer> copias = new HashMap<Integer, Integer>();

        try {

            Statement st = con.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int num_copias = rs.getInt("num_copias");

                copias.put(id, num_copias);
            }

            devolverPeliculas(username, copias);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";

    }

    @Override
    public String devolverPeliculas(String username, Map<Integer, Integer> copias) {

        DBConnection con = new DBConnection();

        try {
            
            for (Map.Entry<Integer, Integer> pelicula : copias.entrySet()) {
                int id = pelicula.getKey();
                int num_copias = pelicula.getValue();

                String sql = "Update peliculas set copias = (Select copias + " + num_copias +
                        " from peliculas where id = " + id + ") where id = " + id;

                Statement st = con.getConnection().createStatement();
                st.executeUpdate(sql);

            }

            this.eliminar(username);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }
        return "false";
    }
    
    @Override
    public String eliminar(String username) {

        DBConnection con = new DBConnection();

        String sql1 = "Delete from alquiler where username = '" + username + "'";
        String sql2 = "Delete from usuarios where username = '" + username + "'";

        try {
            Statement st = con.getConnection().createStatement();
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);

            return "true";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            con.desconectar();
        }

        return "false";
    }
    
}
