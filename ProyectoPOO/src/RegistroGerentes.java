import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegistroGerentes {

    private static final String ARCHIVO_GERENTES = "Gerentes.txt";
    private static final String DELIMITADOR = ",";

public static List<Gerente> leerGerentesDesdeArchivo() {
    List<Gerente> listaGerentes = new ArrayList<>();

    Path archivoPath = Paths.get(ARCHIVO_GERENTES);

    if (!Files.exists(archivoPath)) {
        try {
            Files.createFile(archivoPath);
        } catch (IOException e) {
            
        }
    }
    try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_GERENTES))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.startsWith("Gerente")) {
                continue;  // Ignorar esta línea y pasar a la siguiente
            }

            String[] partes = linea.split(DELIMITADOR);
            
            if (partes.length == 10) {
                Gerente gerente = new Gerente(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6], partes[7], partes[8], partes[9]);
                listaGerentes.add(gerente);
            } else {
                System.out.println("Error en el formato de la línea: " + linea);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return listaGerentes;
}

public static void guardarGerentesEnArchivo(List<Gerente> listaGerentes) {
    try (PrintWriter writer = new PrintWriter(ARCHIVO_GERENTES)) {
        for (Gerente gerente : listaGerentes) {
            writer.println(String.format("%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s" + DELIMITADOR + "%s",
                    gerente.getRFC(), gerente.getNumTrabajador(), gerente.getTipoTrabajador(),
                    gerente.getSucursal(), gerente.getNombre(), gerente.getApellidos(),
                    gerente.getDireccion(), gerente.getTelefono(), gerente.getCorreoElectronico(), gerente.getContraseña()));
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}


    public static void registrarGerente(Gerente gerente) {
        List<Gerente> listaGerentes = new ArrayList<>(leerGerentesDesdeArchivo());

        Map<String, Gerente> linkedHashMap = new LinkedHashMap<>();
        for (Gerente g : listaGerentes) {
            linkedHashMap.put(g.getNumTrabajador(), g);
        }
        linkedHashMap.put(gerente.getNumTrabajador(), gerente);

        listaGerentes = new ArrayList<>(linkedHashMap.values());

        guardarGerentesEnArchivo(listaGerentes);
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese el número de trabajador (o 's' para salir):");
        String numTrabajadorTemp = scan.nextLine();
        if (numTrabajadorTemp.equalsIgnoreCase("s")) {
            System.out.println("Saliendo del registro...");
            return;
        }
        List<Gerente> gerentesRegistrados = leerGerentesDesdeArchivo();
        boolean numTrabajadorExistente = false;
        do {
            for (Gerente gerente : gerentesRegistrados) {
                if (gerente.getNumTrabajador().trim().equalsIgnoreCase(numTrabajadorTemp.trim())) {
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

        Gerente nuevoGerente = new Gerente(rfc, numTrabajadorTemp, tipoTrabajador, sucursal, nombre, apellido, direccion, telefono, correoE, contraseña);
        registrarGerente(nuevoGerente);
    }
}