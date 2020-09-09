<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tout pour la musique</title>
    <meta name="description" content="Cardio is a free one page template made exclusively for Codrops by Luka Cvetinovic" />
    <meta name="keywords" content="html template, css, free, one page, gym, fitness, web design" />
    <meta name="author" content="Luka Cvetinovic for Codrops" />
    <!-- Favicons (created with http://realfavicongenerator.net/)-->
    <link rel="apple-touch-icon" sizes="57x57" href="img/favicons/apple-touch-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="img/favicons/apple-touch-icon-60x60.png">
    <link rel="icon" type="image/png" href="img/favicons/favicon-32x32.png" sizes="32x32">
    <link rel="icon" type="image/png" href="img/favicons/favicon-16x16.png" sizes="16x16">
    <link rel="manifest" href="img/favicons/manifest.json">
    <link rel="shortcut icon" href="img/favicons/favicon.ico">
    <meta name="msapplication-TileColor" content="#00a8ff">
    <meta name="msapplication-config" content="img/favicons/browserconfig.xml">
    <meta name="theme-color" content="#ffffff">
    <!-- Normalize -->
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
    <!-- Owl -->
    <link rel="stylesheet" type="text/css" href="css/owl.css">
    <!-- Animate.css -->
    <link rel="stylesheet" type="text/css" href="css/animate.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.1.0/css/font-awesome.min.css">
    <!-- Elegant Icons -->
    <link rel="stylesheet" type="text/css" href="fonts/eleganticons/et-icons.css">
    <!-- Main style -->
    <link rel="stylesheet" type="text/css" href="css/catalogue.css">
</head>

<body>
<div class="preloader">
    <img src="img/loader.gif" alt="Preloader image">
</div>
<nav class="navbar">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.php"><img src="img/logo.png" data-active-url="img/logo-active.png" alt=""></a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right main-nav">
                <li><a href="index.php">Accueil</a></li>
                <li><a href="catalogue.php">Catalogue</a></li>
                <li><a href="albums.php">Albums</a></li>
                <li><a href="apropos.php">A propos</a></li>
                <?php
                include ('suividesession.php');
                ?>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>
<header id="intro">
    <div class="container">
        <div class="table">
            <div class="header-text">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <section>
                            <h3>
                                A propos
                            </h3>
                            <br />
                            <p>
                                Ce projet a été réalisé dans le cadre du module Programmation Web, par Cécile Macaire & Théo Frison, en deuxième année d'IUT Informatique.
                                Nous avions à effectuer un site de e-commerce.
                                Ce site a été réalisé en HTML/CSS pour la partie cliente, et PHP pour la partie serveur.
                            </p>
                            <br />
                            <h4>
                                Qu'avons nous réalisé ?
                            </h4>
                            <br />
                            <p>
                                Vous pourrez trouver une page d'accueil ainsi qu'un menu se composant d'un catalogue, des albums, d'un à propos et de deux boutons, connexion et inscription.
                                Le catalogue permet de rechercher un compositeur de musique classique. L'utilisateur peut ensuite accéder à l'ensemble de ses oeuvres, puis aux albums qui contiennent l'oeuvre séectionnée.
                                Les albums se composent d'enregistrements que l'utilisateur pourra acheter. Mais pour cela, celui-ci devra se connecter ou s'inscrire puis se connecter pour constituer un panier d'achat.
                                Nous avons mis en place un suivi de session afin de déconnecter l'utilisateur lorsqu'il est innactif depuis longtemps ( 5 minutes ) et de sécuriser les pages sensibles.
                                Nous avons également mis en place des cookies pour que l'utilisateur accède à la dernière page où il était lors de sa connexion.
                                Les informations sur les albums sont complétées par l'utilisation de l'API d'Amazon.
                                On suppose que chaque enregistrement coûte 1€.
                                Quand l'utilisateur veut voir tous les albums disponibles, nous avons décidé de ne pas afficher les informations d'Amazon par soucis de temps d'attente et de serveur.
                            </p>
                            <br />
                            <h4>
                                Difficultés rencontrées
                            </h4>
                            <br />
                            <p>
                                Nous avons rencontrés quelques difficultés. En effet, concernant l'API d'Amazon, nous n'arrivions pas à afficher les informations. Nous avons réussi à rectifier le problème en modifiant certaines lignes du code fournit sur le site du module.
                                Pour le suivi de session, en se connectant, un message d'erreur s'affichait avec 'session_start()'. Le problème a été corrigé.
                                Quand l'utilisateur ajoute quelque chose dans le panier, si il appuie sur le bouton "Retour", cela va le renvoyer sur le même page que là où il était. Nous n'avons pas réussi à corriger le problème.
                                Lorsque la page précharge, on peut tout de même faire défiler la page qui charge. Nous n'avons pas réussi à régler le problème.
                            </p>
                            <br />
                        </section>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>

<footer style="text-align:center;">
    <?php
    include ('footer.php');
    ?>
</footer>

<!-- Holder for mobile navigation -->
<div class="mobile-nav">
    <ul>
    </ul>
    <a href="#" class="close-link"><i class="arrow_up"></i></a>
</div>
<!-- Scripts -->
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/wow.min.js"></script>
<script src="js/typewriter.js"></script>
<script src="js/jquery.onepagenav.js"></script>
<script src="js/main.js"></script>
</body>

</html>
