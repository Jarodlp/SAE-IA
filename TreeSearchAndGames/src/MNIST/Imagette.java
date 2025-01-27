package MNIST;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imagette {
    private int[][] niveauGris;

    private Etiquette etiquette;
    private int nbLignes;
    private int nbColonnes;

    public Imagette(int nbLignes, int nbColonnes, Etiquette etiquette) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.etiquette = etiquette;
        this.niveauGris = new int[nbLignes][nbColonnes];
    }

    public void setPixel(int x, int y, int valeur) {
        this.niveauGris[x][y] = valeur;
    }

    public void tableauToImage(String nomFichier) throws IOException {
        BufferedImage image = new BufferedImage(nbLignes, nbColonnes, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < nbLignes; y++) {
            for (int x = 0; x < nbColonnes; x++) {
                image.setRGB(x, y, (niveauGris[x][y] << 16) | (niveauGris[x][y] << 8) | niveauGris[x][y]);
            }
        }

        nomFichier = nomFichier + "_etiquette" + etiquette.getChiffre();

        // sauvegarde de l'image
        ImageIO.write(image, "png", new File("img/imgCree/" + nomFichier + ".png"));
    }

    public Etiquette getEtiquette() {
        return etiquette;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public int getPixel(int x, int y) {
        return niveauGris[x][y];
    }

    public double distance(Imagette imagetteDonnee){
        double distance = 0;
        for (int y = 0; y < nbLignes; y++) {
            for (int x = 0; x < nbColonnes; x++) {
                distance += Math.pow(niveauGris[x][y] - imagetteDonnee.getPixel(x, y), 2);
            }
        }
        return distance;
    }

    public static double[] convertirImagetteEnInput(Imagette imagette) {
        int lignes = imagette.getNbLignes();
        int colonnes = imagette.getNbColonnes();
        double[] input = new double[lignes * colonnes];

        for (int y = 0; y < lignes; y++) {
            for (int x = 0; x < colonnes; x++) {
                input[y * colonnes + x] = imagette.getPixel(x, y) / 255.0; // Normalisation
            }
        }

        return input;
    }
}
