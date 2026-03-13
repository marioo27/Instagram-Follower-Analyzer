# Instagram Numbers

Aplicacion de consola en Java que analiza los ficheros JSON exportados de Instagram para mostrar seguidores mutuos, gente que te sigue y no sigues, y gente que sigues y no te sigue.

## Requisitos

- Java 17 o superior
- Maven

### 📥 Cómo obtener tus ficheros JSON de Instagram

1. Ve a **Instagram** → **Tu perfil** → **Menú (≡)** → ***Tus datos de Instagram***
2. Selecciona ***Solicitar descarga*** → ***JSON***
3. Recibirás un **email** con enlace de descarga (1-48h)
4. Descarga el ZIP y descomprimelo, abre las carpetas **connections** → **followers_and_following** copia estos 2 ficheros a la raíz del proyecto:
   - `following.json`
   - `followers_1.json`

## Estructura de ficheros esperada

En la misma carpeta donde se ejecuta el programa deben existir estos ficheros:

- `following.json`: contiene el objeto `relationships_following` con los usuarios a los que sigues.
- `followers_1.json`: contiene un array con los usuarios que te siguen.

Estos ficheros se obtienen de la exportacion de datos de Instagram mencionada en el apartado anterior.

## Compilacion y ejecucion

```bash
mvn clean package
````

## 📁 Estructura del Proyecto
```bash
src
├───main
    ├───java
        └───mgr
            └───instagram
                    JsonManager.java
                    Main.java
