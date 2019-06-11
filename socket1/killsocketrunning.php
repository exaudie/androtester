<?php
$host = '192.168.1.221'; //host
$port = '9000'; //port
$null = NULL; //null var

//Create TCP/IP sream socket
$sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
if ( ! socket_set_option($sock, SOL_SOCKET, SO_REUSEADDR, 1)) 
{ 
    echo socket_strerror(socket_last_error($sock)); 
    exit; 
}
?>