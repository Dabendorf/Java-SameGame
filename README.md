# Java-SameGame
Dieses Programm erzeugt das berühmte Spiel SameGame, bei welchem man durch Anklicken gleichfarbig anliegender Steine so viele Punkte wie möglich macht.

## Programmentwicklung
Das Programm wurde mit Java in Eclipse auf einem Mac OS X 10.11 Rechner programmiert. Die Quelltextdateien mit passendem Package sind in ein Projekt zu laden und die Ordner sgfiles und pictures entsprechend zu integrieren.

## Kompatibilität
Die höchstmögliche Kompatibilität wird durch Nutzung von Java 8 und ein Mac OS X Betriebssystem erreicht. Es wurde versucht, das Programm auch für Ubuntu, Windows und Java 7 als JRE kompatibel zu machen.
Etwaige Komplikationen mit anderweitigen Spezifikationen können unter Issues gemeldet werden.

## Spielregeln
Ziel des Spiels ist es durch Wegklicken von Früchten oder Farbplatten möglichst viele Punkte zu machen. Jedes Element was mindestens ein benachbartes gleichaussehendes Element hat kann weggeklickt werden. Die Punktzahl steigt hierbei entsprechend der Menge an zusammenhängenden Steinen.

## Punktevergabe
Die Vergabe von Punkten verläuft durch die quadratische Funktion `0.4*num*num + num`, wobei im äußersten Spezialfall in dem alle 240 Felder durch Zufall die gleiche Farbe/Frucht zeigen eine Höchstpunktzahl von 23.280 Punkten erreichbar ist.

## Motivation
Dieses Spiel wurde im Rahmen des Informatikunterrichts im Jahr 2016 in einem Brandenburger Gymnasium entwickelt. Es dient ausschließlich dem privaten und keinem kommerziellen Zweck.
