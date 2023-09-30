package robot;

import map.*;
import fire.*;

import java.util.HashMap;
import java.util.Set;

/**
 * Classe abstraite qui va implémenter des fonctions et contenir des attributs généraux
 * à tous les types de robot.
 */
public abstract class Robot {
    /**
     * Un objet qui permet de calculer le plus cours chemin entre le robot et une case.
     */
    protected Itineraires navigation;
    /**
     * La carte où se trouve le robot. Nécessaire pour que le robot puisse se déplacer en autonomie.
     */
    private Carte carte;
    /**
     * Un dictionnaire qui à un type de terrain associe la vitesse du robot sur ce terrain.
     */
    private HashMap<NatureTerrain, Integer> terrainEtVitesse;
    /**
     * Un entier qui correspond à la duré d'une intervention unitaire.
     */
    private int tempsEcoulement;
    /**
     * Un entier qui correspond à la quantité d'eau versée lors d'une intervention unitaire.
     */
    private int interventionUnitaire;
    /**
     * La case qui correspond à la position actuelle du robot.
     */
    private Case position;
    /**
     * Un booléen qui indique si robot est occupé avec un incendie.
     */
    private boolean occupee;
    /**
     * L'incendie attribué au robot.
     */
    private Incendie monIncendedie;
    /**
     * Le trajet que le robot doit suivre pour son déplacement actuel.
     */
    private Sommet trajet;


    /**
     * Constructeur de la classe
     * @param carte                   carte où se trouve le robot.
     * @param position                case où se trouve le robot à l'étape initiale.
     * @param terrainEtVitesse        dictionnaire qui associe nature de terrain praticable par le robot et sa vitesse.
     * @param tempsEcoulement         durée d'une intervention unitaire
     * @param interventionUnitaire    quantité d'eau dans une intervention unitaire.
     * @return                        une instance de la classe robot.
     */
    public Robot(Carte carte, Case position, HashMap<NatureTerrain, Integer> terrainEtVitesse, int tempsEcoulement, int interventionUnitaire) {
        this.position = position;
        this.terrainEtVitesse = terrainEtVitesse;
        this.tempsEcoulement = tempsEcoulement;
        this.carte = carte;
        this.interventionUnitaire = interventionUnitaire;
        this.navigation = new Itineraires(this);
    }
    /**
     * Redéfinition de la fonction toString
     * @return   Description de l'objet dans une chaine de caractère.
     */
    @Override
    public String toString(){
        return "Position " + position.toString() + " occupee = " + occupee ;
    }

    /**
     * Renvoie l'incendie attribué au robot. La reférence est nulle si aucun incendie ne lui est attribué.
     * @return  l'incendie attribué au robot
     */

    public Incendie getMonIncendie(){
        return this.monIncendedie;
    }

    /**
     * Renvoie l'intervention unitaire du robot.
     * @return  l'attribue interventionUnitaire.
     */
    public int getInterventionUnitaire() {

        return this.interventionUnitaire;
    }

    /**
     * Renvoie un boolean qui nous dit si le robot peut passer sur ce terrain ou non.
     * @param terrain nature du terrain
     * @return true si le robot peut aller sur ce type de terrain, false sinon.
     */
    public boolean inTerrainPraticable(NatureTerrain terrain){
        return terrainEtVitesse.containsKey(terrain);
    }
    /**
     * Renvoie true si le robot est occupé et false sinon.
     * @return  l'attribut occupe.
     */
    public boolean getOccupee(){

        return occupee;
    }
    /**
     * Renvoie la position du robot.
     * @return  la case où se trouve le robot.
     */
    public Case getPosition() {

        return position;
    }
    /**
     * Permet de récupérer le trajet entre le robot et un incendie.
     * @return le trajet pour atteindre l'incendie.
     */
    public Sommet getTrajet(){
        return trajet;
    }
    /**
     * Permet de récupérer l'attribue itinéraire du robot.
     * On renvoie l'objet tel quel car il est immuable, donc l'encapsulation n'est pas brisée.
     * @return l'attribut itineraires.
     */
    protected Itineraires getItinieraire(){
        return navigation;}

