package map;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant la carte où se déplace les robot.
 */
public class Carte {
    /**
     * Le nombre de ligne de cases.
     */
    private int hauteur;
    /**
     * Le nombre de colonne de cases.
     */
    private int longueur;
    /**
     * Unn tableau des cases qui composes la carte.
     */
    private Case[][] plan;

    /**
     * Ensemble des points d'eau de la carte.
     */
    private Set<Case> waterSources;
    /**
     * Taille d'une case.
     */
    private int tailleCase;

    /**
     * Constructeur de la case
     *
     * @param longueur
     * @param hauteur
     * @param plan
     * @param tailleCase
     */

    public Carte(int longueur, int hauteur, NatureTerrain[][] plan, int tailleCase) {
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.tailleCase = tailleCase;
        this.plan = new Case[hauteur][longueur];
        this.waterSources = new HashSet<Case>();
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < hauteur; j++) {
                Case position = new Case(i, j, plan[i][j]);
                this.plan[i][j] = position;
                if (position.getNatureTerrain() == NatureTerrain.EAU) {
                    waterSources.add(position);
                }
            }
        }
    }

    /**
     * Getter de l'attribut longueur.
     *
     * @return attribut longueur
     */
    public int getLongueur() {
        return longueur;
    }

    /**
     * Getter de l'attribut hauteur
     *
     * @return attribut hauteur
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Retourne la case d'indice (i,j) de la carte
     *
     * @param i indice de la ligne
     * @param j indice de la colonne
     * @return la case d'indice (i,j)
     */
    public Case getCase(int i, int j) {
        return plan[i][j];
    }

    /**
     * Gettrer de l'attribut tailleCase
     *
     * @return la taille d'une case.
     */
    public int getTailleCase() {
        return tailleCase;
    }

    /**
     * Verifie si la case (i,j) a bien un voisin dans la direction passée
     * en paramètre.
     *
     * @param i         indice de la ligne.
     * @param j         indice de la colonne.
     * @param direction direction sur laquelle vérifier.
     * @return true si la case (i,j) a un voisin dans cette direction, false sinon.
     */
    public boolean possedeVoisin(int i, int j, Direction direction) {
        switch (direction) {
            case NORD:
                return i + 1 < this.hauteur;
            case SUD:
                return i - 1 >= 0;
            case EST:
                return j + 1 < this.longueur;
            case OUEST:
                return j - 1 >= 0;
            default:
                throw new IllegalArgumentException("La direction n'existe pas.");

        }
    }

    /**
     * Retourne le voisin de la case (i,j) dans la direction données.
     *
     * @param i         indice de la ligne.
     * @param j         indice de la colonne
     * @param direction
     * @return
     */
    public Case getVoisinCase(int i, int j, Direction direction) {
        if (possedeVoisin(i, j, direction)) {
            switch (direction) {
                case NORD:
                    return getCase(i + 1, j);
                case SUD:
                    return getCase(i - 1, j);
                case EST:
                    return getCase(i, j + 1);
                case OUEST:
                    return getCase(i, j - 1);
                default:
                    return null;
            }
        } else {
            throw new IllegalArgumentException("Erreur : Pas de voisin.");
        }

    }

    /**
     * Getter de l'attribut waterSources
     *
     * @return l'attribut waterSources.
     */
    public Set<Case> getWaterSources() {
        return waterSources;
    }

    /**
     * Fonction qui présente la classe Carte.
     *
     * @return presentation de la classe sous forme de string.
     */
    @Override
    public String toString() {
        return "Carte est de dimension " + longueur + "x" + hauteur + ".";
    }
}