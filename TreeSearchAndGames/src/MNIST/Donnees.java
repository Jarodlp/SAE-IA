package MNIST;

public class Donnees {

    private Imagette[] imagettes;

    public Donnees(int nbImages) {
        this.imagettes = new Imagette[nbImages];
    }

    public Imagette[] getImagettes() {
        return imagettes;
    }

    public void setImagette(int index, Imagette imagette) {
        this.imagettes[index] = imagette;
    }

    public Imagette getImagette(int index) {
        return this.imagettes[index];
    }

}
