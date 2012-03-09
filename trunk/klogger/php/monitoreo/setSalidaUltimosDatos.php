<?php

include('../login/isLogin.php');
require_once('../../dll/conect.php');

extract($_GET);

if (isset($ac)) {
    $consultaSql = "
    UPDATE ultimo_data_estaciones
    SET DIG_OUT" . $id_var . "_DE = $ac
    WHERE ID_EST = $id_est";
    consulta($consultaSql);

    $consultaSql="INSERT INTO COMANDOS (ID_EST, COMANDO, FH_ENCOLADO, ESTADO, IDEQUIPO_EST)
            VALUES($id_est,'AT\$SMGSND=0,\"C\"',DATE_FORMAT(CURRENT_TIMESTAMP(),'%Y-%m-%d %H:%i:%s'),1,
            (SELECT IDEQUIPO_EST FROM ESTACIONES WHERE ID_EST=$id_est))";
    consulta($consultaSql);
    $consultaSql="INSERT INTO COMANDOS (ID_EST, COMANDO, FH_ENCOLADO, ESTADO, IDEQUIPO_EST)
            VALUES($id_est,'AT\$SMGSND=0,\"T$bin/*\"',DATE_FORMAT(CURRENT_TIMESTAMP(),'%Y-%m-%d %H:%i:%s'),1,
            (SELECT IDEQUIPO_EST FROM ESTACIONES WHERE ID_EST=$id_est))";
    consulta($consultaSql);
    
    $datos = array('success'=>'true','ac'=>$ac);
}else{
    $datos = array('failure'=>'true');
}

echo json_encode($datos);
?>
