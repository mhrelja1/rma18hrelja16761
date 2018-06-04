package ba.unsa.etf.rma.merisa.spirala1;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterKnjiga extends BaseAdapter {
    private ArrayList<Knjiga> knjige= new ArrayList<Knjiga>();
    private Activity activity= new Activity();

    public AdapterKnjiga (Activity activity, ArrayList<Knjiga> knjiga)
    {
        this.knjige= knjiga;
        this.activity= activity;
    }

    @Override
    public int getCount()
    {
        if (knjige==null) return 0;
        return  knjige.size();
    }

    @Override
    public Knjiga getItem (int position)
    {
        return knjige.get(position);
    }

    @Override
    public long getItemId (int position)
    {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent)
    {
        View noviView = convertView;
        ViewHolder holder;

        if (noviView == null)
        {
            LayoutInflater mInflater= activity.getLayoutInflater();
            noviView= mInflater.inflate(R.layout.element_liste_knjiga, null);
            holder= new ViewHolder();

            holder.img = (ImageView) noviView.findViewById(R.id.eNaslovna);
            holder.naziv = (TextView) noviView.findViewById(R.id.eNaziv);
            holder.autor = (TextView) noviView.findViewById(R.id.eAutor);
            holder.stranice= (TextView) noviView.findViewById(R.id.eBrojStranica);
            holder.opis= (TextView) noviView.findViewById(R.id.eOpis);
            holder.datum= (TextView) noviView.findViewById(R.id.eDatumObjavljivanja);

            noviView.setTag(holder);
        }
        else {
            holder= (ViewHolder) noviView.getTag();
        }
        if (knjige.get(position).getJedan()==false)
        {
        Glide.with(activity)
              .load(knjige.get(position).getSlika())
                .into(holder.img);
        }
        if (knjige.get(position).getJedan()==true)
        {
            Glide.with(activity)
                    .load(knjige.get(position).getSlikaa())
                    .into(holder.img);
        }

        holder.autor.setText(knjige.get(position).dajAutore());
        holder.naziv.setText(knjige.get(position).getNaziv());
        holder.datum.setText("Datum objave: "+knjige.get(position).getDatumObjavljivanja());
        holder.opis.setText(knjige.get(position).getOpis());
        holder.stranice.setText("Broj stranica: "+String.valueOf(knjige.get(position).getBrojStranica()));
        //if (knjige.get(position).isSelected()) noviView.setBackgroundColor(activity.getResources().getColor(R.color.plava));


        Button preporuka= (Button) noviView.findViewById(R.id.dPreporuci);
        preporuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentPreporuci fp= new FragmentPreporuci();
                Bundle argumenti= new Bundle();
                argumenti.putParcelable("knjiga", knjige.get(position));
                fp.setArguments(argumenti);

                activity.getFragmentManager().beginTransaction().replace(R.id.f0, fp).addToBackStack(null).commit();
            }
        });

        return noviView;
    }
    public class ViewHolder {
        ImageView img;
        TextView naziv;
        TextView autor;
        TextView datum;
        TextView opis;
        TextView stranice;

    }


}
