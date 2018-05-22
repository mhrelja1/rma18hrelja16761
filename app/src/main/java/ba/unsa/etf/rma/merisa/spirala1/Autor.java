package ba.unsa.etf.rma.merisa.spirala1;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Autor implements Parcelable{

    private String imeiPrezime;
    private ArrayList<Knjiga> knjigeImena=new ArrayList<Knjiga>();
    private ArrayList<String> knjige= new ArrayList<String> ();

    //public Autor (String ime, ArrayList<Knjiga> k) { this.ime=ime; knjige=k;}
    public Autor (String ime, String id) { imeiPrezime=ime; knjige.add(id);}
    public Autor () {}
    public Autor (String ime, ArrayList<Knjiga> k) { imeiPrezime=ime; knjigeImena=k; }

   /* protected Autor (Parcel in)
    {
        ime = in.readString();
        in.readTypedList(knjige, Knjiga.CREATOR);
    }*/

    protected Autor (Parcel in)
    {
        imeiPrezime = in.readString();
        knjige = in.readArrayList(ArrayList.class.getClassLoader());
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

    public String getImeAutora () { return imeiPrezime; }
    public ArrayList<String> getKnjigeAutora () { return knjige; }
    public void dodajKnjigu (Knjiga k) { knjigeImena.add(k); }
    public int getBrojKnjiga() { return knjigeImena.size(); }
    public void setImeiPrezime (String ime) { imeiPrezime= ime; }
    public void setKnjige (ArrayList<String> knjige) { this.knjige= knjige; }
    public void postavi (ArrayList<Knjiga> knjige1) {knjigeImena=knjige1;}

    public ArrayList<Knjiga> getKnjige () { return knjigeImena;}
    void dodajKnjigu (String id) { knjige.add(id); }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(imeiPrezime);
        dest.writeValue(knjige);

    }

}
