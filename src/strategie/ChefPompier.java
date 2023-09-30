package strategie;

import fire.Incendie;
import io.Simulateur;
import robot.Robot;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Classe abstraite qui va implémenter des fonctions et contenir des attributs généraux
 * à toutes les stratégies.
 */
public abstract class ChefPompier {
    /**
     * Le simulateur de la stratégie.
     */
    protected Simulateur simulateur;
    /**
     * Liste des robots.
     */
    private LinkedList<Robot> robotList;
    /**
     * Liste des incendies.
     */
    private LinkedList<Incendie> incendieList;

    /**
     * Constructeur de la classe
     * @param simulateur              Le simulateur de la stratégie.
     * @param robotVector             Liste des robots.
     * @param incendieVector          Liste des incendies.
     * @return                        une instance de la classe ChefPomier.
     */
    public ChefPompier(Simulateur simulateur, LinkedList<Robot> robotVector, LinkedList<Incendie> incendieVector){
        this.simulateur = simulateur;
        this.robotList = robotVector;
        this.incendieList = incendieVector;

    }
    /**
     * Fonction qui permet aux filles d'avoir accès au simulateur.
     * @return  l'attribue simulateur
     */
    protected Simulateur getSimulateur(){return simulateur;}
    /**
     * Fonction qui retourne un itérateur sur la liste des incendies.
     * @return  itérateur sur la liste des incendies
     */
    protected ListIterator<Incendie> getIncendieIter(){return incendieList.listIterator(0);}
    /**
     * Fonction qui vérifie si tous les incendies sont éteints.
     * @return  true si tous les incendies sont éteints, false sinon.
     */
    protected boolean toutEstEteint(){
        for(Incendie i : incendieList){
            if(!i.getEteint()){return false;}
        }
        return true;
    }
    /**
     * Fonction qui retourne un itérateur sur la liste des robots.
     * @return  itérateur sur la liste des robots
     */
    protected ListIterator<Robot> getRobotIter(){return robotList.listIterator(0);}

    /**
     * Fonction qui execute la stratégie.
     * @return  true si tous les incendies sont éteints false sinon
     */
    public abstract boolean execute();
}
