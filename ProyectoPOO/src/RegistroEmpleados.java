
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegistroEmpleados {

    private static final String ARCHIVO_EMPLEADOS = "Empleados.txt";
    private static final String DELIMITADOR = ",";

    public static List<Empleado> leerEmpleadosDesdeArchivo() {
        List<Empleado> listaEmpleados = new ArrayList<>();
        Path archivoPath = Paths.get(ARCHIVO_EMPLEADOS);

        if (!Files.exists(archivoPath)) {
            try {
                Files.createFile(archivoPath);
            } catch (IOException e) {

            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_EMPLEADOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Empleado")) {
                    continue;  // Ignorar esta línea y pasar a la siguiente
                }

                String[] partes = linea.split(DELIMITADOR);

                if (partes.length == 10) {
                    Empleado empleado = new Empleado(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6], partes[7], partes[8], partes[9]);
                    listaEmpleados.add(empleado);
                } else {
                    System.out.println("Error en el formato de la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaEmpleados;
    }

    public static void guardarEmpleadosEnArchivo(List<Empleado> listaEmpleados) {
        try (PrintWriter writer = new PrintWriter(ARCHIVO_EMPLEADOS)) {
            for (Empleado empleado : listaEmpleados) {
                writer.println(String.format("%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s",
                        empleado.getRFC(), empleado.getNumTrabajador(), empleado.getTipoTrabajador(),
                        empleado.getSucursal(), empleado.getNombre(), empleado.getApellidos(),
                        empleado.getDireccion(), empleado.getTelefono(), empleado.getCorreoElectronico(), empleado.getContraseña()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void registrarEmpleado(Empleado empleado) {
        List<Empleado> listaEmpleados = new ArrayList<>(leerEmpleadosDesdeArchivo());

        Map<String, Empleado> linkedHashMap = new LinkedHashMap<>();
        for (Empleado e : listaEmpleados) {
            linkedHashMap.put(e.getNumTrabajador(), e);
        }
        linkedHashMap.put(empleado.getNumTrabajador(), empleado);

        listaEmpleados = new ArrayList<>(linkedHashMap.values());

        guardarEmpleadosEnArchivo(listaEmpleados);
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese el número de trabajador (o 's' para salir):");
        String numTrabajadorTemp = scan.nextLine();

        if (numTrabajadorTemp.equalsIgnoreCase("s")) {
            System.out.println("Saliendo del registro...");
            return;
        }

        List<Empleado> empleadosRegistrados = leerEmpleadosDesdeArchivo();
        boolean numTrabajadorExistente = false;

        do {
            for (Empleado empleado : empleadosRegistrados) {
                if (String.valueOf(empleado.getNumTrabajador()).equals(numTrabajadorTemp)) {
                    numTrabajadorExistente = true;
                    break;
                }
            }

            if (numTrabajadorExistente) {
                System.out.println("El número de trabajador ya existe. ¿Desea intentar con otro? (s/n)");
                String respuesta = scan.nextLine().toLowerCase();

                if (respuesta.equals("s")) {
                    System.out.println("Ingrese un nuevo número de trabajador:");
                    numTrabajadorTemp = scan.nextLine();
                    numTrabajadorExistente = false;
                } else if (respuesta.equals("n")) {
                    System.out.println("Saliendo del registro...");
                    return;
                } else {
                    System.out.println("Opción no válida. Por favor, responda con 's' o 'n'.");
                }
            }
        } while (numTrabajadorExistente);

        System.out.println("Número de trabajador válido. Puede continuar con el registro.");
        System.out.println("Ingrese una contraseña:");
        String contraseña = scan.nextLine();
        System.out.println("Ingrese su nombre:");
        String nombre = scan.nextLine();
        System.out.println("Ingrese su apellido:");
        String apellido = scan.nextLine();
        System.out.println("Ingrese su correo electrónico:");
        String correoE = scan.nextLine();
        System.out.println("Ingrese su dirección:");
        String direccion = scan.nextLine();
        System.out.println("Ingrese su teléfono:");
        String telefono = scan.nextLine();
        System.out.println("Ingrese su RFC:");
        String rfc = scan.nextLine();
        System.out.println("Ingrese su tipo de trabajador:");
        String tipoTrabajador = scan.nextLine();
        System.out.println("Ingrese su sucursal:");
        String sucursal = scan.nextLine();

        Empleado nuevoEmpleado = new Empleado(rfc, numTrabajadorTemp, tipoTrabajador, sucursal, nombre, apellido, direccion, telefono, correoE, contraseña);
        registrarEmpleado(nuevoEmpleado);
    }
}