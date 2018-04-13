package ba.unsa.etf.rma.merisa.spirala1;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class Knjiga implements Parcelable {

    private String naslovnaStr;
    private String imeAutora = "";
    private String nazivKnjige = "";
    private String kategorija= " ";
    private boolean selected;
    private View view;

    public Knjiga ( String slika, String ime, String naziv, String kate, boolean selected)
    {
        naslovnaStr = slika;
        imeAutora = ime;
        nazivKnjige = naziv;
        kategorija = kate;
        this.selected= selected;
    }

    protected Knjiga (Parcel in)
    {
        naslovnaStr= in.readString();
        imeAutora = in.readString();
        nazivKnjige = in.readString();
        kategorija = in.readString();
        selected= in.readByte()!=0;

    }


    public static final Creator<Knjiga> CREATOR = new Creator<Knjiga>() {
        @Override
        public Knjiga createFromParcel(Parcel parcel) {
            return new  Knjiga(parcel);
        }

        @Override
        public Knjiga[] newArray(int i) {
            return new Knjiga[i];
        }
    };

    @Override
    public  int describeContents ()
    { return 0; }

    public String getImeAutora () { return imeAutora; }
    public String getNazivKnjige () { return nazivKnjige; }
    public String getKategorija() { return kategorija; }
    public String getNaslovnaStr () { return naslovnaStr; }
    public View getView () {return view; }

    public void  setKategorija (String kate) { kategorija = kate; }
    public void  setImeAutora (String ime) { imeAutora = ime; }
    public void  setNazivKnjige (String naziv) { nazivKnjige = naziv; }
    public void  setnaslovnaStr (String  slika) { naslovnaStr = slika; }
    public void setSelected (boolean a) { selected = a; }
    public boolean isSelected () { return selected; }
    public void setView(View view) {this.view = view; }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(naslovnaStr);
        dest.writeString(imeAutora);
        dest.writeString(nazivKnjige);
        dest.writeString(kategorija);
        dest.writeByte((byte) (selected ? 1:0));

    }

}
