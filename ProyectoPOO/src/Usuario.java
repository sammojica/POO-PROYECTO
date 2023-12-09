import java.util.ArrayList;
import java.util.HashMap;
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

    public void editarUnidadesProducto(Producto producto, int nuevaCantidad) {
        if (productosEnCarrito.containsKey(producto)) {
            productosEnCarrito.put(producto, nuevaCantidad);
            System.out.println("Cantidad actualizada correctamente.");
        } else {
            System.out.println("El producto no está en el carrito.");
        }
    }

// Método para eliminar un producto del carrito
    public void eliminarProducto(Producto producto) {
        if (productosEnCarrito.containsKey(producto)) {
            productosEnCarrito.remove(producto);
            System.out.println("Producto eliminado del carrito.");
        } else {
            System.out.println("El producto no está en el carrito.");
        }
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
            return;
        }
        Scanner scan = new Scanner(System.in);
        boolean salir = false;
        do {
            System.out.println("**Contenido del Carrito**");
            for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                System.out.println("Nombre: " + producto.getNombre() + ", Cantidad: " + cantidad);
            }
            System.out.println("\n¿Qué acción desea realizar?");
            System.out.println("1. Comprar los productos en el carrito");
            System.out.println("2. Editar unidades de un producto");
            System.out.println("3. Eliminar un producto del carrito");
            System.out.println("0. Salir");

            int opcion = scan.nextInt();
            scan.nextLine(); // Consumir la nueva línea pendiente después del nextInt

            switch (opcion) {
                case 1:
                    System.out.println("¿Desea comprar los productos en el carrito? (s/n)");
                    String respuestaCompra = scan.nextLine().toLowerCase();
                    if (respuestaCompra.equals("s")) {
                        // Realizar la compra y actualizar el stock
                        realizarCompra(carrito, usuario, sucursal);
                        salir = true;
                    } else {
                        System.out.println("Compra cancelada.");
                    }
                    break;
                case 2:
                    if (carrito.getProductosEnCarrito().isEmpty()) {
                        System.out.println("El carrito está vacío. No hay productos para editar.");
                    } else {
                        System.out.println("Ingrese el nombre del producto que desea editar:");
                        String nombreProductoEditar = scan.nextLine();
                        Producto productoEditar = obtenerProductoEnCarritoPorNombre(carrito, nombreProductoEditar);
                        if (productoEditar != null) {
                            System.out.println("Ingrese la nueva cantidad:");
                            int nuevaCantidad = scan.nextInt();
                            carrito.editarUnidadesProducto(productoEditar, nuevaCantidad);
                        } else {
                            System.out.println("Producto no encontrado en el carrito.");
                        }
                    }
                    break;
                case 3:
                    if (carrito.getProductosEnCarrito().isEmpty()) {
                        System.out.println("El carrito está vacío. No hay productos para eliminar.");
                    } else {
                        System.out.println("Ingrese el nombre del producto que desea eliminar:");
                        String nombreProductoEliminar = scan.nextLine();
                        Producto productoEliminar = obtenerProductoEnCarritoPorNombre(carrito, nombreProductoEliminar);
                        if (productoEliminar != null) {
                            carrito.eliminarProducto(productoEliminar);
                        } else {
                            System.out.println("Producto no encontrado en el carrito.");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del menú.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (!salir);
    }

    private static Producto obtenerProductoEnCarritoPorNombre(Carrito carrito, String nombreProducto) {
        for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
            Producto producto = entry.getKey();
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                return producto;
            }
        }
        return null;
    }

    public static void realizarCompra(Carrito carrito, Usuario usuario, String sucursal) {
        List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();
        List<Usuario> listaUsuarios = new ArrayList<>(RegistroClientes.leerClientesDesdeArchivo());

        // Verificar el stock antes de realizar la compra
        for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
            Producto productoEnCarrito = entry.getKey();
            int cantidadEnCarrito = entry.getValue();
            int stockDisponible = carrito.obtenerStockDisponible(productoEnCarrito, sucursal);

            if (cantidadEnCarrito > stockDisponible) {
                System.out.println("No hay suficiente stock para el producto '" + productoEnCarrito.getNombre() + "'.");
                System.out.println("Stock disponible: " + stockDisponible);
                System.out.println("Compra cancelada.");
                return;
            }
        }

        // Actualizar el stock y vaciar el carrito
        for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
            Producto productoEnCarrito = entry.getKey();
            int cantidadEnCarrito = entry.getValue();

            // Encontrar el producto correspondiente en la lista original
            Producto productoOriginal = listaProductos.stream()
                    .filter(p -> p.getNombre().equalsIgnoreCase(productoEnCarrito.getNombre()))
                    .findFirst()
                    .orElse(null);

            if (productoOriginal != null) {
                // Actualizar el stock según la sucursal
                switch (sucursal.toLowerCase()) {
                    case "norte":
                        productoOriginal.setStockNorte(productoOriginal.getStockNorte() - cantidadEnCarrito);
                        break;
                    case "sur":
                        productoOriginal.setStockSur(productoOriginal.getStockSur() - cantidadEnCarrito);
                        break;
                    case "centro":
                        productoOriginal.setStockCentro(productoOriginal.getStockCentro() - cantidadEnCarrito);
                        break;
                }
            }
        }
Compra nuevaCompra = new Compra(carrito.getProductosEnCarrito());
    usuario.agregarCompraAlHistorial(nuevaCompra);

    // Actualizar la lista de usuarios con el usuario modificado
    listaUsuarios.removeIf(u -> u.getNombre().equalsIgnoreCase(usuario.getNombre()));
    listaUsuarios.add(usuario);

    // Guardar los cambios en el archivo
    RegistroClientes.guardarClientesEnArchivo(listaUsuarios);
    Producto.guardarProductosEnArchivo(listaProductos);

    // Vaciar el carrito
    carrito.getProductosEnCarrito().clear();

    System.out.println("Compra realizada con éxito. ¡Gracias por su compra!");
    }

}
