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
        final BazaOpenHelper bazaOpenHelper= new BazaOpenHelper(getActivity());



        if (getArguments().containsKey("zadnjiKliknutA"))
        {
            zadnjiKliknutA= getArguments().getInt("zadnjiKliknutA");
        }
        if (zadnjiKliknutA==1) {
            int id=-1;
            ArrayList<Knjiga> knjigeOdabranogAutora = new ArrayList<Knjiga>();


            if (getArguments().containsKey("id")) {
                id = getArguments().getInt("id");
            }

            knjigeOdabranogAutora= bazaOpenHelper.knjigeAutora(id+1);

            AdapterKnjiga adapterKnjiga= new AdapterKnjiga(getActivity(), knjigeOdabranogAutora);
            listaKnjiga.setAdapter(adapterKnjiga);

        }

        if (zadnjiKliknutA==2)
        {
            ArrayList<Knjiga> odabraneKnjige =new ArrayList<Knjiga>();
            int id=1;


            if (getArguments().containsKey("id"))
            { id = getArguments().getInt("id"); }

            odabraneKnjige= bazaOpenHelper.knjigeKategorije(id+1);


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
