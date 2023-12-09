
import java.util.ArrayList;
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
    private String sucursal;
    private Map<Producto, Boolean> inventario;
    private List<Boolean> solicitudes;
    
    public Sucursal(String nombre){
        this.sucursal = nombre;
        this.inventario = new HashMap<>();
        this.solicitudes = new ArrayList<>();
    }
    
}
