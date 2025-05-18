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