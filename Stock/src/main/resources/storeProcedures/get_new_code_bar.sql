CREATE PROCEDURE get_new_code_bar(
    OUT code_bar_generated CHAR(20))
	FLAG: BEGIN
            DECLARE id_foud BIGINT;
            DECLARE continue_loop BOOLEAN;
            SET continue_loop = TRUE;

    FLAG_WHILE: WHILE(continue_loop = TRUE) DO
        SET code_bar_generated = (SELECT 1 + ROUND(RAND() * (10000000000000 - 1)));
        SELECT idProducto INTO id_foud FROM Producto
        WHERE codigoBarras = code_bar_generated;
        IF (id_foud IS NULL) THEN
            SET continue_loop = FALSE;
            LEAVE FLAG_WHILE;
        END IF;
    END WHILE;
END FLAG