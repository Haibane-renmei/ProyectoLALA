/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angel
 */
public class Reserva implements Comparable<Reserva> {
    public int cantidad;
    public String lote;
    public String fecha;
    public String correlativo;

    public Reserva(String correlativo, int cantidad, String lote, String fecha) {
        this.cantidad = cantidad;
        this.lote = lote;
        this.fecha = fecha;
        this.correlativo = correlativo;
    }
    
    public Reserva(String lote, String correlativo) {
        this.cantidad = 0;
        this.lote = lote;
        this.fecha = " ";
        this.correlativo = correlativo;
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public String getLote() {
        return lote;
    }
    
    public String getCorrelativo() {
        return correlativo;
    }

    public String getFecha() {
        return fecha;
    }
    
    public Date getFechaasDate() {
  
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;

        try {
            date = new java.sql.Date(format.parse(fecha).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Reserva.class.getName()).log(Level.SEVERE, null, ex);
        }

            return date;
    }
    

    @Override
    public int compareTo(Reserva o) {

		 if (getFechaasDate() == null || o.getFechaasDate() == null)
                     return 0;
                 
                 return getFechaasDate().compareTo(o.getFechaasDate());
		//descending order
		//return compareQuantity - this.quantity;
                
    }
}
