
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Gerente extends Empleado {

    public Gerente(String RFC, String numTrabajador, String tipoTrabajador, String Sucursal, String nombre, String apellidos, String direccion, String telefono, String correoElectronico, String contraseña) {
        super(RFC, numTrabajador, tipoTrabajador, Sucursal, nombre, apellidos, direccion, telefono, correoElectronico, contraseña);
    }

    @Override
    public String getNumTrabajador() {
        return numTrabajador;
    }
    
    public static void menu(Gerente gerente) {
        Scanner scan = new Scanner(System.in);
        int op;

        do {
            System.out.println("\n\nBienvenido Gerente "+gerente.getApellidos()+" "+gerente.getNombre());
            System.out.println("\n1. Ver empleados.");
            System.out.println("2. Agregar empleados.");
            System.out.println("3. Agregar producto.\n4. Actualizar Stock.");
            System.out.println("5. Solicitud de actualización de Stock a una tienda.");
            System.out.println("6. Autorizar solicitud de actualización de Stock de un empleado.");
            System.out.println("7. Solicitar retiro/depósito de efectivo a una tienda.");
            System.out.println("8. Reporte de Ventas.");
            System.out.println("9. Crear orden de resurtido de ventas.");
            System.out.println("10. Actualizar Información personal.");
            System.out.print("11. Cerrar sesión.\n  > ");
            op = scan.nextInt();
            switch (op) {
                case 1:
                    Empleado.mostrarEmpleadosRegistrados();
                    break;
                case 2:
                    RegistroEmpleados.main();
                    break;
                case 3:
                    Producto.main();
                    break;
                case 4:
                    gerente.actualizarStock();
                    break;
                case 5:
                    gerente.solicitarActualizacionStock(scan);
                    break;
                case 6:
                    gerente.aprobarSolicitud();
                    break;
                case 11:
                    System.out.println("Cerrando sesión...\n\n");
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("\nOpción inválida. Intente de nuevo.");
                    break;
            }
        } while (op != 11);
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        boolean inicioSesionExitoso = false;
        System.out.println("**Inicio de sesión para Gerentes**");
        List<Gerente> gerentesRegistrados = RegistroGerentes.leerGerentesDesdeArchivo();

        // Verificar si hay gerentes registrados
        if (gerentesRegistrados.isEmpty()) {
            System.out.println("Aún no hay gerentes registrados. Regístrese primero.\n\n");
            return;  // Salir del programa si no hay gerentes registrados
        }

        inicioSesionExitoso = false;
        while (!inicioSesionExitoso) {
            System.out.println("Ingrese su número de trabajador (o 's' para salir):");
            String numeroTrabajadorIngresado = scan.nextLine();

            if (numeroTrabajadorIngresado.equalsIgnoreCase("s")) {
                System.out.println("Saliendo del inicio de sesión...");
                return; // Salir del programa o realizar alguna acción de salida
            }

            System.out.println("Ingrese su contraseña:");
            String contraseñaIngresada = scan.nextLine();

            // Validar el número de trabajador y la contraseña
            if (validarNumeroTrabajadorYContraseñaGerente(numeroTrabajadorIngresado, contraseñaIngresada, gerentesRegistrados)) {
                System.out.println("Inicio de sesión exitoso como Gerente.");
                inicioSesionExitoso = true;
                Gerente gerenteSesion = obtenerGerente(numeroTrabajadorIngresado, gerentesRegistrados);
                menu(gerenteSesion);
            } else {
                System.out.println("Número de trabajador o contraseña incorrectos. Intente de nuevo.");
            }
        }
    }

    public static Gerente obtenerGerente(String numTrabajador, List<Gerente> gerentes){
        for(Gerente gerente : gerentes){
            if(gerente.getNumTrabajador().equals(numTrabajador))
                return gerente;
        }
        return null; 
    }


    public static boolean validarNumeroTrabajadorYContraseñaGerente(String numeroTrabajador, String contraseña, List<Gerente> gerentesRegistrados) {
        for (Gerente gerente : gerentesRegistrados) {
            if (gerente.getNumTrabajador().equalsIgnoreCase(numeroTrabajador) && gerente.getContraseña().equalsIgnoreCase(contraseña)) {
                // Número de trabajador y contraseña de gerente válidos
                return true;
            }
        } 
        // Si no se encuentra un gerente con la combinación correcta de número de trabajador y contraseña
        return false;
    }
    
    public void aprobarSolicitud(){
        List<Solicitud> solicitudesPendientes = RegistroSolicitud.leerSolicitudesDesdeArchivo();
        
        if(!solicitudesPendientes.isEmpty()){
            System.out.println("Solicitudes Pendientes:");
            for (int i = 0; i < solicitudesPendientes.size(); i++) {
                System.out.println(i + ". " + solicitudesPendientes.get(i).toString());
            }
            
            Scanner scanner = new Scanner(System.in); 
            for(Solicitud solicitud : solicitudesPendientes) {
                boolean validar = true;
                while(validar){
                    System.out.println("¿Aprobar la solicitud para " + solicitud.getNombreProducto() + "\n en tienda " + solicitud.getSucursal()+ "? (Sí/No): ");
                    String decision = scanner.nextLine().toLowerCase();

                    if (decision.equals("si")) {
                        solicitud.setEstado(Solicitud.EstadoSolicitud.APROBADA);
                        solicitud.setIdGerente(this.getNumTrabajador());
                        System.out.println("Solicitud aprobada.");
                    } else if(decision.equals("no")) {
                        solicitud.setEstado(Solicitud.EstadoSolicitud.RECHAZADA);
                        System.out.println("Solicitud rechazada.");
                    }else{
                        System.out.println("Opción no válida. La solicitud seguirá pendiente.");
                        continue;
                    }
                    System.out.println("Esta segur@ de su decision? (Sí/No): ");
                    String valid = scanner.nextLine().toLowerCase();
                    validar = !valid.equals("si");
                }
            }
            
            RegistroSolicitud.guardarSolicitudesEnArchivo(solicitudesPendientes);
        
        }else
            System.out.println("No hay solicitudes pendientes.");
        
    }
    
    @Override
    public void actualizarStock() {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Ingrese el nombre del producto:");
        String nombreProducto = scan.nextLine();

        System.out.println("Ingrese la sucursal (norte/sur/centro):");
        String sucursal = scan.nextLine();

        // Buscar el producto en la sucursal especificada
        Producto producto = Producto.buscarProductoEnSucursal(nombreProducto, sucursal);

        if (producto == null) {
            System.out.println("No se encontró el producto en la sucursal especificada.");
            return;
        }

        // Mostrar la cantidad actual de productos en la sucursal
        int stockActual = Producto.obtenerStockSegunSucursal(producto, sucursal);
        System.out.println("Cantidad actual de productos en la sucursal " + sucursal + ": " + stockActual);

        // Solicitar al usuario la nueva cantidad de stock
        System.out.println("Ingrese la nueva cantidad de stock:");
        int nuevaCantidad = scan.nextInt();

        // Actualizar el stock según la sucursal especificada
        switch (sucursal.toLowerCase()) {
            case "norte":
                producto.setStockNorte(nuevaCantidad);
                break;
            case "sur":
                producto.setStockSur(nuevaCantidad);
                break;
            case "centro":
                producto.setStockCentro(nuevaCantidad);
                break;
            default:
                System.out.println("Sucursal no válida. No se actualizó el stock.");
                return;
            }

        // Guardar la lista actualizada en el archivo
        List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getNombre().equalsIgnoreCase(nombreProducto)) {
                listaProductos.set(i, producto);
                Producto.guardarProductosEnArchivo(listaProductos);
                System.out.println("Stock actualizado correctamente en la sucursal " + sucursal + ".");
                return;
            }
                    
            System.out.println("Error al actualizar el stock. El producto no se encuentra en la lista.");
        }
    }
    
    
    public void solicitarActualizacionStock(Scanner scan){
        RegistroSolicitud registroSolicitud = new RegistroSolicitud();
        Solicitud nuevaSolicitud = registroSolicitud.crearSolicitud(scan, this.getNumTrabajador(), true);
        
        if(nuevaSolicitud != null){
            System.out.println("Solicitud creada exitosamente:");
            if(verificarStock(nuevaSolicitud)){
                nuevaSolicitud.setEstado(Solicitud.EstadoSolicitud.APROBADA);
            }else{
                nuevaSolicitud.setEstado(Solicitud.EstadoSolicitud.RECHAZADA);
            }
        }else
            System.out.println("No se pudo crear la solicitud de actualización de stock.");

    }
    
   private boolean verificarStock(Solicitud solicitud) {
        // Obtener la cantidad actual de stock
        int stockActual = Producto.obtenerStockSegunSucursal(
                Producto.buscarProductoEnSucursal(solicitud.getNombreProducto(), solicitud.getSucursal()),
                solicitud.getSucursal());

        // Comparar con el límite de stock (50 en este caso)
        return (stockActual+solicitud.getStock()) < 50;
    }


}
