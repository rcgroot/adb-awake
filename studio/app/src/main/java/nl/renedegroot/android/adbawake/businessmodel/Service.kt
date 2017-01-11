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
package nl.renedegroot.android.adbawake.businessmodel

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import nl.renedegroot.android.adbawake.BuildConfig
import nl.renedegroot.android.adbawake.configuration.SharedModel


class Service : NotificationListenerService() {

    private var lockControl: LockControl? = null

    override fun onCreate() {
        super.onCreate()
        lockControl = LockControl(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        lockControl?.enableWakelock(false)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        handleNotification(sbn, true)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        handleNotification(sbn, false)
    }

    private fun handleNotification(sbn: StatusBarNotification?, isAdded: Boolean) {
        if (sbn == null) {
            return
        }
        val enabled = SharedModel.instance.serviceEnabled.get()
        val match = matchesADBNotification(sbn)
        if (enabled && match) {
            lockControl?.enableWakelock(isAdded)
        }
    }

    private fun matchesADBNotification(sbn: StatusBarNotification): Boolean {
        return when (sbn.notification.tickerText) {
            "USB debugging connected", "USB-foutopsporing verbonden" -> true
            else -> false
        }
    }
}
