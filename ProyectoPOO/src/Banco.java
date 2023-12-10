
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author greciamc
 */
public class Banco implements Serializable {
    private Map<String, Double> presupuestos;
    
    public Banco(double presupuestoInicialNorte, double presupuestoInicialSur, double presupuestoInicialCentro) {
        this.presupuestos = new HashMap<>();
        presupuestos.put("norte", presupuestoInicialNorte);
        presupuestos.put("sur", presupuestoInicialSur);
        presupuestos.put("centro", presupuestoInicialCentro);
    }

    public Map<String, Double> getPresupuestos() {
        return presupuestos;
    }
    
    public void guardarEstado() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("banco.dat"))) {
            oos.writeObject(this);
            System.out.println("Estado del banco guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el estado del banco: " + e.getMessage());
        }
    }
    
    public static Banco cargarEstado() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("banco.dat"))) {
            return (Banco) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar el estado del banco. Se creará uno nuevo.");
            return inicializarBanco();
        }
    }
    
    
    public static Banco inicializarBanco() {
        double presupuestoInicialNorte = 100000.0;
        double presupuestoInicialSur = 100000.0;
        double presupuestoInicialCentro = 100000.0;

        return new Banco(presupuestoInicialNorte, presupuestoInicialSur, presupuestoInicialCentro);
    }
    
    public void depositarDinero(String sucursal, double cantidad, String idGerente) {
        if(presupuestos.containsKey(sucursal)) {
            double presupuestoActual = presupuestos.get(sucursal);
            presupuestos.put(sucursal, presupuestoActual + cantidad);
            System.out.println("Depósito de $" + cantidad + " realizado en la sucursal " + sucursal+" por el gerente "+ Gerente.obtenerNombreYApellidoPorID(idGerente));
            //LocalDateTime.now()
            Transaccion transaccion = new Transaccion("depósito", new Date(), cantidad, sucursal, idGerente);
            RegistroTransaccion.registrarTransaccion(transaccion);
        } else {
            System.out.println("Sucursal no válida.");
        }
    }
    
    public void retirarDinero(String sucursal, double cantidad, String idGerente){
        if (presupuestos.containsKey(sucursal)) {
            double presupuestoActual = presupuestos.get(sucursal);
            if (presupuestoActual >= cantidad) {
                presupuestos.put(sucursal, presupuestoActual - cantidad);
                System.out.println("Retiro de $" + cantidad + " realizado en la sucursal " + sucursal);
                
                Transaccion transaccion = new Transaccion("retiro",new Date(), cantidad, sucursal, idGerente);
                RegistroTransaccion.registrarTransaccion(transaccion);
            } else {
                System.out.println("Fondos insuficientes en la sucursal " + sucursal);
            }
        } else {
            System.out.println("Sucursal no válida.");
        }
    }
    
    public void actualizarPresupuestoDespuesCompra(String sucursal, double montoCompra) {
        if (presupuestos.containsKey(sucursal)) {
            double presupuestoActual = presupuestos.get(sucursal);
            presupuestos.put(sucursal, presupuestoActual - montoCompra);
        } else {
            System.out.println("Sucursal no válida.");
        }
    }
    
    public static void imprimirMovimientos(){
        List<Transaccion> transacciones = RegistroTransaccion.leerTransaccionesDesdeArchivo();

        System.out.println("\n********** MOVIMIENTOS BANCARIOS **********");
        for(Transaccion transaccion : transacciones){
            System.out.println("Tipo: " + transaccion.getTipo());
            System.out.println("Fecha: " + transaccion.getFecha());
            System.out.println("Monto: $" + transaccion.getMonto());
            System.out.println("Sucursal: " + transaccion.getSucursal());
            System.out.println("ID del Gerente: " + transaccion.getIdGerente());
            System.out.println("----------------------------------------");
        }
    }
    
    
    public static class Transaccion {
        private Date fecha;
        //private LocalDateTime fecha;
        private double monto;
        private String tipo;
        private String sucursal;
        private String idGerente;

        public Transaccion(String tipo, Date fecha, double monto, String sucursal, String idGerente) {
            this.fecha = fecha;
            this.monto = monto;
            this.tipo = tipo;
            this.sucursal = sucursal;
            this.idGerente = idGerente;
        }

        /*public Transaccion(String tipo, Date fecha, double monto, String sucursal, String idGerente) {
            this.fecha = fecha;
            this.monto = monto;
            this.tipo = tipo;
            this.sucursal = sucursal;
            this.idGerente = idGerente;
        }*/

        public String getSucursal() {
            return sucursal;
        }

        public void setSucursal(String sucursal) {
            this.sucursal = sucursal;
        }

        public String getIdGerente() {
            return idGerente;
        }

        public void setIdGerente(String idGerente) {
            this.idGerente = idGerente;
        }
        
        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
        
        
        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }

        /*public LocalDateTime getFecha() {
            return fecha;
        }

        public void setFecha(LocalDateTime fecha) {
            this.fecha = fecha;
        }*/

        public double getMonto() {
            return monto;
        }

        public void setMonto(double monto) {
            this.monto = monto;
        }

        
    }
    
}
