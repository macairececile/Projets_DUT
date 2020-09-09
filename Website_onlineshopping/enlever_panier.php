<?php

session_start();

$Code = $_GET['Code'];

unset($_SESSION['PANIER']['CODE'][$Code], $_SESSION['PANIER']['TITRE'][$Code], $_SESSION['PANIER'][$Code]);

$_SESSION['PANIER']['CODE'] = array_values($_SESSION['PANIER']['CODE']);
$_SESSION['PANIER']['TITRE'] = array_values($_SESSION['PANIER']['TITRE']);

header('Location: panier.php');