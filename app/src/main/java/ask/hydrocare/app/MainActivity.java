package ask.hydrocare.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
  ProgressBar progressBar1;
  EditText editText1, editText2;
  TextView textView, totalTargetIntake, waterTaken;
  Button button;
  Button btn100;
  Button btn200;
  Button btn300;
  Button btn500;
  double targetIntake;
  static int percentage, taken = 0;
  Map<String, Integer> integerMap = new HashMap<String, Integer>() {{
    put("27", 1140);
    put("28", 1185);
    put("29", 1230);
    put("30", 1260);
    put("31", 1290);
    put("32", 1350);
    put("33", 1410);
    put("34", 1440);
    put("35", 1455);
    put("36", 1470);
    put("37", 1515);
    put("38", 1560);
    put("39", 1605);
    put("40", 1650);
    put("41", 1695);
    put("42", 1740);
    put("43", 1795);
    put("44", 1850);
    put("45", 1950);
    put("46", 2010);
    put("47", 2070);
    put("48", 2100);
    put("49", 2130);
    put("50", 2175);
    put("51", 2220);
    put("52", 2240);
    put("53", 2260);
    put("54", 2280);
    put("55", 2325);
    put("56", 2370);
    put("57", 2400);
    put("58", 2430);
    put("59", 2470);
    put("60", 2520);
    put("61", 2540);
    put("62", 2560);
    put("63", 2580);
    put("64", 2625);
    put("65", 2670);
    put("66", 2700);
    put("67", 2730);
    put("68", 2775);
    put("69", 2820);
    put("70", 2840);
    put("71", 2860);
    put("72", 2880);
    put("73", 2925);
    put("74", 2970);
    put("75", 3000);
    put("76", 3030);
    put("77", 3075);
    put("78", 3120);
    put("79", 3140);
    put("80", 3375);
    put("81", 3180);
    put("82", 3225);
    put("83", 3270);
    put("84", 3300);
    put("85", 3330);
    put("86", 2560);
    put("87", 3400);
    put("88", 3440);
    put("89", 3460);
    put("90", 3480);
    put("91", 3525);
    put("92", 3570);
    put("93", 3600);
    put("94", 3630);
    put("95", 3675);
    put("96", 3720);
    put("97", 3740);
    put("98", 3760);
    put("99", 3780);
    put("100", 3825);
    put("101", 3870);
    put("102", 3900);
    put("103", 3930);
    put("104", 3975);
    put("105", 4020);
    put("106", 4040);
    put("107", 4060);
    put("108", 4080);
    put("109", 4125);
    put("110", 4170);
    put("111", 4200);
    put("112", 4230);
  }};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    editText1 = (EditText) findViewById(R.id.text1);
    editText2 = (EditText) findViewById(R.id.text2);
    textView = (TextView) findViewById(R.id.result);
    textView.setTypeface(Typeface.createFromAsset(getAssets(),"Roboto-BlackItalic.ttf"));
    button = (Button) findViewById(R.id.button);
    btn100 = (Button) findViewById(R.id.intake_100_btn);
    btn200 = (Button) findViewById(R.id.intake_200_btn);
    btn300 = (Button) findViewById(R.id.intake_300_btn);
    btn500 = (Button) findViewById(R.id.intake_500_btn);
    totalTargetIntake = (TextView) findViewById(R.id.target_intake);
    waterTaken = (TextView) findViewById(R.id.water_taken);
    btn100.setOnClickListener(this);
    btn200.setOnClickListener(this);
    btn300.setOnClickListener(this);
    btn500.setOnClickListener(this);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String weight = editText1.getText().toString();
        Integer temp = Integer.parseInt(editText2.getText().toString());
        textView.setText("You should take " + (getResult(weight, temp) / 1000) + " liter per day");
        totalTargetIntake.setText("" + (getResult(weight, temp) / 1000) + "ltr");
        targetIntake = getResult(weight, temp);
        setCurrentProgress(0);
      }
    });
//    setCurrentProgress(35);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.intake_100_btn:
        setProgressBarValue(100);
        break;
      case R.id.intake_200_btn:
        setProgressBarValue(200);
        break;
      case R.id.intake_300_btn:
        setProgressBarValue(300);
        break;
      case R.id.intake_500_btn:
        setProgressBarValue(500);
        break;
    }
  }

  public void setProgressBarValue(int consumed) {
    if (targetIntake > taken) {
      taken = taken + consumed;
      percentage = (int) (Math.ceil(taken * 100) / targetIntake);
      Log.d("test", "percent set " + percentage);
      if (percentage > 100)
        waterTaken.setText("" + 100 + "%");
      else waterTaken.setText("" + percentage + "%");
      Log.d("test", "consumed " + taken);
      Log.d("test", "remaining water " + targetIntake);
      if (percentage > 100)
        setCurrentProgress(100);
      else
        setCurrentProgress(percentage);
    }
  }

  public double getResult(String weight, Integer temp) {
    int weightConstant = integerMap.get(weight);
    double temp1 = 0, result;
    if (temp <= 45 && temp >= 40)
      temp1 = 1;
    if (temp <= 39 && temp >= 30)
      temp1 = 0.85;
    if (temp <= 29 && temp >= 20)
      temp1 = 0.68;
    if (temp <= 19 && temp >= 0)
      temp1 = 0.88;
    result = temp1 * weightConstant;
    return result;
  }

  public void setCurrentProgress(int progress) {
    progressBar1 = (ProgressBar) findViewById(R.id.circle_progress_bar);

    progressBar1.setProgress(progress);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
