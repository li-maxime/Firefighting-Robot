package robot;

import map.*;


import java.util.HashMap;
import java.util.Set;
/**
 * Sous classe de RobotReservoir qui prend en compte les spécificités des drones.
 */
public class Drone extends RobotReservoir {
    /**
     * Constructeur de la classe pour une vitesse par default.
     * @param carte                   carte où se trouve le robot.
     * @param position                case où se trouve le robot à l'étape initiale.
     * @return                        une instance de la classe Drone.
     */
    public Drone(Carte carte, Case position) {
        super(carte, position, Drone.setVitesse(100), 30, 10000, 1800, 10000, ZoneRemplissage.SUR);
    }
    /**
     * Constructeur de la classe si on veut spécifier une vitesse.
     * @param carte                   carte où se trouve le robot.
     * @param position                case où se trouve le robot à l'étape initiale.
     * @return                        une instance de la classe Drone.
     */
    public Drone(Carte carte, Case position, int vitesse) {
        super(carte, position, Drone.setVitesse(vitesse), 30, 10000, 1800, 10000, ZoneRemplissage.SUR);
    }

    /**
     * Méthode privée qui renvoie un dictionnaire qui associe nature du terrain et vitesse du robot sur ce terrain.
     * Elle fournit le paramètre terrainEtVitesse du constructeur de la classe mère.
     * @param vitesse                 vitesse du Drone
     * @return                        un dictionnaire qui associe nature du terrain et vitesse du robot.
     */
    private static final HashMap<NatureTerrain, Integer> setVitesse(int vitesse) {
        if (vitesse <= 150) {
            HashMap<NatureTerrain, Integer> terrainEtVitesse = new HashMap<NatureTerrain, Integer>();
            terrainEtVitesse.put(NatureTerrain.EAU, vitesse);
            terrainEtVitesse.put(NatureTerrain.FORET, vitesse);
            terrainEtVitesse.put(NatureTerrain.ROCHE, vitesse);
            terrainEtVitesse.put(NatureTerrain.TERRAIN_LIBRE, vitesse);
            terrainEtVitesse.put(NatureTerrain.HABITAT, vitesse);
            return terrainEtVitesse;
        } else {
            throw new IllegalArgumentException("La vitesse d'un drone ne peut pas éxcéder 150km/h");
        }
    }
    
    /**
     * Recherche la source d'eau accessible par le drone la
     * plus proche.
     */
    public Sommet toWater() {
		Set<Case> waterSources = this.getCarte().getWaterSources();
		Sommet closestWaterSource = null;
		double minCost = Double.POSITIVE_INFINITY;
		this.navigation = new Itineraires(this);
		
		for (Case position: waterSources) {
			Sommet waterSource = this.getItinieraire().getChemins().get(position);
			if (waterSource != null && waterSource.getCout() < minCost) {
				closestWaterSource = waterSource;
				minCost = waterSource.getCout();
			}
		}
		
		if (closestWaterSource == null || minCost == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("No reachable water sources!");
		}
		
		return closestWaterSource;
	}
}
