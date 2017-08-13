package tutorial.firebase.com.shopPlusPlus.analytics;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.util.HashMap;

/**
 * Created by sudhir on 8/12/17.
 */

public class PerfLifeCycleCallbacks implements Application.ActivityLifecycleCallbacks {

    private static final PerfLifeCycleCallbacks instance = new PerfLifeCycleCallbacks();
    private final HashMap<Activity, Trace> traces = new HashMap<>();

    private PerfLifeCycleCallbacks()
    {

    }

    public static PerfLifeCycleCallbacks getInstance()
    {
        return instance;
    }

    @Nullable
    public Trace getTrace(Activity activity)
    {
        return traces.get(activity);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        String name = activity.getClass().getSimpleName();
        Trace trace = FirebasePerformance.startTrace(name);
        traces.put(activity, trace);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        Trace trace = traces.remove(activity);
        trace.stop();

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}
