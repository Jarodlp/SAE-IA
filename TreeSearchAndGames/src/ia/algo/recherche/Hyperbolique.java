package ia.algo.recherche;

public class Hyperbolique implements TransferFunction{

    @Override
    public double evaluate(double value) {
        return Math.tanh(value);
    }

    @Override
    public double evaluateDer(double value) {
        // σ'(x) = 1 - σ(x)^2 avec value = σ(x) = tanh(x)
        return 1 - (value * value);
    }
}
