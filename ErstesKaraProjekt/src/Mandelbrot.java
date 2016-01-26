import javakara.JavaKaraProgram;
        
/* BEFEHLE:  kara.
 *   move()  turnRight()  turnLeft()
 *   putLeaf()  removeLeaf()
 *
 * SENSOREN: kara.
 *   treeFront()  treeLeft()  treeRight()
 *   mushroomFront()  onLeaf()
 */
public class Mandelbrot extends JavaKaraProgram {

  public void weltAblaufen (int Breite, int Hoehe, float MittelpunktX, float MittelpunktY, float Radius){

  boolean nachRechts = true; // Schaut Kara nach rechts?
  int xInDerWelt = 0; // x wird immer von 0 hoch gezählt. Wenn Kara nacht links läuft, anpassen.
  java.awt.Point Position;


// Kara in linke obere Ecke und nach rechtsschauend positionieren
  inStartposition();
  nachRechts = true;
  

  for (int y=0; y < Hoehe; y++) { // f�r jede Zeile
    for (int x=0; x < Breite; x++){ // f�r jede Spalte
      if (nachRechts){
    	  xInDerWelt = x;
      }
      else {
    	  xInDerWelt = Breite - 1 - x;  // von Rechts nach Links runterz�hlen
      }
      
      // Umrechnen der KaraPosition in Position im Koordinatenkreuz
      Position = transformiereKoordinaten((float) xInDerWelt, (float) y, MittelpunktX, MittelpunktY);
      if (imKreis((float)Position.getX(), (float)Position.getY(), MittelpunktX, MittelpunktY, Radius)){
    	  if (reiseKomplettImKreis((float)Position.getX(), (float)Position.getY(), MittelpunktX, MittelpunktY, Radius)) {
    		  kara.putLeaf();
    	  }
      }
      if (x < Breite -1) kara.move(); // nicht über den Rand laufen!!
    }
    if (y<Hoehe){ // eine Zeile tiefer und in Gegenrichtung ausrichten
      if (nachRechts) {
        nachRechts = false;
        kara.turnRight();
        kara.move();
        kara. turnRight();
      } else {
        nachRechts = true;
        kara.turnLeft();
        kara.move();
        kara. turnLeft();
      }
    }
  }
}
  public java.awt.Point transformiereKoordinaten (float karaX, float karaY, float MittelpunktX, float MittelpunktY){
	  /*
	   * Umrechnen einer Koordinate aus der Kara-Welt (Nullpunkt links oben) 
	   * in ein Koordinatensystem mit Nullpunkt in der Mitte der Kara-Welt
	   */

	  float KoordinatenpunktX, KoordinatenpunktY =0;
	  java.awt.Point NeueKoordinaten;
	  NeueKoordinaten = new java.awt.Point(0,0);
	  
	  // Umrechnen einer Koordinate aus der Kara-Welt (Nullpunkt links oben) 
	  // in ein Koordinatensystem mit Nullpunkt in der Mitte der Kara-Welt
	  KoordinatenpunktX = karaX - MittelpunktX;
	  KoordinatenpunktY = MittelpunktY - karaY;
	  NeueKoordinaten.setLocation((double)KoordinatenpunktX, (double)KoordinatenpunktY);
	  
	  return NeueKoordinaten;

  }
  
  public float xNeuBerechnen (float xalt, float yalt, float xstart){
/* 
 * Berechnen der neuen X-Koordinate anhand der alten Werte entsprechend der Vorgabe
 */  
	  return xalt*xalt - yalt*yalt + xstart;
  }

  public float yNeuBerechnen (float xalt, float yalt, float ystart){
/* 
 * Berechnen der neuen X-Koordinate anhand der alten Werte entsprechend der Vorgabe
 */
	  return 2*xalt*yalt + ystart;
  }
  
