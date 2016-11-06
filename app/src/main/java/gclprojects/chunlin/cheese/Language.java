package gclprojects.chunlin.cheese;

import android.graphics.drawable.Drawable;

class Language  {
    private String name;
    private Drawable flag;

    Language(String name, Drawable flag){
        this.name = name;
        this.flag = flag;
    }

    String getLanguageName() {
        return name;
    }

    Drawable getCountryFlag() {
        return flag;
    }
}
