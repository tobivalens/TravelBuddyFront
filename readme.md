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
