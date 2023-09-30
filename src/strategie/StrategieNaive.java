package strategie;

import event.AttribtionTache;
import event.PauseChefPompier;
import fire.Incendie;
import io.Simulateur;
import robot.Robot;

import java.util.Iterator;
import java.util.LinkedList;
/**
 * Classe qui implémente la stratégie :
 * - Le chef pompier va chercher un robot libre.
 *          -S’il en trouve un, alors il lui assigne un feu.
 *          -S’il n’en trouve pas, il attend quelque instant et recommence sa recherche.
 *
 * - Le chef pompier fait cela tant que tous les feux ne sont pas éteints.
 *
 * -Un robot qui se voit attribuer un feu, se met en statut occupee et se déplace vers le feu pour l’éteindre.
 * Une fois sur place, s’il lui manque de l’eau, il va en chercher au point d’eau le plus proche.
 */
public class StrategieNaive extends ChefPompier{
    /**
     * timeout par defaut
     */
    static final long defaultTimeOut = 1000;
    /**
     * timeout de la stratégie (en seconde)
     */
    private long timeout;
    /**
     * Constructeur de la classe
     * @param simulateur              Le simulateur de la stratégie.
     * @param robotVector             Liste des robots.
     * @param incendieVector          Liste des incendies.
     * @return                        une instance de la classe StrategieNaive.
     */
    public StrategieNaive(Simulateur simulateur, LinkedList<Robot> robotVector, LinkedList<Incendie> incendieVector){
        super(simulateur, robotVector, incendieVector);
        this.timeout = defaultTimeOut;
    }
    /**
     * Constructeur de la classe
     * @param simulateur              Le simulateur de la stratégie.
     * @param robotVector             Liste des robots.
     * @param incendieVector          Liste des incendies.
     * @param timeout                 Le temps de pause de la stratégie
     * @return                        une instance de la classe StrategieNaive.
     */
    public StrategieNaive(Simulateur simulateur, LinkedList<Robot> robotVector, LinkedList<Incendie> incendieVector, long timeout){
        super(simulateur, robotVector, incendieVector);
        this.timeout = timeout;
    }
    /**
     * Fonction qui execute la stratégie :
     * - Le chef pompier va chercher un robot libre.
     *          -S’il en trouve un, alors il lui assigne un feu.
     *          -S’il n’en trouve pas, il attend quelque instant et recommence sa recherche.
     *
     * - Le chef pompier fait cela tant que tous les feux ne sont pas éteints.
     *
     * -Un robot qui se voit attribuer un feu, se met en statut occupee et se déplace vers le feu pour l’éteindre.
     * Une fois sur place, s’il lui manque de l’eau, il va en chercher au point d’eau le plus proche.
     * @return  true si tous les incendies sont éteints false sinon
     */
    @Override
    public boolean execute(){

        Iterator<Robot> iterRobot = getRobotIter();
        Iterator<Incendie> iterIcendie = getIncendieIter();

        Incendie incendie;
        boolean incendieAttribue = false;
        Robot robot;

        //Premier iter pour passer sur tous les incendies.
        while(iterIcendie.hasNext()) {
            incendie = iterIcendie.next();
            if(incendie.isAttribuee()){continue;}
            // Tant que l'incendie n'est pas attribué, le chef pompier essayera de l'affecter à un robot.
            while(!incendieAttribue){

                // On parcours de façon naive tous les robots du simulateur à la recherche d'un robot libre.
                while (iterRobot.hasNext()) {

                    robot = iterRobot.next();
                    if (!robot.getOccupee() && robot.canAccessFire(incendie)) {
                        getSimulateur().ajoutEvenement(new AttribtionTache(incendie, robot, this.getSimulateur().getDateSimu(), getSimulateur()));
                        incendieAttribue = true;
                        //incendie.attribution();
                        break;
                    }
                }
                // On attend si on n'a pas trouvé un robot de libre.
                if(!incendieAttribue){
                    //Tester pour avoir une bonne valeur pour le temps d'attente.
                    getSimulateur().ajoutEvenement(new PauseChefPompier(getSimulateur().getDateSimu()+timeout, getSimulateur(), this));
                    return false;
                }
            }
            incendieAttribue = false;
        }

        return toutEstEteint();
    }
}
