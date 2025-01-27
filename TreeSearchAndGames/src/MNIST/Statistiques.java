package MNIST;

import java.io.IOException;

public class Statistiques {
    public static void main(String[] args) throws IOException, InterruptedException {

        int[] nbImagesATester = {10, 500, 1000, 5000, 10000};

        for (int i = 0; i< 5; i++) {

            int EtiquetteCorrecte = 0;

            for (int j = 0; j < 100; j++) {

                LireImage LireImageTest = new LireImage("img/t10k-images.idx3-ubyte", j + 11);
                Donnees dTest = LireImageTest.lireChemin();


                LireImage lireImage = new LireImage("img/train-images.idx3-ubyte", nbImagesATester[i]);
                Donnees d = lireImage.lireChemin();

                if (d != null) {
                    AlgoClassification algo = new KNN(d,10);
                    int res = algo.predireEtiquette(dTest.getImagette(j + 10));
                    int resAttendu = dTest.getImagette(j + 10).getEtiquette().getChiffre();
                    System.out.println("Résultat obtenue: " + res);
                    System.out.println("Résultat attendu: " + resAttendu + "\n");
                    if (res == resAttendu) {
                        EtiquetteCorrecte++;
                    }
                }
            }
            int totalImagesTeste = 100;
            double pourcentageReussite = ((double) EtiquetteCorrecte / totalImagesTeste) * 100;
            System.out.println("Pourcentage de réussite pour " + nbImagesATester[i] + " images de test: " + pourcentageReussite + "%\n");
        }
    }
}

// Stats PlusProche/KNN :
// Pourcentage de réussite pour 10 images de test: 60.0% / 10.0%
// Pourcentage de réussite pour 500 images de test: 70.0% / 70.0%
// Pourcentage de réussite pour 1000 images de test: 80.0% / 80.0%
// Pourcentage de réussite pour 5000 images de test: 80.0% / 90.0%
// Pourcentage de réussite pour 10000 images de test: 80.0% / 100.0%

// Stats PlusProche/KNN 10 pour les fichiers fashion :
// Pourcentage de réussite pour 10 images de test: 20.0% / 0.0%
// Pourcentage de réussite pour 500 images de test: 60.0% / 90.0%
// Pourcentage de réussite pour 1000 images de test: 50.0% / 60.0%
// Pourcentage de réussite pour 5000 images de test: 70.0% / 70.0%
// Pourcentage de réussite pour 10000 images de test: 80.0% / 90.0%
