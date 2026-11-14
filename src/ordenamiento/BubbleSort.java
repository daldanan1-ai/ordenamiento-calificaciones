package ordenamiento;

import java.io.PrintStream;
import util.Arreglos;

/**
 * Ordenamiento Burbuja (Bubble Sort) para calificaciones, implementado manualmente.
 *
 * Idea: se recorren los pares de vecinos (a[i-1], a[i]) y se intercambian si están
 * en el orden incorrecto. Al final de cada pasada, el mayor de la parte no ordenada
 * "sube" como una burbuja a su posición definitiva al final del arreglo.
 */
public final class BubbleSort {

    private BubbleSort() {
    }

    /**
     * Ordena el arreglo IN-PLACE de menor a mayor.
     *
     * Optimizaciones:
     *  1. Cada pasada recorre un elemento menos (el final ya está ordenado).
     *  2. Bandera "huboIntercambio": si una pasada no intercambia nada, el arreglo
     *     ya está ordenado y se detiene.
     *
     * Complejidad: mejor caso O(n) (ya ordenado: una pasada y 0 intercambios),
     * peor caso O(n^2) (orden inverso), caso promedio O(n^2). Memoria extra O(1).
     * Es estable: dos calificaciones iguales nunca se intercambian.
     */
    public static Estadisticas ordenarAscendente(double[] a) {
        return ordenarAscendente(a, null);
    }

    /** Igual que la anterior; si traza no es null, imprime el arreglo tras cada pasada. */
    public static Estadisticas ordenarAscendente(double[] a, PrintStream traza) {
        Estadisticas est = new Estadisticas();
        if (a == null || a.length < 2) {
            return est;
        }
        for (int limite = a.length; limite > 1; limite--) {
            est.sumarPasada();
            boolean huboIntercambio = false;
            for (int i = 1; i < limite; i++) {
                est.sumarComparacion();
                if (a[i - 1] > a[i]) {          // el de la izquierda es mayor: están al revés
                    intercambiar(a, i - 1, i);
                    est.sumarIntercambio();
                    huboIntercambio = true;
                }
            }
            if (traza != null) {
                traza.printf("  Pasada %d: %s%s%n", est.getPasadas(), Arreglos.formatear(a),
                        huboIntercambio ? "" : "  <- sin intercambios: termina");
            }
            if (!huboIntercambio) {
                break; // ninguna pareja estaba desordenada: el arreglo ya está ordenado
            }
        }
        return est;
    }

    /**
     * Ordena el arreglo IN-PLACE de mayor a menor.
     *
     * Es el mismo algoritmo; la ÚNICA modificación es la condición de intercambio:
     * se intercambia cuando el de la izquierda es MENOR que el de la derecha (<).
     * Así, en cada pasada es el menor el que "sube" al final.
     *
     * Complejidad: igual que la versión ascendente.
     */
    public static Estadisticas ordenarDescendente(double[] a) {
        return ordenarDescendente(a, null);
    }

    /** Igual que la anterior; si traza no es null, imprime el arreglo tras cada pasada. */
    public static Estadisticas ordenarDescendente(double[] a, PrintStream traza) {
        Estadisticas est = new Estadisticas();
        if (a == null || a.length < 2) {
            return est;
        }
        for (int limite = a.length; limite > 1; limite--) {
            est.sumarPasada();
            boolean huboIntercambio = false;
            for (int i = 1; i < limite; i++) {
                est.sumarComparacion();
                if (a[i - 1] < a[i]) {          // cambio clave respecto a la ascendente
                    intercambiar(a, i - 1, i);
                    est.sumarIntercambio();
                    huboIntercambio = true;
                }
            }
            if (traza != null) {
                traza.printf("  Pasada %d: %s%s%n", est.getPasadas(), Arreglos.formatear(a),
                        huboIntercambio ? "" : "  <- sin intercambios: termina");
            }
            if (!huboIntercambio) {
                break;
            }
        }
        return est;
    }

    private static void intercambiar(double[] a, int i, int j) {
        double temporal = a[i];
        a[i] = a[j];
        a[j] = temporal;
    }
}
