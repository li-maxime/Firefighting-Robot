package data;

import fire.*;
import map.*;
import robot.*;

import java.util.LinkedList;

/**
 * Analyse et stocke les information issue de la lecture de carte donnée par LecteurDonnees
 *
 */
public class DonneesSimulation {
    /**
     * Carte lue LecteurDonnees
     */
    private Carte carte;
    /**
     * Tableau des Robot de la simulation
     */
    private Robot[] robots;
    /**
     * Tableau des types des robots
     */
    private int[] robotType;
    /**
     * Tableau des Incendie de la simulation
     */
    private Incendie[] incendies;
    /**
     * Nombre d'Incendie au total
     */
    private int nbIncendie;
    /**
     * Nombre de Robot au total
     */
    private int nbRobot;



    /**
     * Initialise l'attribut carte à partir des paramètres
     * @param nbRow Indique le nombre de ligne de la carte
     * @param nbCol Indique le nombre de colonne de la carte
     * @param plan  Represente un tableau de tableau de taille (nbRow * nbCol) comportant la nature des terrains de chaque case de la carte à créer
     * @param tailleCase Indique la taille des cases de la carte
     */
    public void createCarte(int nbRow, int nbCol, NatureTerrain[][] plan, int tailleCase) {
        this.carte = new Carte(nbRow, nbCol, plan, tailleCase);
    }

    /**
     * Retourne l'attribut carte
     * @return l'attribut carte
     */

    public Carte getCarte() {
        return carte;
    }

    /**
     * Initialise l'attribut incendies correspondant à tableau d'Incendie de taille nbIncendies et affecte la valeur du nombre d'incendie à l'attribut nbIncendie
     * @param nbIncendie Le nombre d'incendie et la taille du tableau incendies
     */


    public void createListIncendies(int nbIncendie) {

        this.incendies = new Incendie[nbIncendie];
        this.nbIncendie = nbIncendie;

    }

    /**
     * Ajoute un Incendie d'intensité intensite, position = (ligne, col) à la position index du tableau incendie
     * @param ligne     Ligne sur laquelle se situe l'incendie
     * @param col       Colonne sur laquelle se situe l'incendie
     * @param intensite Intensité de l'incendie
     * @param index     Position de l'incendie dans le tableau incendies
     */

    public void addIncendie(int ligne, int col, int intensite, int index) {
        this.incendies[index] = new Incendie(carte.getCase(ligne, col), intensite);
    }

    /**
     * Retourne le nombre d'incendie correspondant à l'attribut nbIncendie
     * @return Le nombre d'incendie
     */

    public int getNbIncendie() {
        return nbIncendie;
    }

    /**
     * Retroune le tableau incendies comportant tout les incendies
     * @return  le tableau incendies
     */
    public Incendie[] getIncendies() {
        return incendies;
    }

    /**
     * Retourne l'incendie en position index du tableau incendies
     * @param position  l'index de l'incendie voulue
     * @return          l'incendie à la position index
     */
    public Incendie getIncendie(int index) {
        return incendies[index];
    }

    /**
     * Initialise l'attribut robots correspondant à un tableau de Robot de taille nbRobot, le type de ces Robot dans le tableau robotType et affecte la valeur du nombre de robot à l'attribut nbRobot
     * @param nbRobot   Le nombre de Robot
     */

    public void createListRobots(int nbRobot) {
        this.robots = new Robot[nbRobot];
        this.robotType = new int[nbRobot];
        this.nbRobot = nbRobot;
    }

    /**
     * Ajoute un Robot de type type à la position (ligne, col), ayant une vitesse de vitesse à la position index du tableau robots
     * @param ligne
     * @param col
     * @param type
     * @param vitesse
     * @param index
     */
    public void addRobot(int ligne, int col, String type, int vitesse, int index) {
        Case tile = carte.getCase(ligne, col);
        Robot robot;
        switch (type) {
            case "DRONE":
                robotType[index] = 0;
                if (vitesse <= 0) {
                    robot = new Drone(this.carte, tile);
                } else {
                    robot = new Drone(this.carte, tile, vitesse);
                }
                break;

            case "ROUES":
                robotType[index] = 1;
                if (vitesse <= 0) {
                    robot = new RobotRoue(this.carte, tile);
                } else {
                    robot = new RobotRoue(this.carte, tile, vitesse);
                }
                break;

            case "CHENILLES":
                robotType[index] = 2;
                if (vitesse <= 0) {
                    robot = new RobotChenille(this.carte, tile);
                } else {
                    robot = new RobotChenille(this.carte, tile, vitesse);
                }
                break;

            case "PATTES":
                robotType[index] = 3;
                robot = new RobotPattes(this.carte, tile);
                break;
            default:
                throw new IllegalArgumentException("Le type n'est pas reconnue");
        }
        this.robots[index] = robot;
    }

    /**
     * Retourne le type (Drone, Robot chenille, robot pattes ou robot roue) du robot d'index position.
     * @param position  Index du Robot
     * @return          Type du Robot
     */
    public int getRobotType(int position) {
        return robotType[position];
    }

    /**
     * Retourne l'attribut nbRobot correspondant au nombre de Robot dans la simulation
     * @return le nombre de Robot
     */
    public int getNbRobot() {
        return nbRobot;
    }

    /**
     * Retourne le Robot à la position index du tableau robots
     * @param index Position du robot souhaité dans le tableau
     * @return      Le Robot en position index dans le tableau
     */
    public Robot getRobot(int index) {

        if(0 <= index && index < nbRobot){ return robots[index]; }
        else {throw new IllegalArgumentException("L'indice du robot est incorrecte.");}
    }
    public LinkedList<Robot> listOfRobot(){
        LinkedList<Robot> listRobot = new LinkedList<>();
        for(int i = 0; i < nbRobot; i++){
            listRobot.add(robots[i]);
        }
        return listRobot;
    }

    /**
     * Convertit et retourne sous forme d'une LinkedList le tableau incendies
     * @return  une LinkedList d'Incendie
     */
    public LinkedList<Incendie> listOfIcendie(){
        LinkedList<Incendie> listIncendie = new LinkedList<>();
        for(int i = 0; i < nbIncendie; i++){
            listIncendie.add(incendies[i]);
        }
        return listIncendie;
    }

}
