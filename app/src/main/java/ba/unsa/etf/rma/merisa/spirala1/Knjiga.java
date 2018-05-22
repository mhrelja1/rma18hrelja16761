package ba.unsa.etf.rma.merisa.spirala1;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class Knjiga implements Parcelable {

    private String id= "";
    private String naziv= "";
    private String kategorija= "";
    private String datumObjavljivanja= "";
    private String opis= "";
    private URL slika;
    private ArrayList<Autor> autori;
    private Autor autor;
    private int brojStranica;
    private boolean selected;
    private View view;
    boolean jedan=false;
    String uri="";


    public Knjiga () {}
    public Knjiga ( String id, String naziv, ArrayList<Autor> autori, String opis, String datumObjavljivanja , URL slika, int brojStranica  )
    {
        this.id= id;
        this.naziv= naziv;
        this.autori= autori;
        this.opis= opis;
        this.datumObjavljivanja= datumObjavljivanja;
        this.slika= slika;
        this.brojStranica= brojStranica;
    }

    public Knjiga (String uri, String naziv, String kategorija)
    {
        this.uri=uri;
        this.naziv=naziv;
        this.kategorija=kategorija;
        jedan=true;
    }


    protected Knjiga (Parcel in)
    {
        id= in.readString();
        naziv = in.readString();
        in.readTypedList(autori, Autor.CREATOR);
        opis = in.readString();
        datumObjavljivanja= in.readString();
        slika= (URL)in.readValue(URL.class.getClassLoader());
        brojStranica= in.readInt();

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


    public URL getSlika() { return  slika; }
    public String getSlikaa() { return uri; }
    public void setSlika (URL slika) { this.slika= slika; }

    public void setNaziv (String n) { naziv=n; }
    public void setId (String i) { id=i; }
    public void setAutori (ArrayList<Autor> a) { autori=a; }
    public void setOpis (String o) { opis=o; }
    public void  setDatumObjavljivanja (String datum) { datumObjavljivanja= datum; }
    public void setBrojStranica (int br) { brojStranica=br; }
    public void setKategorija (String k) { kategorija=k; }
    public void setAutor(Autor au) {autor=au;}

    public Autor getAutor () {return autor; }
    public String getId () { return id;}
    public String getNaziv () { return  naziv; }
    public ArrayList<Autor> getAutori () { return autori; }
    public String  dajAutore() {
        if (jedan == false) {
            if (autori.size()==0) return "nije uneseno";
            else {
                String vrati="";
                for (int i = 0; i < autori.size(); i++) {
                    if (i==0) vrati=autori.get(i).getImeAutora();
                    else vrati=vrati+", "+ autori.get(i).getImeAutora();
                }
                return vrati;
            }
        }
        else
        { return autor.getImeAutora();}

    }
    public String getOpis () { return opis; }
    public String getDatumObjavljivanja () {return datumObjavljivanja;}
    public int getBrojStranica () { return brojStranica; }
    public String getKategorija() { return kategorija; }
    public boolean getJedan () {return jedan;}


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(naziv);
        dest.writeTypedList(autori);
        dest.writeString(opis);
        dest.writeString(datumObjavljivanja);
        dest.writeValue(slika);
        dest.writeInt(brojStranica);

    }

}
