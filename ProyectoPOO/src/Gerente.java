
import java.util.ArrayList;
import java.util.InputMismatchException;
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
        int op=0; Banco banco = Banco.cargarEstado();

        do {
            try{
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
                        gerente.solicitarActualizacionStock(scan, true, null);
                        break;
                    case 6:
                        gerente.aprobarSolicitud();
                        break;
                    case 7:
                        gerente.solicitarRetiroDepositoEfectivo(scan, banco);
                        break;
                    case 8:
                        Gerente.menuReporteVentas(gerente);
                        break;
                    case 9: 
                        crearOrdenResurtido();
                        break;
                    case 10:
                        gerente.actualizarDatosPersonales(gerente);
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
            }catch (InputMismatchException e) {
                System.out.println("\nOpción inválida. Intente de nuevo.");
                scan.nextLine(); 
            }   
        } while (op != 11);
        
        banco.guardarEstado();
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        boolean inicioSesionExitoso = false;
        System.out.println("\n**Inicio de sesión para Gerentes**");
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
            
            Scanner scanner = new Scanner(System.in); 
            for(Solicitud solicitud : solicitudesPendientes) {
                System.out.println("Solicitudes Pendientes:");
                for (int i = 0; i < solicitudesPendientes.size(); i++) {
                    System.out.println((i+1) + ". "+solicitud.getNombreProducto()+"");
                }
                boolean validar = true;
                while(validar){
                    System.out.println("\n¿Aprobar la solicitud para " + solicitud.getNombreProducto() + "\n en tienda " + solicitud.getSucursal()+ " por el empleado "+ solicitud.getIdEmpleado()+" o dejarla pendiente? (Sí/No): ");
                    String decision = scanner.nextLine().toLowerCase();

                    if (decision.equals("si")) {
                        solicitud.setEstado2(Solicitud.EstadoSolicitud.APROBADA);
                        solicitud.setIdGerente(this.getNumTrabajador());
                        System.out.println("Solicitud aprobada.");
                    } else if(decision.equals("no")) {
                        solicitud.setEstado2(Solicitud.EstadoSolicitud.RECHAZADA);
                        solicitud.setIdGerente(this.getNumTrabajador());
                        System.out.println("Solicitud rechazada.");
                    }else{
                        System.out.println("Opción no válida. La solicitud seguirá pendiente.");
                        continue;
                    }
                    System.out.println("\nEsta segur@ de su decision? (Sí/No): ");
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
        
        Solicitud solicitud = solicitarActualizacionStock(scan, true, null);
        if(solicitud != null){
            if(solicitud.getEstado1() == Solicitud.EstadoSolicitud.APROBADA){
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

                    //System.out.println("\nError al actualizar el stock. El producto no se encuentra en la lista.");
                }
            }else{
                System.out.println("\nLa solicitud no fue aprobada, ya existe stock suficiente.");
            }
        }else{
            System.out.println("\nNo se pudo realizar la actualizacion de stock.");
        }
    }
    
    //moco true: la pide el gerente, modo false: el empleado desde actualizar stock
    public Solicitud solicitarActualizacionStock(Scanner scan, boolean modo, Solicitud solicitudEmp) {
        RegistroSolicitud registroSolicitud = new RegistroSolicitud();
        if(modo){
            scan.nextLine();
            Solicitud nuevaSolicitud = registroSolicitud.crearSolicitud(scan, this.getNumTrabajador(), true);
            List<Solicitud> solicitudesPendientes = RegistroSolicitud.leerSolicitudesDesdeArchivo();

            if (nuevaSolicitud != null) {
                System.out.println("Solicitud creada exitosamente:");
                if (verificarStock(nuevaSolicitud)) {
                    nuevaSolicitud.setEstado1(Solicitud.EstadoSolicitud.APROBADA);
                    nuevaSolicitud.setEstado2(Solicitud.EstadoSolicitud.APROBADA);
                    System.out.println("Solicitud aprobada.");
                } else {
                    nuevaSolicitud.setEstado1(Solicitud.EstadoSolicitud.RECHAZADA);
                    nuevaSolicitud.setEstado2(Solicitud.EstadoSolicitud.PENDIENTE);
                    System.out.println("Solicitud rechazada.");
                }

                // actualizar solicitud especifica
                for (int i = 0; i < solicitudesPendientes.size(); i++) {
                    Solicitud solicitud = solicitudesPendientes.get(i);
                    if (solicitud.getNombreProducto().equalsIgnoreCase(nuevaSolicitud.getNombreProducto())
                            && solicitud.getSucursal().equalsIgnoreCase(nuevaSolicitud.getSucursal())
                            && solicitud.getStock() == nuevaSolicitud.getStock()) {
                        solicitudesPendientes.set(i, nuevaSolicitud);
                        break;
                    }
                }

                RegistroSolicitud.guardarSolicitudesEnArchivo(solicitudesPendientes);
                return nuevaSolicitud;
            } else {
                System.out.println("No se pudo crear la solicitud de actualización de stock.");
                return null;
            }
        }else{
            List<Solicitud> solicitudesPendientes = RegistroSolicitud.leerSolicitudesDesdeArchivo();
            solicitudEmp.getNombreProducto();
            
            if (verificarStock(solicitudEmp)) {
                solicitudEmp.setEstado1(Solicitud.EstadoSolicitud.APROBADA);
                System.out.println("Solicitud aprobada.");
            } else {
                solicitudEmp.setEstado1(Solicitud.EstadoSolicitud.RECHAZADA);
                System.out.println("Solicitud rechazada.");
            }
            
            for (int i = 0; i < solicitudesPendientes.size(); i++) {
                Solicitud solicitud = solicitudesPendientes.get(i);
                if (solicitud.getNombreProducto().equalsIgnoreCase(solicitudEmp.getNombreProducto())
                        && solicitud.getSucursal().equalsIgnoreCase(solicitudEmp.getSucursal())
                        && solicitud.getStock() == solicitudEmp.getStock()) {
                    solicitudesPendientes.set(i, solicitudEmp);
                    break;
                }
            }

            RegistroSolicitud.guardarSolicitudesEnArchivo(solicitudesPendientes);
            return solicitudEmp;

            }
    }
    
    private boolean verificarStock(Solicitud solicitud) {
        // Obtener la cantidad actual de stock
        int stockActual = Producto.obtenerStockSegunSucursal(
                Producto.buscarProductoEnSucursal(solicitud.getNombreProducto(), solicitud.getSucursal()),
                solicitud.getSucursal());

        // Comparar con el límite de stock (50 en este caso)
        return (stockActual+solicitud.getStock()) < 100;
    }
   
   
    public void solicitarRetiroDepositoEfectivo(Scanner scan, Banco banco){
        System.out.println("\n¿Que tipo de transaccion desea realizar?\n1. Deposito.\n2. Retiro.\n3. Salir sin realizar transaccion.");
        int opc = scan.nextInt();
        System.out.println(banco.getPresupuestos());
        try{
            switch(opc){
                case 1:
                    System.out.println("\nDigite la cantidad a depositar: ");
                    double monto = scan.nextDouble();
                    System.out.println("\nDigite la sucursal a la que se va a depositar: ");
                    scan.nextLine();
                    String sucursal = scan.nextLine().toLowerCase();
                    banco.depositarDinero(sucursal, monto, this.getNumTrabajador());
                    System.out.println(banco.getPresupuestos());
                    break;
                case 2:
                    System.out.println("\nDigite la cantidad a retirar: ");
                    double monto2 = scan.nextDouble();
                    System.out.println("\nDigite la sucursal a la que se va a depositar: ");
                    scan.nextLine();
                    String sucursal2 = scan.nextLine();
                    banco.retirarDinero(sucursal2, monto2, this.getNumTrabajador());
                    System.out.println(banco.getPresupuestos());
                    break;
                case 3:
                    break;
                default:    
                    System.out.println("Opcion no valida.");
            }
        }catch(InputMismatchException e){
            e.printStackTrace();
        }
    }
    
    public static void menuReporteVentas(Gerente gerente) {
        Scanner scan = new Scanner(System.in);
        int op;

        do {
            System.out.println("\n\nReporte de Ventas");
            System.out.println("1. Por Tienda");
            System.out.println("2. Por Producto");
            System.out.println("3. General (Todas las Ventas)");
            System.out.println("4. Ver depósitos/Retiros.");
            System.out.println("5. Volver al Menú Principal");
            System.out.print(" > ");
            op = scan.nextInt();

            switch (op) {
                case 1:
                    // Lógica para mostrar el informe de ventas por tienda
                    System.out.println("Mostrando informe por tienda...");
                    gerente.mostrarInformePorTiendas();
                    break;
                case 2:
                    // Lógica para mostrar el informe de ventas por producto
                    System.out.println("Mostrando informe por producto...");
                    gerente.mostrarInformePorProductos();
                    break;
                case 3:
                    // Lógica para mostrar el informe de ventas general
                    System.out.println("Mostrando informe general...");
                    gerente.mostrarInformeGeneral();
                    break;
                case 4:
                    System.out.println("Mostrando historial de transacciones...");
                    Banco.imprimirMovimientos();
                    break;
                case 5:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("\nOpción inválida. Intente de nuevo.");
                    break;
            }

        } while (op != 4);
    }
    
    public static void mostrarInformeGeneral() {
        List<String> informe = GeneradorDeInformes.leerInformeGeneral();
        for (String linea : informe) {
            System.out.println(linea);
        }
    }
    
    public static void mostrarInformePorProductos() {
        List<String> informe = GeneradorDeInformes.leerInformePorProductos();
        for (String linea : informe) {
            System.out.println(linea);
        }
    }

    
    public static void mostrarInformePorTiendas() {
        List<String> informe = GeneradorDeInformes.leerInformePorTiendas();
        for (String linea : informe) {
            System.out.println(linea);
        }
    } 
    

    public static String obtenerNombreYApellidoPorID(String idGerente){
        RegistroGerentes registroGerente = new RegistroGerentes();
        List<Gerente> listaGerentes = new ArrayList<>(registroGerente.leerGerentesDesdeArchivo());
        for(Gerente gerente : listaGerentes){
            if (gerente.getNumTrabajador().equals(idGerente)) {
                return gerente.getNombre() + " " + gerente.getApellidos();
            }
        }

        return "No se encontró el empleado con ID " + idGerente;
    }
    
    public static Empleado buscarGerentePorNumero(List<Gerente> gerentes, String numeroTrabajador) {
        for (Gerente gerente : gerentes) {
            if (gerente.getNumTrabajador().equals(numeroTrabajador)) {
                return gerente;
            }
        }
        return null;
    }
    
    
    public void actualizarDatosPersonales(Gerente gerente) {
        Scanner scan = new Scanner(System.in);
        List<Gerente> gerentesRegistrados = RegistroGerentes.leerGerentesDesdeArchivo();
        // Buscar al empleado en la lista
        Empleado empleadoEnLista = buscarGerentePorNumero(gerentesRegistrados, gerente.getNumTrabajador());

        if (empleadoEnLista != null) {
            // Mostrar los datos actuales del empleado
            System.out.println("-----DATOS ACTUALES-----\n");
            System.out.println("RFC: " + empleadoEnLista.getRFC());
            System.out.println("Número de trabajador: " + empleadoEnLista.getNumTrabajador());
            System.out.println("Nombre: " + empleadoEnLista.getNombre());
            System.out.println("Apellido: " + empleadoEnLista.getApellidos());
            System.out.println("Correo electrónico: " + empleadoEnLista.getCorreoElectronico());
            System.out.println("Dirección: " + empleadoEnLista.getDireccion());
            System.out.println("Teléfono: " + empleadoEnLista.getTelefono());
            System.out.println("Tipo de trabajador: " + empleadoEnLista.getTipoTrabajador());
            System.out.println("Sucursal: " + empleadoEnLista.getSucursal());

            // Pedir al empleado que elija qué campo desea actualizar
            System.out.println("Seleccione el campo que desea actualizar (1-Nombre, 2-Apellido, 3-Correo, 4-Dirección, 5-Teléfono, 6-RFC, 7-Tipo de Trabajador, 8-Sucursal, 0-Salir):");
            int opcion = scan.nextInt();
            scan.nextLine();  // Consumir la nueva línea pendiente después del nextInt
            System.out.println("---ACTUALIZACIÓN DE DATOS---");
        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nuevo nombre:");
                empleadoEnLista.setNombre(scan.nextLine());
                break;
            case 2:
                System.out.println("Ingrese el nuevo apellido:");
                empleadoEnLista.setApellidos(scan.nextLine());
                break;
            case 3:
                System.out.println("Ingrese el nuevo correo electrónico:");
                empleadoEnLista.setCorreoElectronico(scan.nextLine());
                break;
            case 4:
                System.out.println("Ingrese la nueva dirección:");
                empleadoEnLista.setDireccion(scan.nextLine());
                break;
            case 5:
                System.out.println("Ingrese el nuevo número de teléfono:");
                empleadoEnLista.setTelefono(scan.nextLine());
                break;
            case 6:
                System.out.println("Ingrese el nuevo RFC:");
                empleadoEnLista.setRFC(scan.nextLine());
                break;
            case 7:
                System.out.println("Ingrese el nuevo tipo de trabajador:");
                empleadoEnLista.setTipoTrabajador(scan.nextLine());
                break;
            case 8:
                System.out.println("Ingrese la nueva sucursal:");
                empleadoEnLista.setSucursal(scan.nextLine());
                break;
            case 0:
                System.out.println("Saliendo de la actualización de datos...");
                break;
            default:
                System.out.println("Opción no válida.");
        }

        RegistroGerentes.guardarGerentesEnArchivo(gerentesRegistrados);
        } else {
            System.out.println("Empleado no encontrado en la lista.");
        }
    }


}
