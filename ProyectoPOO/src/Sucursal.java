
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author greciamc
 */
public class Sucursal {
    private String nombreSucursal;
    //private List<Boolean> solicitudes;
    private Map<Producto, Integer> inventario;
    private Map<Empleado, String> empleados;
    private Map<Compra, Date> compras;
    private double efectivo;
    
    public Sucursal(String nombre) {
        this.nombreSucursal = nombre;
        this.inventario = new HashMap<>();
        this.empleados = new HashMap<>();
        this.compras = new HashMap<>();
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public Map<Producto, Integer> getInventario() {
        return inventario;
    }

    public void setInventario(Map<Producto, Integer> inventario) {
        this.inventario = inventario;
    }

    public Map<Empleado, String> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Map<Empleado, String> empleados) {
        this.empleados = empleados;
    }

    public Map<Compra, Date> getCompras() {
        return compras;
    }

    public void setCompras(Map<Compra, Date> compras) {
        this.compras = compras;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }
    
    
    
}
