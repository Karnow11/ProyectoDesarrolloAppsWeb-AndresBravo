# ProyectoDesarrolloAppsWeb-AndresBravo
 Proyecto Web semestral del ramo Desarrollo de Apps Web

Actualización Tarea 2
Debido a muuuchos problemas personales no pude entregar esta tarea en su totalidad, pero aqui las funcionalidades implementadas

- Index: Botones arreglados para funcionar con flask y tabla resumen hecha con la base de datos (guardado de archivos no logrado)
- Agregar actividad: Se pueden agregar las actividades a la base de datos, los temas y contactos, las fotos no se guardan.
- Listado: Se muestran las 10 ultimas instancias de la base de datos, no alcancé a armar el sistema de paginas

Actualización Tarea 4:
-Se pone una imagen default en el inicio, pues nunca se hizo bien la funcionalidad de agregar una imagen al agregar actividades
-Se decidió generar una columna oculta para la tabla por falta de tiempo, pues gasté mi tiempo en hacer funcionar una vista adicional, que al percatarme de que no era posible seguir las instrucciones siguiente con ella decidí generar el envio de notas por la misma pagina, así que se quita el hidden de la columna para agregar notas y se muestra un formulario con las notas 1-7 con la posibilidad de envitar una nota o cancelar el envio, donde al apretar alguno se vuelve a cerrar la columna hidden, además se agregó un alert al enviar la nota para mas cache, además de que me sirvió para diagnosticar errores en el envio a la base de datos.
-Se agrega la base de datos al proyecto por comodidad, pero se sabe que no es una buena practica y debería estar por fuera.
-Se mantienen los archivos antiguos en la rama por comodidad de trabajo.
-Se evita el uso de fragmentos por tiempo, pero igualmente pudo ser usado, pues se reutilizan los headers del index, aunque en el resto de vistas esto no pasaba, por lo cual sería un poco un desperdicio de tanto trabajo para solo usarlo una vez.
-La nota se muestra con dos decimales por claridad visual, menos podía dar problemas al diagnosticar problemas y mas resulta poco estetico.