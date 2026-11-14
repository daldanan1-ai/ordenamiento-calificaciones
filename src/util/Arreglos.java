package util;

/**
 * Utilidades manuales para arreglos de calificaciones (no se usa java.util.Arrays).
 */
public final class Arreglos {

    private Arreglos() {
    }

    /** Copia elemento por elemento, para no modificar el arreglo original. */
    public static double[] copiar(double[] origen) {
        if (origen == null) {
            return new double[0];
        }
        double[] copia = new double[origen.length];
        for (int i = 0; i < origen.length; i++) {
            copia[i] = origen[i];
        }
        return copia;
    }

    /** Texto con el formato [4.5, 3.0, 2.8] y un decimal por calificación. */
    public static String formatear(double[] a) {
        if (a == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(String.format(java.util.Locale.US, "%.1f", a[i]));
        }
        return sb.append("]").toString();
    }

    public static boolean estaOrdenadoAscendente(double[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean estaOrdenadoDescendente(double[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] < a[i]) {
                return false;
            }
        }
        return true;
    }
}
