import java.util.Date;
import java.util.Map;

class Compra {
    private Date fechaCompra;  // Incluye la fecha de la compra
    private Map<Producto, Integer> productosComprados;

    public Compra(Date fechaCompra, Map<Producto, Integer> productosComprados) {
        this.fechaCompra = fechaCompra;
        this.productosComprados = productosComprados;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public Map<Producto, Integer> getProductosComprados() {
        return productosComprados;
    }
}
