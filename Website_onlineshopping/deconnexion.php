<?php
session_start();
session_destroy();

$Location = 'Location: ' . $_COOKIE['Page'];
header( $Location );
