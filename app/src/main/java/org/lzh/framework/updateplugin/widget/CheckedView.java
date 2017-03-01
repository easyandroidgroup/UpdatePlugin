package org.lzh.framework.updateplugin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.lzh.framework.updateplugin.R;

public class CheckedView extends LinearLayout{

    TextView titleTv;
    RadioGroup group;
    RadioButton defRb;
    RadioButton customRb;

    public CheckedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        initViews(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckedView);
        setTitle(ta.getText(R.styleable.CheckedView_title));
        setSubTitle(ta.getText(R.styleable.CheckedView_subtitle));
        ta.recycle();
    }

    public void setTitle (CharSequence title) {
        titleTv.setText(title);
    }

    public void setSubTitle (CharSequence subTitle) {
        customRb.setText(subTitle);
    }

    public boolean isDefaultSelected () {
        return group.getCheckedRadioButtonId() == R.id.def;
    }

    private void initViews(Context context) {
        removeAllViews();
        LayoutInflater.from(context).inflate(R.layout.layout_checked,this);
        titleTv = (TextView) findViewById(R.id.title);
        group = (RadioGroup) findViewById(R.id.group);
        defRb = (RadioButton) findViewById(R.id.def);
        customRb = (RadioButton) findViewById(R.id.custom);
    }

}
