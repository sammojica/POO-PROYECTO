import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Usuario extends Persona {

    public int codigoPostal;
    public String nombreUsuario;
    public double puntos;
    public int nivel;
    public String Sucursal;

    public Carrito carrito;

    public Usuario(int codigoPostal, String nombreUsuario, double puntos, int nivel, String Sucursal, Carrito carrito, String nombre, String apellidos, String direccion, String telefono, String correoElectronico, String contraseña) {
        super(nombre, apellidos, direccion, telefono, correoElectronico, contraseña);
        this.codigoPostal = codigoPostal;
        this.nombreUsuario = nombreUsuario;
        this.puntos = puntos;
        this.nivel = nivel;
        this.Sucursal = Sucursal;
        // Asignar carrito solo si no es null, de lo contrario, crea un nuevo Carrito
        this.carrito = (carrito != null) ? carrito : new Carrito();
    }

    public String toString() {
        return String.format(
                "%d,%s,%f,%d,%s,%s,%s,%s,%s,%s,%s,%s",
                codigoPostal, nombreUsuario, puntos, nivel, Sucursal,
                carritoToString(), nombre, apellidos,
                direccion, telefono, correoElectronico, contraseña);
    }

    public static Usuario fromString(String linea) {
        String[] partes = linea.split(",");
        int codigoPostal = Integer.parseInt(partes[0]);
        String nombreUsuario = partes[1];
        double puntos = Double.parseDouble(partes[2]);
        int nivel = Integer.parseInt(partes[3]);
        String Sucursal = partes[4];
        Carrito carrito = parseCarrito(partes[5]);  // Implementa parseCarrito según tus necesidades
        String nombre = partes[6];
        String apellidos = partes[7];
        String direccion = partes[8];
        String telefono = partes[9];
        String correoElectronico = partes[10];
        String contraseña = partes[11];

        return new Usuario(codigoPostal, nombreUsuario, puntos, nivel, Sucursal, carrito, nombre, apellidos, direccion, telefono, correoElectronico, contraseña);
    }

    private String carritoToString() {
        // Implementa la lógica para convertir el carrito a una cadena
        return (carrito != null) ? carrito.toString() : "null";
    }

    private static Carrito parseCarrito(String cadenaCarrito) {
        // Implementa la lógica para convertir la cadena a un objeto Carrito
        return (cadenaCarrito.equals("null")) ? null : new Carrito();  // Ajusta según tu lógica real
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

    public void ganarPuntos(double totalCompra) {
        int nivelActual = this.getNivel();
        double porcentajeGanancia;
        if (nivelActual >= 0 && nivelActual < 5) {
            porcentajeGanancia = 0.005;  // 0.5%
        } else if (nivelActual >= 5 && nivelActual < 10) {
            porcentajeGanancia = 0.01;   // 1%
        } else {
            porcentajeGanancia = 0.03;   // 3%
        }
        double puntosGanados = totalCompra * porcentajeGanancia;
        double nuevosPuntos = this.getPuntos() + puntosGanados;
        this.setPuntos(nuevosPuntos);
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void subirNivel(double totalCompra, Carrito carrito, String sucursal) {
        int nivelActual = this.getNivel();
        if (nivelActual < 5 && totalCompra >= 450) {
            this.setNivel(nivelActual + 1);
            System.out.println("¡Felicidades! Has subido al nivel " + this.getNivel() + ".");
        } else if (nivelActual < 10 && totalCompra >= 800) {
            this.setNivel(nivelActual + 1);
            System.out.println("¡Felicidades! Has subido al nivel " + this.getNivel() + ".");
        } else if (nivelActual < 15 && totalCompra >= 1400) {
            this.setNivel(nivelActual + 1);
            System.out.println("¡Felicidades! Has subido al nivel " + this.getNivel() + ".");
        } else if (nivelActual == 15 && totalCompra >= 700) {
            System.out.println("¡Felicidades! tienes el nivel 15 y recibes un producto gratuito.");
            Carrito.agregarProductoAleatorioAlCarrito(carrito, sucursal);
        }
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

    public static void main() {
        Scanner scan = new Scanner(System.in);

        System.out.println("**Inicio de sesión**");
        List<Usuario> usuariosRegistrados = RegistroClientes.leerClientesDesdeArchivo();
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

    public static void menu(Usuario usuario) {
        Scanner scan = new Scanner(System.in);
        int op=0;

        do {
            try{
            System.out.println("\n\nBienvenido a la SUPER Tienda FI");
            System.out.println("1. Buscar producto.");
            System.out.println("2. Ver carrito.");
            System.out.println("3. Actualizar datos personales.");
            System.out.println("4. Ver mis puntos y nivel.");
            System.out.println("5. Cerrar sesión.");
            op = scan.nextInt();
            switch (op) {
                case 1:
                    Producto.buscarProducto(usuario.getCarrito(), usuario.getSucursal());
                    break;
                case 2:
                    Carrito.verCarrito(usuario, usuario.getSucursal());
                    break;
                case 3:
                    RegistroClientes.actualizarDatosPersonales(usuario.getNombreUsuario());
                    break;
                case 4:
                    double puntos = usuario.getPuntos();
                    System.out.println("Nivel: " + usuario.getNivel());
                    System.out.println("Total de puntos: " + puntos);
                    System.out.println("Recuerde, cada punto equivale a $0.025.");
                    break;
                case 5:
                    System.out.println("Cerrando sesión...\n\n");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
            }catch (InputMismatchException e) {
                    System.out.println("\nOpción inválida. Intente de nuevo.");
                    scan.nextLine(); 
           } 
        } while (op != 5);

    }

    public boolean realizarPago(String formaDePago) {
        Scanner scan = new Scanner(System.in);

        switch (formaDePago.toLowerCase()) {
            case "efectivo":
                return true; // Pago exitoso
            case "puntos":
                // Lógica para pago con puntos
                System.out.println("Tienes " + this.getPuntos() + " puntos disponibles.");
                System.out.println("Ingrese la cantidad de puntos que desea utilizar para pagar:");

                int puntosUtilizados = scan.nextInt();
                scan.nextLine(); // Consumir la nueva línea pendiente después del nextInt

                if (puntosUtilizados > this.getPuntos()) {
                    System.out.println("No tienes suficientes puntos para realizar el pago.");
                    return false; // Pago no exitoso
                } else {
                    // Calcular el total a pagar con puntos
                    double DineroEnPuntos = puntosUtilizados * 0.025;
                    double totalAPagarConPuntos = this.carrito.calcularTotal() - DineroEnPuntos;

                    if (totalAPagarConPuntos <= 0) {
                        System.out.println("Pago realizado exitosamente con puntos.");
                        double nuevosPuntos = this.getPuntos() - puntosUtilizados;
                        this.setPuntos(nuevosPuntos);
                        return true; // Pago exitoso
                    } else {
                        System.out.println("Total a pagar con puntos: " + totalAPagarConPuntos);
                        System.out.println("¿Desea pagar la diferencia con tarjeta o efectivo? (tarjeta/efectivo)");

                        String respuestaPagarDiferencia = scan.nextLine().toLowerCase();
                        if (respuestaPagarDiferencia.equals("tarjeta")) {
                            // Lógica para pagar la diferencia con tarjeta
                            if (realizarPagoConTarjeta()) {
                                // Preguntar si se requiere factura solo si el pago de la diferencia con tarjeta fue exitoso
                                System.out.println("¿Desea factura? (s/n)");
                                String respuestaFacturaTarjeta = scan.nextLine().toLowerCase();

                                if (respuestaFacturaTarjeta.equals("s")) {
                                    // Lógica para solicitar información de factura
                                    solicitarInformacionFactura();
                                }
                                double nuevosPuntos = this.getPuntos() - puntosUtilizados;
                                this.setPuntos(nuevosPuntos);
                                return true; // Pago exitoso
                            } else {
                                System.out.println("Pago con tarjeta cancelado. Se debe seleccionar otra forma de pago.");
                                return false; // Pago no exitoso
                            }
                        } else if (respuestaPagarDiferencia.equals("efectivo")) {
                            double nuevosPuntos = this.getPuntos() - puntosUtilizados;
                            this.setPuntos(nuevosPuntos);
                            return true; // Pago exitoso
                        } else {
                            System.out.println("Opción no válida. Pago con puntos cancelado. Se debe seleccionar otra forma de pago.");
                            return false; // Pago no exitoso
                        }
                    }
                }
            case "tarjeta":
                // Lógica para pago con tarjeta
                if (realizarPagoConTarjeta()) {
                    // Preguntar si se requiere factura solo si el pago con tarjeta fue exitoso
                    System.out.println("¿Desea factura? (s/n)");
                    String respuestaFacturaTarjeta = scan.nextLine().toLowerCase();

                    if (respuestaFacturaTarjeta.equals("s")) {
                        // Lógica para solicitar información de factura
                        solicitarInformacionFactura();
                    }

                    return true; // Pago exitoso
                } else {
                    System.out.println("Pago con tarjeta cancelado. Se debe seleccionar otra forma de pago.");
                    return false; // Pago no exitoso
                }

            default:
                System.out.println("Forma de pago no válida.");
                return false; // Pago no exitoso
        }
    }

    public static boolean realizarPagoConTarjeta() {
        Scanner scan = new Scanner(System.in);
        // Lógica para obtener información de tarjeta
        System.out.println("Ingrese el número de tarjeta:");
        String numeroTarjeta = scan.nextLine();
        System.out.println("Ingrese el nombre del titular:");
        String nombreTitular = scan.nextLine();
        System.out.println("Ingrese el código de verificación:");
        int codigoVerificacion = scan.nextInt();
        scan.nextLine(); // Consumir la nueva línea pendiente después del nextInt
        System.out.println("Ingrese la fecha de vencimiento (MM/YY):");
        String fechaVencimiento = scan.nextLine();

        // Lógica para procesar el pago con tarjeta (puedes incluir validaciones necesarias)
        return true; // Pago exitoso
    }

    public static void solicitarInformacionFactura() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ingrese el RFC para la factura:");
        String rfc = scan.nextLine();
        System.out.println("Ingrese la dirección fiscal para la factura:");
        String direccionFiscal = scan.nextLine();
        System.out.println("Forma de Pago (Por defecto Tarjeta de Crédito, ¿es correcta la forma de pago?) (s/n)");
        String formaPagoFactura = scan.nextLine().toLowerCase();

    }

}
