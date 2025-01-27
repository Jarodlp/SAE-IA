package MNIST;

public abstract class AlgoClassification {

    private Donnees donneesEntrainement;

    public abstract int predireEtiquette(Imagette imagette);
}
