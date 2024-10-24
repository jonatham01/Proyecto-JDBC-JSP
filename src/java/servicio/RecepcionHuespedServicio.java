package servicio;

import conexion.Conexion;
import entidad.RecepcionHuesped;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecepcionHuespedServicio {
    private PreparedStatement statement;
    
    Connection conexion;
    private Conexion conexionSQL;

    public RecepcionHuespedServicio() {
        this.conexionSQL = new Conexion();
    }
    
    public RecepcionHuesped crearRecepcionHuesped( RecepcionHuesped recepcionHuesped){
        String sql = "INSERT INTO RECEPCION_HUESPED(IDRECEPCION,IDHUESPED,FECHARECEPCION)VALUES(?,?,?)";
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,recepcionHuesped.getIdRecepcion());
            statement.setInt(2, recepcionHuesped.getIdHuesped());
            statement.setDate(3, (Date) recepcionHuesped.getFechaRecepcion());
            int filas = statement.executeUpdate();
            if ( filas > 0 ){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if (resultado.next()){
                        int idRecepcion = resultado.getInt(1);
                        int idHuesped = resultado.getInt(1);
                        statement.close();
                        conexionSQL.desconectar();
                        return mostrarRecepcionHuesped(idRecepcion, idHuesped);
                    }                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecepcionHuespedServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public RecepcionHuesped modificarRecepcionHuesped(RecepcionHuesped recepcionHuesped, int idRecepcion,int idHuesped){
        String sql = "UDTATE RECEPCION_HUESPED SET IDRECEPCION = ?, IDHUESPED = ?, FECHARECEPCION = ? "
                + "WHERE IDRECEPCION = ? AND IDHUESPED = ?" ;
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,recepcionHuesped.getIdRecepcion());
            statement.setInt(2, recepcionHuesped.getIdHuesped());
            statement.setDate(3, (Date) recepcionHuesped.getFechaRecepcion());
            statement.setInt(4, idRecepcion);
            statement.setInt(5, idHuesped);
            int filas = statement.executeUpdate();
            if ( filas > 0 ){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if (resultado.next()){
                        statement.close();
                        conexionSQL.desconectar();
                        return mostrarRecepcionHuesped(resultado.getInt(1), resultado.getInt(2));
                    }                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecepcionHuespedServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public RecepcionHuesped mostrarRecepcionHuesped(int idRecepcion,int idHuesped){
        String sql = "SELECT * FROM RECEPCION_HUESPED WHERE IDRECEPCION = ? AND IDHUESPED =  ?" ;
        
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idRecepcion);
            statement.setInt(2, idHuesped);            
            ResultSet resultado = statement.executeQuery();
            RecepcionHuesped recepcionHuesped = null;
            if(resultado.next()){
                recepcionHuesped = new RecepcionHuesped(
                        resultado.getInt("idRecepcion"),
                        resultado.getInt("idHuesped"),
                        resultado.getDate("fechaRecepcion")
                );
            }
            statement.close();
            conexionSQL.desconectar();
            return recepcionHuesped;
        } catch (SQLException ex) {
            Logger.getLogger(RecepcionHuespedServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
                    
    }
    
    public boolean borrarRecepcionHuesped(int idRecepcion,int idHuesped){
        String sql = "DELETE FROM RECEPCIONHUESPED WHERE IDRECEPCION = ?  AND IDHUESPED = ?" ;
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idRecepcion);
            statement.setInt(2, idHuesped);
            statement.execute(sql);
            statement.close();
            conexionSQL.desconectar();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RecepcionHuespedServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
