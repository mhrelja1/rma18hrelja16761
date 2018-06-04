package ba.unsa.etf.rma.merisa.spirala1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BazaOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mojaBaza.db" ;
    public static final String DATABASE_TABLE_KATE = "Kategorija" ;
    public static final int DATABASE_VERSION = 1 ;
    public static final String KATEGORIJA_ID = "_id" ;
    public static final String KATEGORIJA_NAZIV = "naziv";
    public static final String DATABASE_TABLE_KNJIGE = "Knjiga" ;
    public static final String KNJIGA_ID = "_id" ;
    public static final String KNJIGA_NAZIV = "naziv" ;
    public static final String KNJIGA_OPIS = "opis";
    public static final String KNJIGA_DATUM_OB = "datumObjavljivanja";
    public static final String KNJIGA_BROJ_STRANICA = "brojStranica";
    public static final String KNJIGA_ID_WEBSERVISA = "idWebServisa" ;
    public static final String KNJIGA_ID_KATEGORIJE = "idkategorije" ;
    public static final String KNJIGA_SLIKA = "slika" ;

    public static final String DATABASE_TABLE_AUTOR = "Autor" ;
    public static final String AUTOR_ID = "_id" ;
    public static final String AUTOR_IME = "ime" ;
    public static final String DATABASE_TABLE_AUTORSTVO = "Autorstvo" ;
    public static final String AUTORSTVO_ID = "_id" ;
    public static final String AUTORSTVO_ID_AUTORA = "idautora" ;
    public static final String AUTORSTVO_ID_KNJIGE = "idknjige" ;

    private static final String DATABASE_CREATE_KATE = "create table " +
            DATABASE_TABLE_KATE + " (" + KATEGORIJA_ID +
            " integer primary key autoincrement, " +
            KATEGORIJA_NAZIV + " text not null) ;" ;

    private static final String DATABASE_CREATE_KNJIGE = "create table " +
            DATABASE_TABLE_KNJIGE + " (" + KNJIGA_ID +
            " integer primary key autoincrement, " +
            KNJIGA_NAZIV + " text not null, " + KNJIGA_OPIS + " text, " +
            KNJIGA_DATUM_OB + " text, " + KNJIGA_BROJ_STRANICA + " integer, " +
            KNJIGA_ID_WEBSERVISA + " text not null," + KNJIGA_ID_KATEGORIJE + " integer, " +
            KNJIGA_SLIKA+" text);";

    private static final String DATABASE_CREATE_AUTOR = "create table " +
            DATABASE_TABLE_AUTOR + " (" + AUTOR_ID +
            " integer primary key autoincrement, " +
            AUTOR_IME + " text not null); ";

    private static final String DATABASE_CREATE_AUTORSTVO = "create table " +
            DATABASE_TABLE_AUTORSTVO + " (" + AUTORSTVO_ID +
            " integer primary key autoincrement, " +
            AUTORSTVO_ID_AUTORA + " integer not null, " +
            AUTORSTVO_ID_KNJIGE + " integer not null); ";


    public BazaOpenHelper (Context context ) {
        super ( context , DATABASE_NAME , null , DATABASE_VERSION );
    }

    @Override
    public void onCreate (SQLiteDatabase db)
    {
        db.execSQL (DATABASE_CREATE_KATE);
        db.execSQL (DATABASE_CREATE_KNJIGE);
        db.execSQL (DATABASE_CREATE_AUTOR);
        db.execSQL (DATABASE_CREATE_AUTORSTVO);
    }

    @Override
    public void onUpgrade ( SQLiteDatabase db , int oldVersion , int newVersion ) {
        db.execSQL ( " DROP TABLE IF EXISTS " + DATABASE_TABLE_KATE );
        db.execSQL ( " DROP TABLE IF EXISTS " + DATABASE_TABLE_KNJIGE );
        db.execSQL ( " DROP TABLE IF EXISTS " + DATABASE_TABLE_AUTOR );
        db.execSQL ( " DROP TABLE IF EXISTS " + DATABASE_TABLE_AUTORSTVO );

        onCreate (db);
    }

    public long dodajKategoriju (String naziv)
    {
        try {
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor= db.query(DATABASE_TABLE_KATE, new String[] {KATEGORIJA_ID}, KATEGORIJA_NAZIV + "=?", new String[] {naziv}, null, null, null);
            if (cursor.getCount()>0) return -1;
            else {
                ContentValues novaK = new ContentValues();
                novaK.put(KATEGORIJA_NAZIV, naziv);
                long idKategorije= db.insert(DATABASE_TABLE_KATE, null, novaK);

                return idKategorije;
            }
        }
        catch (Exception e)
        {
            return -1;
        }

    }

    public long dodajKnjigu (Knjiga knjiga)
    {
        try {
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor= db.query(DATABASE_TABLE_KNJIGE, new String[] {KNJIGA_ID}, KNJIGA_NAZIV + "=?", new String[] {knjiga.getNaziv()}, null, null, null);

            if (cursor.getCount()>0) return -1;
            else {

                ContentValues novaKnjiga = new ContentValues();

                novaKnjiga.put(KNJIGA_NAZIV, knjiga.getNaziv());
                novaKnjiga.put(KNJIGA_OPIS, knjiga.getOpis());
                novaKnjiga.put(KNJIGA_DATUM_OB, knjiga.getDatumObjavljivanja());
                novaKnjiga.put(KNJIGA_BROJ_STRANICA, knjiga.getBrojStranica());
                novaKnjiga.put(KNJIGA_ID_WEBSERVISA, knjiga.getId());
                if (knjiga.getJedan()) {
                    novaKnjiga.put(KNJIGA_SLIKA, knjiga.getSlikaa());
                } else {
                    novaKnjiga.put(KNJIGA_SLIKA, knjiga.getSlika().toString());
                }

                Cursor cK = db.query(DATABASE_TABLE_KATE, new String[]{KATEGORIJA_ID}, KATEGORIJA_NAZIV + "=?", new String[]{knjiga.getKategorija()}, null, null, null);
                if (cK.moveToFirst())
                {

                int INDEX_KOLONE_TRAZENE = cK.getColumnIndexOrThrow(KATEGORIJA_ID);
                    novaKnjiga.put(KNJIGA_ID_KATEGORIJE, cK.getInt(INDEX_KOLONE_TRAZENE)); }

                    cK.close();

                    long idKnjige = db.insert(DATABASE_TABLE_KNJIGE, null, novaKnjiga);


                    if (knjiga.getJedan())
                    {
                        Cursor autor = db.query(DATABASE_TABLE_AUTOR, new String[]{AUTOR_ID}, AUTOR_IME + "=?", new String[]{knjiga.getAutor().getImeAutora()}, null, null, null);
                        long idAutora = 0;
                        if (autor.getCount() > 0) {
                            int INDEX_AUTORA = autor.getColumnIndexOrThrow(AUTOR_ID);
                            autor.moveToFirst();
                            idAutora = autor.getInt(INDEX_AUTORA);
                        } else {
                            ContentValues noviAutor = new ContentValues();
                            noviAutor.put(AUTOR_IME, knjiga.getAutor().getImeAutora());

                            idAutora = db.insert(DATABASE_TABLE_AUTOR, null, noviAutor);
                        }


                        ContentValues novoAutorstvo = new ContentValues();
                        novoAutorstvo.put(AUTORSTVO_ID_KNJIGE, idKnjige);
                        novoAutorstvo.put(AUTORSTVO_ID_AUTORA, idAutora);
                        db.insert(DATABASE_TABLE_AUTORSTVO, null, novoAutorstvo);
                        autor.close();

                    }
                    else
                    {
                    for (int i = 0; i < knjiga.getAutori().size(); i++) {
                        Cursor autor = db.query(DATABASE_TABLE_AUTOR, new String[]{AUTOR_ID}, AUTOR_IME + "=?", new String[]{knjiga.getAutori().get(i).getImeAutora()}, null, null, null);
                        long idAutora = 0;
                        if (autor.getCount() > 0) {
                            int INDEX_AUTORA = autor.getColumnIndexOrThrow(AUTOR_ID);
                            autor.moveToFirst();
                            idAutora = autor.getInt(INDEX_AUTORA);
                        } else {
                            ContentValues noviAutor = new ContentValues();
                            noviAutor.put(AUTOR_IME, knjiga.getAutori().get(i).getImeAutora());

                            idAutora = db.insert(DATABASE_TABLE_AUTOR, null, noviAutor);
                        }


                        ContentValues novoAutorstvo = new ContentValues();
                        novoAutorstvo.put(AUTORSTVO_ID_KNJIGE, idKnjige);
                        novoAutorstvo.put(AUTORSTVO_ID_AUTORA, idAutora);
                        db.insert(DATABASE_TABLE_AUTORSTVO, null, novoAutorstvo);
                        autor.close();
                    }
                    }


                return idKnjige;
            }

        }
        catch (Exception e)
        {
            return -100;
        }
    }

    ArrayList<Knjiga> knjigeKategorije (long idKategorije)
    {
        ArrayList<Knjiga> rez=new ArrayList<Knjiga>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor= db.query(DATABASE_TABLE_KNJIGE, new String[] {KNJIGA_ID, KNJIGA_NAZIV, KNJIGA_OPIS, KNJIGA_DATUM_OB, KNJIGA_BROJ_STRANICA, KNJIGA_ID_WEBSERVISA, KNJIGA_SLIKA }, KNJIGA_ID_KATEGORIJE + "=?" , new String [] {String.valueOf(idKategorije)}, null, null, null);
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {   ArrayList<Autor> autori= new ArrayList<Autor>();
                int ID_KNJIGA = cursor.getColumnIndexOrThrow(KNJIGA_ID);
                int idKnjige = cursor.getInt(ID_KNJIGA);
                int ID_WEBS = cursor.getColumnIndexOrThrow(KNJIGA_ID_WEBSERVISA);
                String id_webServis = cursor.getString(ID_WEBS);

                int NAZIV_INDEX = cursor.getColumnIndexOrThrow(KNJIGA_NAZIV);
                String naziv_knjige = cursor.getString(NAZIV_INDEX);

                int OPIS_INDEX = cursor.getColumnIndexOrThrow(KNJIGA_OPIS);
                String opis_knjige = cursor.getString(OPIS_INDEX);

                int DATUM_INDEX = cursor.getColumnIndexOrThrow(KNJIGA_DATUM_OB);
                String datum_objave = cursor.getString(DATUM_INDEX);

                int STRANICE_INDEX = cursor.getColumnIndexOrThrow(KNJIGA_BROJ_STRANICA);
                int broj_stranica = cursor.getInt(STRANICE_INDEX);

                int SLIKA_INDEX = cursor.getColumnIndexOrThrow(KNJIGA_SLIKA);
                String slika = cursor.getString(SLIKA_INDEX);

                try {
                    URL urlS= new URL(slika);


                Cursor cAutorstvo= db.query(DATABASE_TABLE_AUTORSTVO, new String[] {AUTORSTVO_ID_AUTORA}, AUTORSTVO_ID_KNJIGE + "=?",  new String[] {String.valueOf(idKnjige)}, null, null, null);

                    if (cAutorstvo.getCount()>0)
                {

                    while (cAutorstvo.moveToNext()) {

                        int ID_AUTOR = cAutorstvo.getColumnIndexOrThrow(AUTORSTVO_ID_AUTORA);
                        int idAutora = cAutorstvo.getInt(ID_AUTOR);
                        Cursor cAutor = db.query(DATABASE_TABLE_AUTOR, new String[]{AUTOR_IME}, AUTOR_ID + "=?", new String[]{String.valueOf(idAutora)}, null, null, null);
                        if (cAutor.getCount() > 0)
                        {
                           while (cAutor.moveToNext())
                           {
                                int IME_AUTORA = cAutor.getColumnIndexOrThrow(AUTOR_IME);
                               String ime_autora = cAutor.getString(IME_AUTORA);
                               autori.add(new Autor(ime_autora, id_webServis));

                           }
                        }

                    }
                }

                cAutorstvo.close();
                rez.add(new Knjiga(id_webServis, naziv_knjige, autori, opis_knjige, datum_objave, urlS, broj_stranica ));
                }
                catch (MalformedURLException e)
                { }
            }
        }
        return rez;
    }

    ArrayList<Knjiga> knjigeAutora (long idAutora) {
        ArrayList<Knjiga> rez = new ArrayList<Knjiga>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE_AUTORSTVO, new String[]{AUTORSTVO_ID_KNJIGE}, AUTORSTVO_ID_AUTORA + "=" + idAutora, null, null, null, null);
        if (cursor.getCount() > 0)
        {
            //cursor.moveToFirst();
            while (cursor.moveToNext())
            {
                int ID_KNJIGA = cursor.getColumnIndexOrThrow(AUTORSTVO_ID_KNJIGE);
                int idKnjige = cursor.getInt(ID_KNJIGA);
                Cursor cKnjiga = db.query(DATABASE_TABLE_KNJIGE, new String[]{KNJIGA_ID, KNJIGA_NAZIV, KNJIGA_OPIS, KNJIGA_DATUM_OB, KNJIGA_BROJ_STRANICA, KNJIGA_ID_WEBSERVISA, KNJIGA_ID_KATEGORIJE, KNJIGA_SLIKA}, KNJIGA_ID + "=?", new String[]{String.valueOf(idKnjige)}, null, null, null);
                if (cKnjiga.getCount() > 0) {
                    //cKnjiga.moveToFirst();

                    while (cKnjiga.moveToNext()) {
                        ArrayList<Autor> autori = new ArrayList<Autor>();

                        int ID_WEBS = cKnjiga.getColumnIndexOrThrow(KNJIGA_ID_WEBSERVISA);
                        String id_webServis = cKnjiga.getString(ID_WEBS);

                        int NAZIV_INDEX = cKnjiga.getColumnIndexOrThrow(KNJIGA_NAZIV);
                        String naziv_knjige = cKnjiga.getString(NAZIV_INDEX);

                        int OPIS_INDEX = cKnjiga.getColumnIndexOrThrow(KNJIGA_OPIS);
                        String opis_knjige = cKnjiga.getString(OPIS_INDEX);

                        int DATUM_INDEX = cKnjiga.getColumnIndexOrThrow(KNJIGA_DATUM_OB);
                        String datum_objave = cKnjiga.getString(DATUM_INDEX);

                        int STRANICE_INDEX = cKnjiga.getColumnIndexOrThrow(KNJIGA_BROJ_STRANICA);
                        int broj_stranica = cKnjiga.getInt(STRANICE_INDEX);

                        int SLIKA_INDEX = cKnjiga.getColumnIndexOrThrow(KNJIGA_SLIKA);
                        String slika = cKnjiga.getString(SLIKA_INDEX);

                        try {
                            URL urlS = new URL(slika);


                            Cursor cAutorstvo = db.query(DATABASE_TABLE_AUTORSTVO, new String[]{AUTORSTVO_ID_AUTORA}, AUTORSTVO_ID_KNJIGE + "=?", new String[]{String.valueOf(idKnjige)}, null, null, null);
                            if (cAutorstvo.getCount() > 0) {
                                while (cAutorstvo.moveToNext()) {
                                    int ID_AUTOR = cAutorstvo.getColumnIndexOrThrow(AUTORSTVO_ID_AUTORA);
                                    int idAutoraa = cAutorstvo.getInt(ID_AUTOR);
                                    Cursor cAutor = db.query(DATABASE_TABLE_AUTOR, new String[]{AUTOR_IME}, AUTOR_ID + "=?", new String[]{String.valueOf(idAutoraa)}, null, null, null);
                                    cAutor.moveToFirst();
                                    int IME_AUTORA = cAutor.getColumnIndexOrThrow(AUTOR_IME);
                                    String ime_autora = cAutor.getString(IME_AUTORA);
                                    autori.add(new Autor(ime_autora, id_webServis));


                                }
                            }
                            cAutorstvo.close();
                            rez.add(new Knjiga(id_webServis, naziv_knjige, autori, opis_knjige, datum_objave, urlS, broj_stranica));
                        } catch (MalformedURLException e) {
                        }
                    }
                }
            }
        }

        return rez;
    }




}
