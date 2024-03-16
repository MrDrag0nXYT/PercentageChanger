package zxc.mrdrag0nxyt.percentagechanger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private EditText percentageInput;

    private final File SU = new File("/system/bin/su");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SU.exists()) {
            Toast.makeText(this, getString(R.string.no_root), Toast.LENGTH_LONG).show();
            finish();
        }

        percentageInput = findViewById(R.id.percentageInput);
    }

    public void resetPercentage(View view) throws Exception {
        executeCommandAsRoot("dumpsys battery reset level");
    }

    public void setPercentage(View view) throws Exception {
        String input = percentageInput.getText().toString();

        if (!input.isEmpty()) {
            int percentage = Integer.parseInt(input);

            if (percentage < 1) {
                // TODO: улучшить предупреждение что трубка может отключиться
                Toast.makeText(this, getString(R.string.dont_do_that), Toast.LENGTH_SHORT).show();

            } else {
                executeCommandAsRoot("dumpsys battery set level " + percentage);
            }

        } else {
            Toast.makeText(this, getString(R.string.input_cant_be_empty), Toast.LENGTH_SHORT);
        }
    }



    private void executeCommandAsRoot(String command) throws Exception {
        Runtime.getRuntime().exec("su -c " + command);
    }
}