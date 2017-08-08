package tutorial.firebase.com.shopPlusPlus;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import tutorial.firebase.com.shopPlusPlus.utils.Constants;

public class ShoppingHomeActivity extends AppCompatActivity
{

  private static final String TAG = ShoppingHomeActivity.class.getSimpleName();
  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;

  private FirebaseRemoteConfig _firebaseRemoteConfig;

  public static final String HOMEPAGE_TABS = "homepage_tabs";

  public static int TAB_LENGTH = 2;


  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_home);

    _firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);

    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(BuildConfig.DEBUG)
        .build();
    _firebaseRemoteConfig.setConfigSettings(configSettings);

    setupDefaultRemoteConfigMap();

    fetchConfig();

  }

  private void enableActionButton()
  {
    if(TAB_LENGTH == 1)
    {
      Toast.makeText(this, "HELLO FIREBASE! ", Toast.LENGTH_LONG).show();
    }
  }


  private void setupDefaultRemoteConfigMap()
  {
    Map<String, Object> defaultConfigMap = new HashMap<>();
    defaultConfigMap.put(HOMEPAGE_TABS, Constants.DEFAULT_HOMEPAGE_TABS);
    _firebaseRemoteConfig.setDefaults(defaultConfigMap);
  }


  private void fetchConfig()
  {
    long cacheExpiration = 3600;  //1 hr in seconds
    if (_firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
      cacheExpiration = 0;
    }
    _firebaseRemoteConfig.fetch(cacheExpiration)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
            // Make the fetched config available
            // via FirebaseRemoteConfig get<type> calls, e.g., getLong, getString.
            _firebaseRemoteConfig.activateFetched();

            // Update the Tab number with
            // the newly retrieved values from Remote Config.
            applyRetrievedTabNumber();
            enableActionButton();
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            // An error occurred when fetching the config.
            Log.w(TAG, "Error fetching config", e);

            // Update the Tab number with
            // the newly retrieved values from Remote Config.
            //applyRetrievedTabNumber();
          }
        });

  }


  private void applyRetrievedTabNumber()
  {
    TAB_LENGTH = Integer.parseInt(_firebaseRemoteConfig.getString(HOMEPAGE_TABS));
    Log.d("The tab length is :", String.valueOf(TAB_LENGTH));
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_shopping_home, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }


  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment
  {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    public PlaceholderFragment()
    {
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber)
    {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
      View rootView = inflater.inflate(R.layout.fragment_shopping_home, container, false);
      TextView textView = (TextView) rootView.findViewById(R.id.section_label);
      textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
      return rootView;
    }
  }


  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
   * one of the sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter
  {

    public SectionsPagerAdapter(FragmentManager fm)
    {
      super(fm);
    }


    @Override
    public Fragment getItem(int position)
    {
      // getItem is called to instantiate the fragment for the given page.
      // Return a PlaceholderFragment (defined as a static inner class below).
      return PlaceholderFragment.newInstance(position + 1);
    }


    @Override
    public int getCount()
    {
      //notifyDataSetChanged();
      // Show 2 total pages.
      return TAB_LENGTH;
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
      switch (position)
      {
        case 0:
          return "SECTION 1";
        case 1:
          return "SECTION 2";
      }
      return null;
    }
  }
}
