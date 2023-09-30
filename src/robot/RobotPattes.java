package robot;

import map.*;
import fire.*;

import java.util.HashMap;
/**
 * Sous classe de RobotReservoir qui prend en compte les spécificités des robots à pattes.
 */
public class RobotPattes extends RobotSansReservoir {

    /**
     * Un dictionnaire qui associe nature du terrain et vitesse du robot sur ce terrain.
     * Elle sera le paramètre terrainEtVitesse du constructeur de la classe mère.
     * Elle est ici car super() doit être la première instruction du constructeur.
     * Elle n'a pas de getter ni de setter, car elle constitue un artifice pour la construction de la classe.
     */
    private static  HashMap<NatureTerrain, Integer> terrainEtVitesse = new HashMap<NatureTerrain, Integer>() {{
        put(NatureTerrain.TERRAIN_LIBRE, 30);
        put(NatureTerrain.HABITAT, 30);
        put(NatureTerrain.ROCHE, 10);
        put(NatureTerrain.FORET, 30);
    }
    };
    /**
     * Constructeur de la classe pour une vitesse par default.
     * @param carte                   carte où se trouve le robot.
     * @param position                case où se trouve le robot à l'étape initiale.
     * @return                        une instance de la classe RobotPattes.
     */
    public RobotPattes(Carte carte, Case position) {
        super(carte, position, terrainEtVitesse, 1, 10);

    }


}

