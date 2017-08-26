/*
 *     Navigation bar function expansion module
 *     Copyright (C) 2017 egguncle cicadashadow@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.egguncle.xposednavigationbar.hook.util;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by egguncle on 17-8-23.
 */

public class XpLog {

    private final static String TAG="XpNavbar";

    public static void i(String logContent){
        XposedBridge.log("[I/" + TAG + "] "+logContent);
    }

    public static void e(String logContent){
        XposedBridge.log("[E/" + TAG + "] "+logContent);
    }

    public static void e(Throwable e){
        XposedBridge.log("[E/" + TAG + "] "+e.getMessage());
    }

    public static void w(String logContent){
        XposedBridge.log("[W/" + TAG + "] "+logContent);
    }

}
