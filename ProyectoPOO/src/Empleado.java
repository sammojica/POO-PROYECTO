
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Empleado extends Persona {

    public String RFC;
    public String numTrabajador;
    public String tipoTrabajador;
    public String Sucursal;

    public Empleado(String RFC, String numTrabajador, String tipoTrabajador, String Sucursal, String nombre, String apellidos, String direccion, String telefono, String correoElectronico, String contraseña) {
        super(nombre, apellidos, direccion, telefono, correoElectronico, contraseña);
        this.RFC = RFC;
        this.numTrabajador = numTrabajador;
        this.tipoTrabajador = tipoTrabajador;
        this.Sucursal = Sucursal;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getNumTrabajador() {
        return numTrabajador;
    }

    public void setNumTrabajador(String numTrabajador) {
        this.numTrabajador = numTrabajador;
    }

    public String getTipoTrabajador() {
        return tipoTrabajador;
    }

    public void setTipoTrabajador(String tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String Sucursal) {
        this.Sucursal = Sucursal;
    }

    public static void menu(Empleado empleado) {
        Scanner scan = new Scanner(System.in);
        int op;

        do {
            System.out.println("\n\nBienvenido empleado.");
            System.out.println("1. Actualizar stock.");
            System.out.println("2. Actualizar información personal.");
            System.out.println("3. Crear orden de resurtido de productos.");
            System.out.println("4. Cerrar sesión.");
            op = scan.nextInt();
            switch (op) {
                case 1:
                    actualizarStock();
                    break;
                case 2:
                    actualizarDatosPersonalesEmpleado(empleado);
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Cerrando sesión...\n\n");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (op != 4);
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        System.out.println("**Inicio de sesión para Empleados**");
        List<Empleado> empleadosRegistrados = RegistroEmpleados.leerEmpleadosDesdeArchivo();
        // Verificar si hay empleados registrados
        if (empleadosRegistrados.isEmpty()) {
            System.out.println("Aún no hay empleados registrados. Regístrese primero.\n\n");
            return;  // Salir del programa si no hay empleados registrados
        }
        boolean inicioSesionExitoso = false;
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
            Empleado empleadoTemp = validarNumeroTrabajadorYContraseña(numeroTrabajadorIngresado, contraseñaIngresada, empleadosRegistrados);

            if (empleadoTemp != null) {
                System.out.println("Inicio de sesión exitoso.");
                inicioSesionExitoso = true;
                menu(empleadoTemp);

            } else {
                System.out.println("Número de trabajador o contraseña incorrectos. Intente de nuevo.");
            }
        }
    }

    public static Empleado validarNumeroTrabajadorYContraseña(String numeroTrabajador, String contraseña, List<Empleado> empleadosRegistrados) {
        for (Empleado empleado : empleadosRegistrados) {
            if (String.valueOf(empleado.getNumTrabajador()).equals(numeroTrabajador) && empleado.getContraseña().equals(contraseña)) {
                // Número de trabajador y contraseña válidos
                return empleado;
            }
        }
        // Si no se encuentra un empleado con la combinación correcta de número de trabajador y contraseña
        return null;
    }
    public static void mostrarEmpleadosRegistrados() {
        
        List<Empleado> empleadosRegistrados = RegistroEmpleados.leerEmpleadosDesdeArchivo();
        if (empleadosRegistrados.isEmpty()) {
            System.out.println("Aún no hay empleados registrados. Regístrese primero.\n\n");
            return;  // Salir del programa si no hay empleados registrados
        }
        System.out.println("Empleados Registrados:\n");
        for (Empleado empleado : empleadosRegistrados) {
            System.out.println("Número de Trabajador: " + empleado.getNumTrabajador());
            System.out.println("Nombre: " + empleado.getNombre() + " " + empleado.getApellidos());
            System.out.println("RFC: " + empleado.getRFC());
            System.out.println("Tipo de Trabajador: " + empleado.getTipoTrabajador());
            System.out.println("Sucursal: " + empleado.getSucursal());
            System.out.println("Dirección: " + empleado.getDireccion());
            System.out.println("Teléfono: " + empleado.getTelefono());
            System.out.println("Correo Electrónico: " + empleado.getCorreoElectronico());
            System.out.println("Contraseña: " + empleado.getContraseña());
            System.out.println("-----------------------------------------------");
        }
    }
    public static void actualizarDatosPersonalesEmpleado(Empleado empleado) {
      Scanner scan = new Scanner(System.in);
      List<Empleado> empleadosRegistrados = RegistroEmpleados.leerEmpleadosDesdeArchivo();
      // Buscar al empleado en la lista
      Empleado empleadoEnLista = buscarEmpleadoPorNumero(empleadosRegistrados, empleado.getNumTrabajador());

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

          RegistroEmpleados.guardarEmpleadosEnArchivo(empleadosRegistrados);
      } else {
          System.out.println("Empleado no encontrado en la lista.");
      }
  }

  private static Empleado buscarEmpleadoPorNumero(List<Empleado> empleados, String numeroTrabajador) {
      for (Empleado empleado : empleados) {
          if (empleado.getNumTrabajador().equals(numeroTrabajador)) {
              return empleado;
          }
      }
      return null;
  }

    public static void actualizarStock() {
        Scanner scan = new Scanner(System.in);
        //if (SolicitudGerenteActualizarStock());{
            // Actualizar el stock según la sucursal especificada
            
            // Solicitar al usuario el nombre del producto y la sucursal
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
            }

            // Mensaje en caso de que el producto no se encuentre en la lista
            System.out.println("Error al actualizar el stock. El producto no se encuentra en la lista.");
        /*}
        else{
            System.out.println("No se encontró ninguna solicitud");
                }*/
    }


    public static void crearOrdenResurtido() {
        Scanner scan = new Scanner(System.in);

        // Solicitar al usuario la sucursal para la orden de resurtido
        System.out.println("Ingrese la sucursal para la orden de resurtido (norte/sur/centro):");
        String sucursal = scan.nextLine();

        // Obtener la lista de productos desde el archivo
        List<Producto> listaProductos = Producto.leerProductosDesdeArchivo();

        // Mostrar la lista de productos disponibles
        System.out.println("Productos Disponibles para Resurtido:");

        for (Producto producto : listaProductos) {
            int stockActual = Producto.obtenerStockSegunSucursal(producto, sucursal);
            System.out.println("Nombre: " + producto.getNombre() + ", Stock en " + sucursal + ": " + stockActual);
        }

        // Solicitar al usuario los productos que desea resurtir y la cantidad
        List<OrdenDeSurtido> listaOrdenes = new ArrayList<>();

        while (true) {
            System.out.println("\nIngrese el nombre del producto para resurtir (o 's' para salir):");
            String nombreProducto = scan.nextLine();

            if (nombreProducto.equalsIgnoreCase("s")) {
                break;
            }

            System.out.println("Ingrese la cantidad a resurtir:");
            int cantidad = Integer.parseInt(scan.nextLine());

            OrdenDeSurtido orden = new OrdenDeSurtido(sucursal, nombreProducto, cantidad);
            listaOrdenes.add(orden);

            System.out.println("Orden de resurtido registrada para " + nombreProducto + " en " + sucursal);
        }

        // Guardar la lista de órdenes de surtido en un archivo
        OrdenDeSurtido.guardarOrdenesSurtidoEnArchivo(listaOrdenes);
    }

}



