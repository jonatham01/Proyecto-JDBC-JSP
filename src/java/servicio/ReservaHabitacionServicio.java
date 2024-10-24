
package servicio;

import conexion.Conexion;
import entidad.ReservaHabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservaHabitacionServicio {
    PreparedStatement statement;
    
    Connection conexion;
    private Conexion conexionSQL;

    public ReservaHabitacionServicio() {
        this.conexionSQL = new Conexion();
    }
   
   public ReservaHabitacion mostarReservaHabitacion(long idReserva, int idHabitacion ){
        String sql = "SELECT * FROM RESERVA_HABITACION WHERE ID_RESERVA = ? AND ID_HABITACION = ?";
        try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setLong(1,idReserva);
           statement.setInt(2,idHabitacion);
           ResultSet resultado = statement.executeQuery();
           statement.close();
           conexionSQL.desconectar();
           if(resultado.next()){
                return new ReservaHabitacion(
                        resultado.getLong("id_reserva"),
                        resultado.getInt("id_habitacion"),
                        resultado.getShort("cantidad")
                );
            }
           
        } catch (SQLException ex) {
           Logger.getLogger(ReservaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   
   public ReservaHabitacion crearCategoriaHabitacion(ReservaHabitacion data){
       String sql = "INSERT INTO RESERVA_HABITACION(ID_RESERVA, ID_HABITACION, CANTIDAD) VALUES(?,?,?)";
       try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setLong(1, (long) data.getIdReserva());
           statement.setInt(2,data.getIdHabitacion());
           statement.setShort(3,data.getCantidad());
           int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    statement.close();
                    conexionSQL.desconectar();
                    return mostarReservaHabitacion(resultado.getLong(1), resultado.getInt(2));
                }
            }
       } catch (SQLException ex) {
           Logger.getLogger(ReservaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }

   
   public ReservaHabitacion modificarCategoriaHabitacion(ReservaHabitacion data,int idReserva, int idHabitacion){
       String sql = "UPDATE RESERVA_HABITACION "
               + "SET ID_RESERVA = ? , ID_HABITACION = ? , CANTIDAD = ? WHERE ID_RESERVA = ? AND ID_HABITACION = ?";
       
       try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setLong(1, (long) data.getIdReserva());
           statement.setInt(2,data.getIdHabitacion());
           statement.setShort(3,data.getCantidad());
           statement.setLong(4, idReserva);
           statement.setInt(5, idHabitacion);
           int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    statement.close();
                    conexionSQL.desconectar();
                    return mostarReservaHabitacion(resultado.getLong(1), resultado.getInt(2));
                }
            }
       } catch (SQLException ex) {
           Logger.getLogger(ReservaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   public boolean borrarCategoriaHabitacion(Connection conexion, long idReserva, int idHabitacion) {
        String sql = "DELETE FROM RESERVA_HABITACION WHERE ID_RESERVA = ? AND ID_HABITACION = ?";
       try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setLong(1,idReserva);
           statement.setInt(2,idHabitacion);
           statement.execute(sql);
           statement.close();
           conexionSQL.desconectar();
           return true;
       } catch (SQLException ex) {
           Logger.getLogger(ReservaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return false;        
    }
}
