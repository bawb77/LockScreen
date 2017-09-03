package ca.drsystems.lockscreen;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Heavy Arms on 9/1/2017.
 */

public class PasswordWhole implements Parcelable {
    public int[] numeric, shape, colour;
    public boolean numOn, shaOn, colOn;

    public PasswordWhole(int[] n, int[] s, int[] c, boolean nOn, boolean sOn, boolean cOn) {
        this.numeric = n;
        this.shape = s;
        this.colour = c;
        this.numOn = nOn;
        this.shaOn = sOn;
        this.colOn = cOn;
    }

    protected PasswordWhole(Parcel in) {
        numeric = in.createIntArray();
        shape = in.createIntArray();
        colour = in.createIntArray();
        numOn = in.readByte() != 0;
        shaOn = in.readByte() != 0;
        colOn = in.readByte() != 0;
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

    public boolean checkIt(PasswordBlock[] b) {
        boolean okay = true;
        for (int i = 0; i == 4; i++) {
            if (b[i].getNumeric() != numeric[i]) {
                if (numOn) {
                    okay = false;
                }
            }
            if (b[i].getShape() != shape[i]) {
                if (shaOn) {
                    okay = false;
                }
            }
            if (b[i].getColour() != colour[i]) {
                if (colOn) {
                    okay = false;
                }
            }
        }
        return okay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(numeric);
        parcel.writeIntArray(shape);
        parcel.writeIntArray(colour);
        parcel.writeByte((byte) (numOn ? 1 : 0));
        parcel.writeByte((byte) (shaOn ? 1 : 0));
        parcel.writeByte((byte) (colOn ? 1 : 0));
    }
}
