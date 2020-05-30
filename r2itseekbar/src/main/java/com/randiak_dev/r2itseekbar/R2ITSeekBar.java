package com.randiak_dev.r2itseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class R2ITSeekBar extends LinearLayout {
    private TextView tvTitle;
    private String title;
    private SeekBar seekBar;
    private int min;
    private static final int DEF_MIN = -100;
    private int max;
    private static final int DEF_MAX = 100;
    private TextView tvMin;
    private TextView tvValue;
    private TextView tvMax;

    private CustomSeekBarListener listener;

    public R2ITSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.r2it_seek_bar, this, true);

        LinearLayout ll0 = (LinearLayout) this.getChildAt(0);
        LinearLayout llSeekBar = (LinearLayout) ll0.getChildAt(1);
        LinearLayout llValues = (LinearLayout) llSeekBar.getChildAt(1);

        this.tvTitle = (TextView) ll0.getChildAt(0);
        this.seekBar = (SeekBar) llSeekBar.getChildAt(0);
        this.tvMin = (TextView) llValues.getChildAt(0);
        this.tvValue = (TextView) llValues.getChildAt(1);
        this.tvMax = (TextView) llValues.getChildAt(2);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.R2ITSeekBar, 0, 0);
        try {
            this.setTitle(typedArray.getString(R.styleable.R2ITSeekBar_title));
            this.setMin(typedArray.getInteger(R.styleable.R2ITSeekBar_min, DEF_MIN));
            this.setMax(typedArray.getInteger(R.styleable.R2ITSeekBar_max, DEF_MAX));
            this.setDefault(typedArray.getInteger(R.styleable.R2ITSeekBar_def, DEF_MIN));

        } finally {
            typedArray.recycle();
        }

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValue.setText(String.valueOf(progress + min));
                if (listener != null) {
                    listener.onProgressChanged(progress + min);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public R2ITSeekBar(Context context) {
        super(context);
    }

    public void setTitle(String title) {
        this.title = title;
        this.tvTitle.setText(this.title);
    }

    public void setMin(int min) {
        this.min = min;
        this.tvMin.setText(String.valueOf(this.min));
        this.updateSeekBar();
    }

    public void setMax(int max) {
        this.max = max;
        this.tvMax.setText(String.valueOf(this.max));
        this.updateSeekBar();
    }

    private void setDefault(int def) {
        if (def < this.min) {
            this.setProgress(this.min);
        } else if (this.max < def) {
            this.setProgress(this.max);
        } else {
            this.setProgress(def);
        }
    }

    public void setProgress(int progress) {
        if (this.min <= progress && progress <= this.max)
            this.seekBar.setProgress(progress - this.min);
        this.tvValue.setText(String.valueOf(progress));
    }

    public int getProgress() {
        return this.min + seekBar.getProgress();
    }

    public void setCustomSeekBarListener(CustomSeekBarListener listener) {
        this.listener = listener;
    }

    private void updateSeekBar() {
        this.seekBar.setMax(this.max - this.min);
    }

    public interface CustomSeekBarListener {
        public void onProgressChanged(int progress);
    }
}

