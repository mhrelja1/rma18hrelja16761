package ba.unsa.etf.rma.merisa.spirala1;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class KnjigePoznanika extends IntentService {

    public KnjigePoznanika () { super(null); }
    public int STATUS_START = 0;
    public int STATUS_FINISH = 1;
    public int STATUS_ERROR = 2;

    @Override
    public void onCreate () {
        super.onCreate ();
    }

    @Override
    protected void onHandleIntent (Intent intent)
    {
        final ResultReceiver receiver= intent.getParcelableExtra("receiver");
        String korisnik= intent.getStringExtra("korisnik");
        ArrayList<Knjiga> listaKnjiga= new ArrayList<Knjiga>();

        Bundle bundle=new Bundle();
        receiver.send(STATUS_START, Bundle.EMPTY);

        String query=null;
        try {
            query = URLEncoder.encode(korisnik, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url1 = "https://www.googleapis.com/books/v1/users/"+query+"/bookshelves/";

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
                    String ac= knjiga.getString("access");
                    if (ac.equals("PUBLIC"))
                    {
                        String link=knjiga.getString("selfLink");
                        link= link+"/volumes";
                        try {
                            URL url2= new URL(link);
                            String rezultat1;
                            HttpURLConnection urlConnection1 = (HttpURLConnection) url2.openConnection();
                            int responseCode1 = urlConnection.getResponseCode();
                            if (responseCode1 == HttpURLConnection.HTTP_OK) {
                                InputStream in1 = new BufferedInputStream(urlConnection1.getInputStream());
                                rezultat1 = convertStreamToString(in1);

                                JSONObject jo1 = new JSONObject(rezultat1);
                                JSONArray lista1 = jo1.getJSONArray("items");
                                for (int j = 0; j < lista1.length(); j++) {
                                    JSONObject knjigeKorisnika = lista1.getJSONObject(j);
                                    String id=null;
                                    id=knjiga.getString("id");

                                    JSONObject volumeInfo = knjigeKorisnika.getJSONObject("volumeInfo");

                                    String naziv=volumeInfo.getString("title");

                                    String opis= null;
                                    int stranice=0;
                                    String datum=null;
                                    ArrayList<Autor> noviAutori = new ArrayList<Autor>();
                                    String urlS=null;
                                    URL url0= new URL ("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_aFBRTPjKSKenZYyhuI1brprxYGzjucvXKDyBXtenRHHUnRqR");
                                    try {
                                        datum = volumeInfo.getString("publishedDate");
                                        opis = volumeInfo.getString("description");
                                        stranice = volumeInfo.getInt("pageCount");
                                        JSONArray autori = volumeInfo.getJSONArray("authors");
                                        JSONObject slika= volumeInfo.getJSONObject("imageLinks");
                                        urlS= slika.getString("thumbnail");
                                        url0= new URL (urlS);


                                        for (int k = 0; k < autori.length(); k++) {
                                            String broj = String.valueOf(k);
                                            String autor = autori.getString(k);
                                            noviAutori.add(new Autor(autor, id));
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }


                                    listaKnjiga.add(new Knjiga(id, naziv, noviAutori, opis, datum, url0, stranice));

                                }
                            }
                        }
                        catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    }



        bundle.putParcelableArrayList("rezultat", listaKnjiga);
        receiver.send(STATUS_FINISH, bundle);

    }
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

}
