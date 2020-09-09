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
    <link rel="stylesheet" type="text/css" href="css/reponse_oeuvre.css">
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
                        <?php
                        $Password = $_GET["Password"];
                        $Login = $_GET["Login"];
                        // recherche si l'utilisateur est enregistré et possède le bon mot de passe

                        $driver = 'sqlsrv';
                        $host = 'INFO-SIMPLET';
                        $nomDb = 'Classique_Web';
                        $user = 'ETD';
                        $password = 'ETD';
                        // Chaîne de connexion
                        $pdodsn = "$driver:Server=$host;Database=$nomDb;";
                        // Connexion PDO
                        try {
                            $pdo = new PDO($pdodsn, $user, $password);
                            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                        } catch (PDOException $e) {
                            echo 'Échec lors de la connexion : ' . $e->getMessage();
                        }
                        $requete = 'select Abonné.Code_Abonné, Login, Password from Abonné where Login = \'' . $Login . "' and Password = '" . $Password . "'";
                        ///////////////// LE CODE ABONNE EN PLUS
                        $bool = false;

                        foreach ($pdo->query($requete) as $row) {
                            $bool = true;
                            session_start();
                            $_SESSION['USER']['CODE'] =  $row[0];
                            $_SESSION['USER']['NOM'] = $row['Login'];
                            $_SESSION['DERNIERE_ACTION'] = time();
                            $_SESSION['DUREE_LIMITE'] = 60*5;
                            $_SESSION['PANIER'] = array();
                            $_SESSION['PANIER']['CODE'] = array();
                            $_SESSION['PANIER']['TITRE'] = array();

                        }
                        if ($bool) {
                            $Location = 'Location: ' . $_COOKIE['Page'];
                            header( $Location );
                        }

                        else {
                            echo "<p> Mot de passe et/ou login incorrect </p>";
                            echo "<a href='inscription.php'> Cliquez ici pour créer votre compte </a>";
                            echo '<br />';
                            echo '<a style="margin-top: 50px;" class="btn btn-blue" href="javascript:history.go(-1)">Retour</a>';
                        }
                        ?>
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
