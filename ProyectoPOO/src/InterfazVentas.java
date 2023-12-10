//Clase principal
import java.util.InputMismatchException;
import java.util.Scanner;
public class InterfazVentas{
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int op=0;
        do{
            try{
                System.out.println("\n\tSistema de gestión");
                System.out.println("----------- SUPER Tienda FI -----------");
                System.out.println("\nEscoja su tipo de sesión:");
                System.out.println("1. Cliente.");
                System.out.println("2. Empleado.");
                System.out.println("3. Gerente");
                System.out.print("4. Salir\n  > ");
                op = scan.nextInt();

                switch(op){
                    case 1:
                        Cliente.main();
                        break;
                    case 2:
                        Empleado.main();
                        break;
                    case 3:
                        Gerente.main();
                        break;
                    case 4:
                        System.out.println("Saliendo del programa, hasta luego.");
                        break;
                    default:
                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("\nOpción inválida. Intente de nuevo.");
                scan.nextLine(); 
            } 
        }while(op!=4);
        
    }
    
}
