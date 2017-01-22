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

import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Checkable
import nl.renedegroot.android.adbawake.Application
import nl.renedegroot.android.adbawake.about.AboutFragment
import nl.renedegroot.android.adbawake.businessmodel.LockControl
import nl.renedegroot.android.adbawake.businessmodel.Preferences
import javax.inject.Inject


class ConfigurationPresenter(val model: ConfigurationViewModel) : LockControl.OnLockChangedListener {

    private val DIAGLOG_TAG = "ABOUT"
    private val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    @Inject
    lateinit var lockControl: LockControl

    @Inject
    lateinit var preferences: Preferences

    init {
        Application.appComponent.inject(this)
    }

    fun start(context: Context) {
        lockControl.addListener(this)
        model.serviceEnabled.set(preferences.isServiceEnabled(context))
        model.wakeLocked.set(lockControl.isAcquired)
    }

    fun stop() {
        lockControl.removeListener(this)
    }

    fun grantPermission(view: View) {
        view.context.startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

    fun enableService(view: View) {
        var isChecked = false
        if (view is Checkable) {
            isChecked = view.isChecked
        }
        preferences.enableService(view.context, isChecked)
    }

    fun enableLock(view: View) {
        var isChecked = false
        if (view is Checkable) {
            isChecked = view.isChecked
        }
        lockControl.enableWakelock(view.context, isChecked)
    }

    fun showAboutDialog(fm: FragmentManager) {
        AboutFragment().show(fm, DIAGLOG_TAG)
    }

    override fun onLockChanged(control: LockControl, newValue: Boolean) {
        model.wakeLocked.set(control.isAcquired)
    }
}

