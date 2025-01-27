package MNIST;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class LireImage {
    private String cheminImage;

    private int nbImages;

    public LireImage(String cheminImage, int nbImages) {
        this.cheminImage = cheminImage;
        this.nbImages = nbImages;
    }

    public Donnees lireChemin() throws IOException, InterruptedException {
        DataInputStream dis = new DataInputStream(new FileInputStream(cheminImage));

        // entier identifiant le type du fichier
        int typeFichier = dis.readInt();

        if (typeFichier == 2051) {

            return lireEtiquette(dis, true);

        } else if (typeFichier == 2049) {

            lireEtiquette(dis, false);

            return null;

        } else {
            System.out.println("Type de fichier inconnu");
            return null;
        }
    }

    public Donnees lireImagette(DataInputStream dis, Etiquette[] etiquettes) throws IOException {

        int nbImages = dis.readInt();

        if (nbImages > this.nbImages) {
            nbImages = this.nbImages;
        }

        int nbLignes = dis.readInt();

        int nbColonnes = dis.readInt();

        Donnees imagettes = new Donnees(nbImages);

        for (int i = 0; i < nbImages; i++) {
            Imagette imagette = new Imagette(nbLignes, nbColonnes, etiquettes[i]);

            for (int y = 0; y < nbLignes; y++) {
                for (int x = 0; x < nbColonnes; x++) {
                    imagette.setPixel(x, y, dis.readUnsignedByte());
                }
            }

            imagettes.setImagette(i, imagette);
        }

        dis.close();

        return imagettes;

        //imagettes.getImagette(0).tableauToImage("image" + 1);
        //imagettes.getImagette(nbImages - 1).tableauToImage("image" + 2);

    }

    public Donnees lireEtiquette(DataInputStream dis, boolean fichierImage) throws IOException {

        String[] cheminSplit = cheminImage.split("-");
        String cheminEtiquette = cheminSplit[0] + "-" + "labels.idx1-" + cheminSplit[2];

        DataInputStream disEtiquette = new DataInputStream(new FileInputStream(cheminEtiquette));
        int typeFichier = disEtiquette.readInt();

        if (typeFichier != 2049) {
            throw new IOException("Type de fichier inconnu");
        }

        int nbEtiquettes = disEtiquette.readInt();

        if (nbEtiquettes > this.nbImages) {
            nbEtiquettes = this.nbImages;
        }

        Etiquette[] etiquettes = new Etiquette[nbEtiquettes];

        for (int i = 0; i < nbEtiquettes; i++) {
            etiquettes[i] = new Etiquette(disEtiquette.readUnsignedByte());
        }

        disEtiquette.close();

        if (fichierImage) {
            return lireImagette(dis, etiquettes);
        } else {
            return null;
        }
    }
}
