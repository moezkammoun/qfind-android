package qfind.com.qfindappandroid.settingspagefragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.MainActivity;
import qfind.com.qfindappandroid.R;

public class SettingsFragment extends Fragment {
    @BindView(R.id.english_button)
    Button englishButton;
    @BindView(R.id.arabic_button)
    Button arabicButton;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.setting_text)
    TextView settingsText;
    @BindView(R.id.select_language_text)
    TextView selectLanguageText;
    Locale myLocale;
    public Typeface mtypeFace;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setButtonClickListener();
        setFontTypeForText();
    }

    public void setLocale(String lang) {
        int language = 1;
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale(lang));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        if (lang.equalsIgnoreCase("en")) {
            language = 1;
        }else if(lang.equalsIgnoreCase("ar")){
            language = 2;
        }
            SharedPreferences qfindPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = qfindPreferences.edit();
            editor.putInt("AppLanguage", language);
            editor.commit();
            //Intent refresh = new Intent(MainActivity.this, MainActivity.class);
            //this.overridePendingTransition(0,0);
            //this.finish();
            //startActivity(refresh);
            refreshActivityFromFragment();
            //refreshFragment();
        }

    public void refreshActivityFromFragment() {
        Intent intent = getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();
        this.startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().overridePendingTransition(0, 0);
    }

    public void refreshFragment() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    protected void showDialog(final String language) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
        dialog.setContentView(view);
        Button yes = (Button) view.findViewById(R.id.btn_yes);
        Button no = (Button) view.findViewById(R.id.btn_no);
        TextView alertText = (TextView) view.findViewById(R.id.txt_dialog);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do something
                setLocale(language);
                dialog.dismiss();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do something
                dialog.dismiss();

            }
        });


        dialog.show();
    }

    public void setButtonClickListener() {
        arabicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getResources().getConfiguration().locale.getLanguage().equals("en"))
                    showDialog("ar");
                else
                    Toast.makeText(getContext(), getResources().getString(R.string.already_english), Toast.LENGTH_SHORT).show();
            }
        });
        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getResources().getConfiguration().locale.getLanguage().equals("ar"))
                    showDialog("en");
                else
                    Toast.makeText(getContext(), getResources().getString(R.string.already_english), Toast.LENGTH_SHORT).show();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }

    public void setFontTypeForText() {
        mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/GE_SS_Unique_Light.otf");
        arabicButton.setTypeface(mtypeFace);
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
            settingsText.setTypeface(mtypeFace);
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Light.ttf");
        } else {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }

        selectLanguageText.setTypeface(mtypeFace);
        englishButton.setTypeface(mtypeFace);

    }


}
