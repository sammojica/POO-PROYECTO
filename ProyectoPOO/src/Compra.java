import java.util.Date;
import java.util.Map;

class Compra {
    private Date fechaCompra;  // Incluye la fecha de la compra
    private Map<Producto, Integer> productosComprados;
    private String sucursal;

    public Compra(Date fechaCompra, Map<Producto, Integer> productosComprados, String sucursal) {
        this.fechaCompra = fechaCompra;
        this.productosComprados = productosComprados;
        this.sucursal = sucursal;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public Map<Producto, Integer> getProductosComprados() {
        return productosComprados;
    }
    
    public String getSucursal() {
        return sucursal;
    } 
    
}
