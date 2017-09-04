package ca.drsystems.lockscreen;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class PasswordWhole implements Parcelable {
    private PasswordBlock[] input;
    private boolean numericPasswordEnabledFlag, shapePasswordEnabledFlag, colourPasswordEnabledFlag;
    private String[] hashedPasswords;
    MessageDigest messageDigest;

    public PasswordWhole(PasswordBlock[] in, boolean nOn, boolean sOn, boolean cOn) {
        this.input = in;
        this.numericPasswordEnabledFlag = nOn;
        this.shapePasswordEnabledFlag = sOn;
        this.colourPasswordEnabledFlag = cOn;
        hashedPasswords = unPack(input);
    }

    protected PasswordWhole(Parcel in) {
        numericPasswordEnabledFlag = in.readByte() != 0;
        shapePasswordEnabledFlag = in.readByte() != 0;
        colourPasswordEnabledFlag = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (numericPasswordEnabledFlag ? 1 : 0));
        dest.writeByte((byte) (shapePasswordEnabledFlag ? 1 : 0));
        dest.writeByte((byte) (colourPasswordEnabledFlag ? 1 : 0));
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
        for(int i=0;i<pBlock.length;i++){
            numericPassword.concat(String.valueOf(pBlock[i].getNumeric()));
            shapePassword.concat(String.valueOf(pBlock[i].getShape()));
            colourPassword.concat(String.valueOf(pBlock[i].getColour()));
        }
        passwordsArray = new String[]{numericPassword,shapePassword,colourPassword};
        for (int i = 0; i < passwordsArray.length; i++)
        {
            try{
                messageDigest = MessageDigest.getInstance("sha-512");
                messageDigest.update(passwordsArray[i].getBytes());
                byte[] mb = messageDigest.digest();
                String out = "";
                for (int j = 0; j < mb.length; j++)
                {
                    byte temp = mb[j];
                    String s = Integer.toHexString(new Byte(temp));
                    while (s.length() < 2)
                    {
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

    public boolean checkIt(PasswordBlock[] b) {
        String[] passwordAttempt = unPack(b);
        boolean okay = false;
        for (int i = 0; i < passwordAttempt.length; i++) {
            if (passwordAttempt[i].contentEquals(hashedPasswords[i])) {
                okay = true;
            }
        }
        return okay;
    }

}
