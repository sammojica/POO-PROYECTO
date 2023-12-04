
import java.util.List;
import java.util.Scanner;

public class Gerente extends Empleado {

    public Gerente(String RFC, String numTrabajador, String tipoTrabajador, String Sucursal, String nombre, String apellidos, String direccion, String telefono, String correoElectronico, String contraseña) {
        super(RFC, numTrabajador, tipoTrabajador, Sucursal, nombre, apellidos, direccion, telefono, correoElectronico, contraseña);
    }

    public static void menu() {
        Scanner scan = new Scanner(System.in);
        int op;

        do {
            System.out.println("\n\nBienvenido gerente");
            System.out.println("1. Ver empleados.");
            System.out.println("2. Agregar empleados.");
            System.out.println("3. Agregar producto.");
            System.out.println("4. Solicitud de actualización de Stock a una tienda.");
            System.out.println("5. Autorizar solicitud de actualización de Stock de un empleado.");
            System.out.println("6. Solicitar retiro/depósito de efectivo a una tienda.");
            System.out.println("7. Reporte de Ventas.");
            System.out.println("8. Cerrar sesión.");
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
                    break;
                case 5:
                    break;
                case 8:
                    System.out.println("Cerrando sesión...\n\n");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (op != 8);
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
            menu();
        } else {
            System.out.println("Número de trabajador o contraseña incorrectos. Intente de nuevo.");
        }
    }
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


}
