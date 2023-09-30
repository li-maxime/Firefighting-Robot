package event;

import io.Simulateur;
import map.Sommet;
import robot.*;

/**
 * Evenement qui va demander au Robot de se déplacer en suivant un chemin.
 * A la fin du chemin instancie l'Evenement nextEvent
 */
public class RobotSeDeplace extends Evenement {
    /**
     * Chemin à parcourir
     */
    private final Sommet chemin;
    /**
     * Robot devant parcourir le chemin
     */
    private Robot robot;
    /**
     * Evenement à instancier apres avoir complété le chemein
     */
    private Evenement nextEvent;

    /**
     *Initialisation de l'Evenement RobotSeDeplace qui demande au Robot de se déplacer sur le chemin donnée
     *L'Evenement est ajouté au Simulateur à la date date
     * @param robot         Robot devant se déplacer
     * @param chemin        Chemin emprunter par le robot
     * @param date          Date d'execution
     * @param simulateur    Simulateur dans lequel se produit l'Evenement
     * @param nextEvent     Evenement suivant la fin du déplacement
     */
    public RobotSeDeplace(Robot robot, Sommet chemin, long date, Simulateur simulateur, Evenement nextEvent) {
        super(date + (long)(robot.getCarte().getTailleCase()*3.6/robot.getVitesse((robot.getPosition().getNatureTerrain()))), simulateur);
        this.robot = robot;
        this.chemin = chemin;
        this.nextEvent = nextEvent;
    }

    /**
     * Avance d'un pas, met à jour le chemin en enlevant le pas effectué et re instancie l'Evenement avec un délais correspondant à la vitesse de parcours de la prochaine Case
     * Dans le cas ou le Robot à fini de parcourir le chemin (chemin vide), Instancie le prochain Evenement.
     */
    @Override
    public void execute() {
        if (!this.chemin.finDuChemin()) {
            robot.deplacement(this.chemin.getNextDeplacement());
            int distance = robot.getCarte().getTailleCase();
            int vitesseDuRobot = robot.getVitesse((robot.getPosition().getNatureTerrain()));
            if (!this.chemin.finDuChemin()){
                this.comebackAt(distance *3.6 / vitesseDuRobot);
            }
            else{
                this.comebackAt(1);
            }
        }
        else {
            if (nextEvent != null){
                nextEvent.setDate(this.getSimulateur().getDateSimu());
                getSimulateur().ajoutEvenement(nextEvent);
            }

        }
    }

}
