<?php
/**
 * Encripta un texto
 * en base a la codificación de la APP
 *
 * @param String $claveOrg
 * @return String
 */
function encryptClave($claveOrg){
    $salt = "logger_kradac";
    $claveEnc = md5(md5(md5($claveOrg) . md5($salt)));
    return $claveEnc;
}
?>
