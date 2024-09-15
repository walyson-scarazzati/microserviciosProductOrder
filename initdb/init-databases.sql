CREATE DATABASE IF NOT EXISTS order_service;

CREATE DATABASE IF NOT EXISTS inventario_service;

use inventario_service;

SET @tableExists = (
    SELECT COUNT(*) 
    FROM information_schema.tables 
    WHERE table_schema = 'inventario_service' 
    AND table_name = 'inventario'
);

-- If the table exists, insert into it
IF @tableExists > 0 THEN
    INSERT INTO inventario (codigo_sku, cantidad) 
    VALUES ('Iphone_12', 100);
END IF;

-- If the table exists, insert into it
IF @tableExists > 0 THEN
    INSERT INTO inventario (codigo_sku, cantidad) 
    VALUES ('Iphone_12_blue', 0);
END IF;
