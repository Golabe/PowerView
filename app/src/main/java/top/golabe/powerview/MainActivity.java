package top.golabe.powerview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import top.golabe.library.PowerView;

public class MainActivity extends AppCompatActivity {
    private PowerView powerView;
    private static int  progress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        powerView=findViewById(R.id.power);
//        powerView.setPowerMax(1000);
//        powerView.setPowerMin(0);
//        powerView.setPowerProgress(50);
//        powerView.setPowerBgColor(Color.BLUE);
//        powerView.setPowerProgressColor(Color.RED);
//        powerView.setOrientation(PowerView.ORIENTATION_VERTICAL);
    }

    public void add(View view) {

        powerView.setPowerProgress(progress+=10);
    }

    public void reduce(View view) {
        powerView.setPowerProgress(progress-=10);
    }
}
