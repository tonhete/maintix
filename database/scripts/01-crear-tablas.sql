-- Tabla tipo_usuario
CREATE TABLE tipo_usuario (
  id INT IDENTITY(1,1) PRIMARY KEY,
  descripcion VARCHAR(100)
);

-- Tabla usuarios
CREATE TABLE usuarios (
  id INT IDENTITY(1,1) PRIMARY KEY,
  tipo_usuario_id INT,
  email VARCHAR(100) UNIQUE NOT NULL,
  passwd VARCHAR(100) NOT NULL,
  FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id)
);

-- Tabla proveedores
CREATE TABLE proveedores (
  id INT IDENTITY(1,1) PRIMARY KEY,
  nombre VARCHAR(100),
  tlf VARCHAR(20),
  direccion VARCHAR(255)
);

-- Tabla tipo_maquinaria
CREATE TABLE tipo_maquinaria (
  id INT IDENTITY(1,1) PRIMARY KEY,
  descripcion VARCHAR(100),
  mantenimientoA INT,
  mantenimientoB INT,
  mantenimientoC INT,
  despiece TEXT,
  proveedor_id INT,
  repuestos TEXT,
  FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

-- Tabla tipos_maquina
CREATE TABLE tipos_maquina (
  id INT IDENTITY(1,1) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL
);

-- Tabla equipo
CREATE TABLE equipo (
  id INT IDENTITY(1,1) PRIMARY KEY,
  tipo_maquinaria_id INT,
  fechaFabricacion DATE,
  numeroSerie VARCHAR(100),
  horasActuales INT,
  contadorTipoA INT,
  contadorTipoB INT,
  contadorTipoC INT,
  FOREIGN KEY (tipo_maquinaria_id) REFERENCES tipo_maquinaria(id)
);

-- Tabla tipos_mantenimiento
CREATE TABLE tipos_mantenimiento (
  id INT IDENTITY(1,1) PRIMARY KEY,
  nombre VARCHAR(10) NOT NULL,
  descripcion TEXT
);

-- Tabla mantenimientos
CREATE TABLE mantenimientos (
  id INT IDENTITY(1,1) PRIMARY KEY,
  equipo_id INT NOT NULL,
  tipo_mantenimiento_id INT NOT NULL,
  fecha_inicio DATETIME NOT NULL,
  fecha_fin DATETIME DEFAULT NULL,
  estado VARCHAR(20) DEFAULT 'pendiente',
  FOREIGN KEY (equipo_id) REFERENCES equipo(id),
  FOREIGN KEY (tipo_mantenimiento_id) REFERENCES tipos_mantenimiento(id)
);

-- Tabla items_mantenimiento
CREATE TABLE items_mantenimiento (
  id INT IDENTITY(1,1) PRIMARY KEY,
  tipo_maquina_id INT NOT NULL,
  tipo_mantenimiento_id INT NOT NULL,
  descripcion VARCHAR(255) NOT NULL,
  orden INT DEFAULT 0,
  FOREIGN KEY (tipo_maquina_id) REFERENCES tipos_maquina(id),
  FOREIGN KEY (tipo_mantenimiento_id) REFERENCES tipos_mantenimiento(id)
);

-- Tabla checklist_mantenimiento
CREATE TABLE checklist_mantenimiento (
  id INT IDENTITY(1,1) PRIMARY KEY,
  mantenimiento_id INT NOT NULL,
  item_id INT NOT NULL,
  completado BIT DEFAULT 0,
  observaciones TEXT,
  FOREIGN KEY (mantenimiento_id) REFERENCES mantenimientos(id),
  FOREIGN KEY (item_id) REFERENCES items_mantenimiento(id)
);

-- Tabla historico
CREATE TABLE historico (
  id INT IDENTITY(1,1) PRIMARY KEY,
  equipo_id INT,
  horasMaquina INT,
  clase VARCHAR(1) CHECK (clase IN ('A','B','C')),
  operario VARCHAR(100),
  incidencias TEXT,
  finalizado BIT,
  FOREIGN KEY (equipo_id) REFERENCES equipo(id)
);