/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author macana
 */
public class Funcion {
    
    public String ObtenerRif( String razonSo ) {
        conectar cc = new conectar();
        Connection cn = cc.conexion();
         
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT rif_codigo FROM cliente WHERE razon_social='"+razonSo+"';");
         
            if( !rs.next() ){
                return null;
            }
            else{
                return rs.getString("rif_codigo");
            }

        }
        catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
        }
        catch( java.lang.NumberFormatException e){
                   System.out.println("eror en "+e.toString());
        }  
              
       return null; 
    }
    
    
     public static void reiniciarJTable(javax.swing.JTable Tabla){
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        while(modelo.getRowCount()>0)modelo.removeRow(0);
 
        TableColumnModel modCol = Tabla.getColumnModel();
        while(modCol.getColumnCount()>0)modCol.removeColumn(modCol.getColumn(0));
    }
    
    public void listarDatosCliente( String rif, String razonSo,JLabel razonn, JLabel contacto,JLabel tlf,JLabel monto,JLabel montoo, JLabel Rif, JLabel Direccion)
 {
              conectar cc = new conectar();
                Connection cn = cc.conexion();
                try {
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT contacto,telefonos,limite_de_credito, credito_disponible, direccion from cliente Where rif_codigo='"+rif+"' AND razon_social='"+razonSo+"';");
                    while(rs.next()){
                        razonn.setText(razonSo);
                        String contac = rs.getString("contacto");
                        contacto.setText(contac);
                        String telf = rs.getString("telefonos");
                        tlf.setText(telf);
                          String montoC = rs.getString("limite_de_credito");
                          monto.setText(montoC);
                          String montoD = rs.getString("credito_disponible");
                          montoo.setText(montoD);
                        Direccion.setText(rs.getString("Direccion"));
                        Rif.setText(rif);
                        
                        
                       
                            
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("eror en "+e.toString());
               }  
              
              
         }
    
    public void listarDatosClienteNombre( String razonSo,JLabel razonn, JLabel contacto,JLabel tlf,JLabel monto,JLabel montoo, JLabel Rif, JLabel Direccion)
 {
              conectar cc = new conectar();
                Connection cn = cc.conexion();
                try {
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT rif_codigo,contacto,telefonos,limite_de_credito, credito_disponible, direccion from cliente Where  razon_social='"+razonSo+"';");
                    while(rs.next()){
                        razonn.setText(razonSo);
                        String contac = rs.getString("contacto");
                        contacto.setText(contac);
                        String telf = rs.getString("telefonos");
                        tlf.setText(telf);
                          String montoC = rs.getString("limite_de_credito");
                          monto.setText(montoC);
                          String montoD = rs.getString("credito_disponible");
                          montoo.setText(montoD);
                        Direccion.setText(rs.getString("Direccion"));
                        Rif.setText(rs.getString("rif_codigo"));
                        
                        
                       
                            
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("eror en "+e.toString());
               }  
              
              
         }
    
    public void ResetearLabelCliente( JLabel razonn, JLabel contacto,JLabel tlf,JLabel monto,JLabel montoo, JLabel Rif, JLabel Direccion)
 {
  razonn.setText("");
  contacto.setText("");
  tlf.setText("");
  monto.setText("");
  montoo.setText("");
  Direccion.setText("");
  Rif.setText("");
  }
  
    
public void listarProductos(JTable tablaProductos)
    {
     DefaultTableModel model;
    String [] titulos={"codigo producto", "nombre producto","cantidad"};
    String [] registros = new String [3];
           model= new DefaultTableModel(null,titulos){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
           
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery( "SELECT pr.codigo_producto as cp , pr.nombre_producto as np,(SUM(pl.cantidad_x_lote)-R.cantidad_reservada) as pll\n" +
"FROM producto as pr, lote_producto as pl, productos_reservados as R\n" +
"WHERE pr.codigo_producto= pl.codigo_producto\n" +
"AND   pr.codigo_producto = R.codigo_producto\n" +
"GROUP BY pl.codigo_producto,pr.nombre_producto\n" +
"\n" +
"UNION ALL \n" +
"SELECT pr.codigo_producto, pr.nombre_producto,SUM(pl.cantidad_x_lote) as pll\n" +
"FROM producto as pr inner join lote_producto as pl ON (pr.codigo_producto = pl.codigo_producto)\n" +
"left join productos_reservados as R ON ( pr.codigo_producto = R.codigo_producto )\n" +
"WHERE  R.codigo_producto IS NULL \n" +
"GROUP BY pr.codigo_producto, pr.nombre_producto\n" +
"order by pll desc"
                );
                while(rs.next()){
                    registros[0]= rs.getString("cp");
                    registros[1]=rs.getString("np");
                     registros[2]=rs.getString("pll");
                     model.addRow(registros);
                                }
                tablaProductos.setModel(model);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void listarProductos "+e.toString());
               }
                     //PARA DAR TAMAÑO ESPECIFICO A LAS COLUMNAS DE UNA TABLA
 
    }


public void listarEstadisticaAMD(JTable tablaEstadistica, String fechaI,String fechaF)
    {
     DefaultTableModel model;
    String [] titulos={"Producto", "Oferta","Demanda"};
    String [] registros = new String [3];
           model= new DefaultTableModel(null,titulos){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
           
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery( "select p.nombre_producto, resultado.oferta, resultado.demanda\n" +
"from producto as p, \n" +
"(Select OL.codigo_producto, Sum(OL.cantidad) as oferta, D.cantidad as demanda\n" +
"From orden_x_lote as OL , orden_venta as OV, estadisticas_demanda_productos as D\n" +
"WHERE OL.orden_id = OV.id\n" +
"AND OL.codigo_producto = D.codigo_producto\n" +
"AND OV.Hora>='"+fechaI+" 00:00:00'\n" +
"AND OV.Hora<'"+fechaF+" 11:59:59'\n" +
"Group by OL.codigo_producto, OV.hora\n" +
"union all\n" +
"Select D.codigo_producto, 0 as oferta, D.cantidad as demanda\n" +
"From estadisticas_demanda_productos as D \n" +
"left join (\n" +
"Select OL.codigo_producto\n" +
"From orden_x_lote as OL , orden_venta as OV\n" +
"WHERE OL.orden_id = OV.id\n" +
"AND OV.Hora>='"+fechaI+" 00:00:00'\n" +
"AND OV.Hora<'"+fechaF+" 11:59:59') as Result\n" +
"ON ( D.codigo_producto = Result.codigo_producto )\n" +
"WHERE  Result.codigo_producto IS NULL \n" +
"union all\n" +
"Select OL.codigo_producto,  OL.cantidad as oferta, 0 as demanda\n" +
"From estadisticas_demanda_productos as D \n" +
"right join orden_x_lote as OL ON ( D.codigo_producto = OL.codigo_producto )\n" +
"WHERE  D.codigo_producto IS NULL ) as resultado\n" +
"where resultado.codigo_producto = p.codigo_producto;"
                );
                while(rs.next()){
                    registros[0]= rs.getString("nombre_producto");
                    registros[1]=rs.getString("oferta");
                     registros[2]=rs.getString("demanda");
                     model.addRow(registros);
                                }
                tablaEstadistica.setModel(model);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void listarProductos "+e.toString());
               }
    }

public void AñadirProductoTabla(JTable t,String cod,String nombre)
{
    if (ProductoDuplicado(cod, t)){
            JOptionPane.showMessageDialog(t,"El producto ya se encuentra en la tabla de producto demandados");
    }else{
            String [] registros = new String [3];
            registros[0]=cod;
            registros[1]="0";
            registros[2]=nombre;

            ((DefaultTableModel)t.getModel()).addRow(registros);
    }
}
   
public void listarCliente(JTable tablaClientes)
    {
     DefaultTableModel model;
    String [] titulos={"RIF", " RAZON SOCIAL","DIRECCION","CONTACTO"};
    String [] registros = new String [4];
           model= new DefaultTableModel(null,titulos){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT rif_codigo,razon_social,direccion,contacto FROM cliente");
                while(rs.next()){
                    registros[0]= rs.getString("rif_codigo");
                    registros[1]=rs.getString("razon_social");
                     registros[2]=rs.getString("direccion");
                      registros[3]=rs.getString("contacto");
                     model.addRow(registros);
                                }
                tablaClientes.setModel(model);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
    }
 
public void listarProductosOV(JTable DetallesOrdenTable, String idOrden ){
     
         DefaultTableModel model;
         String [] titulos={ "lote" , "Codigo Producto" , "Cantidad" };
         String [] registros = new String [3];
     
            model= new DefaultTableModel(null,titulos) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            
            try {
                Statement st = cn.createStatement();
                
                ResultSet rs = st.executeQuery("SELECT correlativo_lote, codigo_producto, cantidad FROM orden_x_lote WHERE Orden_id = "+idOrden);
                
                while(rs.next()){
                      
                        registros[0]=rs.getString("correlativo_lote");                    
                        registros[1]=rs.getString("codigo_producto");
                        registros[2]=rs.getString("cantidad");

                        model.addRow(registros);                          
                  
                }
                DetallesOrdenTable.setModel(model);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
     
}
     
public void listarOrdenes(JTable tablaOrdenes)
    {
     DefaultTableModel model;
     String [] titulos={"ID", "Cliente","Rif","Direccion","Fecha y hora", "Operadora"};
     String [] registros = new String [6];
     
            model= new DefaultTableModel(null,titulos) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            
            try {
                Statement st = cn.createStatement();
                
                ResultSet rs = st.executeQuery(
                        
                          "SELECT orden_venta.id, orden_venta.cliente_rif , orden_venta.cliente_direccion , orden_venta.Hora, orden_venta.Despachado, usuarios.nombre_completo, cliente.razon_social "
                        + "FROM orden_venta, usuarios, cliente "
                        + "WHERE usuarios.id = orden_venta.operadora_id "
                        + "AND orden_venta.cliente_rif = cliente.rif_codigo  "
                        //+ "AND orden_venta.cliente_direccion= cliente.direccion "
                        
                        
                );
                
                while(rs.next()){
                    
                  if ( rs.getInt("despachado") == 0){  
                      
                        registros[0]=rs.getString("orden_venta.id");                    
                        registros[1]=rs.getString("cliente.razon_social");
                        registros[2]=rs.getString("orden_venta.cliente_rif");
                        registros[3]=rs.getString("orden_venta.cliente_direccion");
                        registros[4]=rs.getString("orden_venta.Hora");
                        registros[5]=rs.getString("usuarios.nombre_completo");
                        model.addRow(registros);
                        
                  }    
                  
                }
                tablaOrdenes.setModel(model);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
  //codigo
  tablaOrdenes.getColumnModel().getColumn(0).setPreferredWidth(45);
  tablaOrdenes.getColumnModel().getColumn(0).setMinWidth(45);
   tablaOrdenes.getColumnModel().getColumn(0).setMaxWidth(45);
   //rif
   tablaOrdenes.getColumnModel().getColumn(2).setPreferredWidth(100);
  tablaOrdenes.getColumnModel().getColumn(2).setMinWidth(100);
   tablaOrdenes.getColumnModel().getColumn(2).setMaxWidth(100);
   //hora y fecha
   tablaOrdenes.getColumnModel().getColumn(4).setPreferredWidth(150);
  tablaOrdenes.getColumnModel().getColumn(4).setMinWidth(150);
   tablaOrdenes.getColumnModel().getColumn(4).setMaxWidth(150);
    }
 
public void listarOrdenesPasadas(JTable tablaOrdenes)
    {
     DefaultTableModel model;
     String [] titulos={"ID", "Cliente","Rif","Direccion","Fecha y hora", "Operadora"};
     String [] registros = new String [6];
     
            model= new DefaultTableModel(null,titulos) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            
            try {
                Statement st = cn.createStatement();
                
                ResultSet rs = st.executeQuery(
                        
                          "SELECT orden_venta.id, orden_venta.cliente_rif , orden_venta.cliente_direccion , orden_venta.Hora, orden_venta.Despachado, usuarios.nombre_completo, cliente.razon_social "
                        + "FROM orden_venta, usuarios, cliente "
                        + "WHERE usuarios.id = orden_venta.operadora_id "
                        + "AND orden_venta.cliente_rif = cliente.rif_codigo  "
                        //+ "AND orden_venta.cliente_direccion= cliente.direccion "
                        
                        
                );
                
                while(rs.next()){
                    
                  if ( rs.getInt("despachado") == 1){  
                      
                        registros[0]=rs.getString("orden_venta.id");                    
                        registros[1]=rs.getString("cliente.razon_social");
                        registros[2]=rs.getString("orden_venta.cliente_rif");
                        registros[3]=rs.getString("orden_venta.cliente_direccion");
                        registros[4]=rs.getString("orden_venta.Hora");
                        registros[5]=rs.getString("usuarios.nombre_completo");
                        model.addRow(registros);
                        
                  }    
                  
                }
                tablaOrdenes.setModel(model);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            //codigo
  tablaOrdenes.getColumnModel().getColumn(0).setPreferredWidth(45);
  tablaOrdenes.getColumnModel().getColumn(0).setMinWidth(45);
   tablaOrdenes.getColumnModel().getColumn(0).setMaxWidth(45);
   //rif
   tablaOrdenes.getColumnModel().getColumn(2).setPreferredWidth(100);
  tablaOrdenes.getColumnModel().getColumn(2).setMinWidth(100);
   tablaOrdenes.getColumnModel().getColumn(2).setMaxWidth(100);
   //hora y fecha
   tablaOrdenes.getColumnModel().getColumn(4).setPreferredWidth(150);
  tablaOrdenes.getColumnModel().getColumn(4).setMinWidth(150);
   tablaOrdenes.getColumnModel().getColumn(4).setMaxWidth(150);
    }


 public Usuario BuscarUsuario( String usu, String contra)
 {
              conectar cc = new conectar();
                Connection cn = cc.conexion();
                try {
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT nombre_completo,aplicacion,id from usuarios Where usuario='"+usu+"' AND clave='"+contra+"'");
                    while(rs.next()){
                        String nombre = rs.getString("nombre_completo");
                        int app= rs.getInt("aplicacion");
                        int id = rs.getInt("id");
                        Usuario uuss= new Usuario(usu, nombre, contra, app, id);
                         return uuss;
                       
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("eror en buscarUsuario "+e.toString());
               }  
              
              
          return null;}
 
 public ArrayList ListarNombresClientes(){
   
            ArrayList keywords = new ArrayList<String>();
             
             
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT razon_social FROM cliente");
                int i=0;
                while(rs.next()){
                    keywords.add(rs.getString("razon_social"));
                }
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
            return keywords;
         } 
 
    public ArrayList ListarIDClientes(){
   
            ArrayList keywords = new ArrayList<String>();
             
             
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT ID FROM cliente");
                int i=0;
                while(rs.next()){
                    keywords.add(rs.getString("ID"));
                }
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
            return keywords;
         } 
    
    public void MarcarComoFacturado(String id){
        
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            try {
                Statement st = cn.createStatement();
                int cant =0 ;
                
                
                //restar de la reserva       
                st.executeUpdate("UPDATE productos_reservados "
                               + "INNER JOIN ( SELECT codigo_producto, SUM(cantidad) as cantidad "
                                            + "FROM orden_x_lote as OL "
                                            + "WHERE OL.orden_id = " +id+" "
                                            + "GROUP BY codigo_producto "
                               + ") AS Result ON ( Result.codigo_producto = productos_reservados.codigo_producto ) "
                               + "SET productos_reservados.cantidad_reservada = productos_reservados.cantidad_reservada - Result.cantidad "

                );        
      
                //restar de la tabla de lotes          
                st.executeUpdate("UPDATE lote_producto "
                               + "INNER JOIN orden_x_lote ON ( Orden_x_lote.correlativo_lote =  lote_producto.correlativo "
                               + "AND Orden_x_lote.Orden_id = "+ id + " )"
                               + "SET  lote_producto.cantidad_x_lote =  lote_producto.cantidad_x_lote - orden_x_lote.cantidad "

                ); 
                
                st.executeUpdate("UPDATE orden_venta SET despachado = 1 WHERE id = " + id );

            } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
            }
            catch( java.lang.NumberFormatException e) {
                   System.out.println("error producido en void listarCliente"+e.toString());
            }
    }
 
public void InsertarDemanda( JTable tabla){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            String FechaActual;
           
            try {
                
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
                FechaActual = sdf.format(cal.getTime());
                
                Statement st = cn.createStatement();

                for (int count = 0; count < ((DefaultTableModel)tabla.getModel()).getRowCount(); count++){
                   
                   String producto = ((DefaultTableModel)tabla.getModel()).getValueAt(count, 0).toString(); //cod
                   String cantidad = ((DefaultTableModel)tabla.getModel()).getValueAt(count, 1).toString(); //cant 
                   ResultSet rs = st.executeQuery("SELECT * FROM estadisticas_demanda_productos WHERE codigo_producto = '"+producto+"' AND fecha = '"+FechaActual+"'");
                    System.out.println("SELECT * FROM estadisticas_demanda_productos WHERE codigo_producto = '"+producto+"' AND fecha = "+FechaActual);
                  
                   if ( !rs.next() ){
                       
                        PreparedStatement MSQL_statement = cn.prepareStatement("INSERT INTO estadisticas_demanda_productos ( Codigo_producto , cantidad , fecha ) VALUES (?,?,?)");

                        MSQL_statement.setString(1, producto);
                        MSQL_statement.setString(2, cantidad);
                        MSQL_statement.setString(3, FechaActual);
                        
                        System.out.println(MSQL_statement);
                        MSQL_statement.executeUpdate();
              
                   }
                   else {
                         System.out.println("updateando: "+ producto);
                        st.executeUpdate("UPDATE estadisticas_demanda_productos SET cantidad = cantidad + "+cantidad+" WHERE Codigo_producto = '"+producto +"' AND fecha = '"+FechaActual+"'");
                        
                   }
                   
                }
                
                    /////LIMPIAR TABLA DE DESPACHO
     
                    DefaultTableModel model = (DefaultTableModel)tabla.getModel();
                    int rows = model.getRowCount(); 
                    for(int i = rows - 1; i >=0; i--){          
                        model.removeRow(i);
                    }
     
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
          
} 

public void CrearRegistroLlamada(String usuario, String riff, String direccion, String idCliente, int exitosa, Calendar cal, String Monto){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            String FechaActual;
            String HoraActual;
           
            try {

                SimpleDateFormat sdfFecha = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
                
                FechaActual = sdfFecha.format(cal.getTime());
                HoraActual = sdfHora.format(cal.getTime());
                
                System.out.println(FechaActual + " " + HoraActual);
                       
                PreparedStatement MSQL_statement = cn.prepareStatement(
                        "INSERT INTO registro_llamadas ( usuario , id_cliente , rif_codigo, direccion, fecha_llamada, hora_llamada, exitosa, monto_venta_contado ) VALUES (?,?,?,?,?,?,?,?)");

                MSQL_statement.setString(1, usuario);
                MSQL_statement.setString(2, idCliente);
                MSQL_statement.setString(3, riff);
                MSQL_statement.setString(4, direccion);
                MSQL_statement.setString(5, FechaActual);
                MSQL_statement.setString(6, HoraActual);
                MSQL_statement.setInt   (7, exitosa);
                MSQL_statement.setInt   (8, Integer.parseInt(Monto));
                        
                System.out.println(MSQL_statement);
                MSQL_statement.executeUpdate();
     
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
          
} 


public void ActualizarUltimaLLmadas(String rif, String direccion, Calendar cal){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            String FechaActual;
           
            try {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                FechaActual = sdf.format(cal.getTime());
                java.sql.Timestamp FA = new java.sql.Timestamp(sdf.parse(FechaActual).getTime());
                       
                PreparedStatement  MSQL_statement = cn.prepareStatement("DELETE FROM ultimas_llamadas WHERE rif_codigo  = ? AND direccion = ? ");
                MSQL_statement.setString(1, rif);
                MSQL_statement.setString(2, direccion);
                MSQL_statement.executeUpdate();
                
                MSQL_statement = cn.prepareStatement(
                        "INSERT INTO ultimas_llamadas ( rif_codigo , direccion, Ultima_Llamada ) VALUES (?,?,?)");

                MSQL_statement.setString(1, rif);
                MSQL_statement.setString(2, direccion);
                MSQL_statement.setTimestamp(3, FA);
         
                        
                System.out.println(MSQL_statement);
                MSQL_statement.executeUpdate();
     
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
          
} 

public void ReservarProducto(String CodigoProducto, int Cantidad){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                
                Statement st = cn.createStatement();

                    ResultSet rs = st.executeQuery("SELECT * FROM productos_reservados WHERE codigo_producto = '"+CodigoProducto+"' ");
                  
                   if ( !rs.next() ){
                       
                        PreparedStatement MSQL_statement = cn.prepareStatement("INSERT INTO productos_reservados ( Codigo_producto , cantidad_reservada ) VALUES (?,?)");

                        MSQL_statement.setString(1, CodigoProducto);
                        MSQL_statement.setInt(2, Cantidad);
   
                        System.out.println(MSQL_statement);
                        MSQL_statement.executeUpdate();
              
                   }
                   else {

                        st.executeUpdate("UPDATE productos_reservados SET cantidad_reservada = cantidad_reservada + "+Cantidad+" WHERE Codigo_producto = '"+CodigoProducto+"' ");
                        
                   }
                   
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
            
          
} 

public void InsertarLotexOrden(int IDorden, String Producto, Reserva R, int cantidad ){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                       
                PreparedStatement MSQL_statement = cn.prepareStatement(
                        "INSERT INTO orden_x_lote ( correlativo_lote, Orden_id , codigo_producto ,lote_producto , cantidad ) VALUES (?,?,?,?,?)");
                
                MSQL_statement.setString(1, R.getCorrelativo());
                MSQL_statement.setInt(2, IDorden);
                MSQL_statement.setString(3, Producto );
                MSQL_statement.setString(4, R.getLote());
                MSQL_statement.setInt(5, cantidad);

                        
                System.out.println(MSQL_statement);
                MSQL_statement.executeUpdate();
     
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
          
} 

public void EliminarLoteDeReserva(String CodigoLote, int Cantidad){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                
                Statement st = cn.createStatement();
                
                st.executeUpdate("UPDATE lotes_reservados SET cantidad_reservada = cantidad_reservada - "+Cantidad+" WHERE correlativo_lote = '"+CodigoLote+"'");
      
                   
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
      
} 



public Reserva[] ObtenerListaDeLotes2(String IDproducto ) {
    
    ArrayList Lista = new ArrayList<Reserva>();
            
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            System.out.println("l2\n");  
            
            try {
                Statement st = cn.createStatement();
                
               ResultSet rs = st.executeQuery(                        
                        "SELECT R.codigo_lote as codigo_lote, R.lote as lote, R.fecha_ingreso, (LP2.cantidad - SUM(R.cantidad )) as cantidadTotal\n" +
                        "FROM \n" +
                        "(SELECT LP.correlativo as codigo_lote, LP.lote_producto as lote, LP.fecha_ingreso, OL.cantidad as cantidad \n" +
                        "FROM lote_producto as LP, orden_x_lote as OL, orden_venta as OV  \n" +
                        "WHERE OV.despachado = 0 \n" +
                        "AND LP.codigo_producto = '" + IDproducto+"' "+
                        "AND OL.orden_id = OV.id \n" +
                        "AND OL.correlativo_lote = LP.correlativo \n" +

                        "UNION ALL \n" +

                        "SELECT LP.correlativo as codigo_lote, LP.lote_producto as lote, LP.fecha_ingreso, LR.cantidad_reservada as cantidad \n" +
                        "FROM lote_producto as LP, lotes_reservados as LR  \n" +
                        "WHERE LP.codigo_producto = '"  + IDproducto+"' "+
                        "AND LR.correlativo_lote = LP.correlativo ) as R,\n" +

                        "(SELECT LP.correlativo as codigo_lote, LP.cantidad_x_lote as cantidad \n" +
                        " FROM Lote_producto as LP\n" +
                        " WHERE LP.codigo_producto = '"  + IDproducto+"' "+
                        " ) as LP2 \n" +
                                
                        "WHERE R.codigo_lote = LP2.codigo_lote\n" +
                        "GROUP BY   R.codigo_lote, R.lote, R.fecha_ingreso\n" +

                        "UNION ALL\n" +
                                
                        "SELECT LP.correlativo as codigo_lote, LP.lote_producto as lote, LP.fecha_ingreso, LP.cantidad_x_lote as cantidad \n" +
                        "FROM lote_producto as LP LEFT JOIN orden_x_lote AS OL ON OL.correlativo_lote = LP.correlativo \n" +
                        "                         LEFT JOIN lotes_reservados AS LR ON LR.correlativo_lote = LP.correlativo\n" +
                        "WHERE  OL.correlativo_lote IS NULL\n" +
                        "AND LR.correlativo_lote IS NULL \n" +
                        "AND LP.codigo_producto = '"  + IDproducto+"' "
                        
                ); 
                
               
              
            
               
                while(rs.next()){
                   
                        Lista.add( new Reserva( rs.getString("codigo_lote"), rs.getInt("cantidadTotal"), rs.getString("lote"), rs.getString("fecha_ingreso") ) );
   
                }
                
                Reserva[] ListaProductos = new Reserva[Lista.size()];
                
                ListaProductos = (Reserva[]) Lista.toArray( ListaProductos);
                
                Arrays.sort(ListaProductos);    
               
		for(Reserva temp: ListaProductos){
		   System.out.println("lote: " + temp.getCorrelativo() + " - " + temp.getFecha() + " - cantidad: "+temp.getCantidad());
		}
                
                return ListaProductos;
                
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                    return null;
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void listarCliente"+e.toString());
                   return null;
               }
    
    
}

public void ReservarLote(String CodigoLote, int Cantidad){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                
                Statement st = cn.createStatement();

                    ResultSet rs = st.executeQuery("SELECT * FROM lotes_reservados WHERE correlativo_lote = '"+CodigoLote+"'");
                  
                   if ( !rs.next() ){
                        
                        rs = st.executeQuery("SELECT codigo_producto FROM lote_producto WHERE correlativo = '"+CodigoLote+"'");
                        rs.next();
                        String CodigoProducto = rs.getString("codigo_producto");
                       
                        PreparedStatement MSQL_statement = cn.prepareStatement("INSERT INTO  lotes_reservados  ( Codigo_producto , correlativo_lote, cantidad_reservada ) VALUES (?,?,?)");

                        MSQL_statement.setString(1, CodigoProducto);
                        MSQL_statement.setString(2, CodigoLote);
                        MSQL_statement.setInt(3, Cantidad);
   
                        System.out.println(MSQL_statement);
                        MSQL_statement.executeUpdate();
              
                   }
                   else {

                        st.executeUpdate("UPDATE lotes_reservados SET cantidad_reservada = cantidad_reservada + "+Cantidad+" WHERE Correlativo_lote = '"+CodigoLote+"' ");
                        
                   }
                   
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
            
          
} 


public int CrearOrdenVenta( int usr , String rif, String direccion){
    
            int ID=0;
            conectar cc = new conectar();
            Connection cn = cc.conexion();
            String HoraActual;
           
            try {
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, 30);
                HoraActual = sdf.format(cal.getTime());
                java.sql.Timestamp HA = new java.sql.Timestamp(sdf.parse(HoraActual).getTime());
                
                
                PreparedStatement MSQL_statement = cn.prepareStatement(
                        "INSERT INTO orden_venta ( hora , operadora_id , cliente_rif, cliente_direccion, despachado ) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                MSQL_statement.setTimestamp(1, HA );
                MSQL_statement.setInt(2, usr);
                MSQL_statement.setString(3, rif);
                MSQL_statement.setString(4, direccion);
                MSQL_statement.setInt(5, 0);

                        
                MSQL_statement.executeUpdate();
     
                ResultSet rs = MSQL_statement.getGeneratedKeys();
                rs.next();
                ID = rs.getInt(1);
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
          
          return ID;
}

public int ObtenerCantidadProducto(String Producto){
    
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                
                Statement st = cn.createStatement();
                
                ResultSet rs = st.executeQuery("SELECT * FROM productos_reservados WHERE codigo_producto = '"+Producto+"' ");
                
                if (!rs.next()){
                rs = st.executeQuery("SELECT codigo_producto, SUM(cantidad_x_lote) as Cant FROM lote_producto WHERE codigo_producto = '"+Producto+"' GROUP BY codigo_producto");
                }
                else{
                rs = st.executeQuery( "SELECT lp.codigo_producto, (SUM(lp.cantidad_x_lote)- R.cantidad_reservada) as Cant FROM lote_producto as lp, productos_reservados as R WHERE lp.codigo_producto = '"+ Producto+"' AND lp.codigo_producto = R.Codigo_Producto   GROUP BY lp.codigo_producto");
               }
                
                rs.next();
                return rs.getInt("Cant");
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
            
            
    
    return 0;
}

public int ObtenerPrecioLote(String Lote){
    
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                
                Statement st = cn.createStatement();

                ResultSet rs = st.executeQuery("SELECT precio_x_lote FROM lote_producto WHERE correlativo = '"+Lote+"'");
                
                rs.next();
                return rs.getInt("precio_x_lote");
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void ObtenerPrecio"+e.toString());
               }
            
            
    
    return 0;
}

public String ObtenerProductoDeLote(String Lote){
    
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                
                Statement st = cn.createStatement();

                ResultSet rs = st.executeQuery("SELECT codigo_producto FROM lote_producto WHERE correlativo = '"+Lote+"'");
                
                rs.next();
                return rs.getString("codigo_producto");
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void ObtenerPrecio"+e.toString());
               }
            
            
    
    return " ";
}

public ArrayList SeleccionarLotes(Reserva[] ArrayLotes, int cant ) {
    
     
            ArrayList Lista = new ArrayList<Reserva>();
            int i=0;
            
            while( cant > 0){

                    if( ArrayLotes[i].getCantidad() <= cant ){
                        
                        if (ArrayLotes[i].getCantidad() != 0){
                            cant = cant - ArrayLotes[i].getCantidad();
                            Lista.add(ArrayLotes[i]);
                        }

                    }
                    else{
                        Reserva R = new Reserva(ArrayLotes[i].getCorrelativo(), cant, ArrayLotes[i].getLote(), ArrayLotes[i].getFecha());
                        Lista.add(R);
                        cant = 0;

                    }


                 i++;   
                }
            
            return Lista;
}


public void Despachar2(int IDorden, JTable tabla){
    
     //suponiendo que la tabla contiene IDproducto - IDlote - Cantidad - Precio
    
     for (int count = 0; count < ((DefaultTableModel)tabla.getModel()).getRowCount(); count++){
         
           
            
            String Lote = ((DefaultTableModel)tabla.getModel()).getValueAt(count, 1).toString(); 
            String Producto = this.ObtenerProductoDeLote(Lote);
            
           int cant;
         
            try{

               cant = Integer.parseInt(((DefaultTableModel)tabla.getModel()).getValueAt(count, 2).toString());

            }catch(java.lang.NumberFormatException e){
               if ( ((DefaultTableModel)tabla.getModel()).getValueAt(count, 1).toString().equals("") )  break;
               else if ( ((DefaultTableModel)tabla.getModel()).getValueAt(count, 1).toString().equals(" ") ) break;
               else { 
                    showMessageDialog(tabla, "El producto "+Producto+" no pudo ser procesado por un error en la cantidad.\n por favor verifique que la cantidad especificada solo contiene numeros.");
                    break;
               }
            }
  
         if( !Producto.equals("")){
                    new Funcion().EliminarLoteDeReserva( Lote , cant);
                    InsertarLotexOrden( IDorden, Producto,  new Reserva("123", Lote) , cant );
        }
   
    }
     
    /////LIMPIAR TABLA DE DESPACHO
     
    DefaultTableModel model = (DefaultTableModel)tabla.getModel();
    int rows = model.getRowCount(); 
    for(int i = rows - 1; i >=0; i--){          
        model.removeRow(i);
    }
     
     
} 
 
public void EliminarDeReserva(String CodigoLote, int Cantidad){
                         
            conectar cc = new conectar();
            Connection cn = cc.conexion();
           
            try {
                
                Statement st = cn.createStatement();
               
                ResultSet rs = st.executeQuery("SELECT codigo_producto FROM lote_producto WHERE correlativo = '"+CodigoLote+"'");
                System.out.println(rs);
                rs.next();
                
                st.executeUpdate("UPDATE productos_reservados SET cantidad_reservada = cantidad_reservada - "+Cantidad+" WHERE Codigo_producto = '"+rs.getString("codigo_producto")+"' ");
      
                   
                
            }     
            catch( Exception e){
                   System.out.println("error producido en void listarCliente"+e.toString());
               }
      
} 



public void AddListaATabla( JTable tabla, String Producto, ArrayList<Reserva> a){
    
        String [] registros = new String [5];
        
    for (int i = 0; i < a.size(); i++) {
                                         
                        conectar cc = new conectar();
                        Connection cn = cc.conexion(); 
                        try {
                            Statement st = cn.createStatement();
                            ResultSet rs = st.executeQuery("SELECT Precio_x_lote FROM lote_producto WHERE correlativo = '"+a.get(i).getCorrelativo()+"'");
                            
                            
                            registros[0]= Producto;
                            registros[1]=a.get(i).getCorrelativo();
                            registros[2]=Integer.toString(a.get(i).getCantidad());
                            registros[3]=rs.getString("precio_x_lote");
                            registros[4]=Integer.toString(rs.getInt("precio_x_lote")*a.get(i).getCantidad());

                            ((DefaultTableModel)tabla.getModel()).addRow(registros); 
                            
                            
                        }     
                        catch( Exception e){
                               System.out.println("error producido en void listarCliente"+e.toString());
                        }
                    
    }
  
 }

 public void ActualizarMontoTotal(JLabel label, JTable tabla){
     
      int monto = 0;
     
      if ( ((DefaultTableModel)tabla.getModel()).getRowCount() == 0 ){
          label.setText("Monto total: 0 Bs.");
          return;
      }
      
      for (int count = 0; count < ((DefaultTableModel)tabla.getModel()).getRowCount(); count++){
          
          int PrecioProductoi = Integer.parseInt(((DefaultTableModel)tabla.getModel()).getValueAt(count, 4).toString());
          monto = monto + PrecioProductoi;
          
      }
      
      label.setText("Monto total: "+monto+" Bs.");
     
 }

   public void ListarUsuario(JList jListUsu) throws SQLException
          {
        conectar cc = new conectar();
        Connection cn = cc.conexion();
        DefaultListModel dlm= new DefaultListModel();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT  concat_ws(' ',usuario,nombre_completo) as nomb FROM usuarios\n" +
"where nombre_completo<> 'super usuario';");
               while(rs.next()){
                   String nom=rs.getString("nomb");
           dlm.addElement(nom);
               }
               jListUsu.setModel(dlm);
             
            }
         catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
          }
   
public boolean DisponibleUsuario(String usuario)
{
    System.out.println("usuario disponible:"+usuario);
    conectar cc = new conectar();
            Connection cn = cc.conexion();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT \n" +
                                               "case when  usuarios.usuario='"+usuario+"'   then 1 else 0 END as verda\n" +
                                               "from usuarios");
                while(rs.next()){
                    System.out.println("true");
                   int num=Integer.parseInt(rs.getString("verda"));
                    System.out.println("num "+num);
                   if(num==1)
                       return true;
                    }
                
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en boolean disponibleUsuario "+e.toString());
               }
    
return false;}

public int NumeroDepartamento(JComboBox combo)
{
     int depa=combo.getSelectedIndex();
       switch(depa){
               case 0: return 1;
               case 1: return 2;
                   }
       return 0;
}
   
public void comprobarUsuario(String nombre,String Usuario,String clave1,int departamento)
{System.out.println("entro a comprobarUsuario");
     
if( DisponibleUsuario(Usuario)!=true)
     {
         System.out.println("entro al if de comprobaru");
       conectar cc = new conectar();
            Connection cn = cc.conexion();
             try {
PreparedStatement MSQL_statement = cn.prepareStatement(
  "INSERT INTO usuarios ( usuario , nombre_completo , clave, aplicacion ) VALUES (?,?,?,?)");

                MSQL_statement.setString(1, Usuario);
                MSQL_statement.setString(2, nombre);
                MSQL_statement.setString(3, clave1);
                MSQL_statement.setInt(4, departamento);
                 System.out.println(MSQL_statement);
                MSQL_statement.executeUpdate();
     
            }     
            catch( Exception e){
                   System.out.println("error producido en void comprobar usuario"+e.toString());
               }
     }
else
     JOptionPane.showMessageDialog(null,"Revisar los datos de usuario");
    
//    return false;
}

     public void deshabilitarUsuario(String usuario)
{
    
    conectar cc = new conectar();
            Connection cn = cc.conexion();
            try {
                Statement st = cn.createStatement();
                st.executeQuery("UPDATE `lala`.`usuarios` SET `activo`='0' WHERE concat_ws(' ',usuario,nombre_completo)='"+usuario+"';");
                
                
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex);
                }
               catch( java.lang.NumberFormatException e)
               {
                   System.out.println("error producido en void deshabilitarUsuario "+e.toString());
               }
    }

public boolean ProductoDuplicado( String CodigoProducto, JTable tabla){
    
    for (int count = 0; count < ((DefaultTableModel)tabla.getModel()).getRowCount(); count++){
       
        if (tabla.getModel().getValueAt(count, 0).toString().equals(CodigoProducto)){
            return true;
        }
    }
    return false;
}
   

public void DeshabilitarEnterEnTabla(JTable table) {
    
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
            table.getActionMap().put("Enter", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //hacer nada
                }
            });
}



}


