import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Usuario extends Persona {
    public int codigoPostal;
    public String nombreUsuario;
    public double puntos;
    public int nivel;
    public String Sucursal;

    public Carrito carrito;
    public List<Compra> historialCompras;

    public Usuario(int codigoPostal, String nombreUsuario, double puntos, int nivel, String Sucursal, Carrito carrito, List<Compra> historialCompras, String nombre, String apellidos, String direccion, String telefono, String correoElectronico, String contraseña) {
        super(nombre, apellidos, direccion, telefono, correoElectronico, contraseña);
        this.codigoPostal = codigoPostal;
        this.nombreUsuario = nombreUsuario;
        this.puntos = puntos;
        this.nivel = nivel;
        this.Sucursal = Sucursal;
        this.carrito = carrito;
        this.historialCompras = historialCompras;
    }
    
    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public double getPuntos() {
        return puntos;
    }

    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String Sucursal) {
        this.Sucursal = Sucursal;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public List<Compra> getHistorialCompras() {
        return historialCompras;
    }

    public void agregarCompraAlHistorial(Compra compra) {
        historialCompras.add(compra);
    }
    
  
    public static void main() {
        Scanner scan = new Scanner(System.in);

        System.out.println("**Inicio de sesión**");
        List<Usuario> usuariosRegistrados = RegistroClientes.leerUsuariosDesdeArchivo();
        // Verificar si hay usuarios registrados
        if (usuariosRegistrados.isEmpty()) {
            System.out.println("Aún no hay usuarios registrados. Regístrese primero.");
            return;  // Salir del programa si no hay usuarios registrados
        }

        boolean inicioSesionExitoso = false;

        while (!inicioSesionExitoso) {
            System.out.println("Ingrese su nombre de usuario (o 's' para salir):");
            String nombreUsuarioIngresado = scan.nextLine();

            if (nombreUsuarioIngresado.equalsIgnoreCase("s")) {
                System.out.println("Saliendo del inicio de sesión...");
                return; // Salir del programa o realizar alguna acción de salida
            }

            System.out.println("Ingrese su contraseña:");
            String contraseñaIngresada = scan.nextLine();
            
            // Validar el nombre de usuario y la contraseña
            Usuario usuariotemp = validarUsuarioYContraseña(nombreUsuarioIngresado, contraseñaIngresada, usuariosRegistrados);
            if (usuariotemp != null) {
                System.out.println("Inicio de sesión exitoso.");
                inicioSesionExitoso = true;
                menu(usuariotemp);
            } else {
                System.out.println("Nombre de usuario o contraseña incorrectos. Intente de nuevo.");
            }
        }
    }

public static Usuario validarUsuarioYContraseña(String nombreUsuario, String contraseña, List<Usuario> usuariosRegistrados) {
    for (Usuario usuario : usuariosRegistrados) {
        if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContraseña().equals(contraseña)) {
            // Validación exitosa, devuelve el objeto Usuario encontrado
            return usuario;
        }
    }
    // Si no se encuentra un usuario con la combinación correcta de nombre de usuario y contraseña
    return null;
}
    
public static void verHistorialCompras(Usuario usuario) {
    List<Compra> historialCompras = usuario.getHistorialCompras();

    if (historialCompras.isEmpty()) {
        System.out.println("No hay historial de compras para el usuario.");
    } else {
        System.out.println("**Historial de Compras para " + usuario.getNombre() + "**");
        for (Compra compra : historialCompras) {
            System.out.println("Fecha: " + compra.getFechaCompra());
            System.out.println("Productos:");
            for (Map.Entry<Producto, Integer> entry : compra.getProductosComprados().entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                System.out.println("   Nombre: " + producto.getNombre() + ", Cantidad: " + cantidad);
            }
            System.out.println("---------------------------------------------");
        }
    }
}
    
    public static void menu(Usuario usuario){
        Scanner scan = new Scanner(System.in);
        int op;

        do {
        System.out.println("\n\nBienvenido a la SUPER Tienda FI");
        System.out.println("1. Buscar producto.");
        System.out.println("2. Ver carrito.");
        System.out.println("3. Actualizar datos personales.");
        System.out.println("4. Ver mis compras.");
        System.out.println("5. Ver mis puntos.");
        System.out.println("6. Cerrar sesión.");
        op = scan.nextInt();
            switch(op) {
                case 1:
                    Producto.buscarProducto(usuario.getCarrito(), usuario.getSucursal());
                    break;
                case 2:
                    Carrito.verCarrito(usuario, usuario.getSucursal());
                    break;
                case 3:
                    RegistroClientes.actualizarDatosPersonales(usuario);
                    break;
                case 4:
                    verHistorialCompras(usuario);
                    break;
                case 5:
                    double puntos = usuario.getPuntos();
                    System.out.println("Total de puntos: " + puntos);
                    break;
                case 6:
                    System.out.println("Cerrando sesión...\n\n");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while(op != 6);
    }

    
}

