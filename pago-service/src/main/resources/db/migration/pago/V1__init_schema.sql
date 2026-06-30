CREATE TABLE pago (
                      id_pago INT AUTO_INCREMENT PRIMARY KEY,
                      id_pedido INT NOT NULL,
                      monto DOUBLE NOT NULL,
                      fecha_pago DATETIME NOT NULL,
                      estado_pago VARCHAR(50) NOT NULL
);