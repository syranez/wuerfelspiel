/*
Testat: (Programmieren I: Java)
=======

Programmieren eines Wuerfelspiels zwischen Computer und einem Spieler

Es sollen alle gewuerfelten Augenzahlen in eine Ergebnis-Liste von
1 bis 6 eingetragen werden. Dazu sind zwei Wuerfel im Spiel.
Computer und Spieler wuerfeln abwechselnd. Abhaengig von den
gewuerfelten Augenzahlen gibt es mehrere Verfahrensweisen:

- Die beiden Wuerfel haben unterschiedliche Augenzahlen, dann kann
	man waehlen, welche Augenzahl man in die Liste eintragen will.
	Allerdings nur, wenn die Augenzahl noch nicht besetzt ist.
	Sind die Zahlen schon besetzt, d.h. es gibt bereits einen
	Eintrag, dann muss man einen anderen, noch nicht besetzten
	Eintrag in der Liste streichen.
	Ein "besetzter" Eintrag ist ein Eintrag, in dem entweder
	bereits eine Augenzahl eingetragen wurde, oder ein Eintrag,
	der gestrichen wurde.

	Beispiele:
        Augenzahl   Eintrag
            1           0
            2           0
            3           0
            4           0
            5           0
            6           0	Wuerfel: 2 und 5
            				--> Eintrag in 2 oder 5 moeglich,
            				bei 5 ergibt sich
            1           0
            2           0
            3           0
            4           0
            5           5
            6           0	Wuerfel: 1 und 5
            				--> Eintrag nur in 1 moeglich,
            				es ergibt sich
            1           1
            2           0
            3           0
            4           0
            5           5
            6           0	Wuerfel: 1 und 5
            				--> kein Eintrag moeglich,
            				es muss gestrichen werden,
            				moeglich waere 2, 3, 4 und 6,
            				streicht man in 3 ergibt sich
            1           1
            2           0
            3           -1		(die -1 als Markierung fuer einen
            4           0		 geloeschten Eintrag ist willkuerlich
            5           5		 gewaehlt)
            6           0

- Die beiden Wuerfel haben gleiche Augenzahlen (= Pasch). Dann kann
	man die	Summe der beiden Wuerfel in die Liste eintragen, sofern
	die Augenzahl noch nicht besetzt ist. Ansonsten muss wie im
	obigen Beispiel gestrichen werden.

	Beispiele:
        Augenzahl   Eintrag
            1           1
            2           0
            3           -1
            4           0
            5           5
            6           0	Wuerfel: 4 und 4
            				--> Eintrag in 4 ergibt
            1           1
            2           0
            3           -1
            4           8		(wegen 2 * 4 !)
            5           5
            6           0	Wuerfel: 1 und 1
            				--> kein Eintrag moeglich,
            				es muss gestrichen werden,
            				moeglich waere 2 und 6,
            				streicht man in 2 ergibt sich
            1           1
            2           -1
            3           -1
            4           8
            5           5
            6           0

Eingaben des Spielers zum Eintrag oder Streichen von Augenzahlen
(siehe obige Beispiele) soll der Computer auf Gueltigkeit pruefen
und bei fehlerhaften Eingaben erneut anfordern.


Die Strategie des Computers als Spieler soll sein:

- Er setzt den Eintrag, wenn moeglich, immer in die groessere Augenzahl ein.

- Er streicht stets den Eintrag mit der kleinsten, noch nicht besetzten
	Augenzahl.

Am Ende des Programms werden alle Eintraege summiert, ausser den
gestrichenen Eintraegen. Sieger ist, wer die groessere Summe hat.

Die Ergebnisliste nach Ablauf des Programms koennte folgendermassen
aussehen:

		Augenzahl	Spieler		Computer
            1           1			2
            2           -1			2
            3           -1			-1
            4           8			4
            5           5			10
            6           6			-1
            			---			---
        Summe  			20			18
        Der Spieler hat gewonnen!

Tipps:
------
	- Zum Wuerfeln kann man die random-Funktion verwenden.
	- Es ist sinnvoll, eine (Zwischen-)Ergebnisliste nach jedem Wurf anzuzeigen.
	- Um das Programm moeglichst einfach und uebersichtlich zu halten, sollten
		manche Programmteile als Methode (Funktion) angelegt werden. Z.B. Ausgabe
		der Ergebnislisten, Streichen eines Eintrags durch den Computer, ...

Hinweise: Die Bearbeitung des Testats umfasst 3 Teile:
---------
	- Ein funktionsfaehiges Java-Programm einschliesslich (stichwortartiger)
        Kommentierung. Mit dieser Kommentierung sollen die Bedeutung der
        Variablen und der Programmfluss nachvollziehbar sein.

	- Vorfuehrung des Programms in einer Uebung und Beantworten von
        Fragen zum Programm

    - Mail an Prof.

Termin  : Spätestens Dienstag, 23.Dezember 2008
---------
*/

import java.io.*;

public class Wuerfelspiel
{
    static final int HUMAN = 0;					// ID des menschlichen Spielers
    static final int MACHINE = 1;				// ID des Maschinenspielers

