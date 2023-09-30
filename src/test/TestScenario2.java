package test;

import data.DonneesSimulation;
import event.AttribtionTache;
import fire.Incendie;
import gui.GUISimulator;
import io.LecteurDonnees;
import io.Simulateur;
import robot.RobotReservoir;

import java.awt.*;

/**
 * Scénario de test demandant à tout les Robots d'éteindre le feu situé en position (5,5) dont l'intensité aura été fixé à 100000.
 * Utilisez un pas de 100
 */
public class TestScenario2 {
    public static void main(String[] args) {
        String chemin = "cartes/carteSujet.map";
        DonneesSimulation data = LecteurDonnees.createData(chemin);

        int largeur = 1600;
        int hauteur = 1000;

        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(largeur, hauteur, Color.WHITE);

        gui.resizePanel(gui.getContentPane().getSize().width, gui.getContentPane().getSize().height - 70);

        // création du simulateur
        Simulateur simulateur = new Simulateur(gui, data, chemin);
        System.out.println("Scénario de test demandant à tout les robots d'éteindre le feu situé en position (5,5) dont l'intensité aura été fixé à 100000.\n" +
                "Utilisez un pas de 100");
        robot.Robot robot;
        Incendie feu = data.getIncendie(4);
        feu.arroser(-100000);
        for (int i = 0; i<3; i++){
            robot = data.getRobot(i);
            robot.attributionTache(feu);
            AttribtionTache tache = new AttribtionTache(feu, robot, 1, simulateur);
            simulateur.ajoutEvenement(tache);
        }




    }
}
