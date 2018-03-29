package ba.unsa.etf.rma.merisa.spirala1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaKnjigaAkt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_knjiga_akt);

        Button povratak= (Button) findViewById(R.id.dPovratak);
        final ArrayList<Knjiga> knjige= getIntent().getParcelableArrayListExtra("listaKnjiga");
        String kategorija= getIntent().getStringExtra("nazivKategorije");
        final ListView listaKnjiga= (ListView) findViewById(R.id.listaKnjiga);
        ArrayList<Knjiga> odabraneKnjige =new ArrayList<Knjiga>();
        for (int i=0; i<knjige.size(); i++)
        {
            if (kategorija.equals(knjige.get(i).getKategorija())) odabraneKnjige.add(knjige.get(i));
        }

        final AdapterKnjiga adapterKnjiga= new AdapterKnjiga(this, odabraneKnjige);
        listaKnjiga.setAdapter(adapterKnjiga);

        for (int i=0; i<knjige.size(); i++)
        {
            if (knjige.get(i).isSelected() && knjige.get(i).getView()!=null)
            {
                View newView= knjige.get(i).getView();
                newView.setBackgroundColor(this.getResources().getColor((R.color.plava)));

            }
        }

        povratak.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                finish();
            }
        });

        listaKnjiga.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Knjiga selection= (Knjiga) listaKnjiga.getItemAtPosition(i);
                selection.setSelected(true);
                selection.setView(view);
                adapterKnjiga.notifyDataSetChanged();
            }
        });
    }
}
