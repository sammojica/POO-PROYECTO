import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Carrito {
    private Map<Producto, Integer> productosEnCarrito;

    public Carrito() {
        this.productosEnCarrito = new HashMap<>();
    }

    public Map<Producto, Integer> getProductosEnCarrito() {
        return productosEnCarrito;
    }

    public void agregarProducto(Producto producto, int cantidad, String sucursal) {
    if (cantidad <= 0) {
        System.out.println("La cantidad debe ser mayor que cero. No se ha agregado el producto al carrito.");
        return;
    }
    // Obtener el stock disponible en la sucursal correspondiente
    int stockDisponible = obtenerStockDisponible(producto, sucursal);
    if (stockDisponible < cantidad) {
        System.out.println("No hay suficiente stock disponible. No se ha agregado el producto al carrito.");
        return;
    }
    // Actualizar el carrito con el nuevo producto y cantidad
    productosEnCarrito.put(producto, productosEnCarrito.getOrDefault(producto, 0) + cantidad);
}

private int obtenerStockDisponible(Producto producto, String sucursal) {
    // Devuelve el stock disponible correspondiente a la sucursal
    int stockDisponible = 0;  // Puedes ajustar este valor de retorno según lo que tenga sentido en tu aplicación

    switch (sucursal.toLowerCase()) {
        case "norte":
            stockDisponible = producto.getStockNorte();
            break;
        case "sur":
            stockDisponible = producto.getStockSur();
            break;
        case "centro":
            stockDisponible = producto.getStockCentro();
            break;
        default:
            System.out.println("Sucursal no válida.");
            break;
    }

    return stockDisponible;
}

    
public static void verCarrito(Usuario usuario, String sucursal) {
    Carrito carrito = usuario.getCarrito();
    if (carrito.getProductosEnCarrito().isEmpty()) {
        System.out.println("El carrito está vacío.");
    } else {
        System.out.println("**Contenido del Carrito**");
        for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println("Nombre: " + producto.getNombre() + ", Cantidad: " + cantidad);
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("\n¿Desea comprar los productos en el carrito? (s/n)");
        String respuesta = scan.nextLine().toLowerCase();
        if (respuesta.equals("s")) {
            // Realizar la compra y actualizar el stock
            realizarCompra(carrito, usuario, sucursal);
        } else {
            System.out.println("Compra cancelada.");
        }
    }
}

private static void realizarCompra(Carrito carrito, Usuario usuario, String sucursal) {
    Date fechaActual = new Date();  // Obtén la fecha actual
    Map<Producto, Integer> productosEnCarrito = carrito.getProductosEnCarrito();
    List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();

    for (Map.Entry<Producto, Integer> entry : productosEnCarrito.entrySet()) {
        Producto productoEnCarrito = entry.getKey();
        int cantidadEnCarrito = entry.getValue();

        // Actualizar el stock del producto en la lista general
        for (Producto producto : listaProductos) {
            if (productoEnCarrito.equals(producto)) {
                int nuevoStock = obtenerNuevoStock(producto, sucursal, cantidadEnCarrito);
                actualizarStock(producto, sucursal, nuevoStock);
                break;
            }
        }
    }

    // Registrar la compra en el historial del usuario
    Compra nuevaCompra = new Compra(fechaActual, productosEnCarrito);
    usuario.agregarCompraAlHistorial(nuevaCompra);

    // Limpiar el carrito después de la compra
    carrito.getProductosEnCarrito().clear();
    System.out.println("Compra realizada con éxito. Gracias por su compra, " + usuario.getNombre() + "!");
}

private static int obtenerNuevoStock(Producto producto, String sucursal, int cantidadComprada) {
    // Devuelve el nuevo stock restando la cantidad comprada del stock correspondiente a la sucursal
    int nuevoStock = 0;  // Puedes ajustar este valor de retorno según lo que tenga sentido en tu aplicación

    switch (sucursal.toLowerCase()) {
        case "norte":
            nuevoStock = producto.getStockNorte() - cantidadComprada;
            break;
        case "sur":
            nuevoStock = producto.getStockSur() - cantidadComprada;
            break;
        case "centro":
            nuevoStock = producto.getStockCentro() - cantidadComprada;
            break;
        default:
            // No se actualiza el stock si la sucursal no es válida
            System.out.println("Sucursal no válida.");
            break;
    }

    return nuevoStock;
}

// Método para actualizar el stock según la sucursal
private static void actualizarStock(Producto producto, String sucursal, int nuevoStock) {
    // Actualiza el stock correspondiente a la sucursal del producto
    switch (sucursal.toLowerCase()) {
        case "norte":
            producto.setStockNorte(nuevoStock);
            break;
        case "sur":
            producto.setStockSur(nuevoStock);
            break;
        case "centro":
            producto.setStockCentro(nuevoStock);
            break;
        default:
            // No se actualiza el stock si la sucursal no es válida
            break;
    }
}


public static void verCarritoSinUsuario(Carrito carrito, String sucursal) {
    if (carrito.getProductosEnCarrito().isEmpty()) {
        System.out.println("El carrito está vacío.");
    } else {
        System.out.println("**Contenido del Carrito**");
        for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println("Nombre: " + producto.getNombre() + ", Cantidad: " + cantidad);
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("\n¿Desea comprar los productos en el carrito? (s/n)");
        String respuesta = scan.nextLine().toLowerCase();
        if (respuesta.equals("s")) {
            // Realizar la compra y actualizar el stock
            realizarCompraSinUsuario(carrito, sucursal);
        } else {
            System.out.println("Compra cancelada.");
        }
    }
}

private static void realizarCompraSinUsuario(Carrito carrito, String sucursal) {
    // Obtén la fecha actual
    Date fechaActual = new Date();    
    // Crear una nueva compra con los productos del carrito
    Compra nuevaCompra = new Compra(fechaActual, carrito.getProductosEnCarrito());
    // Actualizar el stock de productos en la lista general
    List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();
    for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
        Producto productoEnCarrito = entry.getKey();
        int cantidadEnCarrito = entry.getValue();
        for (Producto producto : listaProductos) {
            if (productoEnCarrito.equals(producto) && sucursal.equalsIgnoreCase(sucursal)) {
                int nuevoStock = obtenerNuevoStock(producto, sucursal, cantidadEnCarrito);
                actualizarStock(producto, sucursal, nuevoStock);
                break;
            }
        }
    }
    // Limpiar el carrito después de la compra
    carrito.getProductosEnCarrito().clear();
    // Imprimir mensaje de compra realizada
    System.out.println("Compra realizada con éxito. ¡Gracias por su compra!");
}


}


