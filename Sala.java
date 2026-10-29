public class Sala {
    private int id;
    private String nombre;
    private int capacidad;


    public Sala(int id, String nombre, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }


    public int getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }


    public int getCapacidad() {
        return capacidad;
    }


    @Override
    public String toString() {
        return "ID: " + id
                + " | Sala: " + nombre
                + " | Capacidad: " + capacidad;
    }
}