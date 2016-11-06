package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class LanguageListArrayAdapter extends ArrayAdapter<Language> {

    private final List<Language> languageList;
    private final Activity context;

    private static class ViewHolder {
        private TextView language_name;
        private ImageView country_flag;
    }

    LanguageListArrayAdapter(Activity context, List<Language> languageList) {
        super(context, R.layout.activity_language_list_entry, languageList);
        this.context = context;
        this.languageList = languageList;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_language_list_entry, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.language_name = (TextView) view.findViewById(R.id.language_name);
            viewHolder.country_flag = (ImageView) view.findViewById(R.id.country_flag);

            view.setTag(viewHolder);

        } else {

            view = convertView;

        }

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.language_name.setText(languageList.get(position).getLanguageName());
        holder.country_flag.setImageDrawable(languageList.get(position).getCountryFlag());

        return view;
    }
}