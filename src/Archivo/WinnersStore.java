import java.io.*;
import java.util.*;

public class WinnersStore {

    private final File file;
    private final boolean caseInsensitive;

    /**
     * Crea un WinnersStore que usa el archivo dado.
     * @param file archivo donde se leerán/escribir los conteos
     * @param caseInsensitive si true, tratar \"Anthony\" y \"anthony\" como el mismo nombre
     */
    public WinnersStore(File file, boolean caseInsensitive) {
        this.file = file;
        this.caseInsensitive = caseInsensitive;
    }

    public WinnersStore(File file) {
        this(file, false);
    }

    /**
     * Incrementa en 1 el contador para el nombre dado y persiste el archivo.
     * El nombre se trim() antes de procesar; si queda vacío se ignora.
     */
    public synchronized void incrementWinner(String name) {
        if (name == null) return;
        String key = normalizeName(name);
        if (key.isEmpty()) return;
        // Cargar, incrementar y guardar
        Holder loaded = loadInternal();
        int newValue = loaded.counts.getOrDefault(key, 0) + 1;
        loaded.counts.put(key, newValue);
        // Guardar usando el nombre canónico (primer visto) si existe, o la forma actual
        if (!loaded.displayName.containsKey(key)) {
            loaded.displayName.put(key, name.trim());
        }
        saveInternal(loaded);
    }

    /**
     * Obtiene todos los ganadores como un LinkedHashMap ordenado (victorias desc, nombre asc).
     * Las claves son los nombres en su forma de presentación (primer encontrado).
     */
    public synchronized LinkedHashMap<String, Integer> getAll() {
        Holder h = loadInternal();
        List<Map.Entry<String,Integer>> ordered = orderEntries(h);
        LinkedHashMap<String,Integer> out = new LinkedHashMap<>();
        for (Map.Entry<String,Integer> e : ordered) {
            String display = h.displayName.getOrDefault(e.getKey(), e.getKey());
            out.put(display, e.getValue());
        }
        return out;
    }

    // ------------------ Implementación interna ------------------

    // Holder para counts y mapping de nombres canónicos
    private static class Holder {
        // key = normalized name, value = count
        final Map<String, Integer> counts = new LinkedHashMap<>();
        // key = normalized name, value = display name (primer visto)
        final Map<String, String> displayName = new LinkedHashMap<>();
    }

    // Normaliza el nombre para la clave (aplica trim y opcional toLowerCase)
    private String normalizeName(String raw) {
        if (raw == null) return "";
        String t = raw.trim();
        if (caseInsensitive) t = t.toLowerCase(Locale.ROOT);
        return t;
    }

    // Carga y migra el archivo si es necesario. Mantiene displayName del primer nombre visto.
    private Holder loadInternal() {
        Holder holder = new Holder();
        if (!file.exists()) return holder;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                int idx = line.indexOf(':');
                if (idx < 0) continue; // línea no reconocida -> ignorar
                String rawName = line.substring(0, idx).trim();
                String rawAfter = line.substring(idx + 1).trim();
                String key = normalizeName(rawName);
                if (key.isEmpty()) continue;
                int value = 0;
                try {
                    // Intentar formato nuevo "Nombre: N"
                    value = Integer.parseInt(rawAfter);
                } catch (NumberFormatException nfe) {
                    // Si no es entero, tratamos formato antiguo (fecha u otro) como 1
                    value = 1;
                }
                // Acumular
                holder.counts.put(key, holder.counts.getOrDefault(key, 0) + value);
                // Registrar displayName la primera vez que aparece la clave
                holder.displayName.putIfAbsent(key, rawName);
            }
        } catch (IOException ex) {
            // Si hay error, devolvemos lo que tengamos (silencioso para no romper la UI)
        }
        return holder;
    }

    // Escribe el archivo con formato normalizado "Nombre: N" ordenado
    private void saveInternal(Holder holder) {
        List<Map.Entry<String,Integer>> ordered = orderEntries(holder);
        // Reescribir completamente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Map.Entry<String,Integer> e : ordered) {
                String display = holder.displayName.getOrDefault(e.getKey(), e.getKey());
                bw.write(display + ": " + e.getValue());
                bw.newLine();
            }
        } catch (IOException ex) {
            // ignorar errores de escritura (puedes loggear si quieres)
        }
    }

    // Ordena las entradas por conteo descendente y nombre ascendente (displayName)
    private List<Map.Entry<String,Integer>> orderEntries(Holder holder) {
        List<Map.Entry<String,Integer>> list = new ArrayList<>(holder.counts.entrySet());
        list.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue()); // desc
            if (cmp != 0) return cmp;
            // comparar por displayName (si existe) o por la clave
            String da = holder.displayName.getOrDefault(a.getKey(), a.getKey());
            String db = holder.displayName.getOrDefault(b.getKey(), b.getKey());
            return da.compareToIgnoreCase(db);
        });
        return list;
    }

    // Guarda el mapa proporcionado (la llave debe ser el nombre para mostrar)
    // Método de conveniencia si quieres sobrescribir manualmente.
    public synchronized void saveMap(Map<String,Integer> displayMap) {
        if (displayMap == null) return;
        Holder holder = new Holder();
        for (Map.Entry<String,Integer> e : displayMap.entrySet()) {
            String rawName = e.getKey();
            String key = normalizeName(rawName);
            if (key.isEmpty()) continue;
            holder.displayName.put(key, rawName);
            holder.counts.put(key, e.getValue() == null ? 0 : e.getValue());
        }
        saveInternal(holder);
    }
}
