
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
    private EstadoSolicitud estado1;
    private EstadoSolicitud estado2;
    private String idGerente;
    private String idEmpleado;

    public Solicitud(String nombreProducto, String sucursal, int stock, EstadoSolicitud estado1, EstadoSolicitud estado2, String idGerente, String idEmpleado) {
        this.nombreProducto = nombreProducto;
        this.sucursal = sucursal;
        this.stock = stock;
        this.estado1 = estado1;
        this.estado2 = estado2;
        this.idGerente = idGerente;
        this.idEmpleado = idEmpleado;
    }

    public EstadoSolicitud getEstado1() {
        return estado1;
    }

    public EstadoSolicitud getEstado2() {
        return estado2;
    }

    public void setEstado1(EstadoSolicitud estado1) {
        this.estado1 = estado1;
    }

    public void setEstado2(EstadoSolicitud estado2) {
        this.estado2 = estado2;
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
