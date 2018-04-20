package wumingya.com.studentsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 13271 on 2018/4/17.
 */

public class Setting extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_url);

        final EditText editText = (EditText)findViewById(R.id.edit_text);
        Button  button = (Button) findViewById(R.id.btn_item);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.toString();
            }
        });

    }
}
