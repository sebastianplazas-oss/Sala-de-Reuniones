import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


public class RegistroEventos {


    public static void registrar(String mensaje) {
        String fecha = LocalDateTime.now().toString();


        try (PrintWriter archivo = new PrintWriter(
                new FileWriter("eventos.log", true))) {


            archivo.println("[" + fecha + "] " + mensaje);


        } catch (IOException e) {
            System.out.println(
                    "Error al guardar el evento: " + e.getMessage()
            );
        }
    }
}