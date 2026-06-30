CREATE TABLE pedido (
                        id_pedido INT AUTO_INCREMENT PRIMARY KEY,
                        id_usuario INT NOT NULL,
                        fecha_pedido DATETIME NOT NULL,
                        estado VARCHAR(50) NOT NULL,
                        total DOUBLE NOT NULL
);

CREATE TABLE detalle_pedido (
                                id_detalle INT AUTO_INCREMENT PRIMARY KEY,
                                id_pedido INT NOT NULL,
                                id_producto INT NOT NULL,
                                cantidad_producto INT NOT NULL,
                                precio_producto DOUBLE NOT NULL,

                                CONSTRAINT fk_pedido
                                    FOREIGN KEY (id_pedido)
                                        REFERENCES pedido(id_pedido)
                                        ON DELETE CASCADE
);