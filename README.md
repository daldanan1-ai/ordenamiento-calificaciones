# Ranking de calificaciones con Bubble Sort

Actividad de **Estructura de Datos – Unidad 2: Algoritmos de ordenamiento**.
Ejercicio asignado: **1. Ranking de calificaciones**.

## Descripción

El programa trabaja con un **arreglo `double[]`** con las calificaciones de un grupo de estudiantes, en la escala de 0.0 a 5.0, y:

1. Ordena las calificaciones de **menor a mayor** con **Bubble Sort**, implementado a mano.
2. Usa el mismo algoritmo, **modificado**, para ordenarlas de **mayor a menor**.
3. Muestra el arreglo **antes y después** de ordenarlo.
4. Identifica las **cinco calificaciones más altas** y avisa cuando hay empates con el 5.º lugar.
5. **Contabiliza comparaciones, intercambios y pasadas**.
6. Prueba el algoritmo con **datos aleatorios** y con un **arreglo casi ordenado**, además del mejor caso (ya ordenado) y del peor caso (orden inverso).

No se usa `Arrays.sort()`, `Collections.sort()` ni ningún otro mecanismo automático del lenguaje. Tampoco se usa `java.util.Arrays` para copiar ni para imprimir.

## Estructura del proyecto

```
src/
  ordenamiento/BubbleSort.java          Bubble Sort ascendente y descendente (con traza opcional)
  ordenamiento/Estadisticas.java        Comparaciones, intercambios y pasadas
  ranking/Ranking.java                  Top 5 y empates con el 5.º lugar
  util/Arreglos.java                    Copiar, imprimir y verificar el orden
  util/GeneradorCalificaciones.java     Datos aleatorios y casi ordenados
  app/Main.java                         Menú interactivo y demostraciones
test/
  PruebasBubbleSort.java                20 pruebas automáticas
ejecutar.bat                            Script para compilar y ejecutar en Windows
.vscode/                                Configuración para ejecutar desde VS Code
```

## Cómo funciona Bubble Sort

En cada **pasada** el algoritmo recorre el arreglo comparando **vecinos** (`a[i-1]`, `a[i]`) y los intercambia si están en el orden incorrecto. Al terminar la pasada, el mayor de la parte que aún no está ordenada queda en su posición definitiva al final, como una burbuja que sube.

```
Inicial:   [3.5, 1.9, 4.8, 2.8, 4.2]
Pasada 1:  [1.9, 3.5, 2.8, 4.2, 4.8]   <- 4.8 llega al final
Pasada 2:  [1.9, 2.8, 3.5, 4.2, 4.8]
Pasada 3:  [1.9, 2.8, 3.5, 4.2, 4.8]   <- sin intercambios: termina
```

**Optimizaciones aplicadas:**
- Cada pasada recorre un elemento menos, porque el final ya está ordenado.
- La **bandera `huboIntercambio`** detiene el algoritmo cuando una pasada no hace ningún intercambio. Por eso, con un arreglo ya ordenado basta una pasada (O(n)).

**De menor a mayor → de mayor a menor:** el único cambio es la condición de intercambio, que pasa de `a[i-1] > a[i]` a `a[i-1] < a[i]`.

**Estabilidad:** dos calificaciones iguales nunca se intercambian, porque la comparación es estricta. Por eso el algoritmo es estable y funciona correctamente con valores repetidos.

## Complejidad

| Caso | Comparaciones | Intercambios | Complejidad |
|---|---|---|---|
| Mejor caso (ya ordenado) | n − 1 | 0 | **O(n)** |
| Caso promedio (aleatorio) | ≈ n(n − 1)/2 | ≈ n(n − 1)/4 | **O(n²)** |
| Peor caso (orden inverso) | n(n − 1)/2 | n(n − 1)/2 | **O(n²)** |
| Casi ordenado (k parejas de vecinos intercambiadas) | ≈ 2n (2 pasadas) | k | ≈ **O(n)** |

- **Memoria extra:** O(1), porque ordena *in-place*.
- **Top 5:** O(n²) por el ordenamiento, más O(1) para tomar las 5 primeras posiciones.
- Se cumple que **el número de intercambios es igual al número de inversiones** del arreglo, es decir, de pares `i < j` con `a[i] > a[j]`. Las pruebas automáticas lo verifican en 300 arreglos aleatorios.

