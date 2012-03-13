<?php

include '../login/isLogin.php';
include '../../dll/conect.php';

extract($_POST);
$salida = "{failure:true}";
if (isset($strIdEquipo)) {
    $sql = "INSERT INTO ESTACIONES(
              NOMBRE_EST,
              DIRECCION_EST,
              FECHA_INSTALACION_EST,
              RESPONSABLE_EST,
              RESPONSABLE_CONT_EST,
              IDEQUIPO_EST,
              ALTITUD
            )
            VALUES(
              '$strNombreEst',
              '$strDireccion',
              '$strFecha $strHora',
              '$strResponsable',
              '$strContactoResponsable',
              '$strIdEquipo',
              0
            )";
    consulta($sql);

    $sql = "SELECT id_est FROM estaciones where idequipo_est='$strIdEquipo'";
    consulta($sql);
    $fila = unicaFila();
    
    $sql = "INSERT INTO USUARIO_ESTACION VALUES(" . $_SESSION["ID_USER"] . ",".$fila["id_est"].")";
    consulta($sql);
    $salida = "{success:true}";
}

echo $salida;
?>
