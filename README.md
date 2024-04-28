# Vynils Mobile App

Esta es la app móvil para el gestor de vinilos musicales

## Integrantes

- Santiago Alarcón
- Sebastián Tibaquirá
- Miguel Ávila
- Edwin Silva

## APK

El apk del proyecto se encuentra ubicado en la carpeta ./app y el nombre de la apk es: app-debug.apk

https://github.com/santiagoalarc/frontVynils/blob/develop/app/app-debug.apk

Para su descarga podemos dar click en la opción del icono de descargas como se muestra en la siguiente imagen:

<img width="1447" alt="image" src="https://github.com/santiagoalarc/frontVynils/assets/139934363/3c359b01-65db-49b1-95ed-f12df12667a2">


## Ejecución de forma local

Para correr la aplicación por medio de un IDE que ofrezca ejecución del demo en equipo móviles simulados, se recomienda usar Android Studio y seguir los siguientes pasos:

Para ejecutar un proyecto en Android Studio, sigue estos pasos:

1. Abre el proyecto: En el menú principal de Android Studio, selecciona "File" (Archivo) > "Open" (Abrir) y navega hasta la ubicación de tu proyecto. Selecciona la carpeta del proyecto y haz clic en "OK" (Aceptar).
2. Espera a que se cargue el proyecto: Android Studio cargará el proyecto y realizará cualquier sincronización de archivos necesaria.
Selecciona el dispositivo de destino: En la barra de herramientas superior, verás un menú desplegable que muestra los dispositivos virtuales y físicos disponibles. Selecciona el dispositivo en el que deseas ejecutar tu aplicación.
3. Ejecuta la aplicación: Haz clic en el botón "Run" (Ejecutar) en la barra de herramientas, que generalmente se representa con un ícono de reproducción verde. También puedes ejecutar la aplicación seleccionando "Run" > "Run 'app'" (Ejecutar > Ejecutar "app") en el menú.
<img width="1117" alt="image" src="https://github.com/santiagoalarc/frontVynils/assets/139934363/13819edf-9294-4056-afa5-1174c238a30c">

4. Observa la ejecución de la aplicación: Android Studio compilará tu aplicación y la ejecutará en el dispositivo seleccionado. Verás el progreso de la compilación en la ventana "Run" (Ejecución) en la parte inferior de Android Studio.
5. Interactúa con la aplicación: Una vez que la aplicación se ejecute en el dispositivo, podrás interactuar con ella como lo harías con cualquier otra aplicación de Android.

## Ejecución de API de consumo de forma local

Para la ejecución del proyecto con un consumo de una API de forma local, se espera que el sisguiente repo https://github.com/santiagoalarc/BackVynils sea instalado en la máquina y después de ello, se poceda a ejecutar el comando, dentro de la carpeta BackVynils:


```shell
$ docker compose up --build
```
Después de ejecutar este comando, el servicio como las base de datos estarán corriendo en un contenedor en Docker, con los puertos 3000 y 5432 respectivamente, si deseamos cambiar el consumo de la app movil, en la siguiente sección se especificará en donde cambiar la nueva ruta.

## Consumo de API externa
Actualmente el proyecto se encuentra consumiendo un API para la lectura y escritura de datos relacionados a la App. El repositorio con el codigo de dicha API se encuentra ubicado en el siguiente link: 
https://github.com/santiagoalarc/BackVynils

Para el cambio de la ruta de consumo, se recomienda modificar la variable BASE_URL ubicada en el archivo app/src/main/java/com/example/frontvynils/constants/StaticConstants.kt

