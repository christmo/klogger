<?php

require_once('../../dll/conect.php');

extract($_GET);

//$salida = "{failure:true}";

if (isset($ac)) {
    $consultaSql = "
    UPDATE ultimo_data_estaciones
    SET DIG_OUT" . $id_var . "_DE = $ac
    WHERE ID_EST = $id_est";
    consulta($consultaSql);
//    $salida = "{success:true,ac=$ac}";
    $datos = array('success'=>'true','ac'=>$ac);
}else{
    $datos = array('failure'=>'true');
}

//echo $salida;
echo json_encode($datos);
?>
