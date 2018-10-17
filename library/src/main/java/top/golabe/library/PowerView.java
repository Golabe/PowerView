package top.golabe.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PowerView extends View {
    private static final String TAG = "PowerView";
    private Context mContext;
    public static final int ORIENTATION_VERTICAL = 0;
    public static final int ORIENTATION_HORIZONTAL = 1;

    private static final int DEFAULT_POWER_MAX = 100;
    private static final int DEFAULT_POWER_MIN = 0;
    private final int DEFALUT_WIDTH = 100;
    private final int DEFALUT_HEIGHT = 40;

    private int powerBgColor;
    private int powerProgressColor;
    private int powerMax;
    private int powerMin;
    private int powerProgress;
    private float powerTotalProgress;
    private int orientation ;

    private Path mPowerBoxPath;
    private Path mPowerHeadPath;

    private Paint mPowerBgPaint;
    private Paint mPowerPaint;

    private RectF mPowerRectF;
    private RectF mHeadRectF;
    private int powerBoxH;
    private int powerBoxW;


    public PowerView(Context context) {
        this(context, null);
    }

    public PowerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
        init();
    }

    private void init() {
        mPowerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPowerPaint.setColor(powerProgressColor);
        mPowerPaint.setStyle(Paint.Style.FILL);

        mPowerBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPowerBgPaint.setColor(powerBgColor);
        mPowerBgPaint.setStyle(Paint.Style.FILL);

        mPowerBoxPath = new Path();
        mPowerHeadPath = new Path();
        mPowerRectF = new RectF();
        mHeadRectF = new RectF();

    }

    private int dp2px(float v) {
        float v1 = mContext.getResources().getDisplayMetrics().density;
        return (int) (v * v1 + 0.5F);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.PowerView);
            powerBgColor = a.getColor(R.styleable.PowerView_power_bg_color, mContext.getResources().getColor(R.color.default_power_bg_color));
            powerProgressColor = a.getColor(R.styleable.PowerView_power_progress_color, mContext.getResources().getColor(R.color.default_power_progress_color));
            powerProgress = a.getInteger(R.styleable.PowerView_power_progress, 0);
            powerMax = a.getInteger(R.styleable.PowerView_power_max, DEFAULT_POWER_MAX);
            powerMin = a.getInteger(R.styleable.PowerView_power_min, DEFAULT_POWER_MIN);
            powerTotalProgress = Math.abs(powerMax - powerMin);
            orientation=a.getInteger(R.styleable.PowerView_orientation, ORIENTATION_HORIZONTAL);
            checkProgress();
            a.recycle();
        }
    }

    private void checkProgress() {
        if (powerProgress < 0) {
            powerProgress = 0;
        } else if (powerProgress > 100) {
            powerProgress = 100;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wModel = MeasureSpec.getMode(widthMeasureSpec);
        int hModel = MeasureSpec.getMode(heightMeasureSpec);
        int w, h;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heigth = MeasureSpec.getSize(heightMeasureSpec);
        if (orientation == ORIENTATION_HORIZONTAL) {
            if (wModel == MeasureSpec.EXACTLY && hModel == MeasureSpec.EXACTLY) {
                w = width;
                h = heigth;
            } else {
                w = dp2px(DEFALUT_WIDTH);
                h = dp2px(DEFALUT_HEIGHT);
            }
        } else {
            if (wModel == MeasureSpec.EXACTLY && hModel == MeasureSpec.EXACTLY) {
                w = heigth;
                h = width;
            } else {
                w = dp2px(DEFALUT_HEIGHT);
                h = dp2px(DEFALUT_WIDTH);
            }
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPowerBoxPath.reset();
        mPowerHeadPath.reset();
        if (orientation == ORIENTATION_HORIZONTAL) {
            powerBoxW = 19 * w / 20;
            powerBoxH = h;
            int powerRadius = Math.min(w, h) / 10;
            mPowerBoxPath.addRoundRect(new RectF(0F, 0F, powerBoxW, powerBoxH), powerRadius, powerRadius, Path.Direction.CW);

            float gap = (w - powerBoxW) * 0.2F;
            float headW = (w - powerBoxW) * 0.8F;
            int headH = h / 6;
            float headRadius = headW * 2 / 3;
            mHeadRectF.set(powerBoxW + gap, h / 2F - headH, w, h / 2F + headH);
            mPowerHeadPath.addRoundRect(mHeadRectF, new float[]{0, 0, headRadius, headRadius, headRadius, headRadius, 0, 0}, Path.Direction.CW);

        } else {
            powerBoxW=w;
            powerBoxH=h*19/20;
            int powerRadius = Math.min(w, h) / 10;
            float gap = (h - powerBoxH) * 0.2F;

            mPowerBoxPath.addRoundRect(new RectF(0F, gap+h/20, powerBoxW, powerBoxH), powerRadius, powerRadius, Path.Direction.CW);

            float headH = (h - powerBoxH) * 0.8F;
            int headW= h / 6;
            float headRadius = headH * 2 / 3;
            mHeadRectF.set(powerBoxW/2-headW/2,0,powerBoxW/2+headW/2,headH);
            mPowerHeadPath.addRoundRect(mHeadRectF,new float[]{headRadius,headRadius,headRadius,headRadius,0,0,0,0},Path.Direction.CW);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (orientation == ORIENTATION_HORIZONTAL) {
            drawHorizontal(canvas);
        } else {
            drawVertical(canvas);
        }
    }

    private void drawVertical(Canvas canvas) {
        canvas.drawPath(mPowerHeadPath, mPowerBgPaint);
        canvas.save();
        canvas.clipPath(mPowerBoxPath);
        canvas.drawPath(mPowerBoxPath, mPowerBgPaint);
        mPowerRectF.set(0,powerBoxH-(powerBoxH/powerTotalProgress*powerProgress),powerBoxW , powerBoxH);
        canvas.drawRect(mPowerRectF, mPowerPaint);
        canvas.restore();
    }


    private void drawHorizontal(Canvas canvas) {
        canvas.drawPath(mPowerHeadPath, mPowerBgPaint);
        canvas.save();
        canvas.clipPath(mPowerBoxPath);
        canvas.drawPath(mPowerBoxPath, mPowerBgPaint);
        mPowerRectF.set(0, 0,powerBoxW/powerTotalProgress*powerProgress , powerBoxH);
        canvas.drawRect(mPowerRectF, mPowerPaint);
        canvas.restore();
    }

    public int getPowerBgColor() {
        return powerBgColor;
    }

    public void setPowerBgColor(int powerBgColor) {
        this.powerBgColor = powerBgColor;
        mPowerBgPaint.setColor(powerBgColor);
    }

    public int getPowerProgressColor() {
        return powerProgressColor;
    }

    public void setPowerProgressColor(int powerProgressColor) {
        this.powerProgressColor = powerProgressColor;
        mPowerPaint.setColor(powerProgressColor);
    }

    public int getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(int powerMax) {
        this.powerMax = powerMax;
        invalidate();
    }

    public int getPowerMin() {
        return powerMin;
    }

    public void setPowerMin(int powerMin) {
        this.powerMin = powerMin;
        invalidate();
    }

    public int getPowerProgress() {
        return powerProgress;
    }

    public void setPowerProgress(int powerProgress) {
        this.powerProgress = powerProgress;
        checkProgress();
        invalidate();
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        invalidate();
    }
}
