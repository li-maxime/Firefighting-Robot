package io;

import data.*;
import map.*;
import fire.*;
import event.*;


import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.*;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import robot.Robot;
import strategie.ChefPompier;
import strategie.StrategiePCC;

/**
 * Simulateur d'évènement. Elle permet d'ordonnancer des évènements et de les exécuter à l'instant voulu.
 */
public class Simulateur implements Simulable, ComponentListener {
    /**
     * Chemin de la carte lue
     */
    String filename;
    /**
     * L'interface graphique associée
     */
    private GUISimulator gui;
    /**
     * Information issue de la lecture de la carte
     */
    private DonneesSimulation data;
    /**
     * Date actuelle de la simulation
     */
    private long dateSimulation;
    /**
     * TreeMap comportant les listes d'evenement devant se produire à l'instant de la clé
     */
    private TreeMap<Long, Queue<Evenement>> listEvenement;
    /**
     * HashMap contenant tout les itinéraires de chaque Robot, la clé est le Robot associé
     */
    private Map<Robot,Itineraires> itineraires;


    /**
     * Initialisation du Simulateur à partir de l'interface graphique gui, de l'ensemble des données lues data et du chemin de la carte lue
     * @param gui       Interface graphique
     * @param data      Ensemble de données issue de la lecture de la carte
     * @param filename  Chemin de la carte lue
     */
    public Simulateur(GUISimulator gui, DonneesSimulation data, String filename) {
        this.gui = gui;
        this.filename = filename;
        gui.addComponentListener(this);
        gui.setSimulable(this);
        this.data = data;
        drawMap();
        this.dateSimulation = 0;
        this.listEvenement = new TreeMap<Long, Queue<Evenement>>();
        itineraires = new HashMap<>();
        for(int i = 0; i < data.getNbRobot(); i++){
            itineraires.put(data.getRobot(i), new Itineraires(data.getRobot(i)));
        }
    }

    /**
     * Retourne l'itinéraire du robot
     * @param robot Robot à qui ont veut l'itinéraire
     * @return Itinéraire du robot choisi
     */
    public Itineraires getItineraires(Robot robot){ return itineraires.get(robot);}
    public long getDateSimu() {
        return dateSimulation;
    }

    /**
     * Incrémente la date simulation et execute tout les evenements qui ont une date inférieur ou égale à la date simulation
     */
    public void incrementeDate() {
        //this.dateSimulation += 1;
        //System.out.println("Date Simulation = " + dateSimulation);
        //System.out.println(listEvenement);
        //if (!listEvenement.isEmpty()){
            Map.Entry<Long, Queue<Evenement>> nextEvent = listEvenement.firstEntry();
            while(nextEvent != null && nextEvent.getKey() <= dateSimulation) {

                Queue<Evenement> aExecuter = nextEvent.getValue();
                while (!aExecuter.isEmpty()) {
                    aExecuter.poll().execute();
                }
                listEvenement.remove(nextEvent.getKey());
                nextEvent = listEvenement.firstEntry();
            }

        //}
        dateSimulation += 1;
    }

    /**
     * Retourne si la liste d'évenement est vide
     * @return un booléen avec l'état de la liste d'evenement
     */
    public boolean simulationTerminee() {
        return this.listEvenement.isEmpty();
    }

    /**
     * Ajoute un Evenement à la date event.getDate()
     * @param event Evenement à ajouté à sa date
     */
    public void ajoutEvenement(Evenement event) {
        long date = event.getDate();
        if (date < 0){
            throw new IllegalArgumentException("La date de l'evenement n'as pas été déclaré");
        }
        if (!this.listEvenement.containsKey(date)){

            Queue<Evenement> liste = new LinkedList<Evenement>();
            this.listEvenement.put(date, liste);
        }
        this.listEvenement.get(date).add(event);
    }

