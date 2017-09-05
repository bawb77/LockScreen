package ca.drsystems.lockscreen;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Created by Ben Baxter

class PasswordWhole implements Parcelable {
    private boolean numericPasswordEnabledFlag, shapePasswordEnabledFlag, colourPasswordEnabledFlag;
    private String[] hashedPasswords;
    private static final int arrayNumericPosition=0,arrayShapePosition=1,arrayColourPosition=2;

    PasswordWhole(PasswordBlock[] in, boolean nOn, boolean sOn, boolean cOn) {
        this.numericPasswordEnabledFlag = nOn;
        this.shapePasswordEnabledFlag = sOn;
        this.colourPasswordEnabledFlag = cOn;
        hashedPasswords = unPack(in);
    }

    private String[] unPack(PasswordBlock[] pBlock){
        String numericPassword = "", shapePassword="", colourPassword="";
        String[] passwordsArray;
        for (PasswordBlock aPBlock : pBlock) {
            numericPassword += (String.valueOf(aPBlock.getNumeric()));
            shapePassword += (String.valueOf(aPBlock.getShape()));
            colourPassword += (String.valueOf(aPBlock.getColour()));
        }
        passwordsArray = new String[]{numericPassword,shapePassword,colourPassword};
        for (int i = 0; i < passwordsArray.length; i++)
        {
            try{
                MessageDigest messageDigest = MessageDigest.getInstance("sha-512");
                messageDigest.update(passwordsArray[i].getBytes());
                byte[] mb = messageDigest.digest();
                String out = "";
                for (byte temp : mb) {
                    String s = Integer.toHexString(temp);
                    while (s.length() < 2) {
                        s = "0" + s;
                    }
                    s = s.substring(s.length() - 2);
                    out += s;

                }
                passwordsArray[i] = out;
            }
            catch(NoSuchAlgorithmException e){
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        return passwordsArray;
    }

    boolean checkIt(PasswordBlock[] b) {
        String[] passwordAttempt = unPack(b);
        boolean okay = false;
        if (passwordAttempt[arrayNumericPosition].contentEquals(hashedPasswords[arrayNumericPosition])&&numericPasswordEnabledFlag) {
            okay = true;
        }
        else if(passwordAttempt[arrayShapePosition].contentEquals(hashedPasswords[arrayShapePosition])&&shapePasswordEnabledFlag){
            okay = true;
        }
        else if(passwordAttempt[arrayColourPosition].contentEquals(hashedPasswords[arrayColourPosition])&&colourPasswordEnabledFlag){
            okay = true;
        }
        return okay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (numericPasswordEnabledFlag ? 1 : 0));
        parcel.writeByte((byte) (shapePasswordEnabledFlag ? 1 : 0));
        parcel.writeByte((byte) (colourPasswordEnabledFlag ? 1 : 0));
        parcel.writeStringArray(hashedPasswords);
    }
    private PasswordWhole(Parcel in) {
        numericPasswordEnabledFlag = in.readByte() != 0;
        shapePasswordEnabledFlag = in.readByte() != 0;
        colourPasswordEnabledFlag = in.readByte() != 0;
        hashedPasswords = in.createStringArray();
    }

    public static final Creator<PasswordWhole> CREATOR = new Creator<PasswordWhole>() {
        @Override
        public PasswordWhole createFromParcel(Parcel in) {
            return new PasswordWhole(in);
        }

        @Override
        public PasswordWhole[] newArray(int size) {
            return new PasswordWhole[size];
        }
    };
}
