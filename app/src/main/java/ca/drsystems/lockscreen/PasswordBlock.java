package ca.drsystems.lockscreen;


public class PasswordBlock {
    public int numeric, shape, colour;

    public PasswordBlock(int n,int s,int c){
        this.numeric = n;
        this.shape = s;
        this.colour = c;
    }
    public int getNumeric() {
        return this.numeric;
    }
    public int getShape(){
        return this.shape;
    }
    public int getColour(){
        return this.colour;
    }
}
