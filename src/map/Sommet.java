package map;

import robot.*;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;


/**
 * Les sommets sont utilisés pour la représentation
 * des graphes. Un sommet contient une case, un coût
 * correspondant au temps nécéssaire pour atteindre ladite
 * case, et une liste chainée contenant les directions pour
 * atteindre la case.
 * @author Equipe 27
 *
 */
public class Sommet implements Comparable<Sommet>{
	private double cout;
	private Case position;
	private LinkedList<Direction> direction;
	
	public Sommet(Case position) {
		this.cout = Double.POSITIVE_INFINITY;
		this.position = position;
		this.direction = new LinkedList<>();
	}
	

	public Sommet(Case position, LinkedList<Direction> direction) {
		this.cout = Double.POSITIVE_INFINITY;
		this.position = position;
		this.direction = new LinkedList<>(direction);
	}


	public Case getPosition() {
		return position;
	}


	public LinkedList<Direction> getDirection() {
		return direction;
	}

	public Direction getNextDeplacement() {
		return direction.pop();
	}

	public boolean finDuChemin() {
		return this.direction.isEmpty();
	}

	public double getCout() {
		return cout;
	}
	
	
	protected void setCout(double cout) {
		this.cout = cout;
	}

	/**
	 * Redéfinition de la méthode equals, deux sommets
	 * sont égaux s'ils contiennent la même case
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Sommet)) {
            return false;
        }
		
		Sommet s = (Sommet) obj;
		
		return position == s.getPosition();
	}

	@Override
	public int hashCode() {
		return this.position.hashCode();
	}

	@Override
	public String toString() {
		return "Sommet [coût=" + cout + ", position=" + position + ", direction=" + direction + "]";
	}
	
	
	
	/**
	 * Ajoute les voisins non visités au set des voisins tout
	 * en mettant à jour leurs coûts.
	 * @param voisins : set des voisins des cases visitées
	 * @param carte
	 * @param robot
	 * @param sommetCourant
	 * @param visited
	 */
	public void getVoisins(TreeSet<Sommet> voisins, Carte carte, Robot robot, Sommet sommetCourant, Set<Sommet> visited){
		this.getVoisinDirection(voisins, carte, robot, sommetCourant, visited, Direction.EST);
		this.getVoisinDirection(voisins, carte, robot, sommetCourant, visited, Direction.OUEST);
		this.getVoisinDirection(voisins, carte, robot, sommetCourant, visited, Direction.SUD);
		this.getVoisinDirection(voisins, carte, robot, sommetCourant, visited, Direction.NORD);
	}
		
	public void getVoisinDirection(TreeSet<Sommet> voisins, Carte carte, Robot robot, 
							Sommet sommetCourant, Set<Sommet> visited, Direction direction){
		
			double newCout;
			
			// Si notre case possède une case voisine, nous insérons cette dernière dans un 
			// nouveau Sommet et ajoutons la direction qui y mène.
			if (position.possedeVoisin(carte, direction)) {
				Sommet voisin = new Sommet(position.getVoisinCase(carte, direction), sommetCourant.getDirection());
				voisin.getDirection().add(direction);
				
				// Nous mettons à jour les coûts uniquement lorsque le sommet voisin n'a
				// pas encore été visité.
				if (!visited.contains(voisin)) {
					
					// Si le Robot peut se déplacer dans la case de départ et dans la case 
					// voisine, alors le nouveau coût correspond
					// au temps de déplacement de la première case à la deuxième.
					if (robot.inTerrainPraticable(voisin.getPosition().getNatureTerrain())
							&& robot.inTerrainPraticable(sommetCourant.getPosition().getNatureTerrain())) {
						
						newCout = sommetCourant.getCout() + (carte.getTailleCase() * 3600 /
								(2 * robot.getVitesse(sommetCourant.getPosition().getNatureTerrain()) * 1000)) +
								(carte.getTailleCase() * 3600 / 
								(2 * robot.getVitesse(voisin.getPosition().getNatureTerrain()) * 1000));
					}
					
					// Si le Robot est soit incapable de se déplacer dans la case 
					// de départ ou dans la case d'arrivée,
					// alors le coût sera infini puisqu'il ne pourra pas faire le déplacement
					// d'une case à l'autre.
					else {
						newCout = Double.POSITIVE_INFINITY;
					}
					
					
					// Quand il y'a déjà un chemin possible vers la case,
					// il faut vérifier que ce chemin est moins court
					// avant de mettre à jour le coût du Sommet.
					if (voisins.contains(voisin)) {
						Sommet oldVoisin = voisins.floor(voisin);
						if (oldVoisin.getCout() > newCout) {
							voisin.setCout(newCout);
							voisins.remove(oldVoisin);
							voisins.add(voisin);
						}
					}
					// Sinon, la mise à jour est faite directement.
					else {
						voisin.setCout(newCout);
						voisins.add(voisin);
					}
				}
			}
	}

	/*
	 * Redéfinition de la méthode compareTo afin de garantir
	 * que l'ordre des Sommets soit fait sur l'ordre naturel
	 * de leurs coûts. Deux sommets sont cependant égaux seulement
	 * et seulement si ils contiennent la même case.
	 * La transitivité est conservée. L'égalité correspond à
	 * l'égalité donnée par la fonction equals.
	 */
	@Override
	public int compareTo(Sommet o) {
		return (this.cout < o.getCout()) ? -1 : ((this.equals(o)) ? 0 : 1);
	}
	
	
}
