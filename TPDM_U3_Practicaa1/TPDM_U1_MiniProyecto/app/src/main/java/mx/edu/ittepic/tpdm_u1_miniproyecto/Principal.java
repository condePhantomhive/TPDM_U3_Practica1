package mx.edu.ittepic.tpdm_u1_miniproyecto;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

public class Principal extends AppCompatActivity {
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_principal);

        pb=(ProgressBar)findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            public void run() {
                doWork();
                ventanaMenu();
                finish();
            }
        }).start();

    }
    private void doWork() {
        for (int progress = 0; progress < 100; progress += 20) {
            try {
                Thread.sleep(1000);
                pb.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void ventanaMenu(){
        Intent menu= new Intent(this,mx.edu.ittepic.tpdm_u1_miniproyecto.Menu.class);
        startActivity(menu);
    }

}
