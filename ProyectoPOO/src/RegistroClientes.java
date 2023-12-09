
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistroClientes {

    private static final String ARCHIVO_CLIENTES = "Clientes.txt";

    // Método para leer la lista de usuarios desde el archivo
    public static List<Usuario> leerUsuariosDesdeArchivo() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Verificar si la línea comienza con "Cliente"
                if (linea.startsWith("Cliente")) {
                    continue;  // Ignorar esta línea y pasar a la siguiente
                }

                // Dividir la línea en partes según algún delimitador (por ejemplo, coma)
                String[] partes = linea.split(",");
                // Crear un nuevo usuario y agregarlo a la lista
                Usuario usuario = new Usuario(Integer.parseInt(partes[0]), partes[1], Double.parseDouble(partes[2]), Integer.parseInt(partes[3]),
                        partes[4], new Carrito(), new ArrayList<>(), partes[5], partes[6], partes[7], partes[8], partes[9], partes[10]);
                listaUsuarios.add(usuario);
            }
        } catch (FileNotFoundException e) {
            // El archivo no existe, puedes crearlo si es necesario

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaUsuarios;
    }

    public static void guardarUsuariosEnArchivo(List<Usuario> listaUsuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CLIENTES, true))) {
            for (Usuario usuario : listaUsuarios) {
                // Formatear el usuario como una línea y escribirlo en el archivo sin el prefijo "Cliente"
                bw.write(String.format("%d,%s,%f,%d,%s,%s,%s,%s,%s,%s%n",
                        usuario.getCodigoPostal(), usuario.getNombreUsuario(), usuario.getPuntos(), usuario.getNivel(),
                        usuario.getNombre(), usuario.getApellidos(), usuario.getDireccion(), usuario.getTelefono(),
                        usuario.getCorreoElectronico(), usuario.getContraseña()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Método para registrar un nuevo usuario
    public static void registrarUsuario(Usuario usuario) {
        List<Usuario> listaUsuarios = leerUsuariosDesdeArchivo();

        // Agregar el nuevo usuario a la lista
        listaUsuarios.add(usuario);

        // Guardar la lista actualizada en el archivo
        guardarUsuariosEnArchivo(listaUsuarios);
    }

    public static void main() {
    Scanner scan = new Scanner(System.in);
    System.out.println("Ingrese un nombre de usuario (o 's' para salir):");
    String usuariotemp = scan.nextLine();
    if (usuariotemp.equalsIgnoreCase("s")) {
        System.out.println("Saliendo del registro...");
        return; // Salir del programa o realizar alguna acción de salida
    }
    List<Usuario> usuariosRegistrados = leerUsuariosDesdeArchivo();
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
    System.out.println("Ingrese la sucursal en la que comprará('norte', 'sur' o 'centro'):");
    String sucursal = scan.nextLine().toLowerCase();
    // Validar que la sucursal ingresada sea válida
    if (!sucursal.equals("norte") && !sucursal.equals("sur") && !sucursal.equals("centro")) {
        System.out.println("Sucursal no válida. Saliendo del registro...");
        return; // Salir del programa o realizar alguna acción de salida
    }
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

        Usuario nuevoUsuario1 = new Usuario(codigoP, usuariotemp, 0, 0, sucursal, null, null, nombre, apellido, direccion, numero, correoE, contraseña);
        registrarUsuario(nuevoUsuario1);
    }
    
    public static void actualizarDatosPersonales(Usuario usuario) {
    Scanner scan = new Scanner(System.in);

    // Mostrar los datos actuales del usuario
    System.out.println("Datos actuales:");
    System.out.println("Nombre de usuario: " + usuario.getNombreUsuario());
    System.out.println("Nombre: " + usuario.getNombre());
    System.out.println("Apellido: " + usuario.getApellidos());
    System.out.println("Correo electrónico: " + usuario.getCorreoElectronico());
    System.out.println("Dirección: " + usuario.getDireccion());
    System.out.println("Código postal: " + usuario.getCodigoPostal());
    System.out.println("Número de teléfono: " + usuario.getTelefono());

    // Pedir al usuario que elija qué campo desea actualizar
    System.out.println("Seleccione el campo que desea actualizar (1-Nombre, 2-Apellido, 3-Correo, 4-Dirección, 5-Código Postal, 6-Teléfono, 0-Salir):");
    int opcion = scan.nextInt();
    scan.nextLine();  // Consumir la nueva línea pendiente después del nextInt

    switch (opcion) {
        case 1:
            System.out.println("Ingrese el nuevo nombre:");
            usuario.setNombre(scan.nextLine());
            break;
        case 2:
            System.out.println("Ingrese el nuevo apellido:");
            usuario.setApellidos(scan.nextLine());
            break;
        case 3:
            System.out.println("Ingrese el nuevo correo electrónico:");
            usuario.setCorreoElectronico(scan.nextLine());
            break;
        case 4:
            System.out.println("Ingrese la nueva dirección:");
            usuario.setDireccion(scan.nextLine());
            break;
        case 5:
            System.out.println("Ingrese el nuevo código postal:");
            usuario.setCodigoPostal(scan.nextInt());
            scan.nextLine();  // Consumir la nueva línea pendiente después del nextInt
            break;
        case 6:
            System.out.println("Ingrese el nuevo número de teléfono:");
            usuario.setTelefono(scan.nextLine());
            break;
        case 0:
            System.out.println("Saliendo de la actualización de datos...");
            break;
        default:
            System.out.println("Opción no válida.");
    }

    // Guardar la lista actualizada en el archivo
    guardarUsuariosEnArchivo(leerUsuariosDesdeArchivo());
}

    
}
