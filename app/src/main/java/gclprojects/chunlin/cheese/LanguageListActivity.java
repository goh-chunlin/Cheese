package gclprojects.chunlin.cheese;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class LanguageListActivity extends ListActivity {

    public static String targetedLanguageRequestCode = "language_request_code";
    public String[] languageNames;
    private TypedArray countryFlags;
    private ArrayList<Language> languageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateLanguageList();

        ArrayAdapter<Language> adapter = new LanguageListArrayAdapter(this, languageList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra(targetedLanguageRequestCode, position);
                setResult(RESULT_OK, returnIntent);

                countryFlags.recycle();

                finish();
            }
        });
    }

    private void populateLanguageList() {
        languageList = new ArrayList<>();

        languageNames = getResources().getStringArray(R.array.language_names);

        countryFlags = getResources().obtainTypedArray(R.array.country_flags);

        for(int i = 0; i < languageNames.length; i++){
            languageList.add(new Language(languageNames[i], countryFlags.getDrawable(i)));
        }
    }

}
