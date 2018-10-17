package top.golabe.powerview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import top.golabe.library.PowerView;

public class MainActivity extends AppCompatActivity {
    private PowerView powerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        powerView=findViewById(R.id.power);
//        powerView.setPowerMax(1000);
//        powerView.setPowerMin(0);
//        powerView.setPowerProgress(50);
//        powerView.setPowerBgColor(Color.BLUE);
//        powerView.setPowerProgressColor(Color.RED);
//        powerView.setOrientation(PowerView.ORIENTATION_VERTICAL);
    }
}
