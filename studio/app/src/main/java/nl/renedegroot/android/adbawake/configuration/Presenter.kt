/*------------------------------------------------------------------------------
 **    Author: René de Groot
 ** Copyright: (c) 2017 René de Groot All Rights Reserved.
 **------------------------------------------------------------------------------
 ** No part of this file may be reproduced
 ** or transmitted in any form or by any
 ** means, electronic or mechanical, for the
 ** purpose, without the express written
 ** permission of the copyright holder.
 *------------------------------------------------------------------------------
 *
 *   This file is part of "Stay-awake on adb".
 *
 *   "Stay-awake on adb" is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   "Stay-awake on adb" is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with "Stay-awake on adb".  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package nl.renedegroot.android.adbawake.configuration

import android.content.Intent
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Checkable
import nl.renedegroot.android.adbawake.about.AboutFragment
import nl.renedegroot.android.adbawake.businessmodel.LockControl
import nl.renedegroot.android.adbawake.businessmodel.Preferences


class Presenter {

    val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"

    fun grantPermission(view: View) {
        val context = view.context
        context.startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

    fun enableLock(view: View) {
        var isChecked = false
        if (view is Checkable) {
            isChecked = view.isChecked
        }
        LockControl(view.context).enableWakelock(isChecked)
    }

    fun enableService(view: View) {
        var isChecked = false
        if (view is Checkable) {
            isChecked = view.isChecked
        }
        SharedModel.instance.serviceEnabled.set(isChecked)
        Preferences(view.context).enableService(isChecked)
    }


    fun showAboutDialog(fm: FragmentManager) {
        AboutFragment().show(fm, "ABOUT")
    }
}

