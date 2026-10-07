import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Metodos {
    private int contadorTurnos = 1;

    private void registrarHistorial(ArrayList<String> historial, String accion) {
        historial.add(accion);
    }

    public List<Queue<Cliente>> inicializarCajas(int numCajas) {
        List<Queue<Cliente>> cajas = new ArrayList<>();
        for (int i = 0; i < numCajas; i++) {
            cajas.add(new LinkedList<>());
        }
        return cajas;
    }

    private int cajaConMenosClientes(List<Queue<Cliente>> cajas) {
        int indiceMenor = 0;
        for (int i = 1; i < cajas.size(); i++) {
            if (cajas.get(i).size() < cajas.get(indiceMenor).size()) {
                indiceMenor = i;
            }
        }
        return indiceMenor + 1;
    }

    public List<Queue<Cliente>> registrarCliente(List<Queue<Cliente>> cajas, Scanner sc,
            ArrayList<String> historial) {
        sc.nextLine();
        System.out.println("Ingrese el nombre del cliente: ");
        String nombre = sc.nextLine();

        System.out.println("Ingrese el motivo de atención (ej: devolución, reclamo, pago especial): ");
        String motivo = sc.nextLine();
        System.out.println("Elija la caja (1 a " + cajas.size() + "), o ingrese 0 para asignación automática: ");
        int opt = validarEntero(sc);
        int cajaElegida;
        if (opt == 0) {
            cajaElegida = cajaConMenosClientes(cajas);
            System.out.println("Asignado automáticamente a la caja " + cajaElegida);
        } else if (opt >= 1 && opt <= cajas.size()) {
            cajaElegida = opt;
        } else {
            System.out.println("Caja inválida, se asigna automáticamente");
            cajaElegida = cajaConMenosClientes(cajas);
        }
        Cliente c = new Cliente ();
        cajas.get(cajaElegida - 1).offer(c);
        registrarHistorial(historial,
                "Turno " + contadorTurnos + " (" + nombre + ") se formó en la caja " + cajaElegida);
        System.out.println("Turno asignado: " + contadorTurnos + " en la caja " + cajaElegida);
        contadorTurnos++;
        return cajas;
    }

    
    public Queue<Cliente> atenderCliente(List<Queue<Cliente>> cajas, Queue<Cliente> atendidos, Scanner sc,
            ArrayList<String> historial) {
        System.out.println("¿Qué caja va a atender? (1 a " + cajas.size() + "): ");
        int numCaja = validarEntero(sc);
        if (numCaja < 1 || numCaja > cajas.size()) {
            System.out.println("Caja inválida");
            return atendidos;
        }
        Queue<Cliente> fila = cajas.get(numCaja - 1);
        if (fila.isEmpty()) {
            System.out.println("La caja " + numCaja + " no tiene clientes esperando");
        } else {
            Cliente c = fila.poll();
            c.setEstado("Atendido");

            atendidos.offer(c);
            registrarHistorial(historial,
                    "Turno " + c.getTurno() + " (" + c.getNombre() + ") atendido en caja " + numCaja);
            System.out.println("Se atendió: " + c);
        }
        return atendidos;
    }

    public Queue<Cliente> abandonarFila(List<Queue<Cliente>> cajas, Queue<Cliente> abandonos, Scanner sc,
            ArrayList<String> historial) {
        System.out.println("Ingrese el turno que abandona la fila: ");
        int turno = validarEntero(sc);
        Cliente c = buscarYRemoverEnCajas(cajas, turno);
        if (c == null) {
            System.out.println("No se encontró ese turno en ninguna caja");
        } else {
            c.setEstado("Abandono");
            abandonos.offer(c);
            registrarHistorial(historial,
                    "Turno " + turno + " (" + c.getNombre() + ") abandonó la fila de la caja " + c.getCaja());
            System.out.println("Cliente marcado como abandono");
        }
        return abandonos;
    }

    public List<Queue<Cliente>> cambiarCaja(List<Queue<Cliente>> cajas, Scanner sc, ArrayList<String> historial) {
        System.out.println("Ingrese el turno que quiere cambiar de caja: ");
        int turno = validarEntero(sc);
        Cliente c = buscarYRemoverEnCajas(cajas, turno);
        if (c == null) {
            System.out.println("No se encontró ese turno en ninguna caja");
            return cajas;
        }
        System.out.println("Elija la nueva caja (1 a " + cajas.size() + "): ");
        int nuevaCaja = validarEntero(sc);
        if (nuevaCaja < 1 || nuevaCaja > cajas.size()) {
            System.out.println("Caja inválida, el cliente vuelve a su caja original");
            nuevaCaja = c.getCaja();
        }

        int cajaAnterior = c.getCaja();
        c.setCaja(nuevaCaja);
        cajas.get(nuevaCaja - 1).offer(c);
        registrarHistorial(historial, "Turno " + turno + " (" + c.getNombre() + ") cambió de la caja "
                + cajaAnterior + " a la caja " + nuevaCaja);
        System.out.println("Cliente movido a la caja " + nuevaCaja);
        return cajas;
    }

    public List<Queue<Cliente>> reasignarPorDisponibilidad(List<Queue<Cliente>> cajas, Scanner sc,
            ArrayList<String> historial) {
        System.out.println("Ingrese el turno a reasignar a una caja disponible: ");
        int turno = validarEntero(sc);
        Cliente c = buscarYRemoverEnCajas(cajas, turno);
        if (c == null) {
            System.out.println("No se encontró ese turno en ninguna caja");
            return cajas;
        }
        int cajaAnterior = c.getCaja();
        int cajaNueva = cajaConMenosClientes(cajas);
        c.setCaja(cajaNueva);
        cajas.get(cajaNueva - 1).offer(c);
        registrarHistorial(historial, "Turno " + turno + " (" + c.getNombre() + ") reasignado de la caja "
                + cajaAnterior + " a la caja " + cajaNueva + " por disponibilidad");
        System.out.println("Cliente reasignado automáticamente a la caja " + cajaNueva);
        return cajas;
    }

    private Cliente buscarYRemoverEnCajas(List<Queue<Cliente>> cajas, int turno) {
        for (Queue<Cliente> fila : cajas) {
            Cliente encontrado = removerDeCola(fila, turno);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;

    }

    private Cliente removerDeCola(Queue<Cliente> cola, int turno) {
        Queue<Cliente> aux = new LinkedList<>();
        Cliente encontrado = null;
        while (!cola.isEmpty()) {
            Cliente actual = cola.poll();
            if (actual.getTurno() == turno && encontrado == null) {
                encontrado = actual;
            } else {
                aux.offer(actual);
            }
        }
        while (!aux.isEmpty()) {
            cola.offer(aux.poll());
        }
        return encontrado;
    }

    public void mostrarCaja(List<Queue<Cliente>> cajas, int numCaja) {
        if (numCaja < 1 || numCaja > cajas.size()) {
            System.out.println("Caja inválida");
            return;
        }
        Queue<Cliente> fila = cajas.get(numCaja - 1);
        if (fila.isEmpty()) {
            System.out.println("La caja " + numCaja + " no tiene clientes esperando");
        } else {
            System.out.println("Fila de la caja " + numCaja + ":");
            for (Cliente c : fila) {
                System.out.println(c);
            }
        }
    }

    public void mostrarTodasLasCajas(List<Queue<Cliente>> cajas) {
        for (int i = 0; i < cajas.size(); i++) {
            mostrarCaja(cajas, i + 1);
        }
    }

    public void mostrarCola(Queue<Cliente> cola, String titulo) {
        if (cola.isEmpty()) {

            System.out.println(titulo + ": no hay registros");
        } else {
            System.out.println(titulo + ":");
            for (Cliente c : cola) {
                System.out.println(c);
            }
        }
    }

    public void mostrarHistorial(ArrayList<String> historial) {
        if (historial.isEmpty()) {
            System.out.println("Aún no se ha realizado ninguna operación");
        } else {
            String[] arregloHistorial = historial.toArray(new String[0]);
            System.out.println("Historial de operaciones:");
            for (int i = 0; i < arregloHistorial.length; i++) {
                System.out.println((i + 1) + ". " + arregloHistorial[i]);
            }
        }
    }

    public int validarEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Por favor ingrese un valor numerico valido");
            sc.next();
        }
        return sc.nextInt();
    }
}
