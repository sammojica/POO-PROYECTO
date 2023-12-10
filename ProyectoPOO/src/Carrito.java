import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

    public static void agregarProductoAleatorioAlCarrito(Carrito carrito, String sucursalCliente) {
        // Lógica para obtener un producto aleatorio
        List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();
        Random random = new Random();
        Producto productoAleatorio = listaProductos.get(random.nextInt(listaProductos.size()));
        int cantidad = 1;
        carrito.agregarProducto(productoAleatorio, cantidad, sucursalCliente);
        System.out.println("Producto gratuito agregado al carrito.");
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
                    if (carrito.getProductosEnCarrito().isEmpty()) {
                        System.out.println("El carrito está vacío. No hay productos para comprar.");
                    } else {
                        double totalAPagar = carrito.calcularTotal();
                        System.out.println("Total a pagar: $" + totalAPagar);

                        System.out.println("¿Desea comprar los productos en el carrito? (s/n)");
                        String respuestaCompra = scan.nextLine().toLowerCase();
                        if (respuestaCompra.equals("s")) {
                            // Realizar la compra y actualizar el stock
                            realizarCompra(carrito, usuario, sucursal);
                            salir = true;
                        } else {
                            System.out.println("Compra cancelada.");
                        }
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

    public double calcularTotal() {
        double total = 0.0;

        for (Map.Entry<Producto, Integer> entry : productosEnCarrito.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            total += producto.getPrecio() * cantidad;
        }

        return total;
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
        // Solicitar forma de pago
        System.out.println("Seleccione la forma de pago:");
        System.out.println("1. Efectivo");
        System.out.println("2. Puntos");
        System.out.println("3. Tarjeta de Crédito o Débito");

        Scanner scan = new Scanner(System.in);
        int opcionFormaPago = scan.nextInt();
        scan.nextLine(); // Consumir la nueva línea pendiente después del nextInt

        // Realizar el pago
        boolean pagoExitoso = false;

        switch (opcionFormaPago) {
            case 1:
                pagoExitoso = usuario.realizarPago("efectivo");
                break;
            case 2:
                pagoExitoso = usuario.realizarPago("puntos");
                break;
            case 3:
                pagoExitoso = usuario.realizarPago("tarjeta");
                break;
            default:
                System.out.println("Opción de forma de pago no válida.");
                break;
        }

        if (pagoExitoso) {
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

            generarTicketVirtual(carrito, usuario, sucursal);

            // Calcular y actualizar los puntos del usuario
            double totalCompra = carrito.calcularTotal();
            usuario.ganarPuntos(totalCompra);
            usuario.subirNivel(totalCompra, carrito, sucursal);

            // Actualizar la lista de usuarios con el usuario modificado
            listaUsuarios.removeIf(u -> u.getNombre().equalsIgnoreCase(usuario.getNombre()));
            listaUsuarios.add(usuario);

            // Guardar los cambios en el archivo
            RegistroClientes.guardarClientesEnArchivo(listaUsuarios);
            Producto.guardarProductosEnArchivo(listaProductos);

            // Vaciar el carrito
            carrito.getProductosEnCarrito().clear();
        } else {
            System.out.println("Compra cancelada debido a un problema con el pago.");
        }
    }

    private static void generarTicketVirtual(Carrito carrito, Usuario usuario, String sucursal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String fechaActual = dateFormat.format(new Date());
        String idTienda = obtenerIdTienda(sucursal);
        String idTicket = generarIdTicket();
        String nombreArchivo = fechaActual + "_" + idTienda + "_" + idTicket + ".txt";

        // Imprimir en la salida estándar
        System.out.println("Ticket Virtual - Compra Realizada");
        System.out.println("Fecha de Compra: " + fechaActual);
        System.out.println("ID de Tienda: " + idTienda);
        System.out.println("ID de Ticket: " + idTicket);
        System.out.println();
        System.out.println("Detalles de la Compra:");
        for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println(producto.getNombre() + " x" + cantidad + " - $" + producto.getPrecio() * cantidad);
        }
        System.out.println();
        System.out.println("Total de la Compra: $" + carrito.calcularTotal());
        System.out.println();
        System.out.println("¡Gracias por su compra!");
        // Crear y escribir en el archivo TXT
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            // Escribir en el archivo con el mismo formato
            writer.println("Ticket Virtual - Compra Realizada");
            writer.println("Fecha de Compra: " + fechaActual);
            writer.println("ID de Tienda: " + idTienda);
            writer.println("ID de Ticket: " + idTicket);
            writer.println();
            writer.println("Detalles de la Compra:");
            for (Map.Entry<Producto, Integer> entry : carrito.getProductosEnCarrito().entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                writer.println(producto.getNombre() + " x" + cantidad + " - $" + producto.getPrecio() * cantidad);
            }
            writer.println();
            writer.println("Total de la Compra: $" + carrito.calcularTotal());
            writer.println();
            writer.println("¡Gracias por su compra!");

            System.out.println("Ticket virtual generado con éxito. Nombre del archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar el ticket virtual: " + e.getMessage());
        }
    }

    private static String obtenerIdTienda(String sucursal) {
        switch (sucursal.toLowerCase()) {
            case "norte":
                return "01";
            case "sur":
                return "02";
            case "centro":
                return "03";
            default:
                return "00";
        }
    }

    private static String generarIdTicket() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssSSS");
        return dateFormat.format(new Date());
    }
    
    
    
    
    
    //SIN USUARIO
    
    public static void verCarritoSinUsuario(Carrito carrito, String sucursal) {
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
                if (carrito.getProductosEnCarrito().isEmpty()) {
                    System.out.println("El carrito está vacío. No hay productos para comprar.");
                } else {
                    double totalAPagar = carrito.calcularTotal();
                    System.out.println("Total a pagar: $" + totalAPagar);
                    System.out.println("¿Desea comprar los productos en el carrito? (s/n)");
                    String respuestaCompra = scan.nextLine().toLowerCase();
                    if (respuestaCompra.equals("s")) {
                        // Realizar la compra y actualizar el stock
                        realizarCompraSinUsuario(carrito, sucursal);
                        salir = true;
                    } else {
                        System.out.println("Compra cancelada.");
                    }
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

public static void realizarCompraSinUsuario(Carrito carrito, String sucursal) {
    List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();
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
    // Solicitar forma de pago
    System.out.println("Seleccione la forma de pago:");
    System.out.println("1. Efectivo");
    System.out.println("2. Tarjeta de Crédito o Débito");

    Scanner scan = new Scanner(System.in);
    int opcionFormaPago = scan.nextInt();
    scan.nextLine(); // Consumir la nueva línea pendiente después del nextInt
    // Realizar el pago
    boolean pagoExitoso = false;
    switch (opcionFormaPago) {
        case 1:
            pagoExitoso = realizarPagoEfectivo();
            break;
        case 2:
            if (Usuario.realizarPagoConTarjeta()) {
                    // Preguntar si se requiere factura solo si el pago con tarjeta fue exitoso
                    System.out.println("¿Desea factura? (s/n)");
                    String respuestaFacturaTarjeta = scan.nextLine().toLowerCase();

                    if (respuestaFacturaTarjeta.equals("s")) {
                        // Lógica para solicitar información de factura
                        Usuario.solicitarInformacionFactura();
                    }
                    pagoExitoso = true;
                    break;
                } else {
                    System.out.println("Pago con tarjeta cancelado. Se debe seleccionar otra forma de pago.");
                    break;
                }
        default:
            System.out.println("Opción de forma de pago no válida.");
            break;
    }

    if (pagoExitoso) {
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
        generarTicketVirtual(carrito, null, sucursal);
        // Guardar los cambios en el archivo
        Producto.guardarProductosEnArchivo(listaProductos);
        // Vaciar el carrito
        carrito.getProductosEnCarrito().clear();
    } else {
        System.out.println("Compra cancelada debido a un problema con el pago.");
    }
}

private static boolean realizarPagoEfectivo() {
    // Lógica para pago en efectivo
    System.out.println("Pago en efectivo realizado con éxito.");
    return true;
}


}
