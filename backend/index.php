<?php
echo "Get\n";
if(isset($_GET)){
    var_dump($_GET);
}
echo "Post\n";
if(isset($_POST)){
    var_dump($_POST);
}
echo "Multipart\n";
if(isset($_FILES)){
    var_dump($_FILES);
}
echo "File get\n";
if(file_get_contents('php://input')){
    echo file_get_contents('php://input');
}
?>