<?php

include('../login/isLogin.php');
require_once('../../dll/conect.php');

extract($_GET);

//if (strlen($t) < 14 && $t != 0) {
//    $t = "";
//}
$consultaSql = "
    SELECT 
        ID_EST, 
        NOMBRE_EST, 
        DIRECCION_EST, 
        FECHA_INSTALACION_EST, 
        RESPONSABLE_EST, 
        RESPONSABLE_CONT_EST,
        LON_EST,
        LAT_EST,
        ALARMA
    FROM estaciones
    WHERE ID_EST IN (
      SELECT ID_EST FROM usuario_estacion WHERE ID_US=" . $_SESSION["ID_USER"] . "
    )";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "";
$mayor = "";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];

    $salida .= $fila["ID_EST"] . "%" .
            $fila["LON_EST"] . "%" .
            $fila["LAT_EST"] . "%" .
            $fila["FECHA_INSTALACION_EST"] . "%" .
            $fila["RESPONSABLE_EST"] . "%" .
            $fila["RESPONSABLE_CONT_EST"] . "%" .
            $fila["NOMBRE_EST"] . "%" .
            $fila["DIRECCION_EST"] . "%" .
            $fila["ALARMA"] . "#";

//    if ($fila["FECHA_INSTALACION_EST"] > $mayor) {
//        $mayor = $fila["FECHA_INSTALACION_EST"];
//    }
}

//$obj = array("t" => $mayor, "d" => utf8_encode($salida));
$obj = array("d" => utf8_encode($salida));
echo "" . json_encode($obj) . "";
?>
