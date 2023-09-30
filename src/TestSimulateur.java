import data.*;
import io.LecteurDonnees;
import io.Simulateur;
import gui.GUISimulator;
import strategie.ChefPompier;
import strategie.StrategieNaive;
import strategie.StrategiePCC;


import java.awt.*;

/**
 * Lance la simulation avec la carte indiqué en argument, et le Stratégie plus évolué
 */
public class TestSimulateur {
    /**
     * Lance la simulation avec la carte indiqué en argument, et le Stratégie plus évolué
     * @param args le chemin de la carte
     */
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

        gui.resizePanel(gui.getContentPane().getSize().width, gui.getContentPane().getSize().height - 70);

        // création du simulateur
        Simulateur simulateur = new Simulateur(gui, data, args[0]);

        ChefPompier startegie = new StrategiePCC(simulateur, data.listOfRobot(),data.listOfIcendie());
        System.out.println("Résultat = " + startegie.execute());

    }
}