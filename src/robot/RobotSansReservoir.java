package robot;

import map.*;
import fire.*;

import java.util.HashMap;
/**
 * Sous classe de Robot qui prend en compte les spécificités des robots qui ne possède pas de reservoir.
 */
public abstract class RobotSansReservoir extends Robot {
    /**
     * Constructeur de la classe
     * @param carte                   carte où se trouve le robot.
     * @param position                case où se trouve le robot à l'étape initiale.
     * @param terrainEtVitesse        dictionnaire qui associe nature de terrain praticable par le robot et sa vitesse.
     * @param tempsEcoulement         durée d'une intervention unitaire
     * @param interventionUnitaire    quantité d'eau dans une intervention unitaire.
     * @return                        une instance de la classe robot.
     */
    public RobotSansReservoir(Carte carte, Case position, HashMap<NatureTerrain, Integer> terrainEtVitesse, int tempsEcoulement, int interventionUnitaire) {
        super(carte, position, terrainEtVitesse, tempsEcoulement, interventionUnitaire);
    }
    /**
     * Verse de la poudre sur l'incendie.
     * @param incendie l'incendie cible.
     * @return    true si la poudre a pu être versé, false sinon.
     */
    @Override
    public boolean verser(Incendie incendie) {
        if (incendie.arroser(this.getInterventionUnitaire())) {
            this.estLibre();
        }
        return true;
    }
    
}

