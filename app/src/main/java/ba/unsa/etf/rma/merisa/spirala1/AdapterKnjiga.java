package ba.unsa.etf.rma.merisa.spirala1;


import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.bumptech.glide.Glide;

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
    public View getView (int position, View convertView, ViewGroup parent)
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
        //if (knjige.get(position).isSelected()) noviView.setBackgroundColor(activity.getResources().getColor(R.color.plava));

        return noviView;
    }
    public class ViewHolder {
        ImageView img;
        TextView naziv;
        TextView autor;

    }


}
