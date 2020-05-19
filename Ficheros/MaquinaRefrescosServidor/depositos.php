<?php

// Incluimos fichero en la que está la coenxión con la BBDD

/*
* Se mostrará siempre la información en formato json para que se pueda leer desde un html ( via js )
* o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
*/

$metodo = $_SERVER['REQUEST_METHOD'];

switch ( $metodo ) {
    case 'PUT':
    modificarUno();
    break;

    case 'GET':
    getAll();
    break;

    default:
    # code...
    break;
}

$arrMensaje = array();
// Este array es el codificaremos como JSON tanto si hay resultado como si hay error

function getAll() {

    require 'bbdd.php';
    $query = 'SELECT * FROM depositos';

    $result = $conn->query ( $query );

    if ( isset ( $result ) && $result ) {
        // Si pasa por este if, la query está está bien y se obtiene resultado

        if ( $result->num_rows > 0 ) {
            // Aunque la query esté bien puede no obtenerse resultado ( tabla vacía ). Comprobamos antes de recorrer

            $arrDepositos = array();

            while ( $row = $result->fetch_assoc () ) {

                // Por cada vuelta del bucle creamos un jugador. Como es un objeto hacemos un array asociativo
                $arrDeposito = array();
                // Por cada columna de la tabla creamos una propiedad para el objeto
                $arrDeposito ['id'] = $row['id'];
                $arrDeposito ['nombre'] = $row['nombre'];
                $arrDeposito ['valor'] = $row['valor'];
                $arrDeposito ['cantidad'] = $row['cantidad'];
                // Por último, añadimos el nuevo jugador al array de jugadores
                $arrDepositos [] = $arrDeposito;

            }

            // Añadimos al $arrMensaje el array de jugadores y añadimos un campo para indicar que todo ha ido OK
            $arrMensaje['estado'] = 'ok';
            $arrMensaje['depositos'] = $arrDepositos ;

        } else {

            $arrMensaje['estado'] = 'ok';
            $arrMensaje['depositos'] = [];
            // Array vacío si no hay resultados
        }

    } else {

        $arrMensaje['estado'] = 'error';
        $arrMensaje['mensaje'] = 'SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS';
        $arrMensaje['error'] = $conn->error;
        $arrMensaje['query'] = $query;

    }

    $mensajeJSON = json_encode( $arrMensaje, JSON_PRETTY_PRINT );

    //echo '<pre>';
    // Descomentar si se quiere ver resultado 'bonito' en navegador. Solo para pruebas
    echo $mensajeJSON;
    //echo '</pre>';
    // Descomentar si se quiere ver resultado 'bonito' en navegador

    $conn->close ();
}

function insertarUno() {
    require 'bbdd.php';
    // Incluimos fichero en la que está la coenxión con la BBDD
    require 'jsonEsperadoAdd.php';

    /*
    * Se mostrará siempre la información en formato json para que se pueda leer desde un html ( via js )
    * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
    */

    $arrMensaje = array();
    // Este array es el codificaremos como JSON tanto si hay resultado como si hay error

    /*
    * Lo primero es comprobar que nos han enviado la información via JSON
    */

    $parameters = file_get_contents( 'php://input' );

    if ( isset( $parameters ) ) {

        // Parseamos el string json y lo convertimos a objeto JSON
        $mensajeRecibido = json_decode( $parameters, true );
        // Comprobamos que están todos los datos en el json que hemos recibido
        // Funcion declarada en jsonEsperado.php
        if ( JSONCorrectoAdd( $mensajeRecibido ) == true ) {

            $elemento = $mensajeRecibido['elementoAdd'];

            $id = $elemento['id'];
            $nombre = $elemento['nombre'];
            $descripcion = $elemento['descripcion'];
            $caracteristica = $elemento['caracteristica'];

            $query  = 'INSERT INTO  elementos (id,nombre,descripcion,caracteristica) ';
            $query .= "VALUES ('$id','$nombre','$descripcion','$caracteristica')";

            $result = $conn->query ( $query );

            if ( isset ( $result ) && $result ) {
                // Si pasa por este if, la query está está bien y se ha insertado correctamente

                $arrMensaje['estado'] = 'ok';
                $arrMensaje['mensaje'] = 'Jugador insertado correctamente';

            } else {
                // Se ha producido algún error al ejecutar la query

                $arrMensaje['estado'] = 'error';
                $arrMensaje['mensaje'] = 'SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS';
                $arrMensaje['error'] = $conn->error;
                $arrMensaje['query'] = $query;

            }

        } else {
            // Nos ha llegado un json no tiene los campos necesarios

            $arrMensaje['estado'] = 'error';
            $arrMensaje['mensaje'] = 'EL JSON NO CONTIENE LOS CAMPOS ESPERADOS';
            $arrMensaje['recibido'] = $mensajeRecibido;
            $arrMensaje['esperado'] = $arrEsperado;
        }

    } else {
        // No nos han enviado el json correctamente

        $arrMensaje['estado'] = 'error';
        $arrMensaje['mensaje'] = 'EL JSON NO SE HA ENVIADO CORRECTAMENTE';

    }

    $mensajeJSON = json_encode( $arrMensaje, JSON_PRETTY_PRINT );

    //echo '<pre>';
    // Descomentar si se quiere ver resultado 'bonito' en navegador. Solo para pruebas
    echo $mensajeJSON;
    //echo '</pre>';
    // Descomentar si se quiere ver resultado 'bonito' en navegador

    $conn->close ();

    die();
}

