-- 1. Crear la Tabla Producto si no existe
CREATE TABLE IF NOT EXISTS producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    talla VARCHAR(255),
    color VARCHAR(255),
    precio INT,
    imagen VARCHAR(255),
    estado_producto BIT(1) DEFAULT 1,
    stock_actual INT
    );

-- 2. Insertar Datos Semilla para el equipo
INSERT INTO producto (nombre_producto, descripcion, talla, color, precio, imagen, estado_producto, stock_actual)
VALUES
    ('Polera Oversight', 'Polera de algodón premium oversize', 'L', 'Negro', 19990, 'polera_negra.jpg', 1, 50),
    ('Jeans Slim Fit', 'Jeans elásticos corte slim', '42', 'Azul', 29990, 'jeans_azul.jpg', 1, 30),
    ('Polerón Canguro', 'Polerón con capucha y bolsillo frontal', 'XL', 'Gris', 34990, 'poleron_gris.jpg', 1, 25),
    ('Casaca Mezclilla', 'Casaca clásica de mezclilla deslavada', 'M', 'Azul Claro', 39990, 'casaca_azul.jpg', 1, 15);