package bc.liaoningu.Utils;

import android.content.Context;

/**
 * Created by BC on 2016/10/25 0025.
 */
public class Util {
    public static int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
