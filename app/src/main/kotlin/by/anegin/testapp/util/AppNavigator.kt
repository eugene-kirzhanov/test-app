package by.anegin.testapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import by.anegin.testapp.core.navigation.NavDestination
import by.anegin.testapp.core.navigation.Navigator
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sample Navigator implementation based on FragmentTransactions.
 * Should be attached/detached to the host activity before using.
 */
@Singleton
class AppNavigator @Inject constructor() : Navigator {

    private var activityRef: WeakReference<AppCompatActivity>? = null
    private var fragmentContainerId = 0

    fun setup(activity: AppCompatActivity, fragmentContainerId: Int) {
        this.activityRef = WeakReference(activity)
        this.fragmentContainerId = fragmentContainerId
    }

    fun cleanup() {
        this.activityRef?.clear()
        this.activityRef = null
        fragmentContainerId = 0
    }

    override fun navigateTo(destination: NavDestination) {
        val activity = activityRef?.get() ?: return
        if (fragmentContainerId == 0) return
        activity.supportFragmentManager.commit {
            replace(fragmentContainerId, destination.fragmentClass, destination.arguments)
            if (destination.addToBackStack) {
                addToBackStack(destination.fragmentClass.simpleName)
            }
        }
    }

    override fun navigateBack(): Boolean {
        val activity = activityRef?.get() ?: return false
        return if (activity.supportFragmentManager.backStackEntryCount > 0) {
            activity.supportFragmentManager.popBackStack()
            true
        } else {
            if (!activity.isFinishing) {
                activity.finish()
                true
            } else {
                false
            }
        }
    }
}