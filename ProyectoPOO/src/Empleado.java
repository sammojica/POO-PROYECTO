
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

    public static void menu() {
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

                    break;
                case 2:

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
            if (validarNumeroTrabajadorYContraseña(numeroTrabajadorIngresado, contraseñaIngresada, empleadosRegistrados)) {
                System.out.println("Inicio de sesión exitoso.");
                inicioSesionExitoso = true;
                menu();
            } else {
                System.out.println("Número de trabajador o contraseña incorrectos. Intente de nuevo.");
            }
        }
    }

    public static boolean validarNumeroTrabajadorYContraseña(String numeroTrabajador, String contraseña, List<Empleado> empleadosRegistrados) {
        for (Empleado empleado : empleadosRegistrados) {
            if (String.valueOf(empleado.getNumTrabajador()).equals(numeroTrabajador) && empleado.getContraseña().equals(contraseña)) {
                // Número de trabajador y contraseña válidos
                return true;
            }
        }
        // Si no se encuentra un empleado con la combinación correcta de número de trabajador y contraseña
        return false;
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
    


}
