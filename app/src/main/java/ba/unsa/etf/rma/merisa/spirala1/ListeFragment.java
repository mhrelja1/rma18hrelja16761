package ba.unsa.etf.rma.merisa.spirala1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListeFragment extends Fragment {
    ArrayList<Knjiga> knjige= new ArrayList<Knjiga>();
    public static int REQUEST_CODE=1;
    int zadnjiKliknutAutor;
    ArrayList<Autor> autori=new ArrayList<Autor>();
    OnItemClick oicA;
    OnItemClick oicK;
    ArrayList<String> unosi=new ArrayList<String>();
    SimpleCursorAdapter simpleCursorAdapterK;
    SimpleCursorAdapter simpleCursorAdapterA;


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE && resultCode==getActivity().RESULT_OK) {

            knjige = data.getParcelableArrayListExtra("knjige");
            autori= data.getParcelableArrayListExtra("autori");
        }
        if (requestCode==5 && resultCode==getActivity().RESULT_OK) {

            knjige = data.getParcelableArrayListExtra("knjige");
            autori= data.getParcelableArrayListExtra("autori");

        }
    }*/

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View iv=inflater.inflate(R.layout.fragment_liste, container, false);

       /* if (savedInstanceState!=null)
        {
            unosi= savedInstanceState.getStringArrayList("kategorije");
            knjige= savedInstanceState.getParcelableArrayList("knjige");
            autori= savedInstanceState.getParcelableArrayList("autori");
        }*/


        final ListView lista=(ListView)iv.findViewById(R.id.listaKategorija);
        final EditText unos=(EditText) iv.findViewById(R.id.tekstPretraga);
        final Button dPretraga= (Button) iv.findViewById(R.id.dPretraga);
        final Button dDodajKategoriju= (Button) iv.findViewById(R.id.dDodajKategoriju);
        Button dDodajKnjigu= (Button) iv.findViewById(R.id.dDodajKnjigu);
        final ArrayAdapter<String> zaPrikazKategorija=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, unosi);
        final FilterAdapter adapter=new FilterAdapter(getActivity(), unosi);
        //final AdapterAutor zaPrikazAutora= new AdapterAutor(getActivity(), autori);
        Button kategorija= (Button) iv.findViewById(R.id.dKategorije);
        final Button autor= (Button) iv.findViewById(R.id.dAutori);
        final FragmentManager fragmentManager= getActivity().getFragmentManager();
        final FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        final Configuration configuration= getActivity().getResources().getConfiguration();

        final BazaOpenHelper bazaOpenHelper= new BazaOpenHelper(getActivity());
        final SQLiteDatabase db= bazaOpenHelper.getReadableDatabase();




        Button dDodajOnline= (Button) iv.findViewById(R.id.dDodajOnline);
        dDodajOnline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentOnline fo=new FragmentOnline();
             /*   Bundle argumenti = new Bundle();
                argumenti.putStringArrayList("unosi", unosi);
                argumenti.putParcelableArrayList("knjige", knjige);
                argumenti.putParcelableArrayList("autori", autori);

                fo.setArguments(argumenti);
                fo.setTargetFragment(ListeFragment.this, 5);*/
                fragmentTransaction.replace(R.id.f0, fo).addToBackStack(null).commit();
            }
        });


        kategorija.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                zadnjiKliknutAutor=2;
                dPretraga.setVisibility(View.VISIBLE);
                dDodajKategoriju.setVisibility(View.VISIBLE);
                unos.setVisibility(View.VISIBLE);
                Cursor c = db.rawQuery("SELECT  * FROM "+BazaOpenHelper.DATABASE_TABLE_KATE, null);
                simpleCursorAdapterK= new SimpleCursorAdapter ( getActivity(), android.R.layout.simple_list_item_1 ,c, new String []{ BazaOpenHelper.KATEGORIJA_NAZIV }, new int []{ android.R.id.text1 }, 0 );

                lista.setAdapter(simpleCursorAdapterK);

                try
                {
                    oicK= (OnItemClick) getActivity();
                } catch (ClassCastException e)
                {
                    throw  new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
                }
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        oicK.onItemClicked(zadnjiKliknutAutor, i );
                    }
                });

            }
        });


        autor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                zadnjiKliknutAutor=1;
                dPretraga.setVisibility(View.GONE);
                dDodajKategoriju.setVisibility(View.GONE);
                unos.setVisibility(View.GONE);
                Cursor c = db.rawQuery("SELECT  * FROM "+BazaOpenHelper.DATABASE_TABLE_AUTOR, null);
                simpleCursorAdapterA=  new SimpleCursorAdapter ( getActivity(), android.R.layout.simple_list_item_1 ,c, new String []{ BazaOpenHelper.AUTOR_IME }, new int []{ android.R.id.text1 }, 0 );
                lista.setAdapter(simpleCursorAdapterA);
                // lista.setAdapter(zaPrikazAutora);

                try
                {
                    oicA= (OnItemClick) getActivity();
                } catch (ClassCastException e)
                {
                    throw  new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
                }
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        oicA.onItemClicked(zadnjiKliknutAutor, i );
                    }
                });
            }

        });


        dPretraga.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                adapter.getFilter().filter(unos.getText().toString());

            }
        });

        dDodajKategoriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //unosi.add(unos.getText().toString());
                long rez=bazaOpenHelper.dodajKategoriju(unos.getText().toString());
                adapter.notifyDataSetChanged();
//                simpleCursorAdapterK.notifyDataSetChanged();
                unos.setText("");
                dDodajKategoriju.setEnabled(false);


            }
        });

        dDodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    DodavanjeKnjigeFragment dk = new DodavanjeKnjigeFragment();
                    /*Bundle argumenti = new Bundle();
                    argumenti.putStringArrayList("unosi", unosi);
                    argumenti.putParcelableArrayList("knjige", knjige);
                    argumenti.putParcelableArrayList("autori", autori);
                    dk.setArguments(argumenti);

                    dk.setTargetFragment(ListeFragment.this, 1);*/

                if (configuration.orientation== Configuration.ORIENTATION_LANDSCAPE )
                {
                    fragmentTransaction.replace(R.id.mjesto1, dk).addToBackStack(null).commit();

                }
                else {
                    fragmentTransaction.replace(R.id.f0, dk).addToBackStack(null).commit(); }
            }
        });


        return iv;
    }

   /* @Override
    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        savedInstanceState.putStringArrayList("kategorije", unosi);
        savedInstanceState.putParcelableArrayList("autori", autori);
        savedInstanceState.putParcelableArrayList("knjige", knjige);
    }*/



    public interface OnItemClick {
        public void onItemClicked(int zadnjiKliknutA, int id);
    }


}
