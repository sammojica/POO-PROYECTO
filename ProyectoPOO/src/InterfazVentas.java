//Clase principal
import java.util.InputMismatchException;
import java.util.Scanner;
public class InterfazVentas{
    
    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("**PROYECTO FINAL**");
        System.out.println("-Programación Orientada a Objetos");
        System.out.println("-Grupo 7");
        System.out.println("-Semestre 2024-1");
        System.out.println("-Equipo Bee-T");
        System.out.println("-Integrantes:\nHernandez Sanchez Karla. \nMeneses Calderas Grecia Irais. \nMojica Pereda Elena Samantha. \nRuiz Cervantes Karla Patricia.\n\n");
        int op=0;
        Thread.sleep(3000);
        do{
            try{
            System.out.println("Sistema de gestión");
            System.out.println("-----------SUPER Tienda FI-----------");
            System.out.println("\nEscoja su tipo de sesión:");
            System.out.println("1. Cliente.");
            System.out.println("2. Empleado.");
            System.out.println("3. Gerente");
            System.out.println("4. Salir");
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
