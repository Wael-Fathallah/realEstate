 <?php
require_once('../connect.php');

$mail=$_GET['mail'];
$pass=$_GET['pass'];
$user=$_GET['user'];
if(filter_var($mail, FILTER_VALIDATE_EMAIL)){
    
    $amIcon = "0";
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    if($user == 0){
        $sql = "SELECT * FROM administrateur WHERE mail='$mail' AND mot_de_passe='$pass' LIMIT 1";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            $amIcon = "1";
            $row = mysqli_fetch_row($result);

            echo "OKA";
        } else {
            echo "Les identifiant sont incorrect pour l'admin";
        }
        $result = $conn->query($sql);

    }
    else {
        $sql = "SELECT * FROM utilisateur WHERE mail='$mail' AND password='$pass' LIMIT 1";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            $amIcon = "1";
            $row = mysqli_fetch_row($result);

            echo "OK";
            echo $row[8]; 
        } else {
            echo "Les identifiant sont incorrect";
        }

    }



    $conn->close();
}else {die("Not Valid mail forma");}
?> 