## Requisitos

- Java **17 o superior** (probado con Microsoft OpenJDK 21).
- Opcional: Visual Studio Code con el *Extension Pack for Java*.

## Cómo ejecutar

### Desde la terminal (Windows)

```bat
ejecutar.bat            :: menú interactivo
ejecutar.bat demo       :: ejecuta todas las demostraciones sin menú
ejecutar.bat pruebas    :: ejecuta las pruebas automáticas
```

### Manualmente (cualquier sistema)

```bash
javac -encoding UTF-8 -d out src/ordenamiento/*.java src/ranking/*.java src/util/*.java src/app/*.java test/*.java
java -cp out app.Main           # menú interactivo
java -cp out app.Main demo      # demostración completa
java -cp out PruebasBubbleSort  # pruebas
```

### Desde Visual Studio Code

1. Abrir la carpeta del proyecto (`Archivo > Abrir carpeta`).
2. Ir a **Ejecutar y depurar** (Ctrl+Shift+D).
3. Elegir **Menú interactivo**, **Demostración completa** o **Pruebas** y presionar F5.

## Menú del programa

```
============ RANKING DE CALIFICACIONES (BUBBLE SORT) ============
1. Ordenar las calificaciones del grupo de ejemplo
2. Ingresar mis propias calificaciones
3. Probar con calificaciones aleatorias
4. Probar con un arreglo casi ordenado
5. Ver la traza paso a paso (pasada por pasada)
6. Comparar: aleatorio vs. casi ordenado vs. ordenado vs. inverso
7. Comparar con diferentes tamaños de arreglo
0. Salir
```

## Resultados de ejemplo

Grupo de 15 estudiantes:

```
Antes:          [3.5, 4.2, 2.8, 4.8, 3.0, 4.2, 1.9, 5.0, 3.7, 4.5, 2.5, 4.2, 3.9, 4.0, 3.2]
Menor a mayor:  [1.9, 2.5, 2.8, 3.0, 3.2, 3.5, 3.7, 3.9, 4.0, 4.2, 4.2, 4.2, 4.5, 4.8, 5.0]
   comparaciones: 99 | intercambios: 52 | pasadas: 11
Mayor a menor:  [5.0, 4.8, 4.5, 4.2, 4.2, 4.2, 4.0, 3.9, 3.7, 3.5, 3.2, 3.0, 2.8, 2.5, 1.9]
   comparaciones: 84 | intercambios: 50 | pasadas: 8
TOP 5: 5.0, 4.8, 4.5, 4.2, 4.2  (una calificación más de 4.2 empata con el 5.º lugar)
```

Mismo tamaño (n = 20) con distinta disposición inicial:

| Tipo de arreglo | Comparaciones | Intercambios | Pasadas |
|---|---|---|---|
| Aleatorio | 189 | 104 | 18 |
| Casi ordenado | 37 | 2 | 2 |
| Ya ordenado (mejor caso) | 19 | 0 | 1 |
| Orden inverso (peor caso) | 190 | 186* | 19 |

\* Son menos de 190 porque hay notas repetidas, y dos notas iguales no se intercambian.

Diferentes tamaños (promedio de 5 ejecuciones):

| n | Aleatorio: comparaciones | Aleatorio: intercambios | Casi ordenado: comparaciones | Casi ordenado: intercambios | n(n−1)/2 |
|---|---|---|---|---|---|
| 10 | 42 | 22 | 16 | 1 | 45 |
| 100 | 4.886 | 2.433 | 197 | 2 | 4.950 |
| 1.000 | 496.693 | 247.474 | 1.997 | 2 | 499.500 |
| 5.000 | 12.487.196 | 6.072.783 | 9.997 | 2 | 12.497.500 |

## Conclusiones

- Con datos aleatorios, Bubble Sort hace casi n(n−1)/2 comparaciones: al multiplicar *n* por 10, el trabajo se multiplica por unas 100. Es O(n²).
- Con datos casi ordenados, la bandera `huboIntercambio` lo vuelve casi lineal. Es el escenario donde Bubble Sort es competitivo.
- Para ordenar de mayor a menor solo hay que invertir la condición de comparación; el resto del algoritmo no cambia.
- Bubble Sort es sencillo y estable, pero para grupos grandes conviene un algoritmo O(n log n), como Merge Sort o Quick Sort.
