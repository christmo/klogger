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

$consultaSql = 
"SELECT FECHA_HORA_DE,
VAR1_DE, VAR2_DE, VAR3_DE,VAR4_DE,VAR5_DE,VAR6_DE,
DIG_IN1_DE,DIG_IN2_DE,DIG_IN3_DE,DIG_IN4_DE,DIG_IN5_DE,DIG_IN6_DE,
DIG_OUT1_DE,DIG_OUT2_DE,DIG_OUT3_DE,DIG_OUT4_DE,DIG_OUT5_DE,DIG_OUT6_DE, CARGA
FROM data_estaciones
WHERE  fecha_hora_de >= '$fecha_ini'
AND  fecha_hora_de <= '$fecha_fin'
AND ID_EST = $id_est";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "{\"serie\": [";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "{
            \"fecha_hora\":\"" . $fila["FECHA_HORA_DE"] . "\",
            \"v1\":\"" . $fila["VAR1_DE"] . "\",
            \"v2\":\"" . $fila["VAR2_DE"] . "\",
            \"v3\":\"" . $fila["VAR3_DE"] . "\",
            \"v4\":\"" . $fila["VAR4_DE"] . "\",
            \"v5\":\"" . $fila["VAR5_DE"] . "\",
            \"v6\":\"" . $fila["VAR6_DE"] . "\",
            \"di1\":\"" . $fila["DIG_IN1_DE"] . "\",
            \"di2\":\"" . $fila["DIG_IN2_DE"] . "\",
            \"di3\":\"" . $fila["DIG_IN3_DE"] . "\",
            \"di4\":\"" . $fila["DIG_IN4_DE"] . "\",
            \"di5\":\"" . $fila["DIG_IN5_DE"] . "\",
            \"di6\":\"" . $fila["DIG_IN6_DE"] . "\",
            \"do1\":\"" . $fila["DIG_OUT1_DE"] . "\",
            \"do2\":\"" . $fila["DIG_OUT2_DE"] . "\",
            \"do3\":\"" . $fila["DIG_OUT3_DE"] . "\",
            \"do4\":\"" . $fila["DIG_OUT4_DE"] . "\",
            \"do5\":\"" . $fila["DIG_OUT5_DE"] . "\",
            \"do6\":\"" . $fila["DIG_OUT6_DE"] . "\",
            \"carga\":\"" . $fila["CARGA"] . "\",
        }";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";

echo $salida;
?>