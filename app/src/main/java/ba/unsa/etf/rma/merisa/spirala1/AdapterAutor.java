package ba.unsa.etf.rma.merisa.spirala1;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAutor extends BaseAdapter {
    private ArrayList<Autor> autori= new ArrayList<Autor>();
    private Activity activity= new Activity();

    public AdapterAutor (Activity activity, ArrayList<Autor> autori)
    {
        this.autori= autori;
        this.activity= activity;
    }

    @Override
    public int getCount()
    {
        if (autori==null) return 0;
        return  autori.size();
    }

    @Override
    public Autor getItem (int position)
    {
        return autori.get(position);
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
        AdapterAutor.ViewHolder holder;

        if (noviView == null)
        {
            LayoutInflater mInflater= activity.getLayoutInflater();
            noviView= mInflater.inflate(R.layout.element_liste_autor, null);
            holder= new AdapterAutor.ViewHolder();

            holder.ime = (TextView) noviView.findViewById(R.id.imeAutora);
            holder.broj = (TextView) noviView.findViewById(R.id.brojKnjiga);

            noviView.setTag(holder);
        }
        else {
            holder= (AdapterAutor.ViewHolder) noviView.getTag();
        }
        holder.ime.setText(autori.get(position).getImeAutora());
        holder.broj.setText(String.valueOf(autori.get(position).getBrojKnjiga()));

        return noviView;
    }
    public class ViewHolder {
        TextView ime;
        TextView broj;

    }

}
