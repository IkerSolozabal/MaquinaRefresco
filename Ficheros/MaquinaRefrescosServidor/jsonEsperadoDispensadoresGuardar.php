<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrDispensadorEsperado = array();

$arrEsperado['peticion'] = 'guardar';

$arrDispensadorEsperado['clave'] = 'hola string';
$arrDispensadorEsperado['nombre'] = 'hola String';
$arrDispensadorEsperado['precio'] = '7 int';
$arrDispensadorEsperado['cantidad'] = '7 int';

$arrEsperado['dispensadorGuardar'] = $arrDispensadorEsperado ;

/* Funcion para comprobar si el recibido es igual al esperado */

function JSONmodificar( $recibido ) {

    $auxCorrecto = false;

    if ( isset( $recibido['peticion'] ) && $recibido['peticion'] = 'guardar' && isset( $recibido['dispensadorGuardar'] ) ) {

        $auxElemento = $recibido['dispensadorGuardar'];
        if ( isset( $auxElemento['id'] ) && isset( $auxElemento["clave"] ) && isset( $auxElemento['nombre'] ) && isset( $auxElemento['precio'] ) && isset( $auxElemento['cantidad'] ) ) {
            $auxCorrecto = true;
        }

    }

    return $auxCorrecto;

}