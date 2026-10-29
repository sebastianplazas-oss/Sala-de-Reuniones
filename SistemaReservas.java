import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class SistemaReservas {


    private static Scanner sc = new Scanner(System.in);
    private static GestorReservas gestor = new GestorReservas();


    public static void main(String[] args) {
        int opcion;


        do {
            System.out.println("\n=== SISTEMA DE RESERVAS ===");
            System.out.println("1. Mostrar salas");
            System.out.println("2. Crear reserva");
            System.out.println("3. Consultar reservas por sala");
            System.out.println("4. Consultar reservas por fecha");
            System.out.println("5. Cancelar reserva");
            System.out.println("6. Salir");


            opcion = leerEntero("Selecciona una opción: ");


            try {
                switch (opcion) {
                    case 1:
                        gestor.mostrarSalas();
                        break;


                    case 2:
                        crearReservaMenu();
                        break;


                    case 3:
                        int salaId = leerEntero("ID de la sala: ");
                        gestor.consultarPorSala(salaId);
                        break;


                    case 4:
                        System.out.print("Fecha (AAAA-MM-DD): ");
                        LocalDate fecha = LocalDate.parse(
                                sc.nextLine().trim()
                        );
                        gestor.consultarPorFecha(fecha);
                        break;


                    case 5:
                        int id = leerEntero("ID de la reserva: ");
                        gestor.cancelarReserva(id);
                        break;


                    case 6:
                        RegistroEventos.registrar(
                                "Sistema cerrado por el usuario."
                        );
                        System.out.println("Hasta pronto.");
                        break;


                    default:
                        System.out.println(
                                "Opción inválida. Selecciona del 1 al 6."
                        );
                        RegistroEventos.registrar(
                                "ERROR: opción de menú inválida."
                        );
                }


            } catch (DateTimeParseException e) {
                System.out.println(
                        "Fecha u hora inválida. Intenta de nuevo."
                );
                RegistroEventos.registrar(
                        "ERROR: formato de fecha u hora inválido."
                );
            }


        } while (opcion != 6);


        sc.close();
    }


    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();


            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Error: debes ingresar un número entero."
                );
                RegistroEventos.registrar(
                        "ERROR: entrada no numérica."
                );
            }
        }
    }


    private static void crearReservaMenu() {
        gestor.mostrarSalas();


        int salaId = leerEntero("ID de la sala: ");


        System.out.print("Nombre del responsable: ");
        String responsable = sc.nextLine().trim();


        int asistentes = leerEntero("Número de asistentes: ");


        System.out.print("Fecha (AAAA-MM-DD): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine().trim());


        System.out.print("Hora (HH:MM): ");
        LocalTime hora = LocalTime.parse(sc.nextLine().trim());


        LocalDateTime inicio = LocalDateTime.of(fecha, hora);


        gestor.crearReserva(
                salaId, responsable, inicio, asistentes
        );
    }
}