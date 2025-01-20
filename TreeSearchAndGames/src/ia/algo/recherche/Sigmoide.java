package ia.algo.recherche;

public class Sigmoide implements TransferFunction{

    @Override
    public double evaluate(double value) {
        return 1 / (1 + Math.exp((-value)));
    }

    @Override
    public double evaluateDer(double value) {
        // σ'(x) = σ(x) * (1 - σ(x)) avec value = σ(x)
        return value * (1 - value);
    }
}
