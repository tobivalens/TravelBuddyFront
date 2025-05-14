

CREATE TABLE Imagen (
    id_imagen SERIAL PRIMARY KEY,
    nombre_imagen VARCHAR(255),
    url_imagen VARCHAR(255) NOT NULL
);

CREATE TABLE travel_buddy_user (
    id_usuario SERIAL PRIMARY KEY,
    directus_user_id UUID UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    id_imagen INTEGER,
    ubicacion VARCHAR(255),
    FOREIGN KEY (directus_user_id) REFERENCES directus_users(id),
    FOREIGN KEY (id_imagen) REFERENCES Imagen(id_imagen)
);

CREATE TABLE Evento (
    id_evento SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    codigo_union VARCHAR(20) UNIQUE NOT NULL,
    id_imagen INTEGER,
    id_administrador INTEGER NOT NULL,
    FOREIGN KEY (id_administrador) REFERENCES travel_buddy_user(id_usuario),
    FOREIGN KEY (id_imagen) REFERENCES Imagen(id_imagen)
);

CREATE TABLE ParticipanteEvento (
    id_participacion SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_evento INTEGER NOT NULL,
    rol VARCHAR(20) CHECK (rol IN ('coordinador', 'participante')) DEFAULT 'participante',
    CONSTRAINT fk_usuario_participa FOREIGN KEY (id_usuario) REFERENCES travel_buddy_user(id_usuario),
    CONSTRAINT fk_evento_participa FOREIGN KEY (id_evento) REFERENCES Evento(id_evento),
    CONSTRAINT uq_usuario_evento UNIQUE (id_usuario, id_evento)
);

CREATE TABLE Actividad (
    id_actividad SERIAL PRIMARY KEY,
    id_evento INTEGER NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_actividad DATE,
    hora_actividad VARCHAR(10),
    ubicacion VARCHAR(255),
    id_imagen INTEGER,
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento),
    FOREIGN KEY (id_imagen) REFERENCES Imagen(id_imagen)
);

CREATE TABLE ArchivoAdjunto (
    id_archivo SERIAL PRIMARY KEY,
    id_actividad INTEGER NOT NULL,
    nombre_archivo VARCHAR(255),
    url_archivo VARCHAR(255),
    FOREIGN KEY (id_actividad) REFERENCES Actividad(id_actividad)
);

CREATE TABLE Gasto (
    id_gasto SERIAL PRIMARY KEY,
    id_evento INTEGER NOT NULL,
    deudor_id INTEGER NOT NULL,
    acreedor_id INTEGER NOT NULL,
    monto NUMERIC(10, 2) NOT NULL,
    descripcion VARCHAR(255),
    fecha DATE DEFAULT CURRENT_DATE,
    creado_por INTEGER NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento),
    FOREIGN KEY (deudor_id) REFERENCES travel_buddy_user(id_usuario),
    FOREIGN KEY (acreedor_id) REFERENCES travel_buddy_user(id_usuario),
    FOREIGN KEY (creado_por) REFERENCES travel_buddy_user(id_usuario)
);

CREATE TABLE Notificacion (
    id_notificacion SERIAL PRIMARY KEY,
    id_usuario_destino INTEGER NOT NULL,
    titulo VARCHAR(100),
    mensaje VARCHAR(500),
    fecha_creacion DATE DEFAULT CURRENT_DATE,
    leida BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario_destino) REFERENCES travel_buddy_user(id_usuario)
);


INSERT INTO directus_roles(id, name, icon, description)
VALUES('9e957475-6ab1-4bf8-9acc-2abae37cf58d', 'Usuario', 'user', 'Rol para usuarios autenticados');

INSERT INTO directus_permissions (
  collection,
  action,
  fields,
  policy,
  permissions,
  validation
) VALUES (
  'directus_users',
  'create',
  '*',
  (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'),
  '{}',
  '{}'
);

INSERT INTO directus_permissions (
  collection,
  action,
  fields,
  policy,
  permissions,
  validation
) VALUES (
  'directus_users',
  'read',
  '*',
  (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'),
  '{}',
  '{}'
);

INSERT INTO directus_permissions (
  collection,
  action,
  fields,
  policy,
  permissions,
  validation
) VALUES (
  'travel_buddy_user',
  'create',
  '*',
  (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'),
  '{}',
  '{}'
);


INSERT INTO directus_permissions (
  collection,
  action,
  fields,
  policy,
  permissions,
  validation
) VALUES (
  'travel_buddy_user',
  'read',
  '*',
  (SELECT id FROM directus_policies WHERE name LIKE '%public_label%'),
  '{}',
  '{}'
);

