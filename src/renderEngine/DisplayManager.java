package renderEngine;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	private static long lastFrameTime;
	private static float delta;
		
	private static JFrame metricFrame;
	private static JPanel metricPanel;
	private static JLabel fpsLabel;
	private static int count = 0;
	private static int fps = 0;
	
	public static void createDisplay(){
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		lastFrameTime = getCurrentTime();
	}
	
	public static void createMetricsDisplay(){
		metricFrame = new JFrame();
		metricFrame.setSize(new Dimension(300, 300));
		metricFrame.setVisible(true);
		metricPanel = new JPanel();
		metricFrame.setContentPane(metricPanel);
		fpsLabel = new JLabel();
		fpsLabel.setLocation(20, 20);
		fpsLabel.setFont(new Font("Arial", Font.BOLD, 30));
		metricPanel.add(fpsLabel);
	}
	
	public static void updateMetricDisplay(){
		if (count > 100){
			fps /= count;
			fpsLabel.setText("FPS: " + String.valueOf(fps));
			fps = 0;
			count =  0;
		}
		fps += (int) (1/(delta/1000));
		count++;
	}
	
	public static void closeMetricDisplay(){
		metricFrame.dispose();
	}
	
	public static void updateDisplay(){
		checkInputs();
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
	}
	
	private static void checkInputs(){
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_TAB)){
			DisplayManager.exitFullScreen();
		}
	}
	
	public static void closeDisplay(){
		Display.destroy();
	}
	
	public static void setFullScreen(){
		try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			Display.setDisplayMode(modes[20]);
			Display.setFullscreen(true);
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void exitFullScreen(){
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setFullscreen(false);
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	private static long getCurrentTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static float getFrameTimeSeconds(){
		return delta / 1000f;
	}
	
	public static float getFrameTimeMilliSeconds(){
		return delta;
	}
}
