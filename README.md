./install.sh - pobiera i wypakowuje jave jdk 18 oraz maven 3.8.7 do folderu /opt/<br />
. .profile - zmienia używaną wersje javy oraz mavena na wcześniej pobrane<br />
<br />
w katalogu server <br />
./build.sh - kompiluje serwer<br />
./run.sh - uruchamia serwer<br />
<br />
w katalogu klient <br />
./build.sh - kompiluje klienta<br />
./run.sh - uruchamia klienta<br />
<br />
gdy serwer jest włączony, klient może podać ip serwera oraz nazwe użytkownika aby połączyć się z serwerem.<br />
klient widzi wszystkich użytkowników, który połączyli się z serwerem oraz może wysyłać do wszystkich<br />
wiadomości tekstowe<br />
<br />
./clean.sh - usuwa pobraną jave oraz mavena<br />
