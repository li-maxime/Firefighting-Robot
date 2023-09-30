package event;

import fire.Incendie;
import robot.*;

/**
 * Evenement qui demande à un Robot de verser l'eau de son réservoir sur le feu
 * Une fois le reservoir vide, instancie un Evenement SeRemplir
 * Une fois l'Incendie éteint, libère le robot
 *
 */
public class EteindreFeu extends Evenement {
    private final Incendie feu;
    private final Robot robot;

    /**
     *Initialise l'Evenement Eteindre feu qui demande au Robot de verser l'eau de son réservoir sur le feu jusqu'à qu'il soit éteint ou ait besoin de se remplir
     * @param feu           Incendie à éteindre
     * @param robot         Robot devant éteindre l'incendie
     * @param date          Date d'execution
     * @param simulateur    Simulateur dans lequel se produit l'Evenement
     */
    public EteindreFeu(Incendie feu, Robot robot, long date, io.Simulateur simulateur) {
        super(date, simulateur);
        this.feu = feu;
        this.robot = robot;
    }

    /**
     * Vérifie que le robot est sur la case de l'Incendie sinon lève une erreur IllegalStateException
     * Réalise une intervention unitaire sur le feu si le feu n'est pas éteint ou le reservoir vide
     * Re instancie cet Evenement avec en délais la durée du versement
     * Dans le cas ou le feu est éteint, libère le Robot
     * Dans le cas ou le réservoir est vide, instancie l'Evenement SeRemplir
     */
    @Override
    public void execute() {
        if(robot.getPosition() != robot.getMonIncendie().getPosition()){
            throw new IllegalStateException("Le robot n'est pas sur une case en feu");
        }
        if (!this.feu.getEteint()) {
            if(this.robot.verser(this.feu)){
                this.comebackAt(this.robot.getTempsEcoulement());
            }
            else {
                SeRemplir remplissage = new SeRemplir(getDate(), getSimulateur(), (RobotReservoir) robot);
                getSimulateur().ajoutEvenement(remplissage);
            }
        } else {
            this.robot.estLibre();
        }
    }
}