  public boolean reiseKomplettImKreis (float startx, float starty, float MittelpunktX, float MittelpunktY, float Radius){
	  /*
	   * 100 Mal die neuen Korrdinaten berechnen und prüfen, ob sie noch im Kreis sind. Dabei den Kreis auf Radius 2 normieren.
	   */
	  float xneu, yneu; 				//n�chster Punkt
	  float xMerken,yMerken; 			// Ausgangspunkt für Berechnung des nächsten Punkts merken
	  float Normierungsfaktor=Radius/2; //Berechnungsformel gilt für Radius = 2. Deswegen wird alles durch den Faktor geteilt
	  
// hier ist der Fehler: die Koordinaten sind Kara-Koordinaten und müssen umgewandelt werden in Koordinaten mit Mittelpunkt (0,0)
	  //Point.setLocaction(int,int)
	  
	  xneu = startx/Normierungsfaktor;
	  yneu = starty/Normierungsfaktor;
	  
	  for (int i=0; i<100; i++){
		  xMerken =xneu;
		  yMerken =yneu;
		  xneu = xNeuBerechnen (xMerken, yMerken, startx/Normierungsfaktor);
		  yneu = yNeuBerechnen (xMerken, yMerken, starty/Normierungsfaktor);
		  if (!imKreis(xneu, yneu, MittelpunktX/Normierungsfaktor, MittelpunktY/Normierungsfaktor, Radius/Normierungsfaktor)){
			  return false;
		  }
	  }
	  return true; // wir bis hier gekommen sind, dann waren alle Punkte im Kreis
  }

  public boolean imKreis (float PositionX, float PositionY, float MittelpunktX, float MittelpunktY, float Radius){
/* Prüft, ob ein gegebner Punkt in der Welt im Kreis ist.
** Alle Punkte, die maximal die Länge Radius vom Mittelpunkt entfernt sind, sind im Kreis.
** Die Berechnung der Entfernung zum Mittelpunkt erfolgt über die Formel: a-quadrat + b-quadrat = c-quadrat,
** wobei a und b die x- und y-Entfernung vom Mittelpunkt ist und c ist der Radius
*/

  boolean drin = true;
  
  // Prüfen, ob die Position im Kreis liegt
  
  if ((PositionX*PositionX + PositionY*PositionY) > Radius*Radius){
	  drin = false;
  }

  return drin;

}

  public void inStartposition (){
/* Kara wird nach 0,0 versetzt.
** Anschließend läuft kara einen Schritt und es wird so lange geprüft, ob Kara richtig positioniert ist,
** bis Kara von 0,0 nach 1,0 läuft
*/

  boolean karaStehtRichtig = false;
  java.awt.Point karaPosition;

// Kara in linke obere Ecke
  kara.setPosition(0,0);

// prüfen wohin Kara schaut. Wenn Kara nach rechts schaut: (0,0) -> move -> (0,1)
  while (!karaStehtRichtig) {
    kara.move();
    karaPosition = kara.getPosition();
    if (karaPosition.y != 0) {
    // wir sind hoch oder runter gelaufen. Also zurück und einmal drehen
      kara.turnRight();
      kara.turnRight();
      kara.move();
      kara.turnRight();
    } else {
      if (karaPosition.x != 1) {
      // Wir sind nach links gelaufen. Also zweimal drehen und zurück
        kara.turnRight();
        kara.turnRight();
        kara.move();
        karaStehtRichtig=true;
      } else { // alles war OK. Zurück und wieder drehen
        kara.turnRight();
        kara.turnRight();
        kara.move();
        kara.turnRight();
        kara.turnRight();
        karaStehtRichtig=true;
      }
    }
  }
}

  public void myProgram() {
    // hier kommt das Hauptprogramm hin

    int Weltbreite, Welthoehe=0;
    float MittelpunktX=0, MittelpunktY=0, Radius =0;
//    java.awt.Point Mittelpunkt; // Mittelpunkt als Point-Klasse ist besser, aber erst, wenn die Methoden klar sind.
    
    // Weltgröße ermitteln
    world.clearAll();
    Weltbreite = tools.intInput("Größe der Welt eingeben:");
    Welthoehe = Weltbreite;
    world.setSize(Weltbreite, Welthoehe);


    // Alles auf Kreisgröße 2 normieren
    MittelpunktX = Weltbreite/2;
    MittelpunktY = Welthoehe/2;

    if (Weltbreite < Welthoehe) { // Wenn die Welt kein Quadrat ist, kleinere Seitenlänge wählen
      Radius = Weltbreite/2;
    } 
    else {
      Radius = Welthoehe/2;
    }

tools.showMessage("Radius = " + Radius + "  Mittelpunkt X = " + MittelpunktX + " Mittelpunkt Y = " + MittelpunktY);
System.out.format("Radius = %f%n", Radius);
    weltAblaufen(Weltbreite, Welthoehe, MittelpunktX, MittelpunktY, Radius);
    
  }
}

        
