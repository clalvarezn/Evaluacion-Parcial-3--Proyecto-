-- 1. tabla de Boletas
CREATE TABLE boleta (
                        id_boleta INT AUTO_INCREMENT PRIMARY KEY,
                        id_pago INT NOT NULL,
                        fecha_emision_boleta DATETIME NOT NULL,
                        metodo_pago VARCHAR(50) NOT NULL,
                        pdf_url VARCHAR(255) NOT NULL,
                        neto INT NOT NULL,
                        iva INT NOT NULL,
                        total INT NOT NULL
);

-- 2. (Seed) de prueba
INSERT INTO boleta (id_pago, fecha_emision_boleta, metodo_pago, pdf_url, neto, iva, total)
VALUES (
           101,
           '2026-06-07',
           'DEBITO',
           'https://LovableEcomerce/facturacion/boletas/pdf/folio_101.pdf',
           25202,
           4788,
           29990
       );

INSERT INTO boleta (id_pago, fecha_emision_boleta, metodo_pago, pdf_url, neto, iva, total)
VALUES (
           102,
           '2026-06-07',
           'CREDITO',
           'https://LovableEcomerce/facturacion/boletas/pdf/folio_102.pdf',
           12605,
           2395,
           15000
       );