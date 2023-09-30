package event;

import io.Simulateur;
import map.Sommet;
import robot.Robot;
import robot.RobotReservoir;

/**
 * Evenement qui remplis le reservoir du RobotReservoir
 * Créer un Evenement pour allez éteindre l'Incendie assigné si il n'est pas éteint
 * Le cas échéant rend libre le Robot
 */
public class ReservoirRempli extends Evenement{
    private final RobotReservoir robot;

    /**
     * Initialisation de l'Evenement ReservoirRempli qui remplis le Robot et décide son prochain état
     * L'Evenement est ajouté au Simulateur à la date date
     * @param date          Date d'execution
     * @param simulateur    Simulateur dans lequel se produit l'Evenement
     * @param robot         Robot qui se rempli
     */
    public ReservoirRempli(long date, Simulateur simulateur, RobotReservoir robot){
        super(date, simulateur);
        this.robot = robot;
    }

    /**
     * Remplis le réservoir du RobotReservoir
     * Créer un Evenement pour allez éteindre l'Incendie assigné si il n'est pas éteint
     * Le cas échéant rend libre le Robot
     */
    @Override
    public void execute(){
        robot.remplirReservoir();
        if (robot.getMonIncendie().getEteint()){
            this.robot.estLibre();
            return ;
        }
        robot.nextItineraire();
        robot.majTrajet();
        Sommet chemin = robot.getTrajet();
        EteindreFeu feuCible = new EteindreFeu(robot.getMonIncendie(), robot,-1, getSimulateur());
        RobotSeDeplace deplacementDuRobot = new RobotSeDeplace(robot, chemin, this.getSimulateur().getDateSimu(), getSimulateur(), feuCible);
        getSimulateur().ajoutEvenement(deplacementDuRobot);

    }
}
