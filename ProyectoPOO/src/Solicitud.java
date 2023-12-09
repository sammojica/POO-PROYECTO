
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author greciamc
 */
public class Solicitud {
    private String nombreProducto;
    private String sucursal;
    private int stock;
    private EstadoSolicitud estado;
    private String idGerente;
    private String idEmpleado;

    public Solicitud(String nombreProducto, String sucursal, int stock, EstadoSolicitud estado, String idGerente, String idEmpleado) {
        this.nombreProducto = nombreProducto;
        this.sucursal = sucursal;
        this.stock = stock;
        this.estado = estado;
        this.idGerente = idGerente;
        this.idEmpleado = idEmpleado;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getSucursal() {
        return sucursal;
    }

    public int getStock() {
        return stock;
    }
    
    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getIdGerente() {
        return idGerente;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }
    
    public void setIdGerente(String idGerente) {
        this.idGerente = idGerente;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public enum EstadoSolicitud {
        PENDIENTE,
        APROBADA,
        RECHAZADA
    }
    
    
}
