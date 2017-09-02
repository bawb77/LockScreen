package ca.drsystems.lockscreen;

/**
 * Created by Heavy Arms on 9/1/2017.
 */

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
