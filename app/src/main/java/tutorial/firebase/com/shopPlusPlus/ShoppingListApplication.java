package tutorial.firebase.com.shopPlusPlus;

import android.app.Application;
import com.firebase.client.Firebase;

/**
 * Created by suvaidya on 8/7/17 for project ShopPlusPlusUdacity.
 */

public class ShoppingListApplication extends Application
{
  @Override
  public void onCreate()
  {
    super.onCreate();
    Firebase.setAndroidContext(this);
  }
}