    /**
     * Génère et affiche l'interface graphique en fonction des informations contenues dans data en fonction de la taille de la fenetre
     */
    private void drawMap() {
        Carte map = data.getCarte();
        Incendie[] listIncendie = data.getIncendies();
        Incendie currentIncendie;
        Robot currentRobot;
        Case tileIncendie;
        Case tileRobot;
        int nbRow = map.getHauteur();
        int nbCol = map.getLongueur();
        int width = gui.getPanelWidth();
        int height = gui.getPanelHeight();


        int tileSize = Math.min((width - 40) / nbCol, (height - 40) / nbRow);
        //System.out.println(tileSize);
        int xMargin = (width - nbCol * tileSize) / 2;
        int yMargin = (height - nbRow * tileSize) / 2;
        //System.out.println(yMargin);


        for (int i = 0; i < nbRow; i++) {
            for (int j = 0; j < nbCol; j++) {
                drawTile(tileSize * j + xMargin, tileSize * (i - 1) + yMargin, map.getCase(i, j), tileSize);
            }
        }
        //System.out.println("nbRow " + nbRow);
        int nbIncendies = data.getNbIncendie();
        for (int i = 0; i < nbIncendies; i++) {
            currentIncendie = data.getIncendie(i);
            tileIncendie = currentIncendie.getPosition();
            drawIncendie(tileSize * tileIncendie.getColonne() + xMargin, tileSize * tileIncendie.getLigne() + yMargin, currentIncendie.getEauNecessaire(), tileSize);
        }
        //System.out.println("nbIncendie " + nbIncendies);

        int nbRobot = data.getNbRobot();
        for (int i = 0; i < nbRobot; i++ ) {
            currentRobot = data.getRobot(i);
            tileRobot = currentRobot.getPosition();
            drawRobot(tileSize * tileRobot.getColonne() + xMargin, tileSize * tileRobot.getLigne() + yMargin, tileSize, data.getRobotType(i));
        }
        //System.out.println("nbRobot " + nbRobot);

    }

    /**
     * Affiche la Case tile à la position (x, y) et de taille graphique size * size dans l'interface graphique.
     * Le rendu graphique dépend de la NatureTerrain de la Case.
     * Les ressources graphiques sont contenue dans /images/tile/
     * L'image des habitations varie en fonction du hashcode de la Case.
     * @param x     Postition verticale du pixel en haut à gauche
     * @param y     Postition horizontale du pixel en haut à gauche
     * @param tile  La Case à afficher
     * @param size  La taille de la Case dans l'interface graphique
     */
    private void drawTile(int x, int y, Case tile, int size) {


        NatureTerrain nature = tile.getNatureTerrain();
        String imgDir = "images/tile/";
        String tileType;

        switch (nature) {
            case EAU:
                tileType = "water.png";
                break;
            case ROCHE:
                tileType = "mountain.png";
                break;
            case FORET:
                tileType = "forest2.png";
                break;
            case TERRAIN_LIBRE:
                tileType = "grass.png";
                break;
            case HABITAT:
                int index = tile.hashCode() % 10 + 1;
                if (index > 7) {
                    index = 1;
                }
                if (index == 7) {
                    index = 6;
                }
                tileType = "house/village" + index + ".png";
                break;
            default:
                throw new IllegalArgumentException("Erreur : Nature inconue");
        }

        gui.addGraphicalElement(new ImageElement(x, y, imgDir + tileType, size, size * 2, gui));

    }

