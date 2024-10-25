package servicio;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import dto.CategoriaHabitacionDTO;
import entidad.CategoriaHabitacion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class categoriaHabitacionServicio {
   
   PreparedStatement statement;
   
   Connection conexion;
    private Conexion conexionSQL;

    public categoriaHabitacionServicio() {
        this.conexionSQL = new Conexion();
    }
    
    public List<CategoriaHabitacion> mostarCategoriaHabitaciones(){
        String sql = "SELECT * FROM CATEGORIA_HABITACION ";
        try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           
           ResultSet resultado = statement.executeQuery();
           statement.close();
           conexionSQL.desconectar();
           List<CategoriaHabitacion> categorias = new ArrayList<>();
           while(resultado.next()){
               
               categorias.add( 
                new CategoriaHabitacion(
                        resultado.getInt("id_categoria_habitacion"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio_noche"),
                         resultado.getString("foto_url")
               ));
            }
           return categorias;
           
        } catch (SQLException ex) {
           Logger.getLogger(categoriaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   
   public CategoriaHabitacion mostarCategoriaHabitacion(int id){
        String sql = "SELECT * FROM CATEGORIA_HABITACION WHERE ID_CATEGORIA_HABITACION = ?";
        try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setInt(1,id);
           ResultSet resultado = statement.executeQuery();
           statement.close();
           conexionSQL.desconectar();
           if(resultado.next()){
                return new CategoriaHabitacion(
                        resultado.getInt("id_categoria_habitacion"),
                        resultado.getString("nombre"),
                        resultado.getDouble("precio_noche"),
                         resultado.getString("foto_url")
                );
            }
           
        } catch (SQLException ex) {
           Logger.getLogger(categoriaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   
   public CategoriaHabitacion crearCategoriaHabitacion( CategoriaHabitacionDTO dto){
       String sql = "INSERT INTO CATEGORIA_HABITACION(nombre,precio_noche,foto_url) VALUES(?,?,?)";
       conexion = conexionSQL.conectar();
       try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setString(1,dto.getNombre());
           statement.setDouble(2,dto.getPrecioNoche());
           statement.setString(3,dto.getFotoUrl());
           int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    statement.close();
                    conexionSQL.desconectar();
                    return mostarCategoriaHabitacion(resultado.getInt(1));
                }
            }
       } catch (SQLException ex) {
           Logger.getLogger(categoriaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }

   
   public CategoriaHabitacion modificarCategoriaHabitacion(CategoriaHabitacionDTO dto, int id){
       String sql = "UPDATE CATEGORIA_HABITACION "
               + "SET "
               + "NOMBRE = ? ,PRECIO_NOCHE = ? ,FOTO_URL = ? WHERE ID_CATEGORIA_HABITACION = ?";
       
       try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setString(1,dto.getNombre());
           statement.setDouble(2,dto.getPrecioNoche());
           statement.setString(3,dto.getFotoUrl());
           statement.setInt(4,id);
           int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    statement.close();
                    conexionSQL.desconectar();
                    return mostarCategoriaHabitacion(resultado.getInt(1));
                }
            }
       } catch (SQLException ex) {
           Logger.getLogger(categoriaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   public boolean borrarCategoriaHabitacion( int id) {
        String sql = "DELETE FROM CATEGORIA_HABITACION WHERE ID_CATEGORIA_HABITACION = ?";
       try {
           conexion = conexionSQL.conectar();
           statement = conexion.prepareStatement(sql);
           statement.setInt(1, id);
           statement.execute(sql);
           statement.close();
           conexionSQL.desconectar();
           return true;
       } catch (SQLException ex) {
           Logger.getLogger(categoriaHabitacionServicio.class.getName()).log(Level.SEVERE, null, ex);
       }
       return false;        
    }
    
}
