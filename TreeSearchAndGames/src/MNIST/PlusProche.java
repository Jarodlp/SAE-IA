package MNIST;

public class PlusProche extends AlgoClassification{

    private Donnees donneesEntrainement;

    public PlusProche(Donnees donneesEntrainement) {
        this.donneesEntrainement = donneesEntrainement;
    }

    @Override
    public int predireEtiquette(Imagette imagette) {

        Imagette[] etiquettes = donneesEntrainement.getImagettes();

        int index = 0;
        double distanceMin = Double.MAX_VALUE;

        for (int i = 0; i < donneesEntrainement.getImagettes().length; i++) {
            double distance = imagette.distance(donneesEntrainement.getImagette(i));

            if (distance < distanceMin) {
                distanceMin = distance;
                index = i;
            }
        }

        return etiquettes[index].getEtiquette().getChiffre();
    }
}
