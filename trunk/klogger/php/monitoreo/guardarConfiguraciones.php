<?php

include '../login/isLogin.php';
include '../../dll/conect.php';

extract($_POST);
$salida = "{failure:true}";
if (isset($id_est)) {
    $sql = "INSERT INTO COMANDOS (ID_EST, COMANDO, FH_ENCOLADO, ESTADO, IDEQUIPO_EST)
            VALUES($id_est,'.*.*.CoMaNdO*.*.*:$tiempo',DATE_FORMAT(CURRENT_TIMESTAMP(),'%Y-%m-%d %H:%i:%s'),1,
            (SELECT IDEQUIPO_EST FROM ESTACIONES WHERE ID_EST=$id_est))";
    consulta($sql);

    $sql = "SELECT * FROM CONFIGURACIONES WHERE id_est = 1 AND LLAVE = 'tiempo_reporte_est'";
    consulta($sql);
    $fila = unicaFila();
    if (count($fila) == 0) {
        $sql = "INSERT INTO CONFIGURACIONES
            VALUES($id_est,'tiempo_reporte_est',$tiempo,
            'Tiempo de reporte de esa estación en minutos, llave siempre fija no cambiar',NOW())";
        consulta($sql);
    } else {
        $sql = "UPDATE CONFIGURACIONES 
                SET VALOR = $tiempo, FECHA_HORA=NOW() 
                WHERE ID_EST = $id_est AND LLAVE = 'tiempo_reporte_est'";
        consulta($sql);
    }
    $salida = "{success:true}";
}

echo $salida;
?>
