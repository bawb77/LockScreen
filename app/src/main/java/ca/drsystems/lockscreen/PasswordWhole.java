package ca.drsystems.lockscreen;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Heavy Arms on 9/1/2017.
 */

class PasswordWhole implements Parcelable {
    private PasswordBlock[] input;
    private boolean numOn, shaOn, colOn;
    private String[] hashedPasswords;
    MessageDigest md;

    public PasswordWhole(PasswordBlock[] in, boolean nOn, boolean sOn, boolean cOn) {
        this.input = in;
        this.numOn = nOn;
        this.shaOn = sOn;
        this.colOn = cOn;
        hashedPasswords = unPack(input);
    }

    protected PasswordWhole(Parcel in) {
        numOn = in.readByte() != 0;
        shaOn = in.readByte() != 0;
        colOn = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (numOn ? 1 : 0));
        dest.writeByte((byte) (shaOn ? 1 : 0));
        dest.writeByte((byte) (colOn ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    private String[] unPack(PasswordBlock[] pBlock){
        String numericPassword = "", shapePassword="", colourPassword="";
        String[] passwordsArray;
        for(int i=0;i<4;i++){
            numericPassword.concat(String.valueOf(pBlock[i].getNumeric()));
            shapePassword.concat(String.valueOf(pBlock[i].getShape()));
            colourPassword.concat(String.valueOf(pBlock[i].getColour()));
        }
        passwordsArray = new String[]{numericPassword,shapePassword,colourPassword};
        for (String str : passwordsArray)
        {
            try{
                md = MessageDigest.getInstance("sha-512");
                md.update(str.getBytes());
                byte[] mb = md.digest();
                String out = "";
                for (int i = 0; i < mb.length; i++)
                {
                    byte temp = mb[i];
                    String s = Integer.toHexString(new Byte(temp));
                    while (s.length() < 2)
                    {
                        s = "0" + s;
                    }
                    s = s.substring(s.length() - 2);
                    out += s;
                }
                str = out;
            }
            catch(NoSuchAlgorithmException e){
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        return passwordsArray;
    }

    public boolean checkIt(PasswordBlock[] b) {
        String[] passwordAttempt = unPack(b);
        boolean okay = false;
        for (int i = 0; i < 4; i++) {
            if (passwordAttempt[i].contentEquals(hashedPasswords[i])) {
                okay = true;
            }
        }
        return okay;
    }

}
