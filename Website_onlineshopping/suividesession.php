<?php
session_start();

if (isset($_COOKIE["Page"])) {
    unset($_COOKIE["Page"]);
    setcookie("Page", "http://" . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI'], time() + 500 * 60);
} else {
    setcookie("Page", "http://" . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI'], time() + 500 * 60);
}

if (isset($_SESSION['USER']['CODE'])) {

    if (time() - $_SESSION["DERNIERE_ACTION"] > $_SESSION["DUREE_LIMITE"]){

        header("Location: deconnexion.php");

    } else {
        $_SESSION['DERNIERE_ACTION'] = time(); // on relance
        $_SESSION['DERNIERE_PAGE'] = $_SERVER['REQUEST_URI'];
    }
    echo '<li><a href="deconnexion.php" class="btn btn-blue">Déconnexion</a></li>';
    echo "<li><a style='color: grey;'>Bonjour " . $_SESSION['USER']['NOM'] . " ! </a></li>";
    echo '<li><a href="panier.php" class="btn btn-blue">Mon panier</a></li>';
}
else {
    /* Créer une session */
    echo '<li><a href="inscription.php" class="btn btn-blue">Inscription</a></li>';
    /* Se connecter à une session */
    echo '<li><a href="connexion.php" class="btn btn-blue">Connexion</a></li>';
}
?>
