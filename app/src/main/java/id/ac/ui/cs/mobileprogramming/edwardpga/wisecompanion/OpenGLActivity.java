package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.content.Context;
import android.os.Handler;

public class OpenGLActivity extends Activity {
    Handler handler;
    private GLSurfaceView gLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = new MyGLSurfaceView(this);
        setContentView(gLView);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(OpenGLActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);

    }

    class MyGLSurfaceView extends GLSurfaceView {

        private final MyGLRenderer renderer;

        public MyGLSurfaceView(Context context){
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            renderer = new MyGLRenderer();

            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(renderer);
        }
    }
}