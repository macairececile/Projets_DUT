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
                session_start();
                $Album = $_GET['Album'];
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
                    <div class="col-md-12 text-center">
                        <section>
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

                            $requete = 'Select DISTINCT Titre, Enregistrement.Code_Morceau From Enregistrement
	                  INNER JOIN Composition_Disque ON Enregistrement.Code_Morceau = Composition_Disque.Code_Morceau
	                  INNER JOIN Disque ON Composition_Disque.Code_Disque = Disque.Code_Disque
	                  INNER JOIN Album ON Disque.Code_Album = Album.Code_Album
	                  WHERE Album.Code_Album =' . $Album ;

                            echo "<table border='2'>";
                            echo "<tr>";
                            echo "<th>Morceaux</th>";
                            echo "<th>Extraits</th>";
                            echo "</tr>";
                            foreach ($pdo->query($requete) as $row) {
                                echo "<tr>";
                                echo '<td align="center">' . $row['Titre'] . "<br>" . "</td>";
                                echo '<td align="center">' . '<audio controls><source src="Musique_Oeuvres.php?Code='. $row['Code_Morceau'] . '" type="audio/mpeg"></audio></td>';
                                echo '<td align="center">' . '<a class="btn btn-blue" href="ajouter_panier.php?Titre=' . htmlentities($row['Titre'], ENT_QUOTES) . '&Code=' . $row['Code_Morceau'] . '">Ajouter au panier</a>';
                                echo '</tr>';
                            }

                            echo "</table>";

                            ?>

                            <a style="margin-top: 50px; margin-bottom: 100px;" class="btn btn-blue" href="javascript:history.go(-1)">Retour</a>
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
