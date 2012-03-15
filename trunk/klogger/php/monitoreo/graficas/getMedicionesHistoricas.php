<?php

/*
 * GENERA LA ESTRUCTURA JSON PARA
 * EL DIBUJADO DE LA SERIE EN LA GRAFICA
 * @autor christmo
 * @fecha 2012/03/02
 */

include('../../login/isLogin.php');
require_once('../../../dll/conect.php');

extract($_GET);

$consultaSql = "SELECT TIME(FECHA_HORA_DE) as TIEMPO, VAR".$id_var."_DE
FROM data_estaciones
WHERE  fecha_hora_de >= '$fecha_ini' 
AND  fecha_hora_de <= '$fecha_fin'
AND ID_EST = $id_est
ORDER BY TIEMPO asc";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "{\"serie\": [";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "{
            \"time\":\"" . $fila["TIEMPO"] . "\",
            \"value\":\"" . $fila["VAR".$id_var."_DE"] . "\"
        }";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";

echo $salida;
?>