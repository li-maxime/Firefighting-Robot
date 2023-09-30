package test;

import data.DonneesSimulation;
import event.AttribtionTache;
import event.RobotSeDeplace;
import fire.Incendie;
import gui.GUISimulator;
import io.LecteurDonnees;
import io.Simulateur;
import map.Sommet;
import map.*;
import robot.*;

import java.awt.*;
import java.util.LinkedList;

/**
 * Scénario de test demandant au Robot roue d'éteindre le feu situé en position (5,5).
 * Utilisez un pas de 100
 */
public class TestScenario1 {
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
        System.out.println("Scénario de test demandant au Robot roue d'éteindre le feu situé en position (5,5).\n" +
                "Utilisez un pas de 100");
        RobotReservoir roue = (RobotReservoir) data.getRobot(1);
        Incendie feu = data.getIncendie(4);
        roue.attributionTache(feu);
        AttribtionTache tache = new AttribtionTache(feu, roue, 1, simulateur);
        simulateur.ajoutEvenement(tache);




    }
}
