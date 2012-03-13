<?php

include '../../login/isLogin.php';
include '../../../dll/conect.php';

extract($_GET);

$salida = "{failure:true}";

$consultaSql = "
    SELECT E.ID_EST, E.NOMBRE_EST
    FROM usuario_estacion us, estaciones e
    WHERE US.ID_EST = E.ID_EST
    AND US.ID_US = ".$_SESSION["ID_USER"];

consulta($consultaSql);
$resulset = variasFilas();

$salida = "{\"estaciones\": [";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "{
            \"id\":\"" . $fila["ID_EST"] . "\",
            \"name\":\"" . utf8_encode($fila["NOMBRE_EST"]) . "\"
        }";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";

echo $salida;
?>
