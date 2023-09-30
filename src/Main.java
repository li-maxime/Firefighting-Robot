import data.DonneesSimulation;
import gui.GUISimulator;
import io.LecteurDonnees;
import io.Simulateur;
import strategie.ChefPompier;
import strategie.StrategieNaive;
import strategie.StrategiePCC;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
    /* Version initiale Sujet :
       On peut intercepter les exceptions ici si on le souhaite comme dans le fichier TestLecture.java et non dans creeDonnees : choix de conception a voir avec le reste du groupe. */
        // chargement des donnees
        DonneesSimulation data = LecteurDonnees.createData(args[0]);

        int largeur = 1600;
        int hauteur = 1000;

        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(largeur, hauteur, Color.WHITE);

        // création du simulateur
        Simulateur simulateur = new Simulateur(gui, data, args[0]);
        ChefPompier startegie = new StrategiePCC(simulateur, data.listOfRobot(),data.listOfIcendie());
        //ChefPompier startegie = new StrategieNaive(simulateur, data.listOfRobot(),data.listOfIcendie());
        startegie.execute();
    }
}