package com.atguigu.maxwu.selfcustomerviewexer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 作者: WuKai
 * 时间: 2017/5/18
 * 邮箱: 648838173@qq.com
 * 作用:
 */

public class IdentityImageView extends ViewGroup {

    private int progressColor;
    private CircleImageView bigImageView;
    private CircleImageView smallImageView;
    private boolean isProgressbar = false;
    private float angle = 45;
    private int borderWidth;
    private boolean hintSmallImage;
    private Context mContext;
    private int totalWitdh;
    private TextView tv_vip;
    private Paint mBorderPaint;
    private Paint mProgressbarPaint;
    private float progress;
    private int radius;
    private int smallRadius;
    private float radiusScale;
    private int borderColor;
    private Drawable bigImage;
    private Drawable smallImage;
    private int setprogressColor = 0;


    public IdentityImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IdentityImageView(Context context) {
        this(context, null);
    }

    public IdentityImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setWillNotDraw(false);//使得onDraw方法执行

        addThreeView();
        initAttr(attrs);

    }

    private void addThreeView() {
        bigImageView = new CircleImageView(mContext);
        smallImageView = new CircleImageView(mContext);
        tv_vip = new TextView(mContext);
        tv_vip.setGravity(Gravity.CENTER);
        addView(bigImageView, 0, new LayoutParams(radius, radius));
        addView(smallImageView, 1, new LayoutParams(smallRadius, smallRadius));
        addView(tv_vip, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        smallImageView.bringToFront();
    }

    private void initAttr(AttributeSet attrs) {
        TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.IdentityImageView);
        bigImage = tta.getDrawable(R.styleable.IdentityImageView_iciv_bigImage);
        smallImage = tta.getDrawable(R.styleable.IdentityImageView_iciv_smallImage);
        angle = tta.getFloat(R.styleable.IdentityImageView_angle, 45);
        isProgressbar = tta.getBoolean(R.styleable.IdentityImageView_isProgressbar, false);
        radiusScale = tta.getFloat(R.styleable.IdentityImageView_radiusScale, 0.28f);
        hintSmallImage = tta.getBoolean(R.styleable.IdentityImageView_hint_smallImage, false);
        borderColor = tta.getColor(R.styleable.IdentityImageView_border_color, 0);
        borderWidth = tta.getInt(R.styleable.IdentityImageView_border_width, 0);
        progressColor = tta.getColor(R.styleable.IdentityImageView_progressbar_color, 0);
        if (hintSmallImage) {
            smallImageView.setVisibility(GONE);
        }
        if (bigImageView != null) {
            bigImageView.setImageDrawable(bigImage);
        }
        if (smallImageView != null) {
            smallImageView.setImageDrawable(smallImage);
        }
    }

    private void initPaint() {
        if (mProgressbarPaint == null) {
            mProgressbarPaint = new Paint();
            mProgressbarPaint.setStyle(Paint.Style.STROKE);
            mProgressbarPaint.setAntiAlias(true);
        }
        if (setprogressColor != 0) {
            mProgressbarPaint.setColor(getResources().getColor(setprogressColor));
        } else {
            mProgressbarPaint.setColor(progressColor);
        }
        mProgressbarPaint.setStrokeWidth(borderWidth);

        if (mBorderPaint == null) {
            mBorderPaint = new Paint();
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setAntiAlias(true);
        }
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStrokeWidth(borderWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int viewWidtht = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeightt = MeasureSpec.getSize(heightMeasureSpec);
        switch (viewWidthMode) {
            case MeasureSpec.EXACTLY://高度为指定的或者match_parent
                totalWitdh = viewWidtht < viewHeightt ? viewWidtht : viewHeightt;
                float scale = 1 + radiusScale;
                int radius2 = totalWitdh / 2;
                radius = (int) (radius2 / scale);
                break;
            case MeasureSpec.AT_MOST:
                radius = 200;
                totalWitdh = (int) ((radius + radius * radiusScale) * 2);
                break;
            default:
                radius = 200;
                totalWitdh = (int) ((radius + radius * radiusScale) * 2);
        }
        setMeasuredDimension(totalWitdh, totalWitdh);
        adjustThreeView();
    }

    private void adjustThreeView() {
        bigImageView.setLayoutParams(new LayoutParams(radius, radius));
        smallRadius = (int) (radius * radiusScale);
        smallImageView.setLayoutParams(new LayoutParams(smallRadius, smallRadius));
        tv_vip.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        double cos = Math.cos(Math.PI * angle / 180);
        double sin = Math.sin(Math.PI * angle / 180);
        int left = (int) (totalWitdh / 2 + (radius * cos - smallRadius));
        int top = (int) (totalWitdh / 2 + (radius * sin - smallRadius));
        int right =  (left + 2 * smallRadius);
        int bottom =  (top + 2 * smallRadius);
        smallImageView.layout(left, top, right, bottom);
        tv_vip.layout(left, top, right, bottom);
        bigImageView.layout(smallRadius + borderWidth / 2, smallRadius + borderWidth / 2, totalWitdh - borderWidth / 2 - smallRadius, totalWitdh - borderWidth / 2 - smallRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        if (isProgressbar) {
            RectF rectF = new RectF(smallRadius + borderWidth / 2, smallRadius + borderWidth / 2, getWidth() - smallRadius - borderWidth / 2, getHeight() - smallRadius - borderWidth / 2);
//            RectF rectF = new RectF(smallRadius + borderWidth / 2, smallRadius + borderWidth / 2, getWidth() , getHeight());
            canvas.drawArc(rectF, angle, progress, false, mProgressbarPaint);
        }
        if (borderWidth > 0) {
            canvas.drawCircle(totalWitdh / 2, totalWitdh / 2, radius - borderWidth / 2, mBorderPaint);
        }
    }

    public CircleImageView getBigImageView() {
        if (bigImageView != null) {
            return bigImageView;
        } else return null;
    }

    public CircleImageView getSmallImageView() {
        if (smallImageView != null) {
            return smallImageView;
        } else return null;
    }

    public void setProgress(float progresss) {
        if (progress == progresss) {
            return;
        }
        progress = progresss;
        Log.e("TAG", "progress--" + progress);
        requestLayout();
        invalidate();
    }

    public void setAngle(int angles) {
        if (angles == angle) {
            return;
        }
        angle = angles;
        requestLayout();
        invalidate();
    }

    public void setRadiusScale(float v) {
        if (v == radiusScale) return;
        radiusScale = v;
        requestLayout();
        invalidate();

    }

    /**
     * 设置填充的颜色
     *
     * @param color 边框颜色
     */
    public void setBorderColor(int color) {
        if (color == borderColor) return;
        borderColor = color;
        requestLayout();
        invalidate();

    }

    /**
     * 设置进度条颜色
     *
     * @param color 进度条颜色
     */
    public void setProgressColor(int color) {
        if (color == progressColor) return;
        setprogressColor = color;
        requestLayout();
        invalidate();
    }

    public void setIsProgressbar(boolean progressbar) {
        if (this.isProgressbar == progressbar) {
            return;
        }
        this.isProgressbar = progressbar;
        requestLayout();
        invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        if (this.borderWidth == borderWidth) {
            return;
        }
        this.borderWidth = borderWidth;
        requestLayout();
        invalidate();
    }
}
