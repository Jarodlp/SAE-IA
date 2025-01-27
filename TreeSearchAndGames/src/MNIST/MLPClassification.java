package MNIST;

import static MNIST.Imagette.convertirImagetteEnInput;

public class MLPClassification extends AlgoClassification {

    private MLP mlp;

    public MLPClassification(MLP mlp) {
        this.mlp = mlp;
    }

    @Override
    public int predireEtiquette(Imagette imagette) {
        double[] input = convertirImagetteEnInput(imagette);
        double[] output = mlp.execute(input);

        // Trouver l'index du maximum dans la sortie du MLP
        int maxIndex = 0;
        for (int i = 1; i < output.length; i++) {
            if (output[i] > output[maxIndex]) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }

}
