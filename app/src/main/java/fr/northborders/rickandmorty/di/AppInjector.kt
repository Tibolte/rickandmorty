package fr.northborders.rickandmorty.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import fr.northborders.rickandmorty.MainApplication

/**
 * Helper class to automatically inject fragments if they implement [Injectable].
 */
object AppInjector {

    fun init(application: MainApplication) {
        DaggerAppComponent.builder().application(application)
            .build().inject(application)
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityResumed(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityPaused(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityStopped(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                TODO("Not yet implemented")
            }

            override fun onActivityDestroyed(activity: Activity) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }
}