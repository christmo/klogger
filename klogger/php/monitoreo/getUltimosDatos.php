<?php

include('../login/isLogin.php');
require_once('../../dll/conect.php');

extract($_GET);
$consultaSql = "
SELECT
    VAR1_DE,VAR2_DE,VAR3_DE,VAR4_DE,VAR5_DE,VAR6_DE,
    DIG_IN1_DE,DIG_IN2_DE,DIG_IN3_DE,DIG_IN4_DE,DIG_IN5_DE,DIG_IN6_DE,
    DIG_OUT1_DE,DIG_OUT2_DE,DIG_OUT3_DE,DIG_OUT4_DE, DIG_OUT5_DE,DIG_OUT6_DE
FROM ultimo_data_estaciones
WHERE ID_EST = $id_est
";

consulta($consultaSql);
$fila = unicaFila();

$salida = array();
$salida["var1"]=$fila["VAR1_DE"];
$salida["var2"]=$fila["VAR2_DE"];
$salida["var3"]=$fila["VAR3_DE"];
$salida["var4"]=$fila["VAR4_DE"];
$salida["var5"]=$fila["VAR5_DE"];
$salida["var6"]=$fila["VAR6_DE"];
$salida["din1"]=$fila["DIG_IN1_DE"];
$salida["din2"]=$fila["DIG_IN2_DE"];
$salida["din3"]=$fila["DIG_IN3_DE"];
$salida["din4"]=$fila["DIG_IN4_DE"];
$salida["din5"]=$fila["DIG_IN5_DE"];
$salida["din6"]=$fila["DIG_IN6_DE"];
$salida["dou1"]=$fila["DIG_OUT1_DE"];
$salida["dou2"]=$fila["DIG_OUT2_DE"];
$salida["dou3"]=$fila["DIG_OUT3_DE"];
$salida["dou4"]=$fila["DIG_OUT4_DE"];
$salida["dou5"]=$fila["DIG_OUT5_DE"];
$salida["dou6"]=$fila["DIG_OUT6_DE"];

echo json_encode($salida);

?>
