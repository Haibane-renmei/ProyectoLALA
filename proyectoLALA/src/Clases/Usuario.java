/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author macana
 */
public class Usuario { 
    private String usuario;
    private String nombre;
    private String clave;
    private int app;
    private int id;

    public Usuario(String usuario, String nombre, String clave, int app) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.clave = clave;
        this.app = app;
    }

       public Usuario(String usuario, String nombre, String clave, int app, int id) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.clave = clave;
        this.app = app;
    }
       
    public String getUsuario() {
        return usuario;
    }
    
    public int getID() {
        return id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getApp() {
        return app;
    }

    public void setApp(int app) {
        this.app = app;
    }
    
}
