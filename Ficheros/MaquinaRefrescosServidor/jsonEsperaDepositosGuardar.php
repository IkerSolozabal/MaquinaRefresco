<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrDepositoEsperado = array();

$arrEsperado['peticion'] = 'guardar';

$arrDepositoEsperado['nombre'] = 'hola String';
$arrDepositoEsperado['valor'] = '7 int';
$arrDepositoEsperado['cantidad'] = '8 int';

$arrEsperado['depositoGuardar'] = $arrDepositoEsperado;

/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoAdd( $recibido ) {

    $auxCorrecto = false;

    if ( isset( $recibido['peticion'] ) && $recibido['peticion'] = 'guardar' && isset( $recibido['depositoGuardar'] ) ) {

        $auxElemento = $recibido['depositoGuardar'];
        if ( isset( $auxElemento['id'] ) && isset( $auxElemento['nombre'] ) && isset( $auxElemento['valor'] ) && isset( $auxElemento['cantidad'] ) ) {
            $auxCorrecto = true;
        }

    }

    return $auxCorrecto;

}

