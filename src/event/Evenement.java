package event;

import io.Simulateur;

/**
 * Evenement à executé dans le simulateur à la date date
 */
public abstract class Evenement {
    /**
     * Simulateur sur lequel les evenements sont généré
     */
    private Simulateur simulateur;
    /**
     * Date à laquelle s'execute l'Evenement
     */
    private long date;


    /**
     * Créer l'evenement pour qu'il soit executé à la date date dans le simulateur
     * @param date          Date d'execution
     * @param simulateur    Simulateur dans lequel se produit l'Evenement
     */
    public Evenement(long date, Simulateur simulateur) {
        this.date = date;
        this.simulateur = simulateur;
    }

    /**
     * Retourne la date d'execution de l'evenemnt
     * @return date d'execution
     */
    public long getDate() {
        return this.date;
    }

    /**
     * Change la date d'execution de l'evenement
     * @param date nouvelle date d'execution
     */
    protected void setDate(long date){
        this.date = date;
    }

    /**
     * Insere l'Evenement à sa date d'execution
     * @param evenement Evenement concerné
     */
    private void reInsertion(Evenement evenement){simulateur.ajoutEvenement(evenement);}

    /**
     * REtourne le Simulateur de cet Evenement
     * @return Simulateur de cet Evenement
     */
    protected Simulateur getSimulateur(){return simulateur;}

    /**
     * Ajoute un délai
     * @param delais à cet Evenement et le ré-insert dans la Simulation
     */
    protected void comebackAt(double delais) {
        this.date += delais;
        reInsertion(this);
    }

    /**
     * Execute l'Evenement
     */
    abstract public void execute();
}

