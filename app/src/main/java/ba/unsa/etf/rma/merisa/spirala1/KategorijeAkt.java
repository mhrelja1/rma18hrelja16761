package ba.unsa.etf.rma.merisa.spirala1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class KategorijeAkt extends AppCompatActivity implements ListeFragment.OnItemClick{
    FragmentManager fragmentManager = getFragmentManager();
    ArrayList<Knjiga> knjige=new ArrayList<Knjiga>();
    ArrayList<Autor> autori= new ArrayList<Autor>();
    ArrayList<String> kategorije= new ArrayList<String>();


    @Override
    public void onItemClicked(int zadnjiKliknutA, int id) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Configuration configuration = getResources().getConfiguration();

        KnjigeFragment kf = new KnjigeFragment();
        Bundle arg = new Bundle();
        arg.putInt("zadnjiKliknutA", zadnjiKliknutA);
        arg.putInt("id", id);

        kf.setArguments(arg);
        if (configuration.orientation== Configuration.ORIENTATION_LANDSCAPE )
        {
            fragmentTransaction.replace(R.id.mjesto2, kf).addToBackStack(null).commit();

        }
        else {
        fragmentTransaction.replace(R.id.f0, kf).addToBackStack(null).commit(); }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_kategorije_akt);
        Configuration configuration = getResources().getConfiguration();


        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.f0);

            if (frameLayout != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ListeFragment listeFragment;
                listeFragment = (ListeFragment) fragmentManager.findFragmentById(R.id.f0);
                if (listeFragment == null) {
                    listeFragment = new ListeFragment();
                    fragmentTransaction.replace(R.id.f0, listeFragment).addToBackStack(null).commit();
                }
            }
        }

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ListeFragment listeFragment;
            listeFragment = (ListeFragment) fragmentManager.findFragmentById(R.id.mjesto1);

            if (listeFragment == null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                listeFragment = new ListeFragment();
                fragmentTransaction.replace(R.id.mjesto1, listeFragment).commit();
            }


        }

    }



}
