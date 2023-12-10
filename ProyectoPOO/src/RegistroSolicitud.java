
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author greciamc
 */
public class RegistroSolicitud {
    private static final String ARCHIVO_SOLICITUDES = "Solicitudes.txt";
    private static final String DELIMITADOR = ",";
    
    public static List<Solicitud> leerSolicitudesDesdeArchivo() {
        List<Solicitud> solicitudes = new ArrayList<>();
        Path archivoPath = Paths.get(ARCHIVO_SOLICITUDES);

        if (!Files.exists(archivoPath)) {
            try {
                Files.createFile(archivoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_SOLICITUDES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(DELIMITADOR);

                if (partes.length == 7) {
                    Solicitud solicitud = new Solicitud(partes[0], partes[1], Integer.parseInt(partes[2]), Solicitud.EstadoSolicitud.valueOf(partes[3]), Solicitud.EstadoSolicitud.valueOf(partes[4]),partes[5], partes[6]);
                    solicitudes.add(solicitud);
                } else {
                    System.out.println("Error en el formato de la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return solicitudes;
    }
    
    public static void guardarSolicitudesEnArchivo(List<Solicitud> solicitudes) {
        try (PrintWriter writer = new PrintWriter(ARCHIVO_SOLICITUDES)) {
            for (Solicitud solicitud : solicitudes) {
                writer.println(String.format("%s" + DELIMITADOR + "%s" + DELIMITADOR + "%d" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s",
                        solicitud.getNombreProducto(), solicitud.getSucursal(), solicitud.getStock(), solicitud.getEstado1(), solicitud.getEstado2(), solicitud.getIdGerente(), solicitud.getIdEmpleado()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void registrarSolicitud(Solicitud solicitud){
        List<Solicitud> listaSolicitudes = new ArrayList<>(leerSolicitudesDesdeArchivo());
        listaSolicitudes.add(solicitud);
        guardarSolicitudesEnArchivo(listaSolicitudes);
    }
    
    //modo true: lo pide el gerente (a una tienda), modo false: lo pide el empleado (de un empleado).
    public Solicitud crearSolicitud(Scanner scan, String idUsuario, Boolean modo) {
        System.out.println("Ingrese el nombre del producto:");
        String nombreProducto = scan.nextLine();
        System.out.println("Ingrese la sucursal:");
        String sucursal = scan.nextLine();

        Producto producto = Producto.buscarProductoEnSucursal(nombreProducto, sucursal);
        int stock = 0;  // Initialize to 0 by default

        // Only request stock if modo is true
        if (modo) {
            System.out.println("Ingrese el stock:");
            stock = scan.nextInt();
        }else{
            stock = Producto.obtenerStockSegunSucursal(producto, sucursal);
        }

        List<Solicitud> listaSolicitudes = leerSolicitudesDesdeArchivo();

        // Check if a similar request already exists
        for (Solicitud existente : listaSolicitudes) {
            if (existente.getNombreProducto().equalsIgnoreCase(nombreProducto) && existente.getSucursal().equalsIgnoreCase(sucursal) && existente.getStock() == stock) {
                System.out.println("Ya existe una solicitud para el producto en la sucursal especificada.");
                return existente;
            }
        }

        if (producto != null) {
            Solicitud nuevaSolicitud;
            if (modo) {
                nuevaSolicitud = new Solicitud(producto.getNombre(), sucursal, stock, Solicitud.EstadoSolicitud.PENDIENTE, Solicitud.EstadoSolicitud.PENDIENTE, idUsuario, "NA");
            } else {
                nuevaSolicitud = new Solicitud(producto.getNombre(), sucursal, stock, Solicitud.EstadoSolicitud.PENDIENTE, Solicitud.EstadoSolicitud.PENDIENTE," ", idUsuario);
            }

            registrarSolicitud(nuevaSolicitud);
            System.out.println("\nSolicitud registrada exitosamente.");
            return nuevaSolicitud;
        } else {
            System.out.println("\nNo se encontró el producto que se solicita.");
            return null;
        }
    }
    
    
}
