package ordenamiento;

/**
 * Métricas de una ejecución de Bubble Sort.
 *
 * - comparaciones: veces que se compararon dos calificaciones vecinas.
 * - intercambios:  veces que dos calificaciones vecinas cambiaron de lugar.
 * - pasadas:       recorridos completos realizados sobre el arreglo.
 */
public class Estadisticas {

    private long comparaciones;
    private long intercambios;
    private int pasadas;

    void sumarComparacion() {
        comparaciones++;
    }

    void sumarIntercambio() {
        intercambios++;
    }

    void sumarPasada() {
        pasadas++;
    }

    public long getComparaciones() {
        return comparaciones;
    }

    public long getIntercambios() {
        return intercambios;
    }

    public int getPasadas() {
        return pasadas;
    }

    @Override
    public String toString() {
        return "comparaciones: " + comparaciones + " | intercambios: " + intercambios
                + " | pasadas: " + pasadas;
    }
}
