import java.util.Scanner;

public class Cliente {
    public static void main() {
    Scanner scan = new Scanner(System.in);
    int op;
    pregunta();
    Carrito carritoTemporal = new Carrito(); 
    System.out.println("\n\nBienvenido a la SUPER Tienda FI");
    System.out.println("¿En qué sucursal realizará la compra? [o presione 's' para salir]");
    String sucursaltemp = scan.nextLine();
    
    while (!sucursaltemp.equalsIgnoreCase("s")) {
        do {
            System.out.println("1. Buscar producto.");
            System.out.println("2. Ver carrito.");
            System.out.println("3. Salir");
            op = scan.nextInt();
            switch(op) {
                case 1:
                    Producto.buscarProducto(carritoTemporal, sucursaltemp);
                    break;
                case 2:
                    Carrito.verCarritoSinUsuario(carritoTemporal, sucursaltemp);
                    break;
                case 3:
                    System.out.println("Saliendo del menú...\n\n");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (op != 3);

        System.out.println("¿En qué sucursal realizará la compra? [o presione 's' para salir]");
        scan.nextLine(); // Consumir la nueva línea pendiente después de nextInt
        sucursaltemp = scan.nextLine();
    }

    System.out.println("Saliendo del programa...\n\n");
}

    
    
    public static void pregunta() {
        Scanner scan = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("Bienvenido, ¿Ya se encuentra registrado en el Programa de Lealtad? (s/n)");
            String respuesta = scan.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                Usuario.main();
                salir = true;
            } else if (respuesta.equalsIgnoreCase("n")) {
                System.out.println("¿Desea registrarse? (s/n)");
                String respuesta2 = scan.nextLine();

                if (respuesta2.equalsIgnoreCase("s")) {
                    RegistroClientes.main();
                    salir = true;
                } else if (respuesta2.equalsIgnoreCase("n")) {
                    System.out.println("¿Estás seguro? Estos son los beneficios que perderías (s/n):");
                    System.out.println("Beneficios de tener una cuenta:");
                    System.out.println("* Acumular 0.5% en puntos para el nivel 0.");
                    System.out.println("* Acumular 1% en puntos para el nivel 0.");
                    System.out.println("* Acumular 3% en puntos para el nivel 0.");
                    System.out.println("\nY si eres nivel 15 por cada $700 recibe un producto gratuito de manera aleatoria.");
                    System.out.println("Cada punto acumulado vale $0.025");

                    String respuesta3 = scan.nextLine();
                    if (respuesta3.equalsIgnoreCase("s")) {
                        salir = true;
                    } else if (respuesta3.equalsIgnoreCase("n")) {
                        System.out.println("Entendido... iniciando registro.");
                        RegistroClientes.main(); 
                        salir = true;
                    } else {
                        System.out.println("Respuesta inválida");
                    }
                } else {
                    System.out.println("Respuesta inválida");
                }
            } else {
                System.out.println("Respuesta inválida. Por favor, responda con 's' o 'n'.");
            }
        }
    }
}
