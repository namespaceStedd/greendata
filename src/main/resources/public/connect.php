<?php
// Хостинг, Пользователь, Пароль, Имя базы данных
$connect = mysqli_connect('localhost', 'root', '', 'greendata');

if (!$connect) {
    die('Ошибка подключения к базе данных');
}


// Выборка для выбора типа организации
$sql = mysqli_query($connect, "SELECT * FROM `organization_form`");
$organization_form = array();

while ($row = mysqli_fetch_array($sql)) {
    array_push($organization_form, $row["name"]);
}
?>