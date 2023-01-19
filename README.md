./install.sh - pobiera i wypakowuje jave jdk 18 oraz maven 3.8.7 do folderu /opt/
. .profile - zmienia używaną wersje javy oraz mavena na wcześniej pobrane

w katalogu server 
./build.sh - kompiluje serwer
./run.sh - uruchamia serwer

w katalogu klient 
./build.sh - kompiluje klienta
./run.sh - uruchamia klienta

gdy serwer jest włączony, klient może podać ip serwera oraz nazwe użytkownika aby połączyć się z serwerem.
klient widzi wszystkich użytkowników, który połączyli się z serwerem oraz może wysyłać do wszystkich
wiadomości tekstowe

./clean.sh - usuwa pobraną jave oraz mavena
