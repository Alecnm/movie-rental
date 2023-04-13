/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.sql.Date;

/**
 *
 * @author ACER
 */
public class Alquiler {
    private int id;
    private String username;
    private Date fecha;
    private boolean novedad;
    private String genero;
    private String titulo;

    public Alquiler(int id, String username, Date fecha, boolean novedad, String genero) {
        this.id = id;
        this.username = username;
        this.fecha = fecha;
        this.novedad = novedad;
        this.genero = genero;
    }

    public Alquiler(int id, Date fecha, boolean novedad, String genero, String titulo ) {
        this.id = id;
        this.fecha = fecha;
        this.novedad = novedad;
        this.genero = genero;
        this.titulo = titulo;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isNovedad() {
        return novedad;
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    

    @Override
    public String toString() {
        return "Alquiler{" + "id=" + id + ", username=" + username + ", fecha=" + fecha + ", novedad=" + novedad + ", genero=" + genero + '}';
    }
    
    
}
