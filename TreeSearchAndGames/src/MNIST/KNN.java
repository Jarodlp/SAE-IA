package MNIST;

public class KNN extends AlgoClassification {

    private Donnees donneesEntrainement;

    private int k;

    public KNN(Donnees donneesEntrainement, int k) {
        this.donneesEntrainement = donneesEntrainement;
        this.k = k;
    }
    @Override
    public int predireEtiquette(Imagette imagette) {

            Imagette[] etiquettes = donneesEntrainement.getImagettes();

            int[] index = new int[k];
            double[] distance = new double[k];

            for (int i = 0; i < k; i++) {
                distance[i] = Double.MAX_VALUE;
            }

            for (int i = 0; i < donneesEntrainement.getImagettes().length; i++) {
                double dist = imagette.distance(donneesEntrainement.getImagette(i));

                for (int j = 0; j < k; j++) {
                    if (dist < distance[j]) {
                        distance[j] = dist;
                        index[j] = i;
                        break;
                    }
                }
            }

            int[] chiffres = new int[10];

            for (int i = 0; i < k; i++) {
                chiffres[etiquettes[index[i]].getEtiquette().getChiffre()]++;
            }

            // On veut compter quel chiffre est le plus présent parmi les k plus proches voisins

            int max = 0;
            int res = 0;

            for (int i = 0; i < 10; i++) {
                if (chiffres[i] > max) {
                    max = chiffres[i];
                    res = i;
                }
            }

            return res;
    }
}
