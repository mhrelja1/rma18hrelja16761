package ba.unsa.etf.rma.merisa.spirala1;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class KnjigeFragment extends Fragment {
    ArrayList<Knjiga> knjige = new ArrayList<Knjiga>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View iv = inflater.inflate(R.layout.fragment_knjige, container, false);

        int zadnjiKliknutA=0;
        Button povratak= (Button) iv.findViewById(R.id.dPovratak);
        final ListView listaKnjiga= (ListView) iv.findViewById(R.id.listaKnjiga);



        if (getArguments().containsKey("zadnjiKliknutA"))
        {
            zadnjiKliknutA= getArguments().getInt("zadnjiKliknutA");
        }
        if (zadnjiKliknutA==1) {
            String odabraniAutor="";
            ArrayList<Autor> autori = new ArrayList<Autor>();
            ArrayList<Knjiga> knjigeOdabranogAutora = new ArrayList<Knjiga>();


            if (getArguments().containsKey("listaAutora")) {
                autori = getArguments().getParcelableArrayList("listaAutora");
            }
            if (getArguments().containsKey("odabraniAutor")) {
                odabraniAutor = getArguments().getString("odabraniAutor");
            }

            for (int i = 0; i < autori.size(); i++)
            { if (autori.get(i).getImeAutora().equals(odabraniAutor))
                knjigeOdabranogAutora= autori.get(i).getKnjige(); }

            AdapterKnjiga adapterKnjiga= new AdapterKnjiga(getActivity(), knjigeOdabranogAutora);
            listaKnjiga.setAdapter(adapterKnjiga);

        }


        if (zadnjiKliknutA==2)
        {
            ArrayList<Knjiga> odabraneKnjige =new ArrayList<Knjiga>();
            String kategorija="";


            if (getArguments().containsKey("nazivKategorije"))
            { kategorija = getArguments().getString("nazivKategorije"); }
            if (getArguments().containsKey("listaKnjiga"))
            { knjige = getArguments().getParcelableArrayList("listaKnjiga"); }

            for (int i=0; i<knjige.size(); i++)
            {
                if (kategorija.equals(knjige.get(i).getKategorija())) odabraneKnjige.add(knjige.get(i));
            }

            AdapterKnjiga adapterKnjiga= new AdapterKnjiga(getActivity(), odabraneKnjige);
            listaKnjiga.setAdapter(adapterKnjiga);

        }


        povratak.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick (View v)
                                        {
                                            FragmentManager fragmentManager= getFragmentManager();
                                            fragmentManager.popBackStack();
                                        }
                                    }
        );


        return iv;
    }
}
