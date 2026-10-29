import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class GestorReservas {


    private List<Sala> salas = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();
    private int siguienteId = 1;
    private static final int MAX_RESERVAS = 100;


    public GestorReservas() {
        salas.add(new Sala(114, "Sala 1", 5));
        salas.add(new Sala(216, "Sala 2", 10));
        salas.add(new Sala(382, "Sala 3", 15));
        salas.add(new Sala(426, "Sala 4", 20));
    }


    public void mostrarSalas() {
        for (Sala sala : salas) {
            System.out.println(sala);
        }
    }


    public Sala buscarSala(int id) {
        for (Sala sala : salas) {
            if (sala.getId() == id) {
                return sala;
            }
        }


        RegistroEventos.registrar(
                "ERROR: sala inexistente, ID " + id
        );
        return null;
    }


    public boolean crearReserva(int salaId, String responsable,
                                LocalDateTime inicio, int asistentes) {
        Sala sala = buscarSala(salaId);


        if (sala == null) {
            System.out.println("La sala no existe.");
            return false;
        }


        if (responsable == null || responsable.trim().isEmpty()) {
            System.out.println("El responsable es obligatorio.");
            return false;
        }


        if (inicio == null || !inicio.isAfter(LocalDateTime.now())) {
            System.out.println("La fecha debe ser futura.");
            return false;
        }


        if (inicio.getMinute() != 0
                || inicio.getSecond() != 0
                || inicio.getNano() != 0) {
            System.out.println(
                    "La reserva debe comenzar a una hora exacta."
            );
            return false;
        }


        if (asistentes <= 0 || asistentes > sala.getCapacidad()) {
            System.out.println(
                    "Número de asistentes inválido o capacidad excedida."
            );
            return false;
        }


        if (reservas.size() >= MAX_RESERVAS) {
            System.out.println("Se alcanzó el límite de reservas.");
            RegistroEventos.registrar(
                    "ERROR: límite de reservas alcanzado."
            );
            return false;
        }


        LocalDateTime fin = inicio.plusHours(1);


        for (Reserva r : reservas) {
            if (r.isActiva() && r.getSala().getId() == salaId) {
                boolean seCruzan =
                        inicio.isBefore(r.getFin())
                        && r.getInicio().isBefore(fin);


                if (seCruzan) {
                    System.out.println(
                            "La sala ya está reservada en ese horario."
                    );
                    RegistroEventos.registrar(
                            "ERROR: sala no disponible."
                    );
                    return false;
                }
            }
        }


        Reserva reserva = new Reserva(
                siguienteId++, sala, responsable, inicio, asistentes
        );


        reservas.add(reserva);


        RegistroEventos.registrar(
                "Reserva creada: ID " + reserva.getId()
        );


        System.out.println("Reserva creada correctamente.");
        reserva.mostrar();
        return true;
    }


    public void consultarPorSala(int salaId) {
        if (buscarSala(salaId) == null) {
            System.out.println("La sala no existe.");
            return;
        }


        boolean encontrada = false;


        for (Reserva r : reservas) {
            if (r.getSala().getId() == salaId) {
                r.mostrar();
                encontrada = true;
            }
        }


        if (!encontrada) {
            System.out.println("No hay reservas para esa sala.");
        }


        RegistroEventos.registrar(
                "Consulta de reservas por sala: " + salaId
        );
    }


    public void consultarPorFecha(LocalDate fecha) {
        boolean encontrada = false;


        for (Reserva r : reservas) {
            if (r.getInicio().toLocalDate().equals(fecha)) {
                r.mostrar();
                encontrada = true;
            }
        }


        if (!encontrada) {
            System.out.println("No hay reservas para esa fecha.");
        }


        RegistroEventos.registrar(
                "Consulta de reservas por fecha: " + fecha
        );
    }


    public void cancelarReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                if (!r.isActiva()) {
                    System.out.println("La reserva ya está cancelada.");
                    return;
                }


                r.cancelar();


                RegistroEventos.registrar(
                        "Reserva cancelada: ID " + id
                );


                System.out.println("Reserva cancelada correctamente.");
                return;
            }
        }


        System.out.println("La reserva no existe.");
        RegistroEventos.registrar(
                "ERROR: reserva inexistente, ID " + id
        );
    }
}