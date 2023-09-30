package event;

import io.Simulateur;
import map.NatureTerrain;
import map.Sommet;
import robot.*;

import java.sql.ResultSet;
import java.util.LinkedList;

/**
 * Evenement qui va demander à un Robot de se remplir d'eau et attendre son délais de remplissage.
 * Il demande au Robot de se déplacer à la Case d'eau la plus proche.
 * Il appelle creer ensuite l'Evenement ReservoirRempli
 */
public class SeRemplir extends Evenement{
    /**
     * RobotReservoir qui va se remplir
     */
    private RobotReservoir robot;

    /**
     * Initialisation de l'Evenement SeRemplir qui demande au Robot de se déplacer vers la case d'eau la plus proche et attendre son délais de remplissage.
     * L'Evenement est ajouté au Simulateur à la date date
     * @param date          Date d'execution
     * @param simulateur    Simulateur dans lequel se produit l'Evenement
     * @param robot         RobotReservoir devant se remplir
     */
    public SeRemplir(long date, Simulateur simulateur, RobotReservoir robot){
        super(date, simulateur);
        this.robot = robot;
    }

    /**
     * Met à jour l'itinéraire vers la source d'eau la plus proche,
     * Creer un Evenement pour se déplacer jusqu'à la case de remplissage
     * Demande à cette evenement d'instancier l'Evenement ReservoirRempli quand il aura fini.
     */
    @Override
    public void execute(){
        //robot.remplirReservoir();
        Sommet versEau = robot.toWater();
        Evenement nextEvent = new ReservoirRempli(-1, this.getSimulateur(), this.robot);
        getSimulateur().ajoutEvenement(new RobotSeDeplace(robot, versEau, getDate() + robot.getTempsRemplissage() , getSimulateur(),nextEvent));
        //robot.majTrajet();
        //getSimulateur().ajoutEvenement(new RobotSeDeplace(robot, robot.getTrajet(), getDate() + robot.getTempsRemplissage() + (long) versEau.getCout(), getSimulateur()));
        //getSimulateur().ajoutEvenement(new FinTache(robot,getDate() + robot.getTempsRemplissage() + (long) versEau.getCout(), getSimulateur()));
    }
}
