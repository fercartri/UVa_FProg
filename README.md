# Wordle - El programa que juega como tú

> **Asignatura:** Fundamentos de Programación  
> **Grado:** Ingeniería Informática  
> **Universidad:** Universidad de Valladolid  

Este proyecto implementa una versión del popular juego **Wordle**, adaptada para que el programa sea quien adivine la palabra que el usuario piensa. Con funcionalidades básicas y avanzadas, el programa simula una experiencia interactiva basada en la lógica y las pistas proporcionadas por el usuario.

---

## Introducción

**Wordle** es un juego de palabras creado por **Josh Wardle** en 2021 que se volvió viral rápidamente. En esta adaptación:
- El usuario piensa una palabra de 5 letras.
- El programa intenta adivinar la palabra en **6 intentos o menos**.
- Después de cada intento, el usuario evalúa la propuesta del programa con una cadena de colores codificada en dígitos:
  - `2` (verde): La letra está en la palabra y en la posición correcta.
  - `1` (naranja): La letra está en la palabra pero en la posición incorrecta.
  - `0` (gris): La letra no está en la palabra.

---

## Objetivo del Proyecto

El objetivo es desarrollar un programa que:
1. **Interactúe con el usuario**, interpretando sus pistas.
2. **Busque en un diccionario interno de palabras** de 5 letras aquellas que cumplan las pistas proporcionadas.
3. **Aprenda nuevas palabras** al terminar una partida perdida y las guarde para futuras ejecuciones (en la versión completa).

---

## Funcionalidades

### Versión Básica
- Juego interactivo con validación de entradas del usuario.
- Adición de palabras aprendidas a un diccionario interno (máximo 100 palabras).

### Versión Completa
- Persistencia de datos:
  - **Diccionario actualizado** con palabras aprendidas.
  - **Historial de partidas jugadas y ganadas** por el programa y el usuario.
- Comprobación automática de errores o trampas en las cadenas de colores proporcionadas por el usuario.

### Extras (Versión Especial)
- Mejoras adicionales aprobadas por el profesor (como estrategias avanzadas de selección de palabras).

---

## Herramientas Utilizadas
Lenguaje: Java.
Estrategia: Búsqueda en diccionario interno para proponer palabras que cumplan las pistas dadas.
