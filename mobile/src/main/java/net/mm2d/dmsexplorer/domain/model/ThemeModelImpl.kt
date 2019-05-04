/*
 * Copyright (c) 2017 大前良介 (OHMAE Ryosuke)
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/MIT
 */

package net.mm2d.dmsexplorer.domain.model

import android.app.Activity
import android.os.Build
import androidx.annotation.ColorInt
import net.mm2d.android.util.ActivityLifecycleCallbacksAdapter
import net.mm2d.dmsexplorer.util.ColorUtils
import java.util.*

/**
 * @author [大前良介 (OHMAE Ryosuke)](mailto:ryo@mm2d.net)
 */
class ThemeModelImpl : ActivityLifecycleCallbacksAdapter(), ThemeModel {
    private val map = HashMap<String, Int>()

    override fun setThemeColor(
        activity: Activity,
        @ColorInt toolbarColor: Int,
        @ColorInt statusBarColor: Int
    ) {
        val color =
            if (statusBarColor != 0) statusBarColor else ColorUtils.getDarkerColor(toolbarColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = color
        }
        map[activity.javaClass.name] = toolbarColor
    }

    override fun getToolbarColor(activity: Activity): Int {
        return map[activity.javaClass.name] ?: 0
    }

    override fun onActivityDestroyed(activity: Activity) {
        map.remove(activity.javaClass.name)
    }
}
