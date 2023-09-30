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
 * - Le chef pompier va choisir systématiquement le couple (incendie,robot) les plus proches
 *          -S’il en trouve un, alors il assigne au robot l'incendie.
 *          -S’il n’en trouve pas, il attend quelque instant et recommence sa recherche.
 *
 * - Le chef pompier fait cela tant que tous les feux ne sont pas éteints.
 *
 * -Un robot qui se voit attribuer un feu, se met en statut occupée et se déplace vers le feu pour l’éteindre.
 * Une fois sur place, s’il lui manque de l’eau, il va en chercher au point d’eau le plus proche.
 * @return  true si tous les incendies sont éteints false sinon
 */
public class StrategiePCC extends ChefPompier{
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
     * @return                        une instance de la classe StrategiePCC.
     */
    public StrategiePCC(Simulateur simulateur, LinkedList<Robot> robotVector, LinkedList<Incendie> incendieVector){
        super(simulateur, robotVector, incendieVector);
        this.timeout = defaultTimeOut;
    }
    /**
     * Constructeur de la classe
     * @param simulateur              Le simulateur de la stratégie.
     * @param robotVector             Liste des robots.
     * @param incendieVector          Liste des incendies.
     * @param timeout                  Le temps de pause de la stratégie
     * @return                        une instance de la classe StrategiePCC.
     */
    public StrategiePCC(Simulateur simulateur, LinkedList<Robot> robotVector, LinkedList<Incendie> incendieVector, long timeout){
        super(simulateur, robotVector, incendieVector);
        this.timeout = timeout;
    }
    /**
     * Fonction qui choisit le couple (incendie, robot) les plus proches
     * @return  true si elle a trouvé un couple, false sinon.
     */
    private boolean attributionPlusProche(){
        Iterator<Robot> iterRobot;
        Iterator<Incendie> iterIcendie = getIncendieIter();
        Incendie incendie;
        Incendie plusPocheIncendie = null;
        Robot robot;
        Robot plusProcheRobot = null;
        long plsCourtTemps = Long.MAX_VALUE;
        long tempsVersFeu;
        //on fait un double while afin de parcourir tous les couples (robot,incendie) possible.
        while(iterIcendie.hasNext()) {
            incendie = iterIcendie.next();
            iterRobot = getRobotIter();

            while (iterRobot.hasNext()) {

                robot = iterRobot.next();


                if (!robot.getOccupee() && !incendie.isAttribuee() && robot.canAccessFire(incendie)) {
                    try {
                        tempsVersFeu = robot.getTimeTo(incendie);
                    }
                    catch(IllegalArgumentException exception){
                        // si le robot ne peut pas acceder à la case, on le saute.
                        exception.getMessage();
                        continue;
                    }
                    if (tempsVersFeu < plsCourtTemps) {
                        // Si le temps de parcours est plus petit que le minimum actuel, on le prend.
                        plusProcheRobot = robot;
                        plsCourtTemps = tempsVersFeu;
                        plusPocheIncendie = incendie;
                    }
                }
            }
        }

        if(plusPocheIncendie != null && plusProcheRobot != null ){
            getSimulateur().ajoutEvenement(new AttribtionTache(plusPocheIncendie, plusProcheRobot, this.getSimulateur().getDateSimu(), getSimulateur()));
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    /**
     * Fonction qui execute la stratégie :
     * - Le chef pompier va choisir systématiquement le couple (incendie,robot) les plus proches
     *          -S’il en trouve un, alors il assigne au robot l'incendie.
     *          -S’il n’en trouve pas, il attend quelque instant et recommence sa recherche.
     *
     * - Le chef pompier fait cela tant que tous les feux ne sont pas éteints.
     *
     * -Un robot qui se voit attribuer un feu, se met en statut occupée et se déplace vers le feu pour l’éteindre.
     * Une fois sur place, s’il lui manque de l’eau, il va en chercher au point d’eau le plus proche.
     * @return  true si tous les incendies sont éteints false sinon
     */
    public boolean execute(){

        //Premier iter pour passer sur tous les incendies.
        while(!toutEstEteint()) {
            if(!attributionPlusProche()){
                //Tester pour avoir une bonne valeur pour le temps d'attente.
                getSimulateur().ajoutEvenement(new PauseChefPompier(getSimulateur().getDateSimu()+timeout, getSimulateur(), this));

                return false;
                }
            }

        return toutEstEteint();
    }
}
