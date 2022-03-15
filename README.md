# CinderellaStitchPlugin
Plugin for geometry software Cinderella to export an stitch file for embroidery

## Installationsanleitung für das Plugin:

Unter dem aktuellen [Releases](https://github.com/SimonD1997/CinderellaStitchPlugin/releases/) findet sich die **CinderellaStitchPlugin.jar** die Heruntergeladen und dem Plugin-Verzeichnis der Cinderella-Installation hinzugefügt werden muss. 

Die Datei **Cinderella.Example.cdy** kann mit Cinderella geöffnet werden und enthält einen Button und im Script Editor bereits eingefüllten Code, der das Plugin läd und alle notwendigen Befehle ausführt. 

Die Datei ..... ist eine Beispieldatei und wurde für die, mit der Entwicklung dieses Plugins verbundene, Bachelorarbeit erstellt. 

## Code für Cinderella 
Falls das Plugin in einer eigenen Datei aufgerufen werden soll müssen folgende Befehle verwendet werden. 

Zum Pluginaufruf:
```
use("CinderellaStitchPlugin"); 
```
Punkte abrufen und dem Plugin übergeben:
```
pts2;
forall(allpoints(),
pts1 = [[#,#.x,#.y]];
pts2 = concat(pts2,pts1);
);
getPoints(pts2);
```
Strecken abrufen und dem Plugin übergeben (Punkte müssen unbedingt davor abgerufen und übergeben werden):
```
seg2;
forall(allsegments(),
seg1 = [[#,inspect(#,"definition")]];
seg2 = concat(seg2,seg1);
);
getSegments(seg2);
```
Kreise abrufen und dem Plugin übergeben:
```
circ2;
forall(allcircles(),
circ1 = [[#,#.center,#.radius]];
circ2 = concat(circ2,circ1);
);
println(circ2);
getCircles(circ2);
```
Stichdateiausgabe starten:
```
startProgrammAusgabe();
```
Vor oder nach jedem Programmablauf müssen alle alle Variablen in Cinderella gelöscht werden:
```
clear(); 
```
In der neusten Plugin Version kann möglicherweise folgender Befehl den komplletten obigen Code ersetzten:
```
use("CinderellaStitchPlugin"); 
programmLaden();
```
## Programme zum Visualisieren von Stichdateien
**Embroidermodder:**
https://forum.embroideres.com/files/file/1457-embroidermodder/
http://www.softsea.com/download/Embroidermodder.html

**Embroidery Explorer Plugin:**
https://www.freesierrasoftware.com/site/get%20it%20now.htm

## Folgende Dinge sind für die Nutzung des Plugins zu beachten:
1.Es können aktuell keine langen Sprungtstiche/Jump Stiche/Jump Stiches gestickt werden. In der Ausgabe resultiert dies in einem verschobenen Bild. Zur Umgehung des Problems müssen zur Verbindungstiche eingefügt werden. Es muss außerdem beachtet werden, dass die Strecken und Kreise in der Reihenfolge ihre Erstellung in Cinderella ausgegeben werden. Auch die benötigten Abstände beziehen sich auf diese Reihenfolge bei der Ausgabe und nicht auf die realen Abstände zwischen den Figuren in Cinderella
2.Es können aktuell nur Strecken und Kreise ausgeben werden. Alle anderen Objekte können natürlich zur Konstruktion genutzt werden, finden sich aber nicht in der Stichdatei. 
