import java.time.LocalDateTime;


public class Reserva {
    private int id;
    private Sala sala;
    private String responsable;
    private LocalDateTime inicio;
    private int asistentes;
    private boolean activa;


    public Reserva(int id, Sala sala, String responsable,
                   LocalDateTime inicio, int asistentes) {
        this.id = id;
        this.sala = sala;
        this.responsable = responsable;
        this.inicio = inicio;
        this.asistentes = asistentes;
        this.activa = true;
    }


    public int getId() {
        return id;
    }


    public Sala getSala() {
        return sala;
    }


    public String getResponsable() {
        return responsable;
    }


    public LocalDateTime getInicio() {
        return inicio;
    }


    public LocalDateTime getFin() {
        return inicio.plusHours(1);
    }


    public int getAsistentes() {
        return asistentes;
    }


    public boolean isActiva() {
        return activa;
    }


    public void cancelar() {
        activa = false;
    }


    public void mostrar() {
        System.out.println("ID de reserva: " + id);
        System.out.println("Sala: " + sala.getNombre());
        System.out.println("Responsable: " + responsable);
        System.out.println("Fecha y hora: " + inicio);
        System.out.println("Asistentes: " + asistentes);
        System.out.println("Estado: "
                + (activa ? "Activa" : "Cancelada"));
        System.out.println("-------------------------");
    }
}