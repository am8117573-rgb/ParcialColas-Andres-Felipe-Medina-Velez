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
        int numCajas = m.ValidarEentero(sc);

        List<Queue<ObjCliente>> cajas = new ArrayList<>();

        for (int i = 0; i < numCajas; i++) {
            cajas.add(new LinkedList<>());
        }

        Queue<ObjCliente> atendidos = new LinkedList<>();
        Queue<ObjCliente> abandonos = new LinkedList<>();
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

            int opt = m.ValidarEentero(sc);

            switch (opt) {
                case 1:
                    System.out.println("Numero de caja:");
                    int c1 = m.ValidarEentero(sc);
                    cajas.set(c1 - 1, m.LlenarCola(cajas.get(c1 - 1), m, sc, c1));
                    break;

                case 2:
                    System.out.println("Numero de caja:");
                    int c2 = m.ValidarEentero(sc);
                    cajas.set(c2 - 1, m.Atender(cajas.get(c2 - 1)));
                    break;

                case 3:
                    System.out.println("Numero de caja:");
                    int c3 = m.ValidarEentero(sc);
                    System.out.println("Numero de turno:");
                    int turno = m.ValidarEentero(sc);
                    cajas.set(c3 - 1, m.Abandonar(cajas.get(c3 - 1), turno));
                    break;

                case 4:
                    System.out.println("Caja actual:");
                    int actual = m.ValidarEentero(sc);
                    System.out.println("Caja destino:");
                    int destino = m.ValidarEentero(sc);
                    System.out.println("Turno:");
                    int turnoCambio = m.ValidarEentero(sc);

                    m.CambiarCaja(
                            cajas.get(actual - 1),
                            cajas.get(destino - 1),
                            turnoCambio,
                            destino);
                    break;

                case 5:
                    System.out.println("Caja actual:");
                    int origen = m.ValidarEentero(sc);

                    System.out.println("Turno:");
                    int turnoR = m.ValidarEentero(sc);

                    int menor = 0;

                    for (int i = 1; i < cajas.size(); i++) {
                        if (cajas.get(i).size() < cajas.get(menor).size()) {
                            menor = i;
                        }
                    }

                    if (menor == origen - 1 && cajas.size() > 1) {
                        menor = (menor + 1) % cajas.size();
                    }

                    m.CambiarCaja(
                            cajas.get(origen - 1),
                            cajas.get(menor),
                            turnoR,
                            menor + 1);
                    break;

                case 6:
                    System.out.println("Que caja desea ver? (1 a " + numCajas + ")");
                    int numCaja = m.ValidarEentero(sc);

                    System.out.println(
                            m.MostrarTodosTurnos(cajas.get(numCaja - 1), 1));
                    break;

                case 7:
                    for (Queue<ObjCliente> cola : cajas) {
                        System.out.println(m.MostrarTodosTurnos(cola, 1));
                    }
                    break;

                case 8:
                    Queue<ObjCliente> atendidos2 = new LinkedList<>();

                    for (Queue<ObjCliente> cola : cajas) {
                        for (ObjCliente o : cola) {
                            if (o.getEstado() == 2) {
                                atendidos2.offer(o);
                            }
                        }
                    }

                    System.out.println(
                            m.MostrarTodosTurnos(atendidos2, 1));
                    break;

                case 9:
                    Queue<ObjCliente> abandonos2 = new LinkedList<>();

                    for (Queue<ObjCliente> cola : cajas) {
                        for (ObjCliente o : cola) {
                            if (o.getEstado() == 3) {
                                abandonos2.offer(o);
                            }
                        }
                    }

                    System.out.println(
                            m.MostrarTodosTurnos(abandonos2, 1));
                    break;

                case 10:
                    for (String h : historial) {
                        System.out.println(h);
                    }
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