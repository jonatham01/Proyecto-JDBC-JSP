package servicio;
import conexion.Conexion;
import dto.CamaDTO;
import entidad.Cama;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CamaServicio {
    PreparedStatement statement;
    private Conexion conexionSQL;
    Connection conexion;

    public CamaServicio(Conexion conexion) {
        this.conexionSQL = conexion;
    }
    
    public Cama mostrarCama(int id)  throws SQLException{
        String sql = "SELECT * FROM CAMA WHERE ID_CAMA = " + id;
        try{
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery(sql);
            if( resultado.next()){
                int idCama = resultado.getInt("id_cama");
                String reciboUrl = resultado.getString("recibo_url");
                Date fecha = resultado.getDate("fecha_cama");
                int categoriaCama = resultado.getInt("id_categoria_cama");
                Cama cama = new Cama(idCama,reciboUrl,fecha,categoriaCama);
                statement.close();
                conexionSQL.desconectar();
                return cama;
            }
            
                    
        }catch(SQLException e){
            throw e;
        }
        return null;
    }
        
    public Cama crearCama(Connection conexion, CamaDTO cama) {
        String sql = "INSERT INTO CAMA(recibo_url,fecha_cama,id_categoria_cama) "
                + "VALUES(CAMA.SQL.NEXTVAL,?,?,?,?)";
        try{
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setString(1, cama.getReciboUrl());
            statement.setDate(2, (Date) cama.getFechaCama());
            statement.setInt(3, cama.getIdCategoriaCama());
            int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if(resultado.next()){
                        int id = resultado.getInt(1);
                        statement.close();
                        conexionSQL.desconectar();
                        return mostrarCama(id);
                    }
                }
            }    
        }catch(SQLException e){
            return null;
        }
        return null;
    }
    

    
    public Cama modificarCama(Connection conexion, CamaDTO camaDTO, int idCama) throws SQLException{
        String sql = "UPDATE CAMA "
                        + "SET RECIBO_URL = ? "
                        + ",FECHA_CAMA = ? "
                        + ",ID_CATEGORIA_CAMA = ? "
                        + "WHERE ID_CAMA = ? ";
                
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setString(1, camaDTO.getReciboUrl());
            statement.setDate(2, (Date) camaDTO.getFechaCama());
            statement.setInt(3, camaDTO.getIdCategoriaCama());
            statement.setInt(4,idCama);
            int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    if(resultado.next()){
                        int id = resultado.getInt(1);
                        statement.close();
                        conexionSQL.desconectar();
                        return mostrarCama(id);
                    }
                }
            }  
        }catch (SQLException e){
            throw e;
        }
        return null;
    }
    
    public boolean borrarCama(Connection conexion,int idCama) {
        String sql = "DELETE FROM CAMA WHERE ID_CAMA = ?" ;
        try{
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, idCama);
            statement.execute(sql);
            statement.close();
            conexionSQL.desconectar();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(CamaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
