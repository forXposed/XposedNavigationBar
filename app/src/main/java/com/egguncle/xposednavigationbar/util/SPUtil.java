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

package com.egguncle.xposednavigationbar.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.egguncle.xposednavigationbar.constant.ConstantStr;
import com.egguncle.xposednavigationbar.model.ShortCut;
import com.egguncle.xposednavigationbar.model.ShortCutData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egguncle on 17-6-4.
 * XP框架中似乎不好实现数据库存储，所以用一个SharedPreferences来存储数据
 */

public class SPUtil {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SPUtil instance;
    private static final String SP_NAME = "XposedNavigationBar";
    private static final String TAPS_APPEAR = "taps";
    //在原导航键上添加一个小点，点击后出现扩展的部分
    private static final String HOME_POINT = "home_point";
    private static final String LANGUAGE = "LANGUAGE";
    public static final String LANGUAGE_CHINESE = "zh";
    public static final String LANGUAGE_ENGLICH = "en";
    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int DISMISS = 2;
    private static final String CLEAR_MEM_LEVEL = "clear_mem_level";
    private static final String ICON_SIZE = "icon_size";
    private static final String HookHorizontal = "hook_horizontal";
    public static final String ROOT_DOWN = "root_down";

    //以json形式存储app的设置信息
    private static final String SHORT_CUT_DATA = "short_cut_data";


    private SPUtil() {

    }

    public static SPUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SPUtil.class) {
                instance = new SPUtil();
                //由于xp模块需要读取sp的内容，所以将sp的类型设置为MODE_WORLD_READABLE,但是7.0上不允许使用这个模式，
                //而且考虑到很多用户是旧版升级上来的，所以这个地方根据系统版本做一个判定
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    mSharedPreferences = context.getSharedPreferences(SP_NAME, Activity.MODE_WORLD_READABLE);
                } else {
                    mSharedPreferences = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
                }
                mEditor = mSharedPreferences.edit();
            }
        }
        return instance;
    }


    /**
     * 存储app快捷方式信息
     *
     * @param list
     */
    public void saveShortCut(ArrayList<ShortCut> list) {
        ShortCutData data = new ShortCutData();
        data.setData(list);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        mEditor.putString(SHORT_CUT_DATA, json);
        mEditor.commit();
    }


    /**
     * 从sp的data标签里获得所有app快捷方式的存储信息
     *
     * @return
     */
    public ArrayList<ShortCut> getAllShortCutData() {
        Gson gson = new Gson();
        ArrayList<ShortCut> list = new ArrayList<>();
        String data = mSharedPreferences.getString(SHORT_CUT_DATA, "");
        if ("".equals(data)) {
            return null;
        }
        List<ShortCut> saveData = gson.fromJson(data, ShortCutData.class).getData();
        list.addAll(saveData);
        return list;
    }

    /**
     * 在打开设置界面的时候会有一个对话框提示，如果点击不再提示，则不再显示
     */
    public void nolongerTaps() {
        mEditor.putBoolean(TAPS_APPEAR, false);
        mEditor.commit();
    }

    public boolean getTapsStatus() {
        return mSharedPreferences.getBoolean(TAPS_APPEAR, true);
    }

    /**
     * 设置主导航栏上小点的位置 左 右 或者不显示
     *
     * @param position
     */
    public void setHomePointPosition(int position) {
        mEditor.putInt(HOME_POINT, position);
        mEditor.commit();
    }

    public int getHomePointPosition() {
        try{
            return mSharedPreferences.getInt(HOME_POINT, LEFT);
        }catch (Exception e){
            return LEFT;
        }
    }


    /**
     * 设置内存清理等级
     *
     * @param level
     */
    public void setClearMemLevel(int level) {
        mEditor.putInt(CLEAR_MEM_LEVEL, level);
        mEditor.commit();
    }

    public int getClearMemLevel() {
        return mSharedPreferences.getInt(CLEAR_MEM_LEVEL, ConstantStr.IMPORTANCE_VISIBLE);
    }

    /**
     * 设置图标大小
     *
     * @param
     */
    public void setIconSize(int size) {
        mEditor.putInt(ICON_SIZE, size);
        mEditor.commit();
    }

    public int getIconSize() {
        return mSharedPreferences.getInt(ICON_SIZE, 50);
    }

    public void setLanguage(String language) {
        mEditor.putString(LANGUAGE, language);
        mEditor.commit();
    }

    public String getLanguage() {
        return mSharedPreferences.getString(LANGUAGE, "");
    }

    public void setHookHorizontal(boolean isHook) {
        mEditor.putBoolean(HookHorizontal, isHook);
        mEditor.commit();
    }

    public boolean getHookHorizontal() {
        return mSharedPreferences.getBoolean(HookHorizontal, false);
    }

    public void setRootDown(boolean b) {
        mEditor.putBoolean(ROOT_DOWN, b);
        mEditor.commit();
    }

    public boolean getRootDown() {
        return mSharedPreferences.getBoolean(ROOT_DOWN, false);
    }
}
