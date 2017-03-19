package com.witcher.testagora;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by witcher on 2017/2/27 0027.
 */

public class L {

    public static void i(String str){
            Log.i("witcher",str);
    }
    public static void makeToast(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
