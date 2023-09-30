package map;

/**
 * Classe qui représente une case seule.
 */
public class Case {
    private final int ligne;
    private final int colonne;
    NatureTerrain natureTerrain;

    public Case(int ligne, int colonne, NatureTerrain natureTerrain) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.natureTerrain = natureTerrain;
    }

    @Override
    public String toString() {
        return "(" + ligne + "," + colonne + ") et de type " + natureTerrain + ".";
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public Case getVoisinCase(Carte carte, Direction direction) {
        return carte.getVoisinCase(ligne, colonne, direction);
    }

    public NatureTerrain getNatureTerrain() {


        return natureTerrain;
    }

    public boolean possedeVoisin(Carte carte, Direction direction) {

        return carte.possedeVoisin(ligne, colonne, direction);
    }


}
