import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegistroClientes {

    private static final String ARCHIVO_CLIENTES = "Clientes.txt";
    private static final String DELIMITADOR = ",";

    public static List<Usuario> leerClientesDesdeArchivo() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        try {
            Path archivoPath = Paths.get(ARCHIVO_CLIENTES);

            if (!Files.exists(archivoPath)) {
                Files.createFile(archivoPath);
            }

            try (BufferedReader br = new BufferedReader(new FileReader(archivoPath.toFile()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    Usuario usuario = Usuario.fromString(linea);
                    listaUsuarios.add(usuario);
                }
            }
        } catch (IOException e) {
        }

        return listaUsuarios;
    }

    public static void guardarClientesEnArchivo(List<Usuario> listaUsuarios) {
        try {
            List<Usuario> usuariosExistente = leerClientesDesdeArchivo();
            usuariosExistente.addAll(listaUsuarios);

            // Eliminar duplicados por nombre de usuario
            Map<String, Usuario> usuarioMap = new LinkedHashMap<>();
            for (Usuario usuario : usuariosExistente) {
                usuarioMap.put(usuario.getNombreUsuario(), usuario);
            }

            // Construir la lista sin duplicados
            List<Usuario> listaSinDuplicados = new ArrayList<>(usuarioMap.values());

            List<String> lines = new ArrayList<>();
            for (Usuario usuario : listaSinDuplicados) {
                lines.add(usuario.toString());
            }

            Files.write(Paths.get(ARCHIVO_CLIENTES), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registrarCliente(Usuario usuario) {
        List<Usuario> listaClientes = new ArrayList<>(leerClientesDesdeArchivo());

        Map<Integer, Usuario> linkedHashMap = new LinkedHashMap<>();
        for (Usuario u : listaClientes) {
            linkedHashMap.put(u.getCodigoPostal(), u);
        }
        linkedHashMap.put(usuario.getCodigoPostal(), usuario);

        listaClientes = new ArrayList<>(linkedHashMap.values());

        guardarClientesEnArchivo(listaClientes);
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese un nombre de usuario (o 's' para salir):");
        String usuariotemp = scan.nextLine();
        if (usuariotemp.equalsIgnoreCase("s")) {
            System.out.println("Saliendo del registro...");
            return; // Salir del programa o realizar alguna acción de salida
        }

        List<Usuario> usuariosRegistrados = leerClientesDesdeArchivo();
        boolean nombreUsuarioExistente = false;

        do {
            for (Usuario usuario : usuariosRegistrados) {
                if (usuario.getNombreUsuario().equals(usuariotemp)) {
                    // El nombre de usuario ya existe
                    nombreUsuarioExistente = true;
                    break;
                }
            }

            if (nombreUsuarioExistente) {
                System.out.println("El nombre de usuario ya existe. ¿Desea intentar con otro? (s/n)");
                String respuesta = scan.nextLine().toLowerCase();

                if (respuesta.equals("s")) {
                    System.out.println("Ingrese un nuevo nombre de usuario:");
                    usuariotemp = scan.nextLine();
                    nombreUsuarioExistente = false;
                } else if (respuesta.equals("n")) {
                    System.out.println("Saliendo del registro...");
                    return; // Salir del programa o realizar alguna acción de salida
                } else {
                    System.out.println("Opción no válida. Por favor, responda con 's' o 'n'.");
                }
            }
        } while (nombreUsuarioExistente);

        // Continuar con el registro del nuevo usuario
        System.out.println("Nombre de usuario válido. Puede continuar con el registro.");

        // Nueva solicitud para la sucursal
        String sucursal;
        do {
            System.out.println("Ingrese la sucursal en la que comprará ('norte', 'sur' o 'centro'):");
            sucursal = scan.nextLine().toLowerCase();
            // Validar que la sucursal ingresada sea válida
            if (!sucursal.equals("norte") && !sucursal.equals("sur") && !sucursal.equals("centro")) {
                System.out.println("Sucursal no válida. Intente de nuevo.");
            }
        } while (!sucursal.equals("norte") && !sucursal.equals("sur") && !sucursal.equals("centro"));

        // Resto del código
        System.out.println("Ingrese una contraseña:");
        String contraseña = scan.nextLine();

        if (contraseña.length() < 8) {
            System.out.println("La contraseña debe tener al menos 8 caracteres. Intente de nuevo.");

            // Bucle para pedir una nueva contraseña hasta que sea válida
            while (contraseña.length() < 8) {
                System.out.println("Ingrese una contraseña válida (al menos 8 caracteres):");
                contraseña = scan.nextLine();
            }
        }

        System.out.println("Ingrese su nombre:");
        String nombre = scan.nextLine();
        System.out.println("Ingrese su apellido:");
        String apellido = scan.nextLine();
        System.out.println("Ingrese su correo electrónico:");
        String correoE = scan.nextLine();
        System.out.println("Ingrese su dirección:");
        String direccion = scan.nextLine();
        System.out.println("Ingrese su código postal:");
        int codigoP = scan.nextInt();
        scan.nextLine(); // Consumir la nueva línea pendiente después del nextInt
        System.out.println("Ingrese su número de teléfono:");
        String numero = scan.nextLine();

        Usuario nuevoUsuario1 = new Usuario(codigoP, usuariotemp, 0, 0, sucursal, null, nombre, apellido, direccion, numero, correoE, contraseña);
        registrarCliente(nuevoUsuario1);
    }

    public static void actualizarDatosPersonales(String nombreUsuario) {
        List<Usuario> listaClientes = new ArrayList<>(leerClientesDesdeArchivo());

        boolean usuarioEncontrado = false;
        for (Usuario usuario : listaClientes) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                // Mostrar los datos actuales del usuario
                System.out.println("\n\nDatos actuales del usuario:");
                System.out.println("Nombre: " + usuario.getNombre());
                System.out.println("Apellidos: " + usuario.getApellidos());
                System.out.println("Dirección: " + usuario.getDireccion());
                System.out.println("Correo Electrónico: " + usuario.getCorreoElectronico());
                System.out.println("Teléfono: " + usuario.getTelefono());
                System.out.println("Código postal: " + usuario.getCodigoPostal());

                // Solicitar al usuario qué campo desea actualizar
                Scanner scan = new Scanner(System.in);
                System.out.println("Seleccione el campo que desea actualizar:");
                System.out.println("1. Nombre");
                System.out.println("2. Apellido");
                System.out.println("3. Dirección");
                System.out.println("4. Correo Electrónico");
                System.out.println("5. Número de Teléfono");
                System.out.println("6. Código postal:");
                System.out.println("0. Cancelar");

                int opcion = scan.nextInt();
                scan.nextLine(); // Consumir la nueva línea pendiente después del nextInt

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el nuevo nombre:");
                        String nuevoNombre = scan.nextLine();
                        usuario.setNombre(nuevoNombre);
                        break;
                    case 2:
                        System.out.println("Ingrese el nuevo apellido:");
                        String nuevoApellido = scan.nextLine();
                        usuario.setApellidos(nuevoApellido);
                        break;
                    case 3:
                        System.out.println("Ingrese la nueva dirección:");
                        String nuevaDireccion = scan.nextLine();
                        usuario.setDireccion(nuevaDireccion);
                        break;
                    case 4:
                        System.out.println("Ingrese el nuevo correo electrónico:");
                        String nuevoCorreo = scan.nextLine();
                        usuario.setCorreoElectronico(nuevoCorreo);
                        break;
                    case 5:
                        System.out.println("Ingrese el nuevo número de teléfono:");
                        String nuevoNumero = scan.nextLine();
                        usuario.setTelefono(nuevoNumero);
                        break;
                    case 6:
                        System.out.println("Ingrese el nuevo código postal");
                        int nuevoCP = scan.nextInt();
                        usuario.setCodigoPostal(nuevoCP);
                        break;
                    case 0:
                        System.out.println("Operación cancelada. No se realizaron cambios.");
                        return;
                    default:
                        System.out.println("Opción no válida. No se realizaron cambios.");
                        return;
                }

                System.out.println("Datos actualizados correctamente.");
                usuarioEncontrado = true;
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("Usuario no encontrado. No se pueden actualizar los datos.");
        } else {
            // Actualizar el archivo con los nuevos datos
            guardarClientesEnArchivo(listaClientes);
        }
    }
}
