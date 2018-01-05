package qfind.com.qfindappandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class InformationPage extends AppCompatActivity {


    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("INFORMATION PAGE");
        toolbar.setSubtitle("subtitile");
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_menu_camera);

    }
}
