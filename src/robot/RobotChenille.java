package robot;

import map.*;

import java.util.HashMap;
import java.util.Set;
/**
 * Sous classe de RobotReservoir qui prend en compte les spécificités des robots à chenilles.
 */
public class RobotChenille extends RobotReservoir {
	/**
	 * Constructeur de la classe pour une vitesse par default.
	 * @param carte                   carte où se trouve le robot.
	 * @param position                case où se trouve le robot à l'étape initiale.
	 * @return                        une instance de la classe Drone.
	 */
    public RobotChenille(Carte carte, Case position) {
        super(carte, position, RobotChenille.setVitesse(60), 8, 2000, 300, 100, ZoneRemplissage.A_COTE);
    }
	/**
	 * Constructeur de la classe pour une vitesse par default.
	 * @param carte                   carte où se trouve le robot.
	 * @param position                case où se trouve le robot à l'étape initiale.
	 * @return                        une instance de la classe RobotChenille.
	 */
    public RobotChenille(Carte carte, Case position, int vitesse) {
        super(carte, position, RobotChenille.setVitesse(vitesse), 8, 2000, 300, 100, ZoneRemplissage.A_COTE);
    }
	/**
	 * Méthode privée qui renvoie un dictionnaire qui associe nature du terrain et vitesse du robot sur ce terrain.
	 * Elle fournit le paramètre terrainEtVitesse du constructeur de la classe mère.
	 * @param vitesse                 vitesse du Drone
	 * @return                        un dictionnaire qui associe nature du terrain et vitesse du robot.
	 */
    private static HashMap<NatureTerrain, Integer> setVitesse(int vitesse) {
        if (vitesse <= 80) {
            HashMap<NatureTerrain, Integer> terrainEtVitesse = new HashMap<NatureTerrain, Integer>();
            terrainEtVitesse.put(NatureTerrain.TERRAIN_LIBRE, vitesse);
            terrainEtVitesse.put(NatureTerrain.HABITAT, vitesse);
            terrainEtVitesse.put(NatureTerrain.FORET, vitesse / 2); //remettre les vitesses en double.
            return terrainEtVitesse;
        } else {
            throw new IllegalArgumentException("La vitesse d'un robot à chenille ne peut pas éxcéder 80km/h");
        }
    }
    
    /**
    * Recherche la source d'eau accessible par le robot la
    * plus proche.
    */
    public Sommet toWater() {
    	Carte carte = this.getCarte();
		Set<Case> waterSources = carte.getWaterSources();
		Sommet closestWaterSource = null;
		double minCost = Double.POSITIVE_INFINITY;
		this.navigation = new Itineraires(this);
		
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
