## 🔐 Permisos y Roles 

Actualmente, se han definido *permisos públicos básicos* para permitir el acceso inicial de usuarios no autenticados a ciertos recursos clave. Esto facilita el registro y visualización básica de datos sin requerir inicio de sesión. No se tiene contemplado la creacion de un rol permanente que se asigne al momento de crear el usuario, posteriormente se creara una forma de que el evento reconozca al administrador de los eventos, pero para eso no es necesario definir un rol especifico.

### Permisos del Rol Público 
Se han habilitado las siguientes acciones para el rol *public*:

#### directus_users
- ✅ create: Permite registrar nuevos usuarios.
- ✅ read: Permite leer los usuarios existentes.

#### travel_buddy_user
- ✅ create: Permite la creación de perfiles extendidos.
- ✅ read: Permite leer la información pública de los usuarios.


# Sprint 2.

## Objetivos.

El objetivo principal de este sprint es el de añadir las funcionalidades CRUD para eventos y actividades, esto enfocado en el usuario que toma el "rol" de coordinador en los eventos. Por tanto, en la aplicación se encontrarán las funcionalidades:

- Creación, visualización, edición y eliminación de eventos y actividades.
- Se muestran unicamente los eventos que el usuario coordina.

Para ello, se añadieron los siguientes elementos al data.sql:

### Rol Usuario.

El rol usuario es asignado automáticamente a los usuarios registrados en la aplicación. Este rol tiene asginado la politica Manage Events.

### Politica Manage Events.

La politica Manage Events es aquella que contiene los permisos CRUD necesarios sobre las tablas Evento y Actividad. Además, contiene permisos para lectura de las tablas Travel Buddy User y Directus User, esto para lograr pequeñas funcionalidades como acceder y almacenar el ID y nombre del usuario loggeado.

### Aclaración.

Se buscó e intentó de todas las maneras el asignar la politica Manage Events al rol Usuario de manera automática en el archivo data.sql, sin embargo, no se logró. Ninguna de las tablas como directus_permissions o directus_policies contiene una columna "role; la tabla directus_roles no ofrece una columna "policy"; tampoco se encontró una tabala pivote. 

Por tanto, la asignación de la politica se debe hacer de forma manual en el panel administrativo de Directus.

# Sprint 3.

## Objetivos.

El objetivo principal de este sprint es el de añadir tanto las funcionalidades CRUD para gastos, asi como la posibilidad de unirse a otros eventos por medio de un codigo y subir imagenes a los eventos y actividades. Por tanto, en la aplicación final se encontrarán las siguientes funcionalidades.

- Creación, visualización, edición y eliminación de eventos, actividades y gastos.
- Una pantalla "Mis gastos", donde el usuario puede ver listados los gastos que tiene asignados y la totalidad que debe.
- Opcion para unirse a eventos, donde deberá ingresar el codigo de union proporcionado por el administrador del evento u otros participantes.
- Se muestran tanto los eventos que el usuario coordina como en los que participa. En la pantalla principal puede seleccionar si quiere ver solo los eventos que coordina o solo los eventos en los que participa.

### Politica Manage Events.

La politica Manage Events cuenta con los permisos necesarios para el correcto funcionamiento de la aplicación. Esta aun debe asignarse manualmente desde el panel administrativo de Directus.
  
