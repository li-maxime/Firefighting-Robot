package robot;

import map.*;
import fire.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/**
 * Sous classe de Robot qui prend en compte les spécificités des robots qui possède un reservoir d'eau.
 */

public abstract class RobotReservoir extends Robot {
    /**
     * La capicité du resevoir d'eau.
     */
    private int capaciteEau;
    /**
     * Détermine la zone de remplissage du robot.
     */
    private ZoneRemplissage zoneRemplissage;
    /**
     * Le temps pour remplir le reservoir d'eau.
     */
    private int tempsRemplissage;
    /**
     * Donne le niveau d'eau dans le réservoir du robot.
     */
    private int volumeEauCourant;

    /**
     * Constructeur de la classe
     * @param carte                   carte où se trouve le robot.
     * @param position                case où se trouve le robot à l'étape initiale.
     * @param terrainEtVitesse        dictionnaire qui associe nature de terrain praticable par le robot et sa vitesse.
     * @param tempsEcoulement         durée d'une intervention unitaire
     * @param interventionUnitaire    quantité d'eau dans une intervention unitaire.
     * @param capaciteEau             capacité du reservoir.
     * @param zoneRemplissage         zone où le robot peut remplir son réservoir.
     * @param tempsRemplissage        durée du remplissage du reservoir.
     * @return                        une instance de la classe robot.
     */
    public RobotReservoir(Carte carte, Case position, HashMap<NatureTerrain, Integer> terrainEtVitesse, int tempsEcoulement, int capaciteEau, int tempsRemplissage, int interventionUnitaire, ZoneRemplissage zoneRemplissage) {
        super(carte, position, terrainEtVitesse, tempsEcoulement, interventionUnitaire);
        this.capaciteEau = capaciteEau;
        this.volumeEauCourant = capaciteEau;
        this.tempsRemplissage = tempsRemplissage;
        this.zoneRemplissage = zoneRemplissage;
    }


    public double getvolumeEauCourant() {
        return volumeEauCourant;
    }

    /**
     * Renvoie le temps de remplissage du réservoir.
     * @return l'attribut tempsRemplissage.
     */
    public int getTempsRemplissage(){return tempsRemplissage;}
    public double getCapaciteEau() {

        return this.capaciteEau;
    }

    /**
     * Verse de l'eau sur l'incendie.
     * @param incendie l'incendie cible.
     * @return    true si l'eau a pu être versé, false sinon.
     */
    @Override
    //retourne false si le robot doit être remplie
    public boolean verser(Incendie incendie) {
        if(volumeEauCourant >= this.getInterventionUnitaire()) {
            volumeEauCourant -= this.getInterventionUnitaire();
            if(incendie.arroser(this.getInterventionUnitaire())){};
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Constructeur de la classe
     * @param direction la direction de la case par rapport au robot (NORD, SUD, EST, OUEST)
     * @return true si le voisin est une case eau, false sinon.
     */
    private boolean voisinEstEau(Direction direction) {
        Case position = this.getPosition();
        if (position.possedeVoisin(this.getCarte(), direction)) {
            return position.getVoisinCase(this.getCarte(), direction).getNatureTerrain() == NatureTerrain.EAU;
        } else {
            return false;
        }
    }

    /**
     * Fonction qui permet de remplir le reservoir du robot s'il est bien positionné.
     * Lève une exception sinon.
     */
    public void remplirReservoir() {
        if (zoneRemplissage == ZoneRemplissage.A_COTE) {
            if (this.voisinEstEau(Direction.NORD)) {
                this.volumeEauCourant = capaciteEau;
            } else if (this.voisinEstEau(Direction.SUD)) {
                this.volumeEauCourant = capaciteEau;
            } else if (this.voisinEstEau(Direction.EST)) {
                this.volumeEauCourant = capaciteEau;
            } else if (this.voisinEstEau(Direction.OUEST)) {
                this.volumeEauCourant = capaciteEau;
            } else {
                throw new IllegalCallerException("Le robot n'est pas bien positionné pour se remplir");
            }
        } else {
            if (this.getPosition().getNatureTerrain() == NatureTerrain.EAU) {
                this.volumeEauCourant = capaciteEau;
            } else {
                throw new IllegalCallerException("Le robot n'est pas bien positionné pour se remplir");
            }
        }
    }
    
    /**
     * Recherche la source d'eau accessible par le robot la
     * plus proche.
     */
    public abstract Sommet toWater();

}