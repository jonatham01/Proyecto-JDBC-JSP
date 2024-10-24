package servicio;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import dto.HabitacionDTO;
import entidad.Habitacion;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HabitacionServicio {
    PreparedStatement statement;
    String sql;
    
    Connection conexion;
    private Conexion conexionSQL;

    public HabitacionServicio() {
        this.conexionSQL = new Conexion();
    }
    
    private Habitacion retornarHabitacion(ResultSet resultado){
        try {
            if(resultado.next()){
                return new Habitacion(
                        resultado.getInt("id_habitacion"),
                        resultado.getInt("piso"),
                        resultado.getInt("telefono"),
                        resultado.getInt("id_categoria")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Habitacion mostrarHabitacion( int id){
        sql ="SELECT * FROM HABITACION WHERE ID_HABITACION = ?";
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultado = statement.executeQuery();
            conexionSQL.desconectar();
            return retornarHabitacion(resultado);
        } catch (SQLException ex) {
            Logger.getLogger(FacturaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Habitacion guardarHabitacion( HabitacionDTO dto){
        sql ="INSERT INTO HABITACION(piso,telefono,id_categoria) "
                + "VALUES(?, ? , ? )";
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            if(dto != null){
                statement.setInt(1, dto.getPiso());
                statement.setInt(2, dto.getTelefono());
                statement.setInt(3, dto.getIdCategoria());
            }
            int filas = statement.executeUpdate();
            if ( filas > 0 ){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if (resultado.next()){
                        int id = resultado.getInt(1);
                        statement.close();
                        conexionSQL.desconectar();
                        return mostrarHabitacion(id);
                    }                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }
    
    public Habitacion modificarHabitacion(HabitacionDTO dto, int idHabitacion){
        sql ="UPDATE HABITACION "
                + "SET PISO = ? " 
                + ",TELEFONO = ?" 
                + ", ID_CATEGORIA = ?" 
                + " WHERE ID_HABITACION = ?" ;
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            if(dto != null){
                statement.setInt(1, dto.getPiso());
                statement.setInt(2, dto.getTelefono());
                statement.setInt(3, dto.getIdCategoria());
                statement.setInt(4, idHabitacion);
            }
            int filas = statement.executeUpdate();
            if ( filas > 0 ){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if (resultado.next()){
                        int id = resultado.getInt(1);
                        statement.close();
                        conexionSQL.desconectar();
                        return mostrarHabitacion(id);
                    }                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }
    public boolean borrarHabitacion(int id){
        sql = "DELETE FROM HABITACION "
                + "WHERE ID_HABITCION = ?";
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
