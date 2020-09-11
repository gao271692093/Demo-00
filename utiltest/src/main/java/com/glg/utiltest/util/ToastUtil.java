package com.glg.utiltest.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glg.utiltest.R;

/**
 * Description:通用（主、子线程）的自定义Toast
 *
 * @package: com.glg.utiltest.util
 * @className: ToastUtil
 * @author: gao
 * @date: 2020/9/9 10:23
 */
public class ToastUtil {

    private static final String TAG = "ToastUtil";
    private static Toast toast;


    //如果只想在主线程中弹出自定义toast,则直接调用此方法即可
    public static void showToast(Context context, String titles, String messages) {
        toastProcess(context, titles, messages);
    }

    //如果想在子线程中和子线程中都能使用，则调用此方法即可（前提是在Activity中，因为runOnUiThread属于Activity中的方法）
    public static void showToast1(final Activity context, final String titles, final String messages) {
        if ("main".equals(Thread.currentThread().getName())) {
            toastProcess(context, titles, messages);
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toastProcess(context, titles, messages);
                }
            });
        }
    }

    /**
     * 自定义toast
     *
     * @param context  上下文对象
     * @param titles   toast 标题
     * @param messages toast内容
     */
    private static void toastProcess(Context context, String titles, String messages) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.custom_toast, null);
        ImageView bg = view.findViewById(R.id.toast_bg);
        TextView title = view.findViewById(R.id.toast_title);
        TextView text = view.findViewById(R.id.toast_content);

        bg.setImageResource(R.mipmap.toast_bg);//toast背景
        title.setText(titles); //toast的标题
        text.setText(messages); //toast内容
        if (toast == null) {
            toast = new Toast(context.getApplicationContext());
        }
        toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }

}
