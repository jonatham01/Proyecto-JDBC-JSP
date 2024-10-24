
package servicio;

import conexion.Conexion;
import entidad.RecepcionHabitacion;
import dto.RecepcionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecepcionHabitacionServicio {
    
    private PreparedStatement statement;
    
    Connection conexion;
    private Conexion conexionSQL;

    public RecepcionHabitacionServicio() {
        this.conexionSQL = new Conexion();
    }
    
    public RecepcionHabitacion crearRecepcionHabitacion(Connection conexion, RecepcionDTO dto){
        String sql = "INSERT INTO RECEPCIONHABITACION(FECHA,ID_HABITACION,CATEGORIA) VALUES(CAMA.SQL.NEXTVAL,?,?,?)";
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, (Date) dto.getFecha());
            statement.setInt(2, dto.getIdHabitacion());
            statement.setString(3, dto.getCategoria());
            int filas = statement.executeUpdate();
            if (filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                     statement.close();
                     conexionSQL.desconectar();
                    if(resultado.next()){
                        return mostrarRecepcionHabitacion(resultado.getInt(1));
                    }
                }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(RecepcionHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public RecepcionHabitacion mostrarRecepcionHabitacion(int id){
        String sql = "SELECT * FROM RECEPCION_HABITACION WHERE CODIGO_RECEPCION_HABITACION = ${id}";
        try{
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();
            statement.close();
            conexionSQL.desconectar();
            if(resultado.next()){
                return new RecepcionHabitacion(
                        resultado.getInt("codigo_recepcion_habitacion"),
                        resultado.getDate("fecha"),
                        resultado.getInt("id_habitacion"),
                        resultado.getString("categoria")
                );
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(RecepcionHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public RecepcionHabitacion modificarRecepcionHabitacion( int id, RecepcionDTO dto ){
        String sql = "UPDATE RECEPCION_HABITACION SET FECHA = ?, ID_HABITACION = ?, CATEGORIA = ? "
                + "WHERE CODIGO_RECEPCION_HABITACION = ?";
        try{
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, (Date) dto.getFecha());
            statement.setInt(2, dto.getIdHabitacion());
            statement.setString(3, dto.getCategoria());
            statement.setString(4, dto.getCategoria());
            int filas = statement.executeUpdate();
            if (filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                     statement.close();
                    if(resultado.next()){
                        return mostrarRecepcionHabitacion(resultado.getInt(1));
                    }
                }
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(RecepcionHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public boolean BorrarRecepcionHabitacion(Connection conexion, int id){
        String sql = "DELETE FROM RECEPCION_HABITACION WHERE CODIGO_RECEPCION_HABITACION = ?" ;
        
        try{
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute(sql);
            statement.close();
            conexionSQL.desconectar();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
