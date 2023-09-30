package fire;

import map.Case;

/**
 * Classe representant et regroupant les information d'un incendie
 */
public class Incendie {
    /**
     * Position de cet Incendie
     */
    private final Case position;
    /**
     * Eau necessaire pour éteindre l'Incendie
     */
    private int eauNecessaire;
    /**
     * Booléen représentant l'état de l'Incendie (allumé, éteint)
     */
    private boolean eteint;
    /**
     * Booléen représentant si l'incendie est attribué.
     */
    private boolean attribuee;

    /**
     * Initialise un Incendie à la Case position et demandant eauNecessaire pour être éteint
     * @param position      Case de l'incendie
     * @param eauNecessaire eau necessaire pour éteindre l'incendie
     */
    public Incendie(Case position, int eauNecessaire) {
        this.position = position;
        this.eauNecessaire = eauNecessaire;
        eteint = false;
        attribuee = false;
    }

    /**
     * Retourne la Case sur laquel se situe l'Incendie
     * @return Case position
     */
    public Case getPosition() {

        return position;
    }
    /**
     * Retourne l'eau necessaire pour éteindre l'incendie'
     * @return eauNecessaire
     */
    public int getEauNecessaire() {

        return eauNecessaire;
    }
    /**
     * Retourne l'état de l'Incendie (allumé, éteint)
     * @return estEteint
     */
    public boolean getEteint() {
        return eteint;
    }

    /**
     * Eteint le feu
     */
    public void feuEstEteint() {
        eteint = true;
    }

    /**
     * Diminue le l'eau necessaire de quantité d'eau
     * @param quantiteEau   Eau utilisé lors de l'intervention du Robot
     * @return si le feu est éteint
     */
    public boolean arroser(double quantiteEau) {
        if (quantiteEau >= eauNecessaire) {
            eauNecessaire = 0;
            this.eteint = true;
            return true;
        }
        else {
            eauNecessaire -= quantiteEau;
            return false;
        }
    }

    /**
     * Getter de l'attribut attribut.
     * @return true si l'incendie est attribué, false sinon.
     */
    public boolean isAttribuee(){return attribuee;}

    /**
     * Permet de mettre le feu en état attribué.
     */
    public void attribution() {
        attribuee = true;
    }

    /**
     * Redefinition de la méthode toString
     * @return Chaine de caractère représentant la classe.
     */
    @Override
    public String toString() {
        return "L'incendie à lieu en position " + position + ", l'eau necessaire est " + eauNecessaire + ".";
    }


}
