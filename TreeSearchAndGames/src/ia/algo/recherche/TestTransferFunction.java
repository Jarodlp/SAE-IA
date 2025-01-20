package ia.algo.recherche;

public class TestTransferFunction {

    public static void main(String[] args) {

        TransferFunction sigmoid = new Sigmoide();
        TransferFunction tanh = new Hyperbolique();

        double input = 1.0; // Exemple d'entrée

        // Test de la sigmoïde
        double sigmoidValue = sigmoid.evaluate(input);
        double sigmoidDerValue = sigmoid.evaluateDer(sigmoidValue);

        System.out.println("Sigmoïde:");
        System.out.println("σ(1.0) = " + sigmoidValue);         // ≈ 0.7311
        System.out.println("σ'(1.0) = " + sigmoidDerValue);     // ≈ 0.1966

        // Test de la tangente hyperbolique
        double tanhValue = tanh.evaluate(input);
        double tanhDerValue = tanh.evaluateDer(tanhValue);

        System.out.println("\nTangente Hyperbolique:");
        System.out.println("tanh(1.0) = " + tanhValue);         // ≈ 0.7616
        System.out.println("tanh'(1.0) = " + tanhDerValue);     // ≈ 0.4199
    }
}