    /**
     * Affiche un Incendie de taille size à la position (x, y).
     * Les ressources graphiques pour les Incendie sont contenue dans /images/fire/
     * La couleurs des Incendie dépend de leur intensité
     * <ul>
     *     <li>Pas d'image si le feu est éteint (intensité = 0)</li>
     *     <li>Un feu orange si intensité <7500</li>
     *     <li>Un feu bleu si 7500>intensité>13000 </li>
     *     <li>Un feu violet si intensité >=13000</li>
     * </ul>
     * @param x         Postition verticale du pixel en haut à gauche
     * @param y         Postition horizontale du pixel en haut à gauche
     * @param intensite Intensité de l'Incendie à afficher
     * @param size      Taille de l'image dans l'interface graphique
     */
    private void drawIncendie(int x, int y, int intensite, int size) {
        int index = ((x & y) % 4) + 1;
        if (intensite >= 13000) {
            gui.addGraphicalElement(new ImageElement(x, y - size, "images/fire/firepurple.gif", size, 2 * size, gui));
            //gui.addGraphicalElement(new ImageElement(x, y, "images/fire/fire"  index  "red.png", size, size, gui));
        } else if (intensite >= 7500) {
             gui.addGraphicalElement(new ImageElement(x, y - size, "images/fire/fireblue.gif", size, 2 * size, gui));
        } else if (intensite <= 0) {
        } else {
            //gui.addGraphicalElement(new ImageElement(x, y, "images/fire/fire"  index  "blue.png", size, size, gui));
            gui.addGraphicalElement(new ImageElement(x, y - size, "images/fire/firered.gif", size, 2 * size, gui));
        }
    }

    /**
     * Dessine un Robot de type type, de taille size à la position (x, y)
     * Le Robot peut être de type Drone, roue, chenille ou patte
     * Les ressources graphiques Robot sont dans /images/robot/
     * @param x         Postition verticale du pixel en haut à gauche
     * @param y         Postition horizontale du pixel en haut à gauche
     * @param size      Taille de l'image dans l'interface graphique
     * @param type      Type du robot à afficher
     */
    private void drawRobot(int x, int y, int size, int type) {
        switch (type) {
            case 0:
                gui.addGraphicalElement(new ImageElement(x, y, "images/robot/drone.png", size, size, gui));
                break;
            case 1:
                gui.addGraphicalElement(new ImageElement(x, y, "images/robot/robotroue.png", size, size, gui));
                break;
            case 2:
                gui.addGraphicalElement(new ImageElement(x, y, "images/robot/robotchenille.png", size, size, gui));
                break;
            case 3:
                gui.addGraphicalElement(new ImageElement(x, y, "images/robot/robotpatte.png", size, size, gui));
                break;
            default:
                throw new IllegalArgumentException("Le type du robot est inconnue");

        }

    }

    /**
     * Incrémente la date de la simulation et execute les Evenement assicié à la date lors de l'appuie sur le boutton Next
     */
    @Override
    public void next() {
        incrementeDate();
        //System.out.println(dateSimulation);
        gui.reset();
        gui.resizePanel(gui.getContentPane().getSize().width, gui.getContentPane().getSize().height - 70);
        drawMap();
    }

    /**
     * Recharge la simulation à l'instant t=0  avec la Stratégie Simple lors de l'appuie sur le boutton Début
     */
    @Override
    public void restart() {
        //this.data = createData(this.filename);
        gui.resizePanel(gui.getContentPane().getSize().width, gui.getContentPane().getSize().height - 70);
        dateSimulation = 0;
        this.data = LecteurDonnees.createData(this.filename);
        dateSimulation = 0;
        ChefPompier startegie = new StrategiePCC(this, data.listOfRobot(),data.listOfIcendie());
        startegie.execute();
        drawMap();
    }

    /**
     * Non utilisé
     * @param event the event to be processed
     */
    @Override
    public void componentMoved(ComponentEvent event){ /* pas utilise */ }

    /**
     * Non utilisé
     * @param event the event to be processed
     */
    @Override
    public void componentShown(ComponentEvent event){ /* pas utilise */ }

    /**
     * Non utilisé
     * @param event the event to be processed
     */
    @Override
    public void componentHidden(ComponentEvent event){ /* pas utilise */ }

    /**
     * Redimmensionne la taille d'affichage lors d'un redimensionnment de fenetre
     * @param event the event to be processed
     */
    @Override
    public void componentResized(ComponentEvent event){
        gui.reset();
        gui.resizePanel(gui.getContentPane().getSize().width, gui.getContentPane().getSize().height - 70);
        drawMap();
    }

}
