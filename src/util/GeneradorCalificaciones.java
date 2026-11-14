package util;

import ordenamiento.BubbleSort;

import java.util.Random;

/**
 * Genera arreglos de calificaciones en la escala 0.0 a 5.0 con un decimal.
 */
public final class GeneradorCalificaciones {

    public static final double MINIMA = 0.0;
    public static final double MAXIMA = 5.0;

    private GeneradorCalificaciones() {
    }

    /** n calificaciones aleatorias entre 0.0 y 5.0 (pueden repetirse). */
    public static double[] aleatorias(int n, Random random) {
        double[] notas = new double[n];
        for (int i = 0; i < n; i++) {
            notas[i] = random.nextInt(51) / 10.0; // 0, 0.1, ..., 5.0
        }
        return notas;
    }

    /**
     * Arreglo CASI ORDENADO de menor a mayor: se ordena un arreglo aleatorio
     * y luego se intercambian "desordenes" parejas de vecinos al azar.
     */
    public static double[] casiOrdenadas(int n, int desordenes, Random random) {
        double[] notas = aleatorias(n, random);
        BubbleSort.ordenarAscendente(notas);
        for (int k = 0; k < desordenes && n > 1; k++) {
            // Se busca una pareja de vecinos DIFERENTES; intercambiar dos notas iguales
            // no desordenaría nada (hay solo 51 notas posibles y se repiten mucho).
            int inicio = random.nextInt(n - 1);
            int i = inicio;
            while (notas[i] == notas[i + 1]) {
                i = (i + 1) % (n - 1);
                if (i == inicio) {
                    return notas; // todas las notas son iguales: no se puede desordenar
                }
            }
            double tmp = notas[i];
            notas[i] = notas[i + 1];
            notas[i + 1] = tmp;
        }
        return notas;
    }

    /** Valida que todas las calificaciones estén en la escala 0.0 - 5.0. */
    public static boolean sonValidas(double[] notas) {
        for (double nota : notas) {
            if (Double.isNaN(nota) || nota < MINIMA || nota > MAXIMA) {
                return false;
            }
        }
        return true;
    }
}
