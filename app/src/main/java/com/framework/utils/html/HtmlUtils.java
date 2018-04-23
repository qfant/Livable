package com.framework.utils.html;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import static com.alipay.sdk.app.statistic.c.s;

/**
 * Created by shucheng.qu on 2017/9/21.
 */

public class HtmlUtils {

    public static void getHtml(Context context, TextView textView, String string) {
        string = htmlEncode(string);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
        textView.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页//click must
        Spanned spanned = Html.fromHtml(string, new URLImageGetter(textView, context), new URLTagHandler(context));
        textView.setText(spanned);
    }

    private static String htmlEncode(String s) {
        return s.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&#39;", "\'").replaceAll("&quot;", "\"");
    }

}
