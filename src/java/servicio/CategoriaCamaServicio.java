
package servicio;

import conexion.Conexion;
import java.sql.Connection;
import entidad.CategoriaCama;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaCamaServicio {
    PreparedStatement pst;
    Connection conexion;
    private Conexion conexionSQL;

    public CategoriaCamaServicio() {
        this.conexionSQL = new Conexion();
    }
    
    
    
    public CategoriaCama crearCategoriaCama( CategoriaCama entidad){
        
        String sql = "INSERT INTO categoria_cama (tipo,medidas,foto_url,color) "
                + "Values(?,?,?,?)";
        try {
            conexion = conexionSQL.conectar();
            pst = conexion.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS) ;
            pst.setString(1,entidad.getTipo());
            pst.setString(2,entidad.getMedidas());
            pst.setString(3, entidad.getFotoUrl());
            pst.setString(4, entidad.getColor());
            int filas = pst.executeUpdate();

            
            if (filas > 0) {
                try (ResultSet resultado = pst.getGeneratedKeys()) {
                    if (resultado.next()) {
                        // Obtiene el ID generado
                        int id = resultado.getInt(1);
                        pst.close();
                        conexionSQL.desconectar();
                        return mostrarCategoriaCama((int) id);
                    } 
                }
            }
    
         
        }catch(SQLException e){
             System.out.println("No se pudo crear la categoria cama con exito" + e.getMessage());
            
        }
        
        return null;
    }
    
    public CategoriaCama mostrarCategoriaCama(int id) {
        
        CategoriaCama categoriaCama = null;
        
        String sql = "SELECT * FROM categoria_cama WHERE id_categoria_cama =" +id; 

        try {
            conexion = conexionSQL.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery(); 
            if (rs.next()) {
                int idCategoriaCama = rs.getInt("id_categoria_cama");
                String tipo = rs.getString("tipo");
                String medidas = rs.getString("medidas");
                String fotoUrl = rs.getString("foto_url");
                String color = rs.getString("color");
                categoriaCama = new CategoriaCama(idCategoriaCama, tipo, medidas, fotoUrl, color);
                conexionSQL.desconectar();
            }
        } catch (SQLException e) {
            return null;
        } 
        return categoriaCama;
    }
    
    
    public List<CategoriaCama> mostrarCategoriaCamas() {
        CategoriaCama categoriaCama;
        String sql = "SELECT * FROM categoria_cama "; 
        try {
            conexion = conexionSQL.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery(); 
             List<CategoriaCama> categoriaCamas = new ArrayList<>();
           while(rs.next()){              
                int idCategoriaCama = rs.getInt("id_categoria_cama");
                String tipo = rs.getString("tipo");
                String medidas = rs.getString("medidas");
                String fotoUrl = rs.getString("foto_url");
                String color = rs.getString("color");
                categoriaCama = new CategoriaCama(idCategoriaCama, tipo, medidas, fotoUrl, color);
                categoriaCamas.add(categoriaCama);
                conexionSQL.desconectar();
                return categoriaCamas;
            }
           return categoriaCamas;
        } catch (SQLException e) {
            return null;
        } 
    }
    
    public CategoriaCama modificarCategoriaCama( CategoriaCama entidad,int id){
        
        
        String sql = "UPDATE categoria_cama SET tipo = ? ,medidas = ? , foto_url = ? , color = ? "
                + "WHERE ID_CATEGORIA_CAMA = ?" ;
        try {
            conexion = conexionSQL.conectar();
            pst = conexion.prepareStatement(sql) ;
            pst.setString(1,entidad.getTipo());
            pst.setString(2,entidad.getMedidas());
            pst.setString(3, entidad.getFotoUrl());
            pst.setString(4, entidad.getColor());
            pst.setInt(5, id);
            int filas = pst.executeUpdate();
            conexionSQL.desconectar();
            if (filas > 0){
                return mostrarCategoriaCama(id);
            }
        }catch(SQLException e){
             System.out.println("No se pudo crear la categoria cama con exito" + e.getMessage());
        }
        
        return null;
    }
    
    public String eliminarCategoriaCama(int id){
        String sql = "DELETE FROM categoria_cama  WHERE ID_CATEGORIA_CAMA = ?  ";
        try {
            conexion = conexionSQL.conectar();
            pst = conexion.prepareStatement(sql) ;
            pst.setInt(1, id);
            pst.execute();
            pst.close();
            conexionSQL.desconectar();
            return "Se borro la categoria cama con exito";
           
        }catch(SQLException e){
             System.out.println("No se pudo borrar la categoria cama con exito");
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
   
}