    /*
        Zweidimensionales Array
        Erstes Feld  [0][0...5] => HUMAN
        Zweites Feld [1]][0...5] => MACHINE

    		0	1	2	3	4	5		<- Wuerfelaugen (=Wuerfelaugen+1)
    	0	x	x	x	x	x	x		<- Wuerfelaugen des Spielers mit der ID 0 (hier: HUMAN)
    	1	x	x	x	x	x	x		<- Wuerfelaugen des Spielers mit der ID 1 (hier: MACHINE)
    */
    static int [][] Ergebnisse = new int[2][6];

    // allgemeine Methode zum Einlesen von Benutzereingaben
    public static String readLine() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            if(message != null && message.length() > 0)
//            	System.out.print(message + " ");
            return br.readLine(); // System.in wird mal lieber nicht geschlossen ;O)
        } catch (IOException ie) {
            return null;
        }
    }

    // Wuerfelt den Wuerfel
    // Hier kann man auch statt Math.random() fuer HUMAN eine Tastatureingabe einbauen.
    public static int rollDice ( int Player )
    {
        if ( Player == Wuerfelspiel.HUMAN )
        {
            return (int)(((Math.random())*6)+1);
        }

        else // ( Player == Wuerfelspiel.MACHINE )
        {
            return (int)(((Math.random())*6)+1);
        }
    }

    // Zeigt den aktuellen Stand an
    public static void show ( )
    {
        System.out.println("");
        System.out.println("Zahl\t   Spieler    Programm");
        System.out.println("===============================");
        for ( int i = 0; i < 6; i++ )
        {
            System.out.println((i+1)+"\t   "+Ergebnisse[Wuerfelspiel.HUMAN][i]+"\t      "+Ergebnisse[Wuerfelspiel.MACHINE][i]);
        }
    }

    // Zeigt den Gewinner an
    public static void showWinner ( )
    {
        // Am Ende des Programms werden alle Eintraege summiert, ausser den
        // gestrichenen Eintraegen. Sieger ist, wer die groessere Summe hat.
        // Die Ergebnisliste nach Ablauf des Programms koennte folgendermassen
        // aussehen:
        //
        //        Augenzahl       Spieler         Computer
        //    1           1                       2
        //    2           -1                      2
        //    3           -1                      -1
        //    4           8                       4
        //    5           5                       10
        //    6           6                       -1
        //                          ---                     ---
        //  Summe                   20                      18
        //  Der Spieler hat gewonnen!

        int [] Count = {0,0};

        System.out.println("Endstand:\n\nAugenzahl\tSpieler\t\tComputer");

        for ( int i = 0; i < 6; i++ )
        {
            System.out.println((i+1) + "\t\t" + Ergebnisse[Wuerfelspiel.HUMAN][i] + "\t\t" + Ergebnisse[Wuerfelspiel.MACHINE][i]);
            if ( Ergebnisse[Wuerfelspiel.HUMAN][i] == -1 )
            {
                Ergebnisse[Wuerfelspiel.HUMAN][i] = 0;
            }
            if ( Ergebnisse[Wuerfelspiel.MACHINE][i] == -1 )
            {
                Ergebnisse[Wuerfelspiel.MACHINE][i] = 0;
            }

            Count[Wuerfelspiel.HUMAN] += Ergebnisse[Wuerfelspiel.HUMAN][i];
            Count[Wuerfelspiel.MACHINE] += Ergebnisse[Wuerfelspiel.MACHINE][i];
        }

        System.out.println("\t\t---\t\t---");
        System.out.println("\t\t" + Count[Wuerfelspiel.HUMAN] + "\t\t" + Count[Wuerfelspiel.MACHINE]);

        if ( Count[0] > Count[1] )
        {
            System.out.println("Der Spieler hat gewonnen!");
        }
        else if ( Count[1] > Count[0] )
        {
            System.out.println("Der Computer hat gewonnen!");
        }
        else
        {
            System.out.println("Keiner hat gewonnen! Unentschieden!");
        }
    }

    // Streicht einen Eintrageplatz
    public static void discardSpot ( int Player )
    {
        if ( Player == Wuerfelspiel.HUMAN )
        {
            System.out.println ("Du musst was streichen! Freie Plaetze sind: ");

            for ( int i = 0; i < 6; i++ )
            {
                if ( Ergebnisse[Player][i] == 0 )
                    System.out.println("Freier Platz: " + (i+1));
            }

            // Eingaben des Spielers zum Eintrag oder Streichen von Augenzahlen
            // (siehe obige Beispiele) soll der Computer auf Gueltigkeit pruefen
            // und bei fehlerhaften Eingaben erneut anfordern.

            int toDiscard = 0;
            do
            {
                System.out.println("Welchen Platz streichen?");

                try
                {
                    toDiscard = Integer.parseInt(readLine());
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Nur Zahlen sind erlaubt!");
                    toDiscard = 0; // Nochmal Eingabe verlangen.
                }

                if ( toDiscard >= 1 && toDiscard <= 6 )
                {
                    if ( Ergebnisse[Player][toDiscard-1] != 0 )
                    {
                        System.out.println("Platz ist schon belegt!");
                    }
                    else
                    {
                        break;
                    }
                }
            } while ( true );

            System.out.println("Streiche Platz " + toDiscard + "." );

            Ergebnisse[Player][toDiscard-1] = -1;
        }

        else if ( Player == Wuerfelspiel.MACHINE )
        {
            // Die Strategie des Computers als Spieler soll sein:
            // - Er streicht stets den Eintrag mit der kleinsten, noch nicht besetzten Augenzahl.

            for ( int i = 0; i < 6; i++ )
            {
                if ( Ergebnisse[Player][i] == 0 )
                {
                    Ergebnisse[Player][i] = -1;
                    return;
                }
            }
        }
    }

    public static void main ( String[] args )
    {
        for ( int i = 0; i < 6; i++ )
        {
            roll(Wuerfelspiel.HUMAN);
            roll(Wuerfelspiel.MACHINE);
            show();
        }

        showWinner();
    }

    public static void roll ( int Player )
    {
        if ( Player == Wuerfelspiel.HUMAN )
        {
            System.out.print ("Spieler wuerfelt eine ");
        }

        if ( Player == Wuerfelspiel.MACHINE )
        {
            System.out.print ("Programm wuerfelt eine ");
        }

        // Wuerfeln
        int [] Wuerfe = {0, 0};
        Wuerfe[0] = rollDice(Player);
        Wuerfe[1] = rollDice(Player);

        System.out.println(Wuerfe[0] + " und " + Wuerfe[1] + ".");

        // Pasch?
        if ( Wuerfe[0] == Wuerfe[1] )
        {
            // Die beiden Wuerfel haben gleiche Augenzahlen (= Pasch). Dann kann
            // man die Summe der beiden Wuerfel in die Liste eintragen, sofern
            // die Augenzahl noch nicht besetzt ist. Ansonsten muss wie im
            // obigen Beispiel gestrichen werden.

            if ( Ergebnisse[Player][Wuerfe[0]-1] == 0 )
            {
                // noch keine Augenzahl eingetragen
                Ergebnisse[Player][Wuerfe[0]-1] = 2 * Wuerfe[0];
            }

            else
            {
                // Eintrag streichen
                discardSpot(Player);
            }
        }

        // kein Pasch
        else
        {
            // Die beiden Wuerfel haben unterschiedliche Augenzahlen, dann kann
            // man waehlen, welche Augenzahl man in die Liste eintragen will.
            // Allerdings nur, wenn die Augenzahl noch nicht besetzt ist.
            // Sind die Zahlen schon besetzt, d.h. es gibt bereits einen
            // Eintrag, dann muss man einen anderen, noch nicht besetzten
            // Eintrag in der Liste streichen.
            // Ein "besetzter" Eintrag ist ein Eintrag, in dem entweder
            // bereits eine Augenzahl eingetragen wurde, oder ein Eintrag,
            // der gestrichen wurde.

            // sind beide Felder schon belegt?
            if ( Ergebnisse[Player][Wuerfe[0]-1] != 0 && Ergebnisse[Player][Wuerfe[1]-1] != 0 )
            {
                // Eintrag streichen
                discardSpot(Player);
            }

            // ist nur ein Feld schon belegt?
            else if ( Ergebnisse[Player][Wuerfe[0]-1] != 0 )
            {
                // Einfuegen des anderen Wertes
                Ergebnisse[Player][Wuerfe[1]-1] = Wuerfe[1];
            }

            else if ( Ergebnisse[Player][Wuerfe[1]-1] != 0 )
            {
                // Einfuegen des anderen Wertes
                Ergebnisse[Player][Wuerfe[0]-1] = Wuerfe[0];
            }

            // sind beide Felder noch frei?
            else
            {
                if ( Player == Wuerfelspiel.HUMAN )
                {
                    int toInsert = 0;
                    do
                    {
                        System.out.println("Die Eintraege " + Wuerfe[0] + " und " + Wuerfe[1] + " sind noch frei. Welcher soll eingetragen werden?");

                        try
                        {
                            toInsert = Integer.parseInt(readLine());
                        }

                        catch ( NumberFormatException e )
                        {
                            System.out.println("Nur Zahlen sind erlaubt!");
                            toInsert = 0; // Nochmal abfragen.
                        }
                    } while ( toInsert != Wuerfe[0] && toInsert != Wuerfe[1] );

                    Ergebnisse[Player][toInsert-1] = toInsert;
                }

                else if ( Player == Wuerfelspiel.MACHINE )
                {
                    if ( Wuerfe[0] > Wuerfe[1] )
                    {
                        Ergebnisse[Player][Wuerfe[0]-1] = Wuerfe[0];
                    }

                    else
                    {
                        Ergebnisse[Player][Wuerfe[1]-1] = Wuerfe[1];
                    }
                }
            }
        }
    }
}
