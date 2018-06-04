package ba.unsa.etf.rma.merisa.spirala1;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DodavanjeKnjigeFragment extends Fragment{
    ArrayList<String> kategorije=new ArrayList<String>();
    ArrayList<Knjiga> knjige= new ArrayList<Knjiga>();
    ArrayList<Autor> autori=new ArrayList<Autor>();
    private int PICK_IMAGE_REQUEST = 11;
    Bitmap bitmap= null;
    ImageView slika;
    Uri uri;



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==getActivity().RESULT_OK) {
            uri = data.getData();
            Glide.with(getActivity()).load(uri).into(slika);

        }
    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View iv = inflater.inflate(R.layout.fragment_dodavanje_knjige, container, false);

        /*if (getArguments().containsKey("unosi"))
        {
            kategorije= getArguments().getStringArrayList("unosi");
        }
        if ( getArguments().containsKey("knjige"))
        {
            knjige= getArguments().getParcelableArrayList("knjige");
        }
        if ( getArguments().containsKey("autori"))
        {
            autori= getArguments().getParcelableArrayList("autori");
        }*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kategorije );

        final Spinner sKategorijaKnjige= (Spinner)iv.findViewById(R.id.sKategorijaKnjige);
        final BazaOpenHelper bazaOpenHelper= new BazaOpenHelper(getActivity());
        SQLiteDatabase db= bazaOpenHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT  * FROM "+BazaOpenHelper.DATABASE_TABLE_KATE, null);
        SimpleCursorAdapter simpleCursorAdapterK= new SimpleCursorAdapter( getActivity(), android.R.layout.simple_list_item_1 ,c, new String []{ BazaOpenHelper.KATEGORIJA_NAZIV }, new int []{ android.R.id.text1 }, 0 );
        sKategorijaKnjige.setAdapter(simpleCursorAdapterK);

        final EditText imeAutora= (EditText) iv.findViewById(R.id.imeAutora);
        final EditText nazivKnjige= (EditText) iv.findViewById(R.id.nazivKnjige);
        Button dUpisiKnjigu= (Button) iv.findViewById(R.id.dUpisiKnjigu);
        Button dPonisti= (Button) iv.findViewById(R.id.dPonisti);
        final Button dNadjiSliku= (Button) iv.findViewById(R.id.dNadjiSliku);


        slika= (ImageView) iv.findViewById(R.id.naslovnaStr);
        dNadjiSliku.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {

                                               Intent myIntent= new Intent();
                                               myIntent.setType("image/*");
                                               myIntent.setAction(Intent.ACTION_GET_CONTENT);
                                               startActivityForResult(Intent.createChooser(myIntent, "biranjeSlike"), PICK_IMAGE_REQUEST);

                                           }
                                       }
        );


        dUpisiKnjigu.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick (View v)
                                            {

                                                Knjiga knj = new Knjiga(uri.toString(), nazivKnjige.getText().toString(), sKategorijaKnjige.getSelectedItem().toString());
                                                Autor a = new Autor(imeAutora.getText().toString(), "00");
                                                knj.setAutor(a);
                                                long id=bazaOpenHelper.dodajKnjigu(knj);
                                                imeAutora.setText("");
                                                nazivKnjige.setText("");

                                               /*
                                                knjige.add(knj);

                                                imeAutora.setText("");
                                                nazivKnjige.setText("");
                                                boolean isti=false;

                                                if (knjige.size()==1) {
                                                    ArrayList<Knjiga> listaKnjiga=new ArrayList<Knjiga>();
                                                    listaKnjiga.add(knjige.get(knjige.size()-1));
                                                    autori.add(new Autor (knjige.get(knjige.size()-1).getAutor().getImeAutora(), listaKnjiga));
                                                }
                                                else
                                                {
                                                   for (int i=0; i<autori.size(); i++)
                                                   {
                                                    if (autori.get(i).getImeAutora().equals(knjige.get(knjige.size()-1).getAutor().getImeAutora())) {
                                                        autori.get(i).dodajKnjigu(knjige.get(knjige.size() - 1));
                                                        isti=true;
                                                    }

                                                   }
                                                    if (isti==false)
                                                    {
                                                        ArrayList<Knjiga> listaKnjiga=new ArrayList<Knjiga>();
                                                        listaKnjiga.add(knjige.get(knjige.size()-1));
                                                        autori.add(new Autor (knjige.get(knjige.size()-1).getAutor().getImeAutora(), listaKnjiga));
                                                    }
                                                } */
                                            }
                                        }
        );



        dPonisti.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick (View v)
                                        {
                                            imeAutora.setText("");
                                            nazivKnjige.setText("");

                                            /*Intent intent=new Intent();
                                            intent.putParcelableArrayListExtra("knjige", knjige);
                                            intent.putParcelableArrayListExtra("autori", autori);

                                            getTargetFragment().onActivityResult(
                                                    getTargetRequestCode(),
                                                    Activity.RESULT_OK,
                                                    intent
                                            );*/

                                            FragmentManager fragmentManager= getFragmentManager();
                                            fragmentManager.popBackStack();
                                        }
                                    }
        );



        return iv;
    }

}
