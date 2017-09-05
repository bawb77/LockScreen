package ca.drsystems.lockscreen;

//Created by Ben Baxter

class PasswordBlock {
    private int numeric, shape, colour;

    PasswordBlock(int n, int s, int c){
        this.numeric = n;
        this.shape = s;
        this.colour = c;
    }
    int getNumeric() {
        return this.numeric;
    }
    int getShape(){ return this.shape; }
    int getColour(){
        return this.colour;
    }
}
