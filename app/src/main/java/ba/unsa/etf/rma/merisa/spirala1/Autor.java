package ba.unsa.etf.rma.merisa.spirala1;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Autor implements Parcelable{

    private String ime;
    private ArrayList<Knjiga> knjige=new ArrayList<Knjiga>();

    public Autor (String ime, ArrayList<Knjiga> k) { this.ime=ime; knjige=k;}


    protected Autor (Parcel in)
    {
        ime = in.readString();
        in.readTypedList(knjige, Knjiga.CREATOR);
    }

    public static final Creator<Autor> CREATOR = new Creator<Autor>() {
        @Override
        public Autor createFromParcel(Parcel parcel) {
            return new  Autor(parcel);
        }

        @Override
        public Autor[] newArray(int i) {
            return new Autor[i];
        }
    };

    @Override
    public  int describeContents ()
    { return 0; }

    public String getImeAutora () { return ime; }
    public ArrayList<Knjiga> getKnjigeAutora () { return knjige; }
    public void dodajKnjigu (Knjiga knjiga) { knjige.add(knjiga); }
    public int getBrojKnjiga() { return knjige.size(); }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(ime);
        dest.writeTypedList(knjige);

    }

}