    /**
     * Renvoie la vitesse du robot selon la nature du terrain
     * @param terrain nature du terrain
     * @return la vitesse du robot sur le terrain.
     */

    public int getVitesse(NatureTerrain terrain) {
        if (this.terrainEtVitesse.containsKey(terrain)) {
            return terrainEtVitesse.get(terrain);
        }
        else {
            throw new IllegalArgumentException("Le robot ne va pas sur ce type de terrain");
        }
    }


    //a intégrer dans verser eau plus tard
    /**
     * Renvoie la durée d'une intervention unitaire.
     * @return l'attribut TempsEcoulement.
     */

    public double getTempsEcoulement() {

        return tempsEcoulement;
    }

    /**
     * Permet d'attribuer un incendie à un robot. Lève une exception s'il est occupée ou si le feu est inaccessible pour le robot.
     * @param incendie l'incendie que l'on souhaite attribuer.
     */
    public void attributionTache(Incendie incendie) {
        if (!occupee) {
            monIncendedie = incendie;
            if (!navigation.canAccess(monIncendedie.getPosition())){

                throw new IllegalArgumentException("Le robot ne peut pas atteindre cet incendie");

            }
            trajet = navigation.itineraireTo(monIncendedie.getPosition());
            occupee = true;
        }
        else{
            throw new IllegalCallerException("Le robot est occupee");
        }
    }

    /**
     * Permet de récupérer le temps du trajet entre le robot et un incendie.
     * @param incendie l'incendie cible.
     * @return temps du trajet entre le robot et un incendie.
     */
    public long getTimeTo(Incendie incendie){

        return (long) navigation.itineraireTo(incendie.getPosition()).getCout();
    }
    //re calcule itinéraire jusu'au feu.
    /**
     * Permet de mettre à jour l'attribue trajet et navigation.
     * Pour calculer de nouveau plus cour chemin si le robot a changé de position.
     * Elle recalcule le chemain le plus court vers l'incendie attribué s'il y en a un.
     */
    public void majTrajet(){
        this.navigation = new Itineraires(this);
        if (monIncendedie != null && !navigation.canAccess(monIncendedie.getPosition())){
            throw new IllegalStateException("Le robot ne peut plus accéder au feu.");
        }
        trajet = navigation.itineraireTo(monIncendedie.getPosition());
    }
    /**
     * Permet de vérifier qu'un incendie est accessible par le robot.
     * C'est-à-dire : qu'il existe un trajet de la position actuelle à l'incendie cible.
     * @param incendie l'incendie cible.
     * @return true si le trajet jusqu'à l'incendie existe, false sinon.
     */
    public boolean canAccessFire(Incendie incendie){

        return navigation.canAccess(incendie.getPosition());
    }

    /**
     * Permet remettre le robot dans l'état libre. On remet alors l'attribue occupee à false/
     */
    public void estLibre(){

        occupee = false;
    }



    public Carte getCarte(){

        return carte;
    }
    /**
     * Permet de verser de l'eau ou de la poudre sur un incendie.
     * @param incendie l'incendie cible.
     * @return true si on a pu verser false sinon.
     */
    public abstract boolean verser(Incendie incendie);


    /**
     * Permet de déplacer le robot sur une case voisine. Parmis les
     * cases se trouvant à l'EST, l'OUEST, au NORD ou au SUD.
     * @param direction donne la direction que doit prendre le robot.
     */
    public void deplacement(Direction direction) {
        //le premier if pourrait être mis dans le carte dans getVoisinCase.
        //on replacera le
        if (position.possedeVoisin(carte, direction)) {
            Case voisin = position.getVoisinCase(carte, direction);

            //vérifie si le robot a le droit d'être là.
            if (this.terrainEtVitesse.containsKey(voisin.getNatureTerrain())) {this.position = voisin;}
            else { throw new IllegalArgumentException("Le robot n'a pas le droit d'être là."); }
        }
        else {throw new IllegalArgumentException("La case n'éxiste pas.");}
    }


    /**
     * Permet de mettre à jour l'attribut itinéraire de la classe robot.
     */
    public void nextItineraire(){
        this.navigation = new Itineraires(this);
    }


    

}

