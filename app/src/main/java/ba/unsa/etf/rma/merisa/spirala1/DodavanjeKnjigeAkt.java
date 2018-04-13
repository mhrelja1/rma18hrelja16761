/*package ba.unsa.etf.rma.merisa.spirala1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class DodavanjeKnjigeAkt extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 11;
    Bitmap bitmap= null;
    ArrayList<Knjiga> knjige= new ArrayList<Knjiga>();
    ImageView dNadjiSliku;
    Uri uri;

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                dNadjiSliku= (ImageView) findViewById(R.id.naslovnaStr);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                bitmap = scaleDownBitmap(bitmap, 75, this);
                dNadjiSliku.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodavanje_knjige_akt);

        final Spinner sKategorijaKnjige= (Spinner)findViewById(R.id.sKategorijaKnjige);
        final ArrayList<String> u= getIntent().getStringArrayListExtra("unosi");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, u );
        sKategorijaKnjige.setAdapter(adapter);

        knjige=getIntent().getParcelableArrayListExtra("knjige");
        final EditText imeAutora= (EditText) findViewById(R.id.imeAutora);
        final EditText nazivKnjige= (EditText) findViewById(R.id.nazivKnjige);
        Button dUpisiKnjigu= (Button) findViewById(R.id.dUpisiKnjigu);
        Button dPonisti= (Button) findViewById(R.id.dPonisti);
        final Button dNadjiSliku= (Button) findViewById(R.id.dNadjiSliku);

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

        dPonisti.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick (View v)
                                        {
                                            imeAutora.setText(" ");
                                            nazivKnjige.setText(" ");
                                            Intent myIntent =new Intent(DodavanjeKnjigeAkt.this, KategorijeAkt.class);
                                            Bundle argument= new Bundle();
                                            argument.putParcelableArrayList("listaKnjiga", knjige);
                                            myIntent.putExtras(argument);
                                            setResult(Activity.RESULT_OK, myIntent);
                                            finish();
                                        }
                                    }
        );

        dUpisiKnjigu.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick (View v)
                                        {

                                            knjige.add (new Knjiga ( bitmap, imeAutora.getText().toString(), nazivKnjige.getText().toString(), sKategorijaKnjige.getSelectedItem().toString(), false));
                                            imeAutora.setText(" ");
                                            nazivKnjige.setText(" ");
                                        }
                                    }
        );


    }
}
*/