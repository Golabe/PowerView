# PowerView

![此处输入图片的描述][1]
![此处输入图片的描述][2]

##Usage
##Gradle
>  compile 'top.golabe.powerview:library:0.0.2'

## In layout.xml

    <top.golabe.library.PowerView
        android:id="@+id/power"
        app:power_progress_color="@color/colorPrimaryDark"
        app:power_bg_color="@color/colorPrimary"
        app:power_max="100"
        app:power_min="0"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="4dp"
        app:orientation="vertical"
        app:power_progress="20" />

## In java
        powerView=findViewById(R.id.power);
        powerView.setPowerMax(1000);
        powerView.setPowerMin(0);
        powerView.setPowerProgress(50);
        powerView.setPowerBgColor(Color.BLUE);
        powerView.setPowerProgressColor(Color.RED);
        powerView.setOrientation(PowerView.ORIENTATION_VERTICAL);
## Attrs

        <attr name="orientation" format="enum">
            <enum name="vertical" value="0" />
            <enum name="horizontal" value="1" />
        </attr>
        <attr name="power_bg_color" format="color" />
        <attr name="power_progress_color" format="color" />
        <attr name="power_progress" format="integer" />
        <attr name="power_min" format="integer" />
        <attr name="power_max" format="integer" />


  [1]: https://github.com/Golabe/PowerView/blob/master/images/image1.jpg?raw=true
  [2]: https://github.com/Golabe/PowerView/blob/master/images/image1.jpg?raw=true
