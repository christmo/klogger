<?php

include ('../../dll/conect.php');
require_once ('encript.php');
extract($_POST);

$encriptClave = encryptClave($ps);
echo $encriptClave;

$consultaSql = " SELECT ID_US, USER_US, PASS_US FROM " .
        " USUARIOS WHERE USER_US = '" . $us .
        "' AND PASS_US = '" . $encriptClave . "'";

echo $consultaSql;


consulta($consultaSql);
$registro = unicaFila();

session_start();
//$_SESSION["INI"] = 'http://200.0.29.117:8080/karview/';

if ($registro["PASS_US"] == $encriptClave && $registro["USER_US"] == $us) {

    $_SESSION["USER"] = $registro["USER_US"];
    $_SESSION["ID_USER"] = $registro["ID_US"];
    $_SESSION["SESION"] = true;
//    $_SESSION["ROL"] = $registro["ID_ROL"];

//    // Deteccion de la ip y del proxy
//    if (isset($HTTP_SERVER_VARS["HTTP_X_FORWARDED_FOR"])) {
//        $ip = $HTTP_SERVER_VARS["HTTP_X_FORWARDED_FOR"];
//        $array = split(",", $ip);
//        $ip_proxy = $array[0];
//        $host = @gethostbyaddr($ip_proxy);
//        $ip_proxy = $HTTP_SERVER_VARS["REMOTE_ADDR"];
//    } else {
//        $ip = $_SERVER['REMOTE_ADDR'];
//        $host = @gethostbyaddr($ip);
//    }
//
//    $us = $registro["USUARIO"];
//    $fecha = @date("Y-m-d");
//    $hora = @date("H:i:s");
//
//    $consultaSql = "INSERT INTO ACCESOS VALUES ('$ip','$host','$us','$fecha','$hora')";
//    $sal = consulta($consultaSql);

    echo "1";
} else {
    echo "0";
}
?>
