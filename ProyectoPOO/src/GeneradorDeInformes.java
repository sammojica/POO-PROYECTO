import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneradorDeInformes {
    private static List<Producto> productosVendidos = new ArrayList<>();
    private static Map<String, List<Compra>> comprasPorTienda = new HashMap<>();
    private static List<Compra> todasLasCompras = new ArrayList<>();
    private static List<String> cambiosRealizados = new ArrayList<>();

    public static void generarReporteDeVentas(List<Compra> historialCompras) {
        try {
            // Limpiar registros anteriores
            productosVendidos.clear();
            comprasPorTienda.clear();
            todasLasCompras.clear();
            cambiosRealizados.clear();

            // Generar y guardar el reporte de ventas general
            generarReporteGeneral(historialCompras, "InformeVentasGeneral.txt");

            // Generar y guardar el reporte de ventas por productos
            generarReportePorProductos(historialCompras, "InformeVentasPorProductos.txt");

            // Generar y guardar el reporte de ventas por tiendas
            generarReportePorTiendas(historialCompras, "InformeVentasPorTiendas.txt");

            // Guardar cambios realizados en la lista
            guardarCambiosEnLista();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private static void generarReporteGeneral(List<Compra> historialCompras, String fileName) throws IOException {
    try (FileWriter writer = new FileWriter(fileName, true)) {
        writer.write("Informe de Ventas General\n");
        writer.write("-------------------------\n\n");

        for (Compra compra : historialCompras) {
            todasLasCompras.add(compra);

            writer.write("Fecha de Compra: " + compra.getFechaCompra() + "\n");
            writer.write("Productos Comprados:\n");

            for (Map.Entry<Producto, Integer> entry : compra.getProductosComprados().entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                productosVendidos.add(producto);

                writer.write("   Nombre: " + producto.getNombre() + ", Cantidad: " + cantidad + "\n");
            }

            writer.write("---------------------------------------------\n");
        }
    }
}

private static void generarReportePorProductos(List<Compra> historialCompras, String fileName) throws IOException {
    try (FileWriter writer = new FileWriter(fileName, true)) {
        writer.write("Informe de Ventas por Productos\n");
        writer.write("--------------------------------\n\n");

        Map<Producto, Integer> ventasPorProducto = new HashMap<>();

        for (Compra compra : historialCompras) {
            for (Map.Entry<Producto, Integer> entry : compra.getProductosComprados().entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                productosVendidos.add(producto);
                ventasPorProducto.put(producto, ventasPorProducto.getOrDefault(producto, 0) + cantidad);
            }
        }

        for (Map.Entry<Producto, Integer> entry : ventasPorProducto.entrySet()) {
            Producto producto = entry.getKey();
            int totalVentas = entry.getValue();
            writer.write("Nombre: " + producto.getNombre() + ", Total de Ventas: " + totalVentas + "\n");
        }
    }
}

private static void generarReportePorTiendas(List<Compra> historialCompras, String fileName) throws IOException {
    try (FileWriter writer = new FileWriter(fileName, true)) {
        writer.write("Informe de Ventas por Tiendas\n");
        writer.write("-------------------------------\n\n");

        for (Compra compra : historialCompras) {
            String sucursal = compra.getSucursal();
            comprasPorTienda.computeIfAbsent(sucursal, k -> new ArrayList<>()).add(compra);
        }

        for (Map.Entry<String, List<Compra>> entry : comprasPorTienda.entrySet()) {
            String sucursal = entry.getKey();
            List<Compra> comprasEnTienda = entry.getValue();
            int totalVentas = comprasEnTienda.size();
            writer.write("Sucursal: " + sucursal + ", Total de Ventas: " + totalVentas + "\n");
        }
    }
}


    private static void guardarCambiosEnLista() {
        cambiosRealizados.add("Informe de Ventas General generado (InformeVentasGeneral.txt)");
        cambiosRealizados.add("Informe de Ventas por Productos generado (InformeVentasPorProductos.txt)");
        cambiosRealizados.add("Informe de Ventas por Tiendas generado (InformeVentasPorTiendas.txt)");
    }

    // Métodos para obtener información
    public static List<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public static Map<String, List<Compra>> getComprasPorTienda() {
        return comprasPorTienda;
    }

    public static List<Compra> getTodasLasCompras() {
        return todasLasCompras;
    }

    public static List<String> getCambiosRealizados() {
        return cambiosRealizados;
    }

    public static List<String> leerInformeGeneral() {
        return leerInforme("InformeVentasGeneral.txt");
    }

    public static List<String> leerInformePorProductos() {
        return leerInforme("InformeVentasPorProductos.txt");
    }

    public static List<String> leerInformePorTiendas() {
        return leerInforme("InformeVentasPorTiendas.txt");
    }

    private static List<String> leerInforme(String fileName) {
        List<String> informe = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                informe.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return informe;
    }
}