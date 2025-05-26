-- ============================
-- Roles
-- ============================

INSERT INTO directus_roles (id, name, icon, description, parent) VALUES
  ('9e957475-6ab1-4bf8-9acc-2abae37cf58d', 'Usuario', 'user', 'Rol para usuarios autenticados', NULL);

-- ============================
-- Usuarios de Directus
-- (Ejemplo con hash de contraseña, modificar según tu entorno)
-- ============================

INSERT INTO directus_users (id, first_name, email, password, role, status) VALUES
  (gen_random_uuid(), 'Usuario Prueba 1', 'usuario1@ejemplo.com', '$argon2i$v=19$m=16,t=2,p=1$Qk4zb1RuWDJCcVRpb2JoSw$SzyC3tGEP6swGBt0TvBkiw',
    '9e957475-6ab1-4bf8-9acc-2abae37cf58d', 'active'),
  (gen_random_uuid(), 'Usuario Prueba 2', 'usuario2@ejemplo.com', '$argon2i$v=19$m=16,t=2,p=1$Qk4zb1RuWDJCcVRpb2JoSw$SzyC3tGEP6swGBt0TvBkiw',
    '9e957475-6ab1-4bf8-9acc-2abae37cf58d', 'active');

-- ============================
-- Perfil de usuario (travel_buddy_user equivalente)
-- ============================

CREATE TABLE travel_buddy_user (
    id_usuario SERIAL PRIMARY KEY,
    directus_user_id UUID UNIQUE NOT NULL REFERENCES directus_users(id) ON DELETE CASCADE,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    ubicacion VARCHAR(255)
);

-- Insertar perfiles para usuarios creados arriba

INSERT INTO travel_buddy_user (directus_user_id, telefono, fecha_nacimiento, ubicacion) VALUES
  ((SELECT id FROM directus_users WHERE email = 'usuario1@ejemplo.com'), '3001234567', '1990-01-01', 'Ciudad A'),
  ((SELECT id FROM directus_users WHERE email = 'usuario2@ejemplo.com'), '3007654321', '1985-05-15', 'Ciudad B');

-- ============================
-- Tabla Imagen
-- ============================

CREATE TABLE Imagen (
    id_imagen SERIAL PRIMARY KEY,
    nombre_imagen VARCHAR(255),
    url_imagen VARCHAR(255) NOT NULL
);

-- ============================
-- Tabla Evento
-- ============================

CREATE TABLE Evento (
    id_evento SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    codigo_union VARCHAR(20) UNIQUE NOT NULL,
    id_imagen INTEGER,
    id_administrador INTEGER NOT NULL REFERENCES travel_buddy_user(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_imagen) REFERENCES Imagen(id_imagen)
);

-- ============================
-- Tabla Actividad
-- ============================

CREATE TABLE Actividad (
    id_actividad SERIAL PRIMARY KEY,
    id_evento INTEGER NOT NULL REFERENCES Evento(id_evento) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_actividad DATE,
    hora_actividad VARCHAR(10),
    ubicacion VARCHAR(255),
    id_imagen INTEGER,
    FOREIGN KEY (id_imagen) REFERENCES Imagen(id_imagen)
);

-- ============================
-- Tabla ParticipanteEvento
-- ============================

CREATE TABLE ParticipanteEvento (
    id_participacion SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL REFERENCES travel_buddy_user(id_usuario) ON DELETE CASCADE,
    id_evento INTEGER NOT NULL REFERENCES Evento(id_evento) ON DELETE CASCADE,
    rol VARCHAR(20) CHECK (rol IN ('coordinador', 'participante')) DEFAULT 'participante',
    CONSTRAINT uq_usuario_evento UNIQUE (id_usuario, id_evento)
);

-- ============================
-- Insertar datos de prueba para Evento y Actividad
-- ============================

INSERT INTO Evento (nombre, descripcion, codigo_union, id_administrador) VALUES
('Evento Prueba 1', 'Descripción del evento 1', 'EVT123', 1),
('Evento Prueba 2', 'Descripción del evento 2', 'EVT456', 2);

INSERT INTO Actividad (id_evento, nombre, descripcion) VALUES
(1, 'Actividad 1 Evento 1', 'Descripción actividad 1'),
(1, 'Actividad 2 Evento 1', 'Descripción actividad 2'),
(2, 'Actividad 1 Evento 2', 'Descripción actividad 3');

-- ============================
-- Asociar usuarios a eventos en ParticipanteEvento
-- ============================

INSERT INTO ParticipanteEvento (id_usuario, id_evento, rol) VALUES
(1, 1, 'coordinador'),
(2, 2, 'coordinador');

-- ============================
-- Permisos para directus_users (crear y leer)
-- ============================

INSERT INTO directus_permissions (
    collection,
    action,
    fields,
    policy,
    permissions,
    validation
) VALUES
('directus_users', 'create', '*', (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'), '{}', '{}'),
('directus_users', 'read', '*', (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'), '{}', '{}');

-- ============================
-- Permisos para travel_buddy_user (crear y leer)
-- ============================

INSERT INTO directus_permissions (
    collection,
    action,
    fields,
    policy,
    permissions,
    validation
) VALUES
('travel_buddy_user', 'create', '*', (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'), '{}', '{}'),
('travel_buddy_user', 'read', '*', (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'), '{}', '{}');

-- ============================
-- Política para manejar eventos y actividades
-- ============================

INSERT INTO directus_policies(id, name, icon) VALUES
('d2b33d94-2f31-4d49-bc62-2afea76fc59e', 'Manage events', 'badge');

-- ============================
-- Permisos CRUD para Evento
-- ============================

INSERT INTO directus_permissions (collection, action, fields, policy, permissions, validation) VALUES
('evento', 'create', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}'),
('evento', 'read', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}'),
('evento', 'update', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}'),
('evento', 'delete', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}');

-- ============================
-- Permisos CRUD para Actividad
-- ============================

INSERT INTO directus_permissions (collection, action, fields, policy, permissions, validation) VALUES
('actividad', 'create', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}'),
('actividad', 'read', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}'),
('actividad', 'update', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}'),
('actividad', 'delete', '*', 'd2b33d94-2f31-4d49-bc62-2afea76fc59e', '{}', '{}');
