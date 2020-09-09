<?php

session_start();

$Titre = $_GET['Titre'];
$Code = $_GET['Code'];

if (isset($_SESSION['USER']['CODE'])) {

    if (time() - $_SESSION["DERNIERE_ACTION"] > $_SESSION["DUREE_LIMITE"]){

        header("Location: deconnexion.php");

    } else {
        $_SESSION["DERNIERE_ACTION"] = time(); // on relance

        array_push($_SESSION['PANIER']['CODE'], $Code);
        array_push($_SESSION['PANIER']['TITRE'], $Titre);

        $Location = 'Location: ' . $_COOKIE['Page'];
        header( $Location );
    }

} else {

    header("Location: connexion.php");

}
