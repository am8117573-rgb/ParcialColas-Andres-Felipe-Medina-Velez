import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Metodos m = new Metodos();

        System.out.println("Cuantas cajas especiales tiene el supermercado?");
        int numCajas = m.validarEntero(sc);

        List<Queue<Cliente>> cajas = m.inicializarCajas(numCajas);
        Queue<Cliente> atendidos = new LinkedList<>();
        Queue<Cliente> abandonos = new LinkedList<>();
        ArrayList<String> historial = new ArrayList<>();

        boolean continuar = true;

        while (continuar) {
            System.out.println("Bienvenidos al sistema de cajas especiales del supermercado");
            System.out.println("Que desea realizar");
            System.out.println("1) Nuevo cliente llega a una caja");
            System.out.println("2) Atender siguiente cliente de una caja");
            System.out.println("3) Cliente abandona la fila");
            System.out.println("4) Cliente cambia de caja");
            System.out.println("5) Reasignar cliente a la caja con menos fila");
            System.out.println("6) Mostrar fila de una caja especifica");
            System.out.println("7) Mostrar todas las cajas");
            System.out.println("8) Mostrar clientes atendidos");
            System.out.println("9) Mostrar abandonos");
            System.out.println("10) Mostrar historial de operaciones");
            System.out.println("11) Salir");

            int opt = m.validarEntero(sc);

            switch (opt) {
                case 1:
                    cajas = m.registrarCliente(cajas, sc, historial);
                    break;

                case 2:
                    atendidos = m.atenderCliente(cajas, atendidos, sc, historial);
                    break;

                case 3:
                    abandonos = m.abandonarFila(cajas, abandonos, sc, historial);
                    break;

                case 4:
                    cajas = m.cambiarCaja(cajas, sc, historial);
                    break;

                case 5:
                    cajas = m.reasignarPorDisponibilidad(cajas, sc, historial);
                    break;

                case 6:
                    System.out.println("Que caja desea ver? (1 a " + numCajas + ")");
                    int numCaja = m.validarEntero(sc);
                    m.mostrarCaja(cajas, numCaja);
                    break;

                case 7:
                    m.mostrarTodasLasCajas(cajas);
                    break;

                case 8:
                    m.mostrarCola(atendidos, "Atendidos");
                    break;

                case 9:
                    m.mostrarCola(abandonos, "Abandonos");
                    break;

                case 10:
                    m.mostrarHistorial(historial);
                    break;

                case 11:
                    System.out.println("Hasta luego");
                    continuar = false;
                    break;

                default:
                    System.out.println("Esta opcion no existe");
                    break;
            }
        }
    }
 }