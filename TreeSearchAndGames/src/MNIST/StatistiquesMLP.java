package MNIST;

import java.io.IOException;

import static MNIST.Imagette.convertirImagetteEnInput;

public class StatistiquesMLP {
    public static void main(String[] args) throws IOException, InterruptedException {

        int[] nbImagesATester = {10, 500, 1000, 5000, 10000};

        // Configuration du MLP
        int[] structure = {28 * 28, 128, 10}; // 28x28 pixels, nb neurones, 10 sorties
        double learningRate = 0.1;
        TransferFunction fonctionTransfert = new SigmoidFunction();
        MLP mlp = new MLP(structure, learningRate, fonctionTransfert);

        for (int i = 0; i < nbImagesATester.length; i++) {

            double time = System.currentTimeMillis();

            LireImage lireImageTrain = new LireImage("img/train-images.idx3-ubyte", nbImagesATester[i]);
            Donnees donneesTrain = lireImageTrain.lireChemin();

            // Entraînement du MLP
            System.out.println("Entraînement du MLP...");
            for (int epoch = 0; epoch < 10; epoch++) { // Nombre d'itérations d'entraînement
                double currentLearningRate = learningRate * (1 + epoch * 0.1);
                mlp.setLearningRate(currentLearningRate);


                for (Imagette imagette : donneesTrain.getImagettes()) {
                    double[] input = convertirImagetteEnInput(imagette);
                    double[] expectedOutput = new double[10];
                    expectedOutput[imagette.getEtiquette().getChiffre()] = 1.0;

                    mlp.backPropagate(input, expectedOutput);
                }
                System.out.println("Epoch " + (epoch + 1) + " terminée.");
            }

            // Test du MLP
            int etiquettesCorrectes = 0;
            LireImage lireImageTest = new LireImage("img/t10k-images.idx3-ubyte", 100);
            Donnees donneesTest = lireImageTest.lireChemin();
            MLPClassification algo = new MLPClassification(mlp);

            for (Imagette imagette : donneesTest.getImagettes()) {
                int prediction = algo.predireEtiquette(imagette);
                int attendu = imagette.getEtiquette().getChiffre();

                if (prediction == attendu) {
                    etiquettesCorrectes++;
                }
            }

            int totalImagesTeste = donneesTest.getImagettes().length;
            double pourcentageReussite = ((double) etiquettesCorrectes / totalImagesTeste) * 100;
            System.out.println("Pourcentage de réussite pour " + nbImagesATester[i] + " images de test: " + pourcentageReussite + "%\n");

            time = System.currentTimeMillis() - time;
            time /= 1000;
            System.out.println("Temps d'exécution: " + time + " secondes\n");

            double learningRateFinal = mlp.getLearningRate();
            System.out.println("Taux d'apprentissage final: " + learningRateFinal + "\n");
        }
    }
}