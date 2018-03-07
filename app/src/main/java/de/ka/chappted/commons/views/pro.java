package de.ka.chappted.commons.views;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import de.ka.chappted.R;

public class pro extends RelativeLayout {


    public pro(Context context) {
        super(context);
    }

    public pro(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public pro(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public interface ChallengeListener{

        void onChallengClicked();

        void onRetryClicked();

    }



    public enum Type {
        LOADING(R.layout.layout_item_loading),
        NO_CONNECTION(R.layout.layout_item_challenge),
        NO_LOCATION_PERMISSION(R.layout.layout_item_challenge),
        DEFAULT(R.layout.layout_item_challenge),
        HEADER(R.layout.layout_item_challenge);

        @LayoutRes
        private int mLayoutResId;

        Type(@LayoutRes int layoutResId) {
            this.mLayoutResId = layoutResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }
    }
}
