package ba.unsa.etf.rma.merisa.spirala1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentOnline extends Fragment implements DohvatiKnjige.IDohvatiKnjigeDone, DohvatiNajnovije.IDohvatiNajnovijeDone, MojResultReceiver.Receiver {

    ArrayList<Knjiga> knjigeRezultat= new ArrayList<Knjiga>();
    ArrayList<String> naziviKnjiga= new ArrayList<String>();
    ArrayList <Knjiga> knjige= new ArrayList<Knjiga>();
    ArrayList <Autor> autori= new ArrayList<>();
    Spinner sRezultat;

    @Override
    public void onReceiveResult (int resultCode, Bundle resultData)
    {
        switch (resultCode) {
            case 0:
                break;
            case 1: {
                naziviKnjiga= new ArrayList<String>();
                knjigeRezultat = resultData.getParcelableArrayList("rezultat");
                for (int i=0; i<knjigeRezultat.size(); i++)
                {
                    naziviKnjiga.add(knjigeRezultat.get(i).getNaziv());
                }
                sRezultat = (Spinner) getView().findViewById(R.id.sRezultat);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, naziviKnjiga);
                sRezultat.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onDohvatiKnjigeDone (ArrayList<Knjiga> knjige)
    {
        knjigeRezultat=knjige;
        naziviKnjiga= new ArrayList<String>();
        for (int i=0; i<knjige.size(); i++)
        {
            naziviKnjiga.add(knjigeRezultat.get(i).getNaziv());
        }

        sRezultat= (Spinner) getView().findViewById(R.id.sRezultat);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, naziviKnjiga);
        sRezultat.setAdapter(adapter);

    }
    @Override
    public void onNajnovijeDone (ArrayList<Knjiga> knjige)
    {
        knjigeRezultat=knjige;
        naziviKnjiga= new ArrayList<String>();
        for (int i=0; i<knjige.size(); i++)
        {
            naziviKnjiga.add(knjigeRezultat.get(i).getNaziv());
        }

        sRezultat= (Spinner) getView().findViewById(R.id.sRezultat);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, naziviKnjiga);
        sRezultat.setAdapter(adapter);

    }

    public boolean jeLiA (String s)
    {
        if (s.length()>6 && s.substring(0,6).equals("autor:")) return true;
        else return false;
    }

    public boolean jeLiK (String s)
    {
        if (s.length()>9 && s.substring(0,9).equals("korisnik:")) return true;
        else return false;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> kategorije= new ArrayList<String> ();

       /* if (getArguments().containsKey("unosi"))
        {
            kategorije= getArguments().getStringArrayList("unosi");
        }
        if (getArguments().containsKey("knjige"))
        {
            knjige= getArguments().getParcelableArrayList("knjige");
        }
        if (getArguments().containsKey("autori"))
        {
            autori= getArguments().getParcelableArrayList("autori");
        }*/
        final BazaOpenHelper bazaOpenHelper= new BazaOpenHelper(getActivity());

        final SQLiteDatabase db= bazaOpenHelper.getReadableDatabase();

        //ArrayAdapter<String> aaa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kategorije );

        Cursor c = db.rawQuery("SELECT  * FROM "+BazaOpenHelper.DATABASE_TABLE_KATE, null);
        SimpleCursorAdapter simpleCursorAdapterK= new SimpleCursorAdapter( getActivity(), android.R.layout.simple_list_item_1 ,c, new String []{ BazaOpenHelper.KATEGORIJA_NAZIV }, new int []{ android.R.id.text1 }, 0 );

       final Spinner sKategorije= (Spinner)getView().findViewById(R.id.sKategorije);
       // sKategorije.setAdapter(aaa);
        sKategorije.setAdapter(simpleCursorAdapterK);


        Button dPovratak = (Button) getView().findViewById(R.id.dPovratak);
        dPovratak.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
/*
                Intent intent= new Intent();
                intent.putParcelableArrayListExtra("knjige", knjige);
                intent.putParcelableArrayListExtra("autori", autori);

                getTargetFragment().onActivityResult(
                        getTargetRequestCode(),
                        Activity.RESULT_OK,
                        intent
                );
*/

                getFragmentManager().popBackStack();
            }
        });

        final EditText tekstUpit= (EditText) getView().findViewById(R.id.tekstUpit);
        Button dRun = (Button) getView().findViewById(R.id.dRun);

        dRun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
                String[] rijeci = tekstUpit.getText().toString().split("\\W+");
                String ss= tekstUpit.getText().toString();
                //String s= tekstUpit.getText().toString();

                ArrayList<String> zaSlanje = new ArrayList<String>(Arrays.asList(rijeci));


                if (rijeci.length==1 && jeLiA(ss)==false && jeLiK(ss)==false)
                {
                    new DohvatiKnjige((DohvatiKnjige.IDohvatiKnjigeDone) FragmentOnline.this).execute(zaSlanje);

                }
                else if (rijeci.length>1 && jeLiA(ss)==false && jeLiK(ss)==false ) {
                    int brojac = 0;
                    for (int i = 0; i < ss.length(); i++) {
                        if (ss.charAt(i) == ';') brojac++;
                    }
                    if (brojac== rijeci.length-1) new DohvatiKnjige((DohvatiKnjige.IDohvatiKnjigeDone)FragmentOnline.this).execute(zaSlanje);
                }
                else if (jeLiA(ss))
                {
                    String imeAutora= ss.substring(6);
                    new DohvatiNajnovije((DohvatiNajnovije.IDohvatiNajnovijeDone) FragmentOnline.this).execute(imeAutora);

                }
                else if (jeLiK(ss))
                {

                    String korisnik= ss.substring(9);
                    Intent intent = new Intent ( Intent.ACTION_SYNC, null, getActivity(), KnjigePoznanika.class );
                    MojResultReceiver mReceiver = new MojResultReceiver (new Handler());
                    mReceiver.setReceiver (FragmentOnline.this);
                    intent.putExtra("receiver", mReceiver);
                    intent.putExtra("korisnik", korisnik);
                    getActivity().startService(intent);

                }

            }
        });


        Button dAdd= (Button) getView().findViewById(R.id.dAdd);
        dAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Knjiga a = knjigeRezultat.get(sRezultat.getSelectedItemPosition());
                Cursor c= (Cursor) sKategorije.getSelectedItem();
                int INDEX_KATE = c.getColumnIndexOrThrow(BazaOpenHelper.KATEGORIJA_NAZIV );
                String nazivK= c.getString(INDEX_KATE);
                a.setKategorija(nazivK);
                long vrati=bazaOpenHelper.dodajKnjigu(a);
               /*knjige.add(a);

                for (int i = 0; i < a.getAutori().size(); i++)
                {  boolean isti=false;
                    for (int j=0; j< autori.size(); j++) {
                        if (a.getAutori().get(i).equals(autori.get(j).getImeAutora()))
                        {
                            autori.get(j).dodajKnjigu(a); autori.get(j).dodajKnjigu(a.getId());
                            isti=true;
                        }
                    }
                    if (isti==false)
                    {
                        Autor au=new Autor(a.getAutori().get(i).getImeAutora(), a.getId());
                        ArrayList<Knjiga> lista=new ArrayList<Knjiga>();
                        lista.add(a);
                        au.postavi(lista);
                        autori.add(au);
                    }
                } */

            }
        });

    }


}
