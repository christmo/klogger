<?php
/**
 * Este Script hace el encolamiento de un comando que quiera ser enviado al equipo
 */
include('../login/isLogin.php');
require_once('../../dll/conect.php');

extract($_POST);

if (isset($id_est)) {

    $consultaSql="INSERT INTO COMANDOS (ID_EST, COMANDO, FH_ENCOLADO, ESTADO, IDEQUIPO_EST)
            VALUES($id_est,'AT\$SMGSND=0,\"C\"',DATE_FORMAT(CURRENT_TIMESTAMP(),'%Y-%m-%d %H:%i:%s'),1,
            (SELECT IDEQUIPO_EST FROM ESTACIONES WHERE ID_EST=$id_est))";
    consulta($consultaSql);
    $consultaSql="INSERT INTO COMANDOS (ID_EST, COMANDO, FH_ENCOLADO, ESTADO, IDEQUIPO_EST)
            VALUES($id_est,'AT\$SMGSND=0,\"R$fechaRecuperar//\"',DATE_FORMAT(CURRENT_TIMESTAMP(),'%Y-%m-%d %H:%i:%s'),1,
            (SELECT IDEQUIPO_EST FROM ESTACIONES WHERE ID_EST=$id_est))";
    consulta($consultaSql);
    
    $datos = array('success'=>'true');
}else{
    $datos = array('failure'=>'true');
}

echo json_encode($datos);
?>
