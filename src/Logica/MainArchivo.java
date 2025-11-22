import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainArchivo {

    public static void main(String[] args) throws Exception {

        File f = args.length > 0 ? new File(args[0]) : new File("ganadores.txt");
        WinnersStore store = new WinnersStore(f, true); 

        System.out.println("WinnersStore demo usando: " + f.getAbsolutePath());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            printMap(store.getAll());
            System.out.println("Comandos: + nombre  | list | exit");

            while ((line = in.readLine()) != null) {

                if (line.isBlank()) continue;
                line = line.trim();

                if (line.equalsIgnoreCase("exit")) break;

                if (line.equalsIgnoreCase("list")) {
                    printMap(store.getAll());
                    continue;
                }

                if (line.startsWith("+ ")) {
                    String nombre = line.substring(2).trim();
                    store.incrementWinner(nombre);
                    System.out.println("Incrementado: " + nombre);
                    printMap(store.getAll());
                    continue;
                }

                System.out.println("Comando no reconocido.");
            }
        }
    }

    private static void printMap(LinkedHashMap<String,Integer> map) {
        System.out.println("--- GANADORES ---");
        if (map.isEmpty()) {
            System.out.println("(sin registros)");
            return;
        }
        for (Map.Entry<String,Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        System.out.println("------------------");
    }
}
