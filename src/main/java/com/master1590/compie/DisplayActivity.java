package com.master1590.compie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;

public class DisplayActivity extends AppCompatActivity {

    private HashMap<Integer, Point> triangle_points;
    int canvasWidth;
    int canvasHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //retrieve triangle points
        triangle_points=(HashMap<Integer, Point>) getIntent().getSerializableExtra("triangle_values");

        if(triangle_points==null){
            Toast.makeText(DisplayActivity.this,
                    getResources().getString(R.string.bad_values),
                    Toast.LENGTH_LONG).show();
            finish();
        }
        //if values ok - draw
        drawTriangle();
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void drawTriangle(){
        canvasWidth = (int) getResources().getDimension(R.dimen.drawing_zone_width);
        canvasWidth = pxToDp(canvasWidth);
        canvasHeight =(int) getResources().getDimension(R.dimen.drawing_zone_height);
        canvasHeight = pxToDp(canvasHeight);
        setContentView( new CanvasView(this));
    }

    public class CanvasView extends View {
        Context context;
        public CanvasView(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //set canvas size - defined as 600dp
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = canvasHeight;
            lp.width = canvasWidth;
            setLayoutParams(lp);
            //draw the triangle
            Paint paint_triangle = new Paint();

            paint_triangle.setColor(getColor(R.color.colorPrimaryDark));
            canvas.drawPaint(paint_triangle);

            paint_triangle.setStrokeWidth(6);
            paint_triangle.setColor(getColor(R.color.colorAccent));
            paint_triangle.setStyle(Paint.Style.STROKE);
            paint_triangle.setAntiAlias(true);

            Point a = triangle_points.get(1);
            Point b = triangle_points.get(2);
            Point c = triangle_points.get(3);

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.lineTo(b.getX(), b.getY());
            path.lineTo(c.getX(), c.getY());
            path.lineTo(a.getX(), a.getY());
            path.close();

            canvas.drawPath(path, paint_triangle);

            //calculate and draw the angle in degrees
            double  signed_angle = calcAngle(a,c,b);
            Paint paint1_degrees = new Paint();
            canvas.drawPaint(paint_triangle);
            paint_triangle.setColor(Color.BLACK);
            paint_triangle.setTextSize(45);
            canvas.drawText(String.valueOf(signed_angle), a.getX()+20, a.getY(), paint1_degrees);

            signed_angle = calcAngle(b,a,c);
            Paint paint2_degrees = new Paint();
            canvas.drawPaint(paint_triangle);
            paint_triangle.setColor(Color.BLACK);
            paint_triangle.setTextSize(45);
            canvas.drawText(String.valueOf(signed_angle), b.getX()+20, b.getY(), paint2_degrees);

            signed_angle = calcAngle(c,a,b);
            Paint paint3_degrees = new Paint();
            canvas.drawPaint(paint_triangle);
            paint_triangle.setColor(Color.BLACK);
            paint_triangle.setTextSize(45);
            canvas.drawText(String.valueOf(signed_angle), c.getX()+20, c.getY(), paint3_degrees);

        }
    }

    private int calcAngle(Point center, Point point1, Point point2) {
        return Math.abs((int)Math.toDegrees(Math.atan2(point1.getX() - center.getX(),point1.getY() - center.getY())-
                Math.atan2(point2.getX()- center.getX(),point2.getY()- center.getY())));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
