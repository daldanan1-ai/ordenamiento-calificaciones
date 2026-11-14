package ranking;

import ordenamiento.BubbleSort;
import util.Arreglos;

/**
 * Ranking de calificaciones a partir del arreglo ordenado de mayor a menor.
 */
public final class Ranking {

    public static final int TOP = 5;

    private Ranking() {
    }

    /**
     * Devuelve las cinco calificaciones más altas (o todas, si hay menos de cinco).
     * Se ordena una COPIA de mayor a menor con Bubble Sort y se toman las primeras.
     * Complejidad: O(n^2) por el ordenamiento + O(1) para tomar las 5 primeras.
     */
    public static double[] cincoMasAltas(double[] calificaciones) {
        double[] copia = Arreglos.copiar(calificaciones);
        BubbleSort.ordenarDescendente(copia);
        return primeras(copia, TOP);
    }

    /** Toma las primeras k posiciones de un arreglo YA ordenado de mayor a menor. */
    public static double[] primeras(double[] ordenadoDesc, int k) {
        int cantidad = Math.min(k, ordenadoDesc.length);
        double[] top = new double[cantidad];
        for (int i = 0; i < cantidad; i++) {
            top[i] = ordenadoDesc[i];
        }
        return top;
    }

    /**
     * Cuenta cuántas calificaciones FUERA del top empatan con la quinta.
     * Ejemplo: si la 5.ª es 4.0 y la 6.ª y 7.ª también son 4.0, devuelve 2.
     * Sirve para avisar que el corte del top 5 deja por fuera notas iguales.
     */
    public static int empatesFueraDelTop(double[] ordenadoDesc) {
        if (ordenadoDesc.length <= TOP) {
            return 0;
        }
        double quinta = ordenadoDesc[TOP - 1];
        int empates = 0;
        for (int i = TOP; i < ordenadoDesc.length && ordenadoDesc[i] == quinta; i++) {
            empates++;
        }
        return empates;
    }
}
