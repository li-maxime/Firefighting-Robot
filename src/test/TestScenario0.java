package test;

import data.DonneesSimulation;
import event.Evenement;
import gui.GUISimulator;
import io.LecteurDonnees;
import io.Simulateur;
import map.Sommet;
import robot.Robot;
import map.*;
import event.*;

import java.awt.*;
import java.util.LinkedList;

/**
 * Scénario de test qui consiste à demander au Robot drone de la carte CarteSujet de se déplacer 4 fois au Nord
 * On s'attend à avoir une erreur du type : La Case n'existe pas
 * Utiliser un pas de temps de l'ordre de 100
 */
public class TestScenario0 {
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

        Robot drone = data.getRobot(0);
        System.out.println(" Scénario de test qui consiste à demander au Robot drone de la carte CarteSujet de se déplacer 4 fois au Nord\n On s'attend à avoir une erreur du type : La Case n'existe pas" +
                "\n Utiliser un pas de temps de l'ordre de 100");

        LinkedList<Direction> trajet = new LinkedList<>();
        for (int i = 0; i<4; i++){
            trajet.add(Direction.SUD);
        }
        Sommet sommet = new Sommet(drone.getPosition(),trajet);
        RobotSeDeplace deplacement = new RobotSeDeplace(drone, sommet, 1,simulateur,null);
        simulateur.ajoutEvenement(deplacement);



    }
}
