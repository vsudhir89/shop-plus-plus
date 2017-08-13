package tutorial.firebase.com.shopPlusPlus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tutorial.firebase.com.shopPlusPlus.R;

public class BaseActivity extends AppCompatActivity
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Intent i = new Intent(this, ShoppingHomeActivity.class);
    startActivity(i);
  }
}
