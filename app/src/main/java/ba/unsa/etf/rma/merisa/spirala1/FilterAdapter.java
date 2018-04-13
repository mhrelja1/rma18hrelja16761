package ba.unsa.etf.rma.merisa.spirala1;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends BaseAdapter implements Filterable {

    private ArrayList<String> unosi= new ArrayList<String>();
    private Activity activity= new Activity();
    private ArrayList<String> unosiC= new ArrayList<String>();


    public FilterAdapter (Activity activity, ArrayList<String> unosi)
    {
        this.unosi= unosi;
        this.unosiC= unosi;
        this.activity= activity;
    }

    @Override
    public int getCount() {
        return unosiC.size();
    }

    @Override
    public String getItem (int position)
    {
        return unosiC.get(position);
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
        ViewHolder1 holder;

        if (noviView == null)
        {
            LayoutInflater mInflater= activity.getLayoutInflater();
            noviView= mInflater.inflate(R.layout.element_liste_kategorija, null);
            holder= new ViewHolder1();

            holder.naziv = (TextView) noviView.findViewById(R.id.naziv);

            noviView.setTag(holder);
        }
        else {
            holder= (ViewHolder1) noviView.getTag();
        }
        holder.naziv.setText(unosi.get(position));
        return noviView;
    }
    public class ViewHolder1 {
        TextView naziv;

    }

    @Override
    public Filter getFilter () {
        Filter filter = new Filter() {


            @Override
                    protected FilterResults performFiltering (CharSequence unos)
            {
                FilterResults rezultati= new FilterResults();
                //String unos1 = unos.toString().toLowerCase();

                final ArrayList<String> lista= unosi;
                int count= lista.size();
                final ArrayList<String> nlista= new ArrayList<String> (count);
                String u;

                for (int i=0; i< count ; i++)
                {
                    u= lista.get(i).toString();

                    if (u.toLowerCase().contentEquals(unos)) nlista.add(u.toString());

                    boolean a= u.toLowerCase().contentEquals(unos);
                }

                rezultati.count= nlista.size();
                rezultati.values= nlista;
                Log.e("VALUES", rezultati.values.toString());
                return rezultati;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults (CharSequence unos, FilterResults results)
            {
                unosiC= (ArrayList<String>) results.values;
                final Button dDodajKategoriju= (Button) activity.findViewById(R.id.dDodajKategoriju);
                if (getCount()==0) dDodajKategoriju.setEnabled(true);

                notifyDataSetChanged();

            }
        };
        return filter;
    }


}
