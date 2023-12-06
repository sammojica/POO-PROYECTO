import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OrdenDeSurtido {
    private static final String ARCHIVO_ORDENES_SURTIDO = "OrdenesSurtido.txt";
    private static final String DELIMITADOR = ",";

    private String sucursal;
    private String nombreProducto;
    private int cantidad;

    public OrdenDeSurtido(String sucursal, String nombreProducto, int cantidad) {
        this.sucursal = sucursal;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    public String getSucursal() {
        return sucursal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public static void guardarOrdenesSurtidoEnArchivo(List<OrdenDeSurtido> listaOrdenes) {
        try (PrintWriter writer = new PrintWriter(ARCHIVO_ORDENES_SURTIDO)) {
            for (OrdenDeSurtido orden : listaOrdenes) {
                writer.println(String.format("%s" + DELIMITADOR + "%s" + DELIMITADOR + "%d",
                        orden.getSucursal(), orden.getNombreProducto(), orden.getCantidad()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<OrdenDeSurtido> leerOrdenesSurtidoDesdeArchivo() {
        List<OrdenDeSurtido> listaOrdenes = new ArrayList<>();
        Path archivoPath = Paths.get(ARCHIVO_ORDENES_SURTIDO);

        if (!Files.exists(archivoPath)) {
            try {
                Files.createFile(archivoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_ORDENES_SURTIDO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(DELIMITADOR);

                if (partes.length == 3) {
                    OrdenDeSurtido orden = new OrdenDeSurtido(partes[0], partes[1], Integer.parseInt(partes[2]));
                    listaOrdenes.add(orden);
                } else {
                    System.out.println("Error en el formato de la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaOrdenes;
    }

    public static void mostrarOrdenesSurtido() {
        List<OrdenDeSurtido> listaOrdenes = leerOrdenesSurtidoDesdeArchivo();

        System.out.println("Ordenes de Surtido Registradas:\n");

        for (OrdenDeSurtido orden : listaOrdenes) {
            System.out.println("Sucursal: " + orden.getSucursal());
            System.out.println("Producto: " + orden.getNombreProducto());
            System.out.println("Cantidad: " + orden.getCantidad());
            System.out.println("-----------------------------------------------");
        }
    }

    public static void guardarOrdenSurtido(OrdenDeSurtido orden) {
        List<OrdenDeSurtido> listaOrdenes = new ArrayList<>(leerOrdenesSurtidoDesdeArchivo());

        listaOrdenes.add(orden);

        guardarOrdenesSurtidoEnArchivo(listaOrdenes);
    }
}
