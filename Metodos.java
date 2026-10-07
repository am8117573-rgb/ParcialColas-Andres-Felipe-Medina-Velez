import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Metodos {

    public Queue<ObjCliente> LlenarCola(Queue<ObjCliente> cola, Metodos m, Scanner sc, int numCaja) {
        boolean continuar = true;

        while (continuar) {
            ObjCliente o = new ObjCliente();
            o.setTurno(m.ValidarTurno(cola));
            o.setCaja(numCaja);
            System.out.println("Ingrese el nombre del cliente ");
            sc.nextLine();
            o.setNombre(sc.nextLine());
            o.setMotivo(m.MenuMotivo(sc));
            o.setEstado(1);
            System.out.println("Desea Agregar mas clientes 1 si , 2 no ");
            int opt = sc.nextInt();
            if (opt == 2) {
                System.out.println("Vuelve Pronto");
                continuar = false;
            }
            cola.offer(o);

        }
        return cola;

    }

    public int ValidarTurno(Queue<ObjCliente> cola) {
        int turno = 0;
        if (cola.isEmpty()) {
            turno = 1;
        } else {
            turno = cola.size() + 1;
        }
        return turno;
    }

    public int MenuMotivo(Scanner sc) {
        System.out.println("Motivo de atencion del cliente");
        System.out.println("1) Devolucion");
        System.out.println("2) Reclamo");
        System.out.println("3) Pago especial");
        System.out.println("4) Consulta");
        System.out.println("5) Otro ");
        return sc.nextInt();

    }

    public String MostrarTodosTurnos(Queue<ObjCliente> cola, int opt) {
        switch (opt) {
            case 1:
                for (ObjCliente o : cola) {
                    System.out.println("Turno: " + o.getTurno());
                    System.out.println("Cliente: " + o.getNombre());
                    System.out.println(MenuMotivito(o.getMotivo()));
                    System.out.println("Caja: " + o.getCaja());
                    if (o.getEstado() == 1) {
                        System.out.println("Estado: Esperando");
                    } else if (o.getEstado() == 2) {
                        System.out.println("Estado: Atendido");
                    } else {
                        System.out.println("Estado: Abandono");
                    }
                    System.out.println("----------------------------------------- \n");

                }

                break;
            case 2:
                for (ObjCliente o : cola) {
                    if (o.getEstado() == 1) {
                        System.out.println("Turno: " + o.getTurno());
                        System.out.println("Cliente: " + o.getNombre());
                        System.out.println(MenuMotivito(o.getMotivo()));
                        System.out.println("Caja: " + o.getCaja());
                        if (o.getEstado() == 1) {
                            System.out.println("Estado: Esperando");
                        } else if (o.getEstado() == 2) {
                            System.out.println("Estado: Atendido");
                        } else {
                            System.out.println("Estado: Abandono");
                        }
                    }

                }
                break;

            default:
                for (ObjCliente o : cola) {
                    if (o.getEstado() != 1) {
                        System.out.println("Turno: " + o.getTurno());
                        System.out.println("Cliente: " + o.getNombre());
                        System.out.println(MenuMotivito(o.getMotivo()));
                        System.out.println("Caja: " + o.getCaja());
                        if (o.getEstado() == 1) {
                            System.out.println("Estado: Esperando");
                        } else if (o.getEstado() == 2) {
                            System.out.println("Estado: Atendido");
                        } else {
                            System.out.println("Estado: Abandono");
                        }

                    }

                }
                break;
        }
        return "Datos mostrados correctamente";
    }

    private static String MenuMotivito(int opt) {
        String mensaje = "";
        switch (opt) {
            case 1:
                mensaje = "Devolucion";
                break;
            case 2:
                mensaje = "Reclamo";
                break;
            case 3:
                mensaje = "Pago especial";
                break;
            case 4:
                mensaje = "Consulta";
                break;

            default:
                mensaje = "Otro";
                break;
        }
        return mensaje;
    }

    public Queue<ObjCliente> Atender(Queue<ObjCliente> cola) {
        for (ObjCliente o : cola) {
            if (o.getEstado() == 1) {
                System.out.println("El siguiente turno es " + o.getTurno() + " cliente: " + o.getNombre());
                o.setEstado(2);
                break;
            }
        }
        System.out.println("Turno atendido correctamente ");
        return cola;
    }

    public Queue<ObjCliente> Abandonar(Queue<ObjCliente> cola, int turno) {
        for (ObjCliente o : cola) {
            if (o.getTurno() == turno) {
                System.out.println("El cliente " + o.getNombre() + " abandono la fila");
                o.setEstado(3);
                break;
            }
        }
        return cola;
    }

 
    public Queue<ObjCliente> CambiarCaja(Queue<ObjCliente> colaActual, Queue<ObjCliente> colaDestino, int turno,
            int numCajaDestino) {
        Queue<ObjCliente> aux = new LinkedList<>();
        ObjCliente encontrado = null;

        while (!colaActual.isEmpty()) {
            ObjCliente o = colaActual.poll();
            if (o.getTurno() == turno && encontrado == null) {
                encontrado = o;
            } else {
                aux.offer(o);
            }
        }
        while (!aux.isEmpty()) {
            colaActual.offer(aux.poll());
        }

        if (encontrado == null) {
            System.out.println("No se encontro ese turno en la caja actual");
        } else {
            encontrado.setCaja(numCajaDestino);
            encontrado.setTurno(ValidarTurno(colaDestino));
            colaDestino.offer(encontrado);
            System.out.println("Cliente movido a la nueva caja correctamente");
        }
        return colaDestino;
    }

    public int ValidarEentero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println(
                    "Por favor tenga en cuenta que se le esta pidiendo un dato numerico ojala en el rango de 1 a 5 ");
            sc.next();
        }
        return sc.nextInt();
    }

    public Stack<ObjCliente> Apilar(Queue<ObjCliente> c, Stack<ObjCliente> p) {
        for (ObjCliente o : c) {
            if (o.getEstado() == 1) {
                p.push(o);
            }
        }
        return p;
    }

    public void MostrarPila(Stack<ObjCliente> p) {
        for (ObjCliente o : p) {
            System.out.println("Turno: " + o.getTurno());
            System.out.println("Cliente: " + o.getNombre());
            System.out.println(MenuMotivito(o.getMotivo()));
            System.out.println("Caja: " + o.getCaja());
            if (o.getEstado() == 1) {
                System.out.println("Estado: Esperando");
            } else if (o.getEstado() == 2) {
                System.out.println("Estado: Atendido");
            } else {
                System.out.println("Estado: Abandono");
            }
        }
    }

    public ObjCliente[] ArregloAtendidos(Queue<ObjCliente> c) {
        ObjCliente[] arreglo = new ObjCliente[Dimension(c)];
        int i = 0;
        for (ObjCliente o : c) {
            if (o.getEstado() != 1) {
                arreglo[i] = o;
                i++;
            }
        }
        return arreglo;
    }

    private static int Dimension(Queue<ObjCliente> c) {
        int cont = 0;
        for (ObjCliente o : c) {
            if (o.getEstado() != 1) {
                cont++;
            }
        }
        return cont;
    }

    public void MostrarArreglo(ObjCliente[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println("Turno: " + a[i].getTurno());
            System.out.println("Cliente: " + a[i].getNombre());
            System.out.println(MenuMotivito(a[i].getMotivo()));
            System.out.println("Caja: " + a[i].getCaja());
            if (a[i].getEstado() == 1) {
                System.out.println("Estado: Esperando");
            } else if (a[i].getEstado() == 2) {
                System.out.println("Estado: Atendido");
            } else {
                System.out.println("Estado: Abandono");
            }
        }
    }

 }