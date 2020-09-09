<?php
$pdo = new PDO("sqlsrv:Server=INFO-SIMPLET;Database=Classique_Web", "ETD", "ETD");
$stmt = $pdo->prepare("SELECT Photo FROM Musicien WHERE Code_Musicien=?");
$stmt->execute(array($_GET['Code']));
$stmt->bindColumn(1, $lob, PDO::PARAM_LOB);
$stmt->fetch(PDO::FETCH_BOUND);
$image = pack("H*", $lob);
header("Content-Type: image/jpeg");
echo $image;


