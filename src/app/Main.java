package app;

import ordenamiento.BubbleSort;
import ordenamiento.Estadisticas;
import ranking.Ranking;
import util.Arreglos;
import util.GeneradorCalificaciones;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Programa principal del Ejercicio 1: Ranking de calificaciones con Bubble Sort.
 *
 * Uso:
 *   java -cp out app.Main         -> menú interactivo
 *   java -cp out app.Main demo    -> ejecuta todas las demostraciones sin menú
 */
public class Main {

    private static final String LINEA = "-".repeat(78);

    /** Calificaciones de ejemplo de un grupo de 15 estudiantes (con repetidas). */
    private static final double[] GRUPO = {
        3.5, 4.2, 2.8, 4.8, 3.0, 4.2, 1.9, 5.0, 3.7, 4.5, 2.5, 4.2, 3.9, 4.0, 3.2
    };

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // punto decimal en la salida

        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            ordenarYMostrar("GRUPO DE EJEMPLO", GRUPO);
            mostrarTraza(new double[] {3.5, 1.9, 4.8, 2.8, 4.2});
            compararTiposDeArreglo(20, new Random(2026));
            compararTamanos(new Random(2026));
            return;
        }
        ejecutarMenu();
    }

    // ------------------------------------------------------------------ menú

    private static void ejecutarMenu() {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        while (true) {
            System.out.println();
            System.out.println("============ RANKING DE CALIFICACIONES (BUBBLE SORT) ============");
            System.out.println("1. Ordenar las calificaciones del grupo de ejemplo");
            System.out.println("2. Ingresar mis propias calificaciones");
            System.out.println("3. Probar con calificaciones aleatorias");
            System.out.println("4. Probar con un arreglo casi ordenado");
            System.out.println("5. Ver la traza paso a paso (pasada por pasada)");
            System.out.println("6. Comparar: aleatorio vs. casi ordenado vs. ordenado vs. inverso");
            System.out.println("7. Comparar con diferentes tamaños de arreglo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            if (!sc.hasNextLine()) {
                return;
            }
            switch (sc.nextLine().trim()) {
                case "1" -> ordenarYMostrar("GRUPO DE EJEMPLO", GRUPO);
                case "2" -> {
                    double[] propias = leerCalificaciones(sc);
                    if (propias != null) {
                        ordenarYMostrar("CALIFICACIONES INGRESADAS", propias);
                    }
                }
                case "3" -> {
                    int n = leerTamano(sc, 15);
                    ordenarYMostrar("CALIFICACIONES ALEATORIAS (n = " + n + ")",
                            GeneradorCalificaciones.aleatorias(n, random));
                }
                case "4" -> {
                    int n = leerTamano(sc, 15);
                    ordenarYMostrar("CALIFICACIONES CASI ORDENADAS (n = " + n + ")",
                            GeneradorCalificaciones.casiOrdenadas(n, 2, random));
                }
                case "5" -> {
                    System.out.print("Calificaciones para la traza (Enter = ejemplo 3.5, 1.9, 4.8, 2.8, 4.2): ");
                    String linea = sc.hasNextLine() ? sc.nextLine().trim() : "";
                    double[] datos = linea.isEmpty() ? new double[] {3.5, 1.9, 4.8, 2.8, 4.2} : convertir(linea);
                    if (datos != null) {
                        mostrarTraza(datos);
                    }
                }
                case "6" -> compararTiposDeArreglo(20, random);
                case "7" -> compararTamanos(random);
                case "0" -> {
                    System.out.println("Programa finalizado.");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static double[] leerCalificaciones(Scanner sc) {
        System.out.print("Escriba las calificaciones separadas por comas (ej: 4.5, 3.2, 5.0): ");
        return sc.hasNextLine() ? convertir(sc.nextLine()) : null;
    }

    /** Convierte "4.5, 3,2 ; 5" en un arreglo; acepta coma decimal si se separa con ';'. */
    private static double[] convertir(String linea) {
        String separador = linea.contains(";") ? ";" : ",";
        String[] partes = linea.split(separador);
        double[] notas = new double[partes.length];
        int k = 0;
        for (String parte : partes) {
            String texto = parte.trim().replace(',', '.');
            if (texto.isEmpty()) {
                continue;
            }
            try {
                notas[k++] = Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido: \"" + parte.trim() + "\". Use números como 4.5");
                return null;
            }
        }
        double[] resultado = new double[k];
        for (int i = 0; i < k; i++) {
            resultado[i] = notas[i];
        }
        if (resultado.length == 0) {
            System.out.println("No se ingresó ninguna calificación.");
            return null;
        }
        if (!GeneradorCalificaciones.sonValidas(resultado)) {
            System.out.println("Todas las calificaciones deben estar entre 0.0 y 5.0.");
            return null;
        }
        return resultado;
    }

    private static int leerTamano(Scanner sc, int porDefecto) {
        System.out.print("Cantidad de calificaciones (Enter = " + porDefecto + "): ");
        String linea = sc.hasNextLine() ? sc.nextLine().trim() : "";
        try {
            int n = linea.isEmpty() ? porDefecto : Integer.parseInt(linea);
            return Math.max(1, Math.min(n, 1000));
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido, se usan " + porDefecto + ".");
            return porDefecto;
        }
    }

    // ------------------------------------------------------- demostraciones

    /** Muestra antes/después en ambos sentidos, métricas y el top 5. */
    private static void ordenarYMostrar(String titulo, double[] original) {
        System.out.println(LINEA);
        System.out.println(titulo + "  (" + original.length + " calificaciones)");
        System.out.println("Antes:              " + Arreglos.formatear(original));

        double[] asc = Arreglos.copiar(original);
        Estadisticas estAsc = BubbleSort.ordenarAscendente(asc);
        System.out.println("Menor a mayor:      " + Arreglos.formatear(asc));
        System.out.println("   " + estAsc + " | ¿ordenado? " + Arreglos.estaOrdenadoAscendente(asc));

        double[] desc = Arreglos.copiar(original);
        Estadisticas estDesc = BubbleSort.ordenarDescendente(desc);
        System.out.println("Mayor a menor:      " + Arreglos.formatear(desc));
        System.out.println("   " + estDesc + " | ¿ordenado? " + Arreglos.estaOrdenadoDescendente(desc));

        double[] top = Ranking.primeras(desc, Ranking.TOP);
        System.out.println("TOP " + Ranking.TOP + " calificaciones más altas:");
        for (int i = 0; i < top.length; i++) {
            System.out.printf("   %d.º lugar: %.1f%n", i + 1, top[i]);
        }
        if (top.length < Ranking.TOP) {
            System.out.println("   (solo hay " + top.length + " calificaciones)");
        }
        int empates = Ranking.empatesFueraDelTop(desc);
        if (empates > 0) {
            System.out.printf("   Nota: %d calificación(es) más con %.1f empatan con el 5.º lugar.%n",
                    empates, top[Ranking.TOP - 1]);
        }
        System.out.println("El arreglo original no se modificó: " + Arreglos.formatear(original));
    }

    private static void mostrarTraza(double[] datos) {
        System.out.println(LINEA);
        System.out.println("TRAZA PASO A PASO");
        System.out.println("Inicial:   " + Arreglos.formatear(datos));
        System.out.println("Menor a mayor (en cada pasada el MAYOR queda al final):");
        double[] asc = Arreglos.copiar(datos);
        System.out.println("   " + BubbleSort.ordenarAscendente(asc, System.out));
        System.out.println("Mayor a menor (en cada pasada el MENOR queda al final):");
        double[] desc = Arreglos.copiar(datos);
        System.out.println("   " + BubbleSort.ordenarDescendente(desc, System.out));
    }

    /** Mismo tamaño, distinta disposición inicial: muestra mejor, peor y casos intermedios. */
    private static void compararTiposDeArreglo(int n, Random random) {
        System.out.println(LINEA);
        System.out.println("COMPARACIÓN POR TIPO DE ARREGLO (n = " + n + ", orden de menor a mayor)");
        double[] aleatorio = GeneradorCalificaciones.aleatorias(n, random);
        double[] casi = GeneradorCalificaciones.casiOrdenadas(n, 2, random);
        double[] ordenado = Arreglos.copiar(casi);
        BubbleSort.ordenarAscendente(ordenado);
        double[] inverso = Arreglos.copiar(ordenado);
        BubbleSort.ordenarDescendente(inverso);

        String[] nombres = {"Aleatorio", "Casi ordenado", "Ya ordenado (mejor caso)", "Orden inverso (peor caso)"};
        double[][] casos = {aleatorio, casi, ordenado, inverso};
        for (int c = 0; c < casos.length; c++) {
            System.out.println(nombres[c] + ": " + Arreglos.formatear(casos[c]));
        }
        System.out.println();
        System.out.printf("%-27s | %-13s | %-12s | %-8s%n", "Tipo de arreglo", "Comparaciones", "Intercambios", "Pasadas");
        for (int c = 0; c < casos.length; c++) {
            Estadisticas e = BubbleSort.ordenarAscendente(Arreglos.copiar(casos[c]));
            System.out.printf("%-27s | %-13d | %-12d | %-8d%n", nombres[c],
                    e.getComparaciones(), e.getIntercambios(), e.getPasadas());
        }
        System.out.printf("Máximo teórico n(n-1)/2 = %d comparaciones; mejor caso n-1 = %d.%n",
                (long) n * (n - 1) / 2, n - 1);
        System.out.println("En orden inverso hay menos de n(n-1)/2 intercambios si existen notas repetidas,");
        System.out.println("porque dos calificaciones iguales nunca se intercambian (el algoritmo es estable).");
    }

    /** Promedio de 5 ejecuciones por tamaño, para datos aleatorios y casi ordenados. */
    private static void compararTamanos(Random random) {
        System.out.println(LINEA);
        System.out.println("COMPARACIÓN POR TAMAÑO (promedio de 5 ejecuciones, orden de menor a mayor)");
        System.out.printf("%-6s | %-15s | %-15s | %-15s | %-15s | %-10s%n",
                "n", "Aleat. comp.", "Aleat. interc.", "Casi ord. comp.", "Casi ord. int.", "n(n-1)/2");
        int[] tamanos = {10, 100, 1_000, 5_000};
        int repeticiones = 5;
        for (int n : tamanos) {
            long compA = 0, intA = 0, compC = 0, intC = 0;
            for (int r = 0; r < repeticiones; r++) {
                Estadisticas a = BubbleSort.ordenarAscendente(GeneradorCalificaciones.aleatorias(n, random));
                Estadisticas c = BubbleSort.ordenarAscendente(GeneradorCalificaciones.casiOrdenadas(n, 2, random));
                compA += a.getComparaciones();
                intA += a.getIntercambios();
                compC += c.getComparaciones();
                intC += c.getIntercambios();
            }
            System.out.printf("%-6d | %-15d | %-15d | %-15d | %-15d | %-10d%n", n,
                    compA / repeticiones, intA / repeticiones, compC / repeticiones, intC / repeticiones,
                    (long) n * (n - 1) / 2);
        }
        System.out.println("Con datos aleatorios las comparaciones se acercan a n(n-1)/2 (O(n^2));");
        System.out.println("con datos casi ordenados la bandera corta el algoritmo en pocas pasadas.");
    }
}
