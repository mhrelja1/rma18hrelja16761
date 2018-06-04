package ba.unsa.etf.rma.merisa.spirala1;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.ArrayList;

public class FragmentPreporuci extends Fragment {

    ArrayList<String> emails= new ArrayList<String>();
    ArrayList<String> imena= new ArrayList<String>();
    Knjiga knjiga= new Knjiga();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View iv= inflater.inflate(R.layout.fragment_preporuci, container, false);
        if (getArguments().containsKey("knjiga"))
        {
            knjiga= getArguments().getParcelable("knjiga");
        }


        String[] where= new String[] {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Email.DATA };
        Cursor cursor= getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, where, null,null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String ime = cursor.getString(0);
                String email = cursor.getString(1);

                if (email!="")
                {
                    emails.add(email);
                    imena.add(ime);
                }

            } while (cursor.moveToNext());
        }

        cursor.close();

        final Spinner spinner = (Spinner) iv.findViewById(R.id.sKontakti);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, imena);
        spinner.setAdapter(adapter);

        Button dugme= (Button) iv.findViewById(R.id.dPosalji);
        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String poruka= "Zdravo "+imena.get(spinner.getSelectedItemPosition())+", "+System.lineSeparator()+"Pročitaj knjigu "+knjiga.getNaziv()+" od autora "+knjiga.AutorZaMail()+"!";

                String[] TO = {emails.get(spinner.getSelectedItemPosition())};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preporuka");
                emailIntent.putExtra(Intent.EXTRA_TEXT, poruka);
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    getActivity().finish();
                }
                catch (android.content.ActivityNotFoundException ex) {
                 Toast.makeText(getActivity(), "There is no email client installed.",
                    Toast.LENGTH_SHORT).show();
        }}

        });



        return iv;
    }
}
