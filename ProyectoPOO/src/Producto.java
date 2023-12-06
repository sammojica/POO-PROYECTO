import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Producto {

    public String nombre;
    public int codigoProducto;
    public double precio;
    public String categoria;
    public int stockCentro;
    public int stockNorte;
    public int stockSur;

    public Producto(String nombre, int codigoProducto, double precio, String categoria, int stockCentro, int stockNorte, int stockSur) {
        this.nombre = nombre;
        this.codigoProducto = codigoProducto;
        this.precio = precio;
        this.categoria = categoria;
        this.stockCentro = stockCentro;
        this.stockNorte = stockNorte;
        this.stockSur = stockSur;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStockCentro() {
        return stockCentro;
    }

    public void setStockCentro(int stockCentro) {
        this.stockCentro = stockCentro;
    }

    public int getStockNorte() {
        return stockNorte;
    }

    public void setStockNorte(int stockNorte) {
        this.stockNorte = stockNorte;
    }

    public int getStockSur() {
        return stockSur;
    }

    public void setStockSur(int stockSur) {
        this.stockSur = stockSur;
    }
    private static final String ARCHIVO_PRODUCTOS = "Productos.txt";
    private static final String DELIMITADOR = ",";

    public static List<Producto> leerProductosDesdeArchivo() {
        List<Producto> listaProductos = new ArrayList<>();
        Path archivoPath = Paths.get(ARCHIVO_PRODUCTOS);

        if (!Files.exists(archivoPath)) {
            try {
                Files.createFile(archivoPath);
            } catch (IOException e) {
               
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PRODUCTOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(DELIMITADOR.trim());
                if (partes.length == 7) {
                    Producto producto = new Producto(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2]),
                            partes[3], Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), Integer.parseInt(partes[6]));
                    listaProductos.add(producto);
                } else {
                    System.out.println("Error en el formato de la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    public static void guardarProductosEnArchivo(List<Producto> listaProductos) {
        try (PrintWriter writer = new PrintWriter(ARCHIVO_PRODUCTOS)) {
            for (Producto producto : listaProductos) {
                writer.println(String.format("%s" + DELIMITADOR + "%d" + DELIMITADOR + "%.6f" + DELIMITADOR + "%s" + DELIMITADOR + "%d" + DELIMITADOR + "%d" + DELIMITADOR + "%d",
                        producto.getNombre(), producto.getCodigoProducto(), producto.getPrecio(),
                        producto.getCategoria(), producto.getStockCentro(), producto.getStockNorte(), producto.getStockSur()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void agregarProducto(Producto producto) {
        List<Producto> listaProductos = leerProductosDesdeArchivo();

        // Verificar si el nuevo producto ya existe en la lista
        boolean productoExistente = listaProductos.stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(producto.getNombre()));

        if (productoExistente) {
            System.out.println("Ya existe un producto con el mismo nombre. No se puede agregar.");
            return;
        }

        // Agregar el nuevo producto a la lista
        listaProductos.add(producto);

        // Guardar la lista actualizada en el archivo
        guardarProductosEnArchivo(listaProductos);
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        List<Producto> productosRegistrados = leerProductosDesdeArchivo();

        System.out.println("Ingrese el nombre del producto (o 's' para salir):");
        String nombreProducto = scan.nextLine();

        if (nombreProducto.equalsIgnoreCase("s")) {
            System.out.println("Saliendo del registro...");
            return;
        }

        boolean productoExistente = false;

        for (Producto producto : productosRegistrados) {
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                productoExistente = true;
                break;
            }
        }

        if (productoExistente) {
            System.out.println("Ya existe un producto con el mismo nombre. No se puede agregar.");
            return;
        }

        System.out.println("Nombre de producto válido. Puede continuar con el registro.");
        System.out.println("Ingrese el código del producto:");
        int codigoProducto = scan.nextInt();
        System.out.println("Ingrese el precio del producto:");
        double precioProducto = scan.nextDouble();
        scan.nextLine();
        System.out.println("Ingrese la categoría del producto:");

        String categoriaProducto = scan.nextLine();

        // Solicitar el stock para cada tienda
        System.out.println("Ingrese el stock para la tienda Centro:");
        int stockCentro = scan.nextInt();

        System.out.println("Ingrese el stock para la tienda Norte:");
        int stockNorte = scan.nextInt();

        System.out.println("Ingrese el stock para la tienda Sur:");
        int stockSur = scan.nextInt();

        Producto nuevoProducto = new Producto(nombreProducto, codigoProducto, precioProducto, categoriaProducto, stockCentro, stockNorte, stockSur);
        agregarProducto(nuevoProducto);
    }

    public static void buscarProducto(Carrito carrito, String sucursalCliente) {
        Scanner scan = new Scanner(System.in);
        System.out.println("**Buscar Producto por Nombre**");

        // Obtener la lista de productos desde el archivo
        List<Producto> listaProductos = leerProductosDesdeArchivo();

        // Solicitar el nombre del producto a buscar
        System.out.println("Ingrese el nombre del producto a buscar:");
        String nombreBusqueda = scan.nextLine();

        boolean productoEncontrado = false;

        // Buscar productos por nombre y mostrarlos
        for (Producto producto : listaProductos) {
            if (producto.getNombre().equalsIgnoreCase(nombreBusqueda)) {
                // Mostrar el stock según la sucursal del cliente
                int stockSegunSucursal = obtenerStockSegunSucursal(producto, sucursalCliente);
                System.out.println("Stock en la sucursal " + sucursalCliente + ": " + stockSegunSucursal);

                productoEncontrado = true;

                // Preguntar al usuario si desea agregar el producto al carrito
                System.out.println("¿Desea agregar este producto al carrito? (s/n)");
                String respuesta = scan.nextLine().toLowerCase();

                if (respuesta.equals("s")) {
                    // Pedir la cantidad de productos a agregar al carrito
                    System.out.println("Ingrese la cantidad a agregar al carrito:");
                    int cantidad = scan.nextInt();
                    scan.nextLine(); // Consumir la nueva línea pendiente después de nextInt

                    // Verificar si hay suficiente stock
                    if (cantidad <= stockSegunSucursal) {
                        // Agregar el producto al carrito con la cantidad especificada
                        carrito.agregarProducto(producto, cantidad, sucursalCliente);
                        System.out.println("Producto agregado al carrito.");
                    } else {
                        System.out.println("No hay suficiente stock para la cantidad solicitada.");
                    }
                }
                break; // Salir del bucle después de encontrar el primer producto con el nombre especificado
            }
        }

        // Mostrar mensaje si no se encontraron productos con el nombre especificado
        if (!productoEncontrado) {
            System.out.println("No se encontraron productos con el nombre '" + nombreBusqueda + "'.");
        }
    }

// Método para obtener el stock según la sucursal

    public static int obtenerStockSegunSucursal(Producto producto, String sucursal) {

        // Devuelve el stock correspondiente a la sucursal del producto
        switch (sucursal.toLowerCase()) {
            case "norte":
                return producto.getStockNorte();
            case "sur":
                return producto.getStockSur();
            case "centro":
                return producto.getStockCentro();
            default:
                return 0; // Manejar una sucursal no válida como 0 stock
        }
    }

    public static Producto buscarProductoEnSucursal(String nombreProducto, String sucursal) {
        List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();

        for (Producto producto : listaProductos) {
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                return producto;
            }
        }

        return null;
    }
}
