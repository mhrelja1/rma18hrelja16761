package ba.unsa.etf.rma.merisa.spirala1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KategorijeAkt extends AppCompatActivity {

    ArrayList<String> unosi=new ArrayList<String>();
    ArrayList<Knjiga> knjige= new ArrayList<Knjiga>();
    private static final int ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                knjige= data.getParcelableArrayListExtra("listaKnjiga");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije_akt);

        ListView lista=(ListView)findViewById(R.id.listaKategorija);
        final EditText unos=(EditText) findViewById(R.id.tekstPretraga);
        Button dPretraga= (Button) findViewById(R.id.dPretraga);
        final Button dDodajKategoriju= (Button) findViewById(R.id.dDodajKategoriju);
        Button dDodajKnjigu= (Button) findViewById(R.id.dDodajKnjigu);
        final ArrayAdapter<String> zaPrikaz=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unosi);

        final FilterAdapter adapter=new FilterAdapter(this, unosi);
        lista.setAdapter(zaPrikaz);


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
                    unosi.add(unos.getText().toString());
                    adapter.notifyDataSetChanged();
                    zaPrikaz.notifyDataSetChanged();
                    unos.setText("");
                    dDodajKategoriju.setEnabled(false);

            }
        });

        dDodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(KategorijeAkt.this, DodavanjeKnjigeAkt.class);
                myIntent.putStringArrayListExtra("unosi", unosi);
                myIntent.putParcelableArrayListExtra("knjige", knjige);
                startActivityForResult(myIntent, ACTIVITY_REQUEST_CODE);
            }
        });


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(KategorijeAkt.this, ListaKnjigaAkt.class);
                Bundle arg= new Bundle();
                arg.putParcelableArrayList("listaKnjiga", knjige);
                myIntent.putExtras(arg);
                myIntent.putExtra("nazivKategorije",unosi.get(i));
                KategorijeAkt.this.startActivity(myIntent);
            }
        });


    }
}
