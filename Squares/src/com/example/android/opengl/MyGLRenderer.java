package com.example.android.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Square[][] mSquare = new Square[10][3];// =new ArrayList<Square>();
    private Square[][] markSquare = new Square[9][9];
    private Square scoreSquare;
    private boolean[][] markedSquares = new boolean[9][9];
    private float mX = 2;
    private float mY = 2;
    private float mWidth;
    private float mHeight;
    private Board board;
    private int player1score;
    private int player2score;
    private boolean currentPlayer;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);

        board = new Board(9,9);
        
        scoreSquare = new Square(-0.05f, 0.7f ,0.1f, 0.1f);
        
        for(int i=0; i<9; i++) {
        		for(int j=0; j<9; j++) {
        			markSquare[i][j] = new Square(-0.7f + i*1.4f/9, 0.4f- j*1.4f/9, 1.4f/9f, -1.4f/9f);
        		}
        }
        
        for(int i=0; i < 10; i++) {
        	mSquare[i][0] = new Square(-0.7f, 0.4f - i*1.4f/9, 1.4f, 0.01f);
        	mSquare[i][1] = new Square(-0.7f + i*1.39f/9, -1.0f, 0.01f, 1.4f);
        }
               
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Draw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();   // reset the matrix to its default state

        // When using GL_MODELVIEW, you must set the view point
        GLU.gluLookAt(gl, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //mX=0.69f;
        for(int i = 0; i<9;i++)
        	for(int j=0; j<9;j++)
        		if(mX > -0.7 + + i*1.39f/9 && mX < -0.7f + 1.39f/9+ i*1.39f/9)
        			if(mY < 0.4f- j*1.4f/9 && mY > 0.4f - 1.4f/9 - j*1.4f/9){
        				if(markedSquares[i][j] == false)
							try {
								if(currentPlayer) { 
									player1score += board.put(i,j);
									currentPlayer = false;
								}
								else {
									player2score += board.put(i, j);
									currentPlayer = true;
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        				markedSquares[i][j] = true;
        			}
        
        for(int i=0; i<9; i++) {
    		for(int j=0; j<9; j++) {
    			if(markedSquares[i][j] == true)
    				gl.glColor4f(0.2f, 0.2f, 0.898039216f, 1.0f);
    			else
    				gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    			markSquare[i][j].draw(gl);
    		}
    	}
        
        gl.glColor4f(0.2f, 0.709803922f, 0.898039216f, 1.0f);
        for(int i=0; i < 10; i++) {
        	mSquare[i][0].draw(gl);
        	mSquare[i][1].draw(gl);
        }
        
        if (player1score-player2score > 0)
        	gl.glColor4f(0.98f, 0.98f, 0.12f, 1.0f);
        else if (player1score-player2score < 0)
        	gl.glColor4f(0.11f, 0.97f, 0.94f, 1.0f);
        else
        	gl.glColor4f(0.454f, 0.5252f, 0.14523f, 1.0f);
        
        if(player1score-player2score<50)
        	gl.glTranslatef((player1score-player2score)/70f, 0f, 0.0f);
        else
        	gl.glTranslatef(50/70f, 0f, 0.0f);
        scoreSquare.draw(gl);
      
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Adjust the viewport based on geometry changes
        // such as screen rotations
        gl.glViewport(0, 0, width, height);
        
        mWidth = width;
        mHeight =height;

        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
    }
    

    public void setX(float x) {
    	mX = 0.7f-1.4f*x/mWidth;
    }
    
    public void setY(float y) {
    	mY = 1-2*y/mHeight;
    }


}