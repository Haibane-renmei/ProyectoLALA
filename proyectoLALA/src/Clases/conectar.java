/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;

public class conectar {
   Connection conexion=null;
    public Connection conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
 //           conexion = DriverManager.getConnection("jdbc:mysql://localhost/bdconsultorio?useServerPrepStmts=true","root", "");

           conexion=DriverManager.getConnection("jdbc:mysql://localhost/lala","root","200896");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return conexion;
    }
    
}