
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author greciamc
 */
public class RegistroTransaccion {
    private static final String ARCHIVO_TRANSACCIONES = "Transacciones.txt";
    private static final String DELIMITADOR = ",";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public static List<Banco.Transaccion> leerTransaccionesDesdeArchivo() {
        List<Banco.Transaccion> transacciones = new ArrayList<>();
        Path archivoPath = Paths.get(ARCHIVO_TRANSACCIONES);

        if (!Files.exists(archivoPath)) {
            try {
                Files.createFile(archivoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_TRANSACCIONES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(DELIMITADOR);

                if (partes.length == 5) {
                    try{
                        //LocalDateTime fecha = LocalDateTime.parse(partes[1], formatter);
                        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(partes[1]);
                        Banco.Transaccion transaccion = new Banco.Transaccion(partes[0], fecha, Double.parseDouble(partes[2]), partes[3], partes[4]);
                        transacciones.add(transaccion);
                    }catch(NumberFormatException | DateTimeParseException | ParseException e){ //ParseException
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Error en el formato de la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transacciones;
    }

    public static void guardarTransaccionEnArchivo(Banco.Transaccion transaccion) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_TRANSACCIONES, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //String fecha2 = formatter.format(transaccion.getFecha());
            String fecha2= dateFormat.format(transaccion.getFecha());
            writer.println(String.format("%s" + DELIMITADOR + "%s" + DELIMITADOR + "%.2f" + DELIMITADOR + "%s" + DELIMITADOR + "%s",
                    transaccion.getTipo(),fecha2, transaccion.getMonto(), transaccion.getSucursal(), transaccion.getIdGerente()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void registrarTransaccion(Banco.Transaccion transaccion) {
        List<Banco.Transaccion> listaTransacciones = new ArrayList<>(leerTransaccionesDesdeArchivo());
        listaTransacciones.add(transaccion);
        guardarTransaccionEnArchivo(transaccion);
    }

}
