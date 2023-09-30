package event;

import fire.*;
import robot.*;
import io.*;
import map.*;

/**
 * Evenement qui va attribuer un Incendie à un Robot
 */
public class AttribtionTache extends Evenement {
    private final Incendie feu;
    private final Robot robot;

    /**
     * Instancie un Evenement AttributionTache qui attribue un Incendie à un Robot
     * @param feu           Incendie à éteindre
     * @param robot         Robot devant éteindre l'incendie
     * @param date          Date d'execution
     * @param simulateur    Simulateur dans lequel se produit l'Evenement
     */
    public AttribtionTache(Incendie feu, Robot robot, long date, Simulateur simulateur) {
        super(date, simulateur);
        this.feu = feu;
        this.robot = robot;
        this.robot.nextItineraire();
        this.robot.attributionTache(feu);

        this.feu.attribution();

    }

    /**
     * Attribue l'Incendie au Robot
     * Met à jour l'itinéraire du Robot vers l'Incendie
     * DEmande au Robot de se déplacer vers l'Incendie pour l'éteindre
     */
    @Override
    public void execute() {
        Sommet chemin = robot.getTrajet();

        //enlever le cast forcé quand akram aura fini
        EteindreFeu feuCible = new EteindreFeu(feu, robot,-1, getSimulateur());
        //variable pour la lisibilité.
        RobotSeDeplace deplacementDuRobot = new RobotSeDeplace(robot, chemin, this.getDate(), getSimulateur(), feuCible);

        getSimulateur().ajoutEvenement(deplacementDuRobot);

    }
}
