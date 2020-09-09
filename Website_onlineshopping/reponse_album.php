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
    <link rel="stylesheet" type="text/css" href="css/reponse_album.css">
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
                $Oeuvre = $_GET['Oeuvre'];
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
                    <?php
                    $driver = 'sqlsrv';
                    $host = 'INFO-SIMPLET';
                    $nomDb = 'Classique_Web';
                    $user = 'ETD';
                    $password = 'ETD';
                    // Chaîne de connexion
                    $pdodsn = "$driver:Server=$host;Database=$nomDb";
                    // Connexion PDO
                    try {
                        $pdo = new PDO($pdodsn, $user, $password);
                        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                    } catch (PDOException $e) {
                        echo 'Échec lors de la connexion : ' . $e->getMessage();
                    }
                    $requete = 'Select DISTINCT Titre_Album, Album.Code_Album, Album.Pochette, Album.ASIN From Album
                                   INNER JOIN Disque ON Album.Code_Album = Disque.Code_Album
                                   INNER JOIN Composition_Disque ON Disque.Code_Disque = Composition_Disque.Code_Disque
                                   INNER JOIN Enregistrement ON Composition_Disque.Code_Morceau = Enregistrement.Code_Morceau
                                   INNER JOIN Composition ON Enregistrement.Code_Composition = Composition.Code_Composition
                                   INNER JOIN Composition_Oeuvre ON Composition.Code_Composition = Composition_Oeuvre.Code_Composition
                                   INNER JOIN Oeuvre ON Composition_Oeuvre.Code_Oeuvre = Oeuvre.Code_Oeuvre
                                   WHERE Oeuvre.Code_Oeuvre = ' . $Oeuvre;


                    echo '<p style="border-bottom: 1px solid grey; color:#333;"> Albums </p>';
                    echo '<br />';
                    echo '<ul>';

                    require('Exeu-Amazon-ECS-PHP-Library-1867eaa/lib/AmazonECS.class.php'); //nom de la classe téléchargée


                    foreach ($pdo->query($requete) as $row) {
                        echo '<ol>';
                        echo '<img width="140px" height="120px" src="image_Album.php?Code_Album=' . $row['Code_Album'] . '" alt="Pas de photo">';
                        echo '<a style="margin-left: 30px;" href="reponse_enregistrement.php?Album=' . $row['Code_Album'] . '">' . $row['Titre_Album'] . '</a>';

                        if(isset($row['ASIN'])){
                            $Aws_ID = "AKIAIIGU7F26WMYL2DZA"; // Identifiant
                            $Aws_SECRET = "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"; //Secret
                            $associateTag="pro0e07-21"; // AssociateTag
                            $client = new AmazonECS($Aws_ID, $Aws_SECRET, 'FR', $associateTag);
                            $response = $client->responseGroup('Large')->lookup($row['ASIN']);
                            $items = $response->Items;
                            $it = $items->Item;


                            echo '<br />';
                            echo '<br />';
                            echo '<br />';
                            echo "<ol>";
                            if(isset($it->ASIN)) {
                                echo "<li>ASIN : " . $it->ASIN . "</li>";
                            }

                            if(isset($it->DetailPageURL)){
                                echo "<li><a href='". $it->DetailPageURL ."'>Aller directement sur la page Amazon</a></li>";
                            }

                            if(isset($it->ItemAttributes->Binding)){
                                echo "<li>Format : " . $it->ItemAttributes->Binding . "</li>";
                            }

                            if(isset($it->ItemAttributes->Brand)){
                                echo "<li>Marque : " . $it->ItemAttributes->Brand . "</li>";
                            }

                            if(isset($it->ItemAttributes->Label)){
                                echo "<li>Titre : " . $it->ItemAttributes->Label . "</li>";
                            }

                            if(isset($it->ItemAttributes->ReleaseDate)){
                                echo "<li>Date de sortie : " . $it->ItemAttributes->ReleaseDate . "</li>";
                            }

                            if(isset($it->OfferSummary->LowestNewPrice->FormattedPrice)) {
                                echo "<li>Prix : " . $it->OfferSummary->LowestNewPrice->FormattedPrice . "</li>";
                            }

                            if(isset($it->SalesRank)){
                                echo "<li>Rang : " . $it->SalesRank . "</li>";
                            }
                            echo "</ol>";
                        }

                        else {
                            echo "      Pas d'information pour cet album.";
                        }

                        echo '<br />';
                        echo '</ol>';

                    }
                    echo '</ul>';
                    ?>
                    <br />
                    <a style="margin-top: 50px;" class="btn btn-blue" href="javascript:history.go(-1)">Retour</a>
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
