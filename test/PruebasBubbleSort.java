import ordenamiento.BubbleSort;
import ordenamiento.Estadisticas;
import ranking.Ranking;
import util.Arreglos;
import util.GeneradorCalificaciones;

import java.util.Random;

/**
 * Pruebas automáticas sin librerías externas.
 * Ejecutar con: java -cp out PruebasBubbleSort
 */
public class PruebasBubbleSort {

    private static int aprobadas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        // --- Ascendente y descendente básicos
        double[] a = {3.5, 1.0, 4.8, 2.2, 4.8, 0.0};
        BubbleSort.ordenarAscendente(a);
        verificar("ascendente ordena de menor a mayor",
                iguales(a, new double[] {0.0, 1.0, 2.2, 3.5, 4.8, 4.8}));
        double[] d = {3.5, 1.0, 4.8, 2.2, 4.8, 0.0};
        BubbleSort.ordenarDescendente(d);
        verificar("descendente ordena de mayor a menor",
                iguales(d, new double[] {4.8, 4.8, 3.5, 2.2, 1.0, 0.0}));

        // --- Casos borde
        verificar("arreglo vacío: 0 comparaciones",
                BubbleSort.ordenarAscendente(new double[0]).getComparaciones() == 0);
        verificar("arreglo null: no lanza excepción",
                BubbleSort.ordenarAscendente(null).getComparaciones() == 0);
        double[] uno = {4.0};
        verificar("un solo elemento: 0 comparaciones y queda igual",
                BubbleSort.ordenarAscendente(uno).getComparaciones() == 0 && uno[0] == 4.0);
        double[] iguales = {3.0, 3.0, 3.0, 3.0};
        Estadisticas e = BubbleSort.ordenarAscendente(iguales);
        verificar("todos iguales: 0 intercambios y 1 pasada",
                e.getIntercambios() == 0 && e.getPasadas() == 1);

        // --- Mejor caso: ya ordenado
        double[] ordenado = {1.0, 2.0, 3.0, 4.0, 5.0};
        e = BubbleSort.ordenarAscendente(ordenado);
        verificar("mejor caso: n-1 comparaciones, 0 intercambios, 1 pasada",
                e.getComparaciones() == 4 && e.getIntercambios() == 0 && e.getPasadas() == 1);

        // --- Peor caso: orden inverso sin repetidos
        double[] inverso = {5.0, 4.0, 3.0, 2.0, 1.0};
        e = BubbleSort.ordenarAscendente(inverso);
        verificar("peor caso: n(n-1)/2 = 10 comparaciones e intercambios",
                e.getComparaciones() == 10 && e.getIntercambios() == 10);

        // --- Casi ordenado: un solo par de vecinos intercambiado
        double[] casi = {1.0, 2.0, 4.0, 3.0, 5.0};
        e = BubbleSort.ordenarAscendente(casi);
        verificar("casi ordenado: 1 intercambio y 2 pasadas",
                e.getIntercambios() == 1 && e.getPasadas() == 2);

        // --- Descendente sobre arreglo ascendente = peor caso de la descendente
        double[] asc = {1.0, 2.0, 3.0, 4.0, 5.0};
        e = BubbleSort.ordenarDescendente(asc);
        verificar("descendente sobre ascendente: 10 intercambios",
                e.getIntercambios() == 10 && Arreglos.estaOrdenadoDescendente(asc));

        // --- Ranking
        double[] grupo = {3.5, 4.2, 2.8, 4.8, 3.0, 4.2, 1.9, 5.0, 3.7, 4.5, 2.5, 4.2};
        double[] top = Ranking.cincoMasAltas(grupo);
        verificar("top 5 correcto con repetidos",
                iguales(top, new double[] {5.0, 4.8, 4.5, 4.2, 4.2}));
        verificar("top 5 no modifica el arreglo original", grupo[0] == 3.5 && grupo[11] == 4.2);
        double[] desc = Arreglos.copiar(grupo);
        BubbleSort.ordenarDescendente(desc);
        verificar("empates fuera del top: 1 (un 4.2 adicional)", Ranking.empatesFueraDelTop(desc) == 1);
        verificar("top con menos de 5 notas devuelve todas",
                iguales(Ranking.cincoMasAltas(new double[] {2.0, 4.0, 3.0}), new double[] {4.0, 3.0, 2.0}));

        // --- Generador
        Random random = new Random(1);
        double[] aleatorias = GeneradorCalificaciones.aleatorias(500, random);
        verificar("generador: notas aleatorias en escala 0.0 - 5.0",
                GeneradorCalificaciones.sonValidas(aleatorias));
        double[] casiGen = GeneradorCalificaciones.casiOrdenadas(500, 3, random);
        e = BubbleSort.ordenarAscendente(Arreglos.copiar(casiGen));
        verificar("generador: casi ordenado tiene entre 1 y 3 intercambios",
                e.getIntercambios() >= 1 && e.getIntercambios() <= 3);

        // --- Propiedad: intercambios = inversiones; resultado ordenado y es permutación
        boolean intercambiosOk = true;
        boolean ordenadoOk = true;
        boolean permutacionOk = true;
        boolean maxComparacionesOk = true;
        for (int prueba = 0; prueba < 300; prueba++) {
            int n = random.nextInt(60);
            double[] datos = GeneradorCalificaciones.aleatorias(n, random);
            double[] copia = Arreglos.copiar(datos);
            Estadisticas est = BubbleSort.ordenarAscendente(copia);
            if (est.getIntercambios() != contarInversiones(datos)) {
                intercambiosOk = false;
            }
            if (!Arreglos.estaOrdenadoAscendente(copia)) {
                ordenadoOk = false;
            }
            if (!mismaFrecuencia(datos, copia)) {
                permutacionOk = false;
            }
            if (est.getComparaciones() > (long) n * (n - 1) / 2) {
                maxComparacionesOk = false;
            }
        }
        verificar("300 arreglos aleatorios: intercambios = número de inversiones", intercambiosOk);
        verificar("300 arreglos aleatorios: el resultado queda ordenado", ordenadoOk);
        verificar("300 arreglos aleatorios: no se pierden ni duplican notas", permutacionOk);
        verificar("300 arreglos aleatorios: comparaciones <= n(n-1)/2", maxComparacionesOk);

        System.out.println();
        System.out.println("Pruebas aprobadas: " + aprobadas + " | fallidas: " + fallidas);
        if (fallidas > 0) {
            System.exit(1);
        }
    }

    /** Pares (i < j) con a[i] > a[j]: exactamente los que Bubble Sort debe intercambiar. */
    private static long contarInversiones(double[] a) {
        long inversiones = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    inversiones++;
                }
            }
        }
        return inversiones;
    }

    /** Compara cuántas veces aparece cada nota (0.0 .. 5.0) en ambos arreglos. */
    private static boolean mismaFrecuencia(double[] x, double[] y) {
        if (x.length != y.length) {
            return false;
        }
        int[] conteo = new int[51];
        for (double v : x) {
            conteo[(int) Math.round(v * 10)]++;
        }
        for (double v : y) {
            conteo[(int) Math.round(v * 10)]--;
        }
        for (int c : conteo) {
            if (c != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean iguales(double[] x, double[] y) {
        if (x.length != y.length) {
            return false;
        }
        for (int i = 0; i < x.length; i++) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            aprobadas++;
            System.out.println("[OK]    " + descripcion);
        } else {
            fallidas++;
            System.out.println("[FALLA] " + descripcion);
        }
    }
}
