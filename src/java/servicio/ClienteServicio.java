package servicio;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import entidad.Cliente;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteServicio {
   PreparedStatement statement;
   String sql;
   Connection conexion;
   private Conexion conexionSQL;

    public ClienteServicio() {
        this.conexionSQL = new Conexion();
    }
   
   
    
   public Cliente crearCliente( Cliente cliente){
       sql = "INSERT INTO CLIENTE(CEDULA,NOMBRE,TIPO_IDENTIFICACION) VALUES(?,?,?)";
        try{
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, cliente.getCedula());
            statement.setString(2, cliente.getNombre());
            statement.setString(3, cliente.getTipoIdentificacion());
            int filas = statement.executeUpdate();
            if ( filas > 0 ){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if (resultado.next()){
                        int id = resultado.getInt(1);
                        statement.close();
                        conexionSQL.desconectar();
                        sql = null;
                        return mostrarCliente(id);
                    }                    
                }
            }
        }catch(SQLException ex){
             Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
   }
   public Cliente mostrarCliente( int cedula){
       sql = "SELECT * FROM CLIENTE WHERE CEDULA = ?" ;
       try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setInt(1, cedula);
           ResultSet resultado = statement.executeQuery();
            if(resultado.next()){
               Cliente cliente = new Cliente(
                       resultado.getInt("cedula"),
                       resultado.getString("nombre"),
                       resultado.getString("tipo_identificacion")
               );
                statement.close();
                conexionSQL.desconectar();
                sql = null;
                return cliente;
           }
           
       } catch (SQLException ex) {
           Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   
   public Cliente modificarCliente( Cliente cliente, int cedula){
       sql = "UPDATE CLIENTE "
               + "SET CEDULA = ?, NOMBRE = ?, TIPO_IDENTIFICACION = ? "
               + "WHERE CEDULA = ?";
        try {
            conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setInt(1, cliente.getCedula());
           statement.setString(2, cliente.getNombre());
           statement.setString(3, cliente.getTipoIdentificacion());
           statement.setInt(4, cedula);
           int filas = statement.executeUpdate();
            if ( filas > 0 ){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if (resultado.next()){
                        int id = resultado.getInt(1);
                        statement.close();
                        conexionSQL.desconectar();
                        sql = null;
                        return mostrarCliente(id);
                    }                    
                }
            }
           
       } catch (SQLException ex) {
           Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   
   public boolean borrarCliente(Connection conexion, int cedula){
       sql = "DELETE  FROM CLIENTE WHERE CEDULA = ?" ;
       try{
           conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, cedula);
            statement.execute(sql);
            statement.close();
            conexionSQL.desconectar();
            return true;
       } catch (SQLException ex) {
           Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return false;
   }
    
}
