package map;

import java.util.*;
import robot.*;
/**
 * La classe Itineraires représente les plus court
 * chemins vers toutes les cases de la carte à partir
 * de la case du robot auquelle elle est associée.
 * Les chemins sont calculés grace à l'algorithme de Dijkstra.
 * @author Équipe 27
 */

public class Itineraires {
    private Map<Case, Sommet> chemins;
    private Set <Sommet> graphe;
    
    
    
    
    /**
     * Calcule les plus court chemins en utilisant
     * l'algorithme de Dijkstra
     * @param robot auxquelles les itinéraires seront associés
     */
    public Itineraires(Robot robot) {
		Carte carte = robot.getCarte();
		this.graphe = new HashSet<Sommet>();
		
		// Initialisation des sommets non visités
		for(int i =0; i < carte.getHauteur(); i++){
			for(int j =0; j < carte.getHauteur(); j++){
				this.graphe.add(new Sommet(carte.getCase(i, j)));
				
			}
		}
		
		chemins = new HashMap<Case, Sommet> ();
    	
    	Sommet depart = new Sommet(robot.getPosition());
    	depart.setCout(0);
    	Set<Sommet> visited = new HashSet<Sommet>();
    	visited.add(depart);
    	graphe.remove(depart);
    	chemins.put(depart.getPosition(), depart);
    	
    	// Initialisation du sommet courant et des voisins non
    	// encore marqués. L'utilisation d'une TreeSet garantit un ordre
    	// selon le coût permettant de sélectionner à chaque fois les
    	// chemins les moins coûteux.
    	Sommet sommetCourant = depart;
    	TreeSet<Sommet> voisins = new TreeSet<>();
    	
    	while (!graphe.isEmpty()) {
    		
    		sommetCourant.getVoisins(voisins, carte, robot, sommetCourant, visited);
    		sommetCourant = voisins.pollFirst();
    		visited.add(sommetCourant);
    		graphe.remove(sommetCourant);
    		chemins.put(sommetCourant.getPosition(), sommetCourant);
    			
    	}
	}
	

	/**
	 * @param destination
	 * @return un booléen correspondant à si la destination est
	 * accessible ou non
	 */
	public boolean canAccess(Case destination){
		return this.chemins.get(destination).getCout() != Double.POSITIVE_INFINITY;
	}

	/**
	 * @param destination
	 * @return Une instance de la classe Sommet contenant
	 * le temps nécéssaire pour atteindre la case destination
	 * et une liste chainée des directions à prendre
	 */
	public Sommet itineraireTo(Case destination) {
		Sommet sommet =  this.chemins.get(destination);
			
		if (sommet.getCout() == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("Path not found! Robot cannot access square!");
		}
			
		return this.chemins.get(destination);
	}


	public Map<Case, Sommet> getChemins() {
		return chemins;
	}


	@Override
	public String toString() {
		return "Itineraires [chemins=" + chemins + "]";
	}
	
	
}
