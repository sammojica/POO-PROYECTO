import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Compra {
    private Date fechaCompra;  // Incluye la fecha de la compra
    private Map<Producto, Integer> productosComprados;

    // Modifica el constructor para no requerir la fecha inicialmente
    public Compra(Map<Producto, Integer> productosComprados) {
        this.fechaCompra = new Date();  // Asigna la fecha actual al crear la compra
        this.productosComprados = productosComprados;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public Map<Producto, Integer> getProductosComprados() {
        return productosComprados;
    }
    
}
