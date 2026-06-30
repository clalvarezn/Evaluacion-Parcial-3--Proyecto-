
CREATE TABLE envios (
                        id_envio BIGINT AUTO_INCREMENT PRIMARY KEY,
                        direccion_envio VARCHAR(255) NOT NULL,
                        comuna VARCHAR(100) NOT NULL,
                        costo_envio DOUBLE NOT NULL,
                        estado_envio VARCHAR(50) NOT NULL,
                        fecha_despacho DATE NOT NULL,
                        fecha_entrega DATE,
                        id_boleta BIGINT NOT NULL,
                        id_pago BIGINT NOT NULL
);
