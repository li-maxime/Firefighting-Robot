package robot;

import map.*;

import java.util.HashMap;
import java.util.Set;
/**
 * Sous classe de RobotReservoir qui prend en compte les spécificités des robots à roues.
 */
public class RobotRoue extends RobotReservoir {
	/**
	 * Constructeur de la classe pour une vitesse par default.
	 * @param carte                   carte où se trouve le robot.
	 * @param position                case où se trouve le robot à l'étape initiale.
	 * @return                        une instance de la classe RobotRoue.
	 */
    public RobotRoue(Carte carte, Case position) {
        super(carte, position, RobotRoue.setVitesse(80), 5, 5000, 600, 100, ZoneRemplissage.A_COTE);
    }
	/**
	 * Constructeur de la classe si on veut spécifier une vitesse.
	 * @param carte                   carte où se trouve le robot.
	 * @param position                case où se trouve le robot à l'étape initiale.
	 * @return                        une instance de la classe RobotRoue.
	 */
    public RobotRoue(Carte carte, Case position, int vitesse) {
        super(carte, position, RobotRoue.setVitesse(vitesse), 5, 5000, 600, 100, ZoneRemplissage.A_COTE);
    }
	/**
	 * Méthode privée qui renvoie un dictionnaire qui associe nature du terrain et vitesse du robot sur ce terrain.
	 * Elle fournit le paramètre terrainEtVitesse du constructeur de la classe mère.
	 * @param vitesse                 vitesse du Drone
	 * @return                        un dictionnaire qui associe nature du terrain et vitesse du robot.
	 */
    private static final HashMap<NatureTerrain, Integer> setVitesse(int vitesse) {
        HashMap<NatureTerrain, Integer> terrainEtVitesse = new HashMap<NatureTerrain, Integer>();
        terrainEtVitesse.put(NatureTerrain.TERRAIN_LIBRE, vitesse);
        terrainEtVitesse.put(NatureTerrain.HABITAT, vitesse);
        return terrainEtVitesse;
    }
    
    
    /**
    * Recherche la source d'eau accessible par le robot la
    * plus proche.
    */
    public Sommet toWater() {
    	Carte carte = this.getCarte();
		Set<Case> waterSources = carte.getWaterSources();
		Sommet closestWaterSource = null;
		this.navigation = new Itineraires(this);
				
		double minCost = Double.POSITIVE_INFINITY;
		
		for (Case position: waterSources) {
			if (position.possedeVoisin(carte, Direction.EST)){
  				Case voisin = position.getVoisinCase(carte, Direction.EST);
  				Sommet waterSource = this.getItinieraire().getChemins().get(voisin);
  				if (waterSource != null && waterSource.getCout() < minCost) {
  					closestWaterSource = waterSource;
  					minCost = waterSource.getCout();
  				}		
  			}
			
			if (position.possedeVoisin(carte, Direction.OUEST)){
  				Case voisin = position.getVoisinCase(carte, Direction.OUEST);
  				Sommet waterSource = this.getItinieraire().getChemins().get(voisin);
  				if (waterSource != null && waterSource.getCout() < minCost) {
  					closestWaterSource = waterSource;
  					minCost = waterSource.getCout();
  				}		
  			}
			
			if (position.possedeVoisin(carte, Direction.NORD)){
  				Case voisin = position.getVoisinCase(carte, Direction.NORD);
  				Sommet waterSource = this.getItinieraire().getChemins().get(voisin);
  				if (waterSource != null && waterSource.getCout() < minCost) {
  					closestWaterSource = waterSource;
  					minCost = waterSource.getCout();
  				}		
  			}
			
			if (position.possedeVoisin(carte, Direction.SUD)){
  				Case voisin = position.getVoisinCase(carte, Direction.SUD);
  				Sommet waterSource = this.getItinieraire().getChemins().get(voisin);
  				if (waterSource != null && waterSource.getCout() < minCost) {
  					closestWaterSource = waterSource;
  					minCost = waterSource.getCout();
  				}		
  			}
			
		}
		
		if (closestWaterSource == null || minCost == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("No reachable water sources!");
		}
		
		return closestWaterSource;
	}
}
