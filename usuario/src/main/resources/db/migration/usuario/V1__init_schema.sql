CREATE TABLE usuarios (
                          id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre_usuario VARCHAR(100) NOT NULL,
                          correo VARCHAR(150) NOT NULL UNIQUE,
                          contrasena VARCHAR(255) NOT NULL,
                          telefono VARCHAR(20),
                          estado_usuario VARCHAR(20) NOT NULL
);
