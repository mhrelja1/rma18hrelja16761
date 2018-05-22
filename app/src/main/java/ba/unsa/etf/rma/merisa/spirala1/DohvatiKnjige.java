package ba.unsa.etf.rma.merisa.spirala1;


import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DohvatiKnjige extends AsyncTask<ArrayList<String>, Integer, Void> {
    public ArrayList<Knjiga> rezultati= new ArrayList<Knjiga>();
    private IDohvatiKnjigeDone pozivatelj;
    public DohvatiKnjige (IDohvatiKnjigeDone p) { pozivatelj=p; }

    public String convertStreamToString(InputStream is)
    {
        BufferedReader reader= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String line=null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line+ "\n");
            }

        }
        catch (IOException e) {}
        finally {
            try { is.close();}
            catch (IOException e) {}
        }
        return sb.toString();
    }

    @Override
    protected Void doInBackground (ArrayList<String>... params) {
        ArrayList<String > listaUnosa=params[0];
        String query = null;
        for (int b=0; b<listaUnosa.size(); b++) {
            try {
                query = URLEncoder.encode(listaUnosa.get(b), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url1 = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + query+"&maxResults=5";

            try {
                URL url = new URL(url1);
                String rezultat;
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    rezultat = convertStreamToString(in);

                    JSONObject jo = new JSONObject(rezultat);
                    JSONArray lista = jo.getJSONArray("items");

                    for (int i = 0; i < lista.length(); i++) {
                        JSONObject knjiga = lista.getJSONObject(i);

                        String id=null;
                        id=knjiga.getString("id");

                        JSONObject volumeInfo = knjiga.getJSONObject("volumeInfo");

                        String naziv=volumeInfo.getString("title");

                        String opis= null;
                        int stranice=0;
                        String datum=null;
                        ArrayList<Autor> noviAutori = new ArrayList<Autor>();
                        String urlS="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_aFBRTPjKSKenZYyhuI1brprxYGzjucvXKDyBXtenRHHUnRqR";
                        URL url0=new URL (urlS);
                        try {
                            JSONArray autori = volumeInfo.getJSONArray("authors");


                            for (int j = 0; j < autori.length(); j++) {
                                String broj = String.valueOf(j);
                                String autor = autori.getString(j);
                                noviAutori.add(new Autor(autor, id));
                            }

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            opis = volumeInfo.getString("description");

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            stranice = volumeInfo.getInt("pageCount");

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            datum = volumeInfo.getString("publishedDate");

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            JSONObject slika= volumeInfo.getJSONObject("imageLinks");
                            urlS= slika.getString("thumbnail");
                            url0= new URL (urlS);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                        rezultati.add(new Knjiga(id, naziv, noviAutori, opis, datum, url0, stranice));

                    }


                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

return  null;
    }

    @Override
    protected void onPostExecute (Void aVoid)
    {
        super.onPostExecute(aVoid);
        pozivatelj.onDohvatiKnjigeDone(rezultati);
    }
    public interface IDohvatiKnjigeDone
    { public void onDohvatiKnjigeDone (ArrayList<Knjiga> rez); }
}
