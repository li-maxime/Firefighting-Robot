package event;

import io.Simulateur;
import strategie.ChefPompier;

/**
 * Evènement qui permet de relancer la stratégie après une pause.
 */
public class PauseChefPompier extends Evenement{

    private ChefPompier chefPompier;

    /**
     * Constructeur de la classe
     * @param date du retour du chef pompier
     * @param simulateur simulateur associé
     * @param chefPompier la stratégie
     */
    public PauseChefPompier(long date, Simulateur simulateur, ChefPompier chefPompier){
        super(date, simulateur);
        this.chefPompier = chefPompier;
    }

    /**
     * permet de relancer la stratégie.
     */
    @Override
    public void execute(){
        chefPompier.execute();
    }
}
