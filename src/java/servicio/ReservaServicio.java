package servicio;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import entidad.Reserva;
import dto.ReservaDTO;

public class ReservaServicio {
    PreparedStatement statement;
    Connection conexion;
    private Conexion conexionSQL;

    public ReservaServicio() {
        this.conexionSQL = new Conexion();
    }
    
    public Reserva mostrarReserva(long id){
        try {
            conexion = conexionSQL.conectar();
            String sql = "SELECT * FROM RESERVA WHERE ID_RESERVA = ?";
            statement = conexion.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultado = statement.executeQuery();
            statement.close();
            conexionSQL.desconectar();
            if(resultado.next()){
                return new Reserva(
                        resultado.getLong("id_reserva"),
                        resultado.getDate("fecha_reserva"),
                        resultado.getString("estado"),
                        resultado.getDate("fecha_inicio").toLocalDate(),
                        resultado.getDate("fecha_fin").toLocalDate(),
                        resultado.getInt("id_cliente"),
                        resultado.getLong("id_factura")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    

    
    public Reserva guardarReserva(ReservaDTO dto){
        String sql = "INSERT INTO RESERVA(FECHA_RESERVA,ESTADO,FECHA_INICIO,FECHA_FIN,ID_CLIENTE,ID_FACTURA) "
                + "VALUES(CAMA.SQL.NEXTVAL,?,?,?,?,?,?)";
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, (Date) dto.getFechaReserva());
            statement.setString(2, dto.getEstado());
            statement.setDate(3, Date.valueOf(dto.getFechaInicio()));
            statement.setDate(4, Date.valueOf(dto.getFechaFin()));
            statement.setInt(5, dto.getIdCliente());
            statement.setLong(6, dto.getIdFactura());
            int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    Reserva reserva = mostrarReserva( resultado.getLong(1));
                    statement.close();
                    conexionSQL.desconectar();
                    return reserva;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Reserva modificarReserva(ReservaDTO dto, long id){
        String sql = "UPDATE RESERVA SET FECHA_RESERVA = ? , ESTADO = ? , FECHA_INICIO = ?"
                + " ,FECHA_FIN = ? , ID_CLIENTE = ? , ID_FACTURA = ? "
                + "WHERE ID_RESERVA = ?" ;
        try {
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, (Date) dto.getFechaReserva());
            statement.setString(2, dto.getEstado());
            statement.setDate(3, Date.valueOf(dto.getFechaInicio()));
            statement.setDate(4, Date.valueOf(dto.getFechaFin()));
            statement.setInt(5, dto.getIdCliente());
            statement.setLong(6, dto.getIdFactura());
            statement.setLong(7, id);
            int filas = statement.executeUpdate();
            if(filas > 0){
                try(ResultSet resultado = statement.getGeneratedKeys()){
                    Reserva reserva = mostrarReserva( resultado.getLong(1));
                    statement.close();
                    conexionSQL.desconectar();
                    return reserva;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    public boolean eliminarReserva( long id){
        String sql = "DELETE FROM RESERVA WHERE ID_RESERVA = ? " ;
        try {
            conexion = conexionSQL.conectar();
            statement = conexion.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute(sql);
            statement.close();
            conexionSQL.desconectar();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
