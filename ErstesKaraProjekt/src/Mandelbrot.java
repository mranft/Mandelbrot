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
  int xInDerWelt = 0; // x wird immer von 0 hoch gez�hlt. Wenn Kara nacht rechts l�uft, anpassen. 


// Kara in linke obere Ecke und nach rechtsschauend positionieren
  inStartposition();
  nachRechts = true;
  

  for (int y=0; y < Hoehe; y++) {
    for (int x=0; x < Breite; x++){
      if (nachRechts){
    	  xInDerWelt = x;
      }
      else {
    	  xInDerWelt = Breite - 1 - x;  
      }
      if (imKreis(xInDerWelt, y, MittelpunktX, MittelpunktY, Radius)){
    	  kara.putLeaf();
      }
      if (x < Breite -1) kara.move(); // nicht �ber den Rand laufen!!
    }
    if (y<Hoehe){
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
  
  public boolean reiseKomplettImKreis (int startx, int starty, float MittelpunktX, float MittelpunktY, float Radius){
	  /*
	   * 100 Mal die neuen Korrdinaten berechnen und pr�fen, ob sie noch im Kreis sind. Dabei den Kreis auf Radius 2 normieren.
	   */
	  int xneu, yneu;
	  
	  xneu = startx;
	  yneu = starty;
	  
	  for (i=0; i<100; i++){
		  int xMerken,Ymerken;
		  xMerken =xneu;
		  yMerken =yneu;
		  xneu = xNeuBerechnen (xMerken, yMerken, startx);
		  yneu = yNeuBerechnen (xMerken, yMerken, starty);
	  }
  }

  public boolean imKreis (float WeltpunktX, float WeltpunktY, float MittelpunktX, float MittelpunktY, float Radius){
/* Pr�ft, ob ein gegebner Punkt in der Welt im Kreis ist.
** Alle Punkte, die maximal die L�nge Radius vom Mittelpunkt entfernt sind, sind im Kreis.
** Die Berechnung der Entfernung zum Mittelpunkt erfolgt �ber die Formel: a-quadrat + b-quadrat = c-quadrat,
** wobei a und b die x- und y-Entfernung vom Mittelpunkt ist und c ist der Radius
*/

  boolean drin = true;
  float KoordinatenpunktX, KoordinatenpunktY =0;
  float ab, c=0;

  // Umrechnen einer Koordinate aus der Kara-Welt (Nullpunkt links oben) 
  // in ein Koordinatensystem mit Nullpunkt in der Mitte der Kara-Welt
  KoordinatenpunktX = WeltpunktX - MittelpunktX;
  KoordinatenpunktY = MittelpunktY - WeltpunktY;
  
  // Pr�fen, ob der Koordinatenpunkt im Kreis liegt
  
  ab = KoordinatenpunktX*KoordinatenpunktX + KoordinatenpunktY*KoordinatenpunktY;
  c = Radius*Radius;
  /*
  tools.showMessage("WeltX: " + WeltpunktX + " WeltY: " + WeltpunktY);
  tools.showMessage("KoordX: " + KoordinatenpunktX + " KoordY: " + KoordinatenpunktY);
  tools.showMessage("ab: " + ab + " c: " + c);
  
  tools.showMessage("WeltX: " + WeltpunktX + " WeltY: " + WeltpunktY + " KoordX: " + KoordinatenpunktX + " KoordY: " + KoordinatenpunktY + " ab: " + ab + " c: " + c);
  */
  
  if ((KoordinatenpunktX*KoordinatenpunktX + KoordinatenpunktY*KoordinatenpunktY) > Radius*Radius){
	  drin = false;
  }

//  tools.showMessage("im Kreis: " + drin);
  return drin;

}

  public void inStartposition (){
/* Kara wird nach 0,0 versetzt.
** Anschlie�end l�uft kara einen Schritt und es wird so lange gepr�ft, ob Kara richtig positioniert ist,
** bis Kara von 0,0 nach 1,0 l�uft
*/

  boolean karaStehtRichtig = false;
  java.awt.Point karaPosition;

// Kara in linke obere Ecke
  kara.setPosition(0,0);

// pr�fen wohin Kara schaut. Wenn Kara nach rechts schaut: (0,0) -> move -> (0,1)
  while (!karaStehtRichtig) {
    kara.move();
    karaPosition = kara.getPosition();
    if (karaPosition.getY()!=0) {
    // wir sind hoch oder runter gelaufen. Also zur�ck und einmal drehen
      kara.turnRight();
      kara.turnRight();
      kara.move();
      kara.turnRight();
    } else {
      if (karaPosition.getX() != 1) {
      // Wir sind nach links gelaufen. Also zweimal drehen und zur�ck
        kara.turnRight();
        kara.turnRight();
        kara.move();
        karaStehtRichtig=true;
      } else { // alles war OK. Zur�ck und wieder drehen
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
    
    // Weltgr��e ermitteln
    world.clearAll();
    Weltbreite = tools.intInput("Gr��e der Welt eingeben:");
    Welthoehe = Weltbreite;
    world.setSize(Weltbreite, Welthoehe);


    // Alles auf Kreisgr��e 2 normieren
    MittelpunktX = Weltbreite/2;
    MittelpunktY = Welthoehe/2;

    if (Weltbreite < Welthoehe) { // Wenn die Welt kein Quadrat ist, kleinere Seitenl�nge w�hlen
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

        