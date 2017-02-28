package com.cherish.cdsnapp.Until;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cherish on 2017/2/27.
 */

public class ToastUtil {
    public static void toast(Context context , String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
