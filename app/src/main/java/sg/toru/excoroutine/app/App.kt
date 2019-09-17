package sg.toru.excoroutine.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

class App:Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?,
                                             outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?,
                                   savedInstanceState: Bundle?) {
        Log.i("App", "currentActivity: ${activity?.title}")
    }
}