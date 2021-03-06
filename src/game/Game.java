package game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Game {
	private float time;
	private boolean isActive = false;
	private Atmosphere sky = new Atmosphere();
	public static Environment land = new Environment();
	public static GUI gui;
	
	public Game(Context context){
		gui = new GUI(context);
	}
	public void draw(Canvas canvas, Paint paint){
		if (isActive){
			//draw game components
			sky.draw(canvas, paint);
			land.draw(canvas, paint);
			gui.draw(canvas, paint);
		}
	}
	public void update(double mod){
		if (isActive){
			if (time > 1440) time %= 1440;
			else time += (mod*25);
			//update game components
			sky.update(mod, time);
			land.update(mod);
			gui.update(mod);
		}
	}
	public void setActivity(boolean isActive){ this.isActive=isActive; }
	public void down(int x, int y){
		if (isActive){
			land.down(x, y);
			gui.down(x, y);
		}
	}
	public void move(int x, int y){
		if (isActive){
			land.move(x, y);
			gui.move(x, y);
		}
	}
	public void up(int x, int y){
		if (isActive){
			land.up(x, y);
			gui.up(x, y);
		}
	}
	public float getTime(){ return time; }
	public int getMainX(){ return land.getMainX(); }
}
