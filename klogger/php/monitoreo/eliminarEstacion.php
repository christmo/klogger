<?php

include '../login/isLogin.php';
include '../../dll/conect.php';

extract($_POST);

$salida = "{failure:true}";
if (isset($id_est)) {
    $sql = "DELETE FROM USUARIO_ESTACION WHERE ID_EST = $id_est AND ID_US =" . $_SESSION["ID_USER"];
    consulta($sql);

    $sql = "DELETE FROM estaciones WHERE ID_EST=$id_est";
    consulta($sql);

    $salida = "{success:true}";
}

echo $salida;
?>