function delete() {
    require 'bbdd.php';
    $query = 'DELETE FROM elementos';

    if ( isset( $_GET['id'] ) ) {
        $query .= ' WHERE id='.$_GET['id'];
    }

    $conn->query ( $query );

    $conn->close ();
}

function modificarUno() {
    require 'bbdd.php';
    // Incluimos fichero en la que está la coenxión con la BBDD
    require 'jsonEsperaDepositosGuardar.php';

    /*
    * Se mostrará siempre la información en formato json para que se pueda leer desde un html ( via js )
    * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
    */

    $arrMensaje = array();
    // Este array es el codificaremos como JSON tanto si hay resultado como si hay error

    /*
    * Lo primero es comprobar que nos han enviado la información via JSON
    */

    $parameters = file_get_contents( 'php://input' );

    if ( isset( $parameters ) ) {

        // Parseamos el string json y lo convertimos a objeto JSON
        $mensajeRecibido = json_decode( $parameters, true );
        // Comprobamos que están todos los datos en el json que hemos recibido
        // Funcion declarada en jsonEsperado.php
        if ( JSONmodificar( $mensajeRecibido ) == true ) {

            $deposito = $mensajeRecibido['elementoGuardar'];

            $nombre = $deposito['nombre'];
            $valor = $deposito['valor'];
            $cantidad= $deposito['cantidad'];

            $query  = "UPDATE depositos set cantidad = '$cantidad' WHERE valor = '$valor'";


            $result = $conn->query ( $query );

            if ( isset ( $result ) && $result ) {
                // Si pasa por este if, la query está está bien y se ha insertado correctamente

                $arrMensaje['estado'] = 'ok';
                $arrMensaje['mensaje'] = 'Deposito mofificado correctamente';

            } else {
                // Se ha producido algún error al ejecutar la query

                $arrMensaje['estado'] = 'error';
                $arrMensaje['mensaje'] = 'SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS';
                $arrMensaje['error'] = $conn->error;
                $arrMensaje['query'] = $query;

            }

        } else {
            // Nos ha llegado un json no tiene los campos necesarios

            $arrMensaje['estado'] = 'error';
            $arrMensaje['mensaje'] = 'EL JSON NO CONTIENE LOS CAMPOS ESPERADOS';
            $arrMensaje['recibido'] = $mensajeRecibido;
            $arrMensaje['esperado'] = $arrEsperado;
        }

    } else {
        // No nos han enviado el json correctamente

        $arrMensaje['estado'] = 'error';
        $arrMensaje['mensaje'] = 'EL JSON NO SE HA ENVIADO CORRECTAMENTE';

    }

    $mensajeJSON = json_encode( $arrMensaje, JSON_PRETTY_PRINT );

    //echo '<pre>';
    // Descomentar si se quiere ver resultado 'bonito' en navegador. Solo para pruebas
    echo $mensajeJSON;
    //echo '</pre>';
    // Descomentar si se quiere ver resultado 'bonito' en navegador

    $conn->close ();

}

?>