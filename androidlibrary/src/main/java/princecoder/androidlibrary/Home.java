package princecoder.androidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    public static  String messageTag="messageTag";
    TextView messageTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        messageTxt= (TextView) findViewById(R.id.txtMessage);
        String message=getIntent().getStringExtra(messageTag);
        messageTxt.setText(message);
    }
}
