 <?php
require_once('connect.php');

mysql_select_db($database_localhost,$con);

$nom=$_GET['nom'];
$prenom=$_GET['prenom'];
mysql_query("INSERT INTO personnes VALUES ('','$nom','$prenom') ");
echo "OK";
?>