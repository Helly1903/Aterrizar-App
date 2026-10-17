# ✈️ Aterrizar CO — Primer avance

Aplicación Android de una **agencia de viajes**, hecha con **Kotlin y Jetpack Compose**.

> **Estado:** primer avance. Por ahora es solo la interfaz (parte estética). Los botones y el buscador todavía no hacen nada.



## 🎯 Sobre la actividad

Actividad basada en la interfaz de ElectroStore hecha en clase el viernes 2 de octubre. Se adaptó a la temática asignada (agencia de viajes) y se optimizaron las Cards.

| Requisito | Cumplido | Dónde verlo |
|---|---|---|
| Interfaz adaptada a la temática | ✅ | [MainActivity.kt](app/src/main/java/com/example/aterrizar_app/MainActivity.kt) |
| Colección de datos con la información de las Cards | ✅ | [Destino.kt](app/src/main/java/com/example/aterrizar_app/Destino.kt) |
| Recorrer la colección con un `for` para generar las Cards | ✅ | [MainActivity.kt](app/src/main/java/com/example/aterrizar_app/MainActivity.kt) → función `AterrizarApp()` |
| Sin código repetido por Card | ✅ | Una sola función `DestinoCard(destino)` |

---

## 🗂️ Navega por el proyecto

| Qué | Archivo o carpeta |
|---|---|
| Pantalla principal (header, buscador, categorías, Cards) | [MainActivity.kt](app/src/main/java/com/example/aterrizar_app/MainActivity.kt) |
| **Colección de datos de las Cards** (`data class Destino` y lista `destinos`) | [Destino.kt](app/src/main/java/com/example/aterrizar_app/Destino.kt) |
| Colores de la marca | [Color.kt](app/src/main/java/com/example/aterrizar_app/ui/theme/Color.kt) |
| Tema de la app | [Theme.kt](app/src/main/java/com/example/aterrizar_app/ui/theme/Theme.kt) |
| Tipografía | [Type.kt](app/src/main/java/com/example/aterrizar_app/ui/theme/Type.kt) |
| 🖼️ **Imágenes de los destinos** | [app/src/main/res/drawable](app/src/main/res/drawable) |
| Fuente del logo (Fredoka Bold) | [app/src/main/res/font](app/src/main/res/font) |
| Historial de commits | [Ver commits](https://github.com/Helly1903/Aterrizar-App/commits/master) |

---

## 🔍 ¿Dónde está la información de las Cards?

Todos los datos están en un solo lugar: [`Destino.kt`](app/src/main/java/com/example/aterrizar_app/Destino.kt).

```kotlin
data class Destino(
    val nombre: String,
    val pais: String,
    val descripcion: String,
    val duracion: String,
    val precio: String,
    val categoria: String,
    val colorCategoria: Color,
    val imagen: Int
)

val destinos = listOf(
    Destino(nombre = "Cartagena", pais = "Colombia", /* ... */),
    Destino(nombre = "Cancún", pais = "México", /* ... */),
    // ...
)
```

Para agregar un destino nuevo solo hay que añadir un `Destino(...)` a la lista y poner su imagen en `drawable`.

## 🔁 ¿Cómo se generan las Cards?

En [`MainActivity.kt`](app/src/main/java/com/example/aterrizar_app/MainActivity.kt), un `for` recorre la lista y crea una Card por cada destino:

```kotlin
for (destino in destinos) {
    DestinoCard(destino = destino)
    Spacer(modifier = Modifier.height(16.dp))
}
```

---

## 🧳 Destinos

| Destino | País | Categoría | Duración | Precio |
|---|---|---|---|---|
| Cartagena | Colombia | 🏛️ Historia | 5 días / 4 noches | $1.850.000 |
| Cancún | México | 🏖️ Playa | 6 días / 5 noches | $3.200.000 |
| Machu Picchu | Perú | 🏛️ Historia | 5 días / 4 noches | $3.900.000 |
| Roma | Italia | 🏛️ Historia | 8 días / 7 noches | $6.500.000 |
| París | Francia | 🌆 Ciudad | 7 días / 6 noches | $6.900.000 |
| Santorini | Grecia | 🏖️ Playa | 7 días / 6 noches | $7.200.000 |
| Río de Janeiro | Brasil | 🏖️ Playa | 6 días / 5 noches | $4.600.000 |
| Patagonia | Argentina | 🏔️ Montaña | 8 días / 7 noches | $5.400.000 |

---

## 🎨 Paleta de colores

| Color | Código | Uso |
|---|---|---|
| Azul Océano | `#0B3C5D` | Color principal, títulos |
| Turquesa | `#1CA7C4` | Secundario, playas |
| Naranja Atardecer | `#FF7A3D` | Precios y botón Reservar |
| Arena | `#FFF6E9` | Fondo |
| Ocre Historia | `#C9923E` | Categoría Historia |
| Verde Montaña | `#3E8E5A` | Categoría Montaña |
| Morado Ciudad | `#8E5BD0` | Categoría Ciudad |
| Texto Oscuro | `#1F2933` | Texto general |

---

## ✨ Componentes de la interfaz

- **Header** con degradado, círculos decorativos, logo y estela de vuelo dibujada con `Canvas`.
- **Buscador** con ícono (solo visual).
- **Categorías** en fila con scroll horizontal.
- **Cards de destino** con imagen, etiqueta de categoría, descripción, duración, precio y botón.
---

## ▶️ Cómo ejecutarlo

1. Clona el repositorio:
```bash
   git clone https://github.com/Helly1903/Aterrizar-App.git
```
2. Ábrelo en Android Studio.
3. Espera a que Gradle sincronice.
4. Ejecuta la app en un emulador o en un celular.

---

👤 **Autor:** Santiago — [@Helly1903](https://github.com/Helly1903)
