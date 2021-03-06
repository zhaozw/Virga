package buttons;

import textures.SpriteSheet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Vibrator;

public class Button {
	private boolean pressed, hide, center;
	private SpriteSheet sprite;
	private double x, y, xSize, ySize, padding;
	private Vibrator vibrator;
	
	public Button(Bitmap newBitmap, int x, int y, boolean center, Context context){
		SpriteSheet newSprite = new SpriteSheet(newBitmap, 1, 2, 0.0);
		//basic button
		this.x=x;
		this.y=y;
		this.center=center;
		sprite = new SpriteSheet(newSprite.getBitmap(), 
				newSprite.getHFrames(), newSprite.getVFrames(), newSprite.getRate());
		xSize = sprite.getBitWidth();
		ySize = sprite.getBitHeight();
		if (center) sprite.center();
		sprite.update(x, y);
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE) ;
		padding = 12; //adjust to player's fingers
	}
	public void draw(Canvas canvas){
		Paint paint = new Paint();
		if (!hide) canvas.drawBitmap(sprite.getBitmap(), sprite.getSpriteRect(), sprite.getDestRect(), paint);
	}
	public void draw(int fade, Canvas canvas){	
		Paint paint = new Paint();
		paint.setARGB(fade, 255, 255, 255);
		if (!hide) canvas.drawBitmap(sprite.getBitmap(), sprite.getSpriteRect(), sprite.getDestRect(), paint);
	}
	public void resize(int newWidth, int newHeight){
		xSize=newWidth;
		ySize=newHeight;
		sprite.resize(newWidth, newHeight);
		sprite.update(x, y);
	}
	public void resize(int ratio){
		xSize *= ratio;
		ySize *= ratio;
		sprite.resize((int)xSize, (int)ySize);
		sprite.update(x, y);
	}
	public boolean down(int x1, int y1){
		if (!hide){
			if (center){
				if (Math.abs(x1-x) < ((xSize/2)+padding) && Math.abs(y1-y) < ((ySize/2)+padding)){
					if (!pressed) vibrator.vibrate(25);
					pressed = true;
					sprite.animate(1, 0);
				} else { pressed = false; sprite.animate(0, 0); }
			}
			else{
				if (Math.abs(x1-x-(xSize/2)) < ((xSize/2)+padding) && Math.abs(y1-y-(ySize/2)) < ((ySize/2)+padding)){
					if (!pressed) vibrator.vibrate(25);
					pressed = true;
					sprite.animate(1, 0);
				} else { pressed = false; sprite.animate(0, 0); }
			}
		}
		return pressed;
	}
	public boolean move(int x1, int y1){
		if (!hide) down(x1, y1);
		return false;
	}
	public boolean up(int x1, int y1){ 
		if (pressed && !hide){
			sprite.animate(0, 0);
			pressed = false;
			return true;
		}
		else return false; 
	}
	public void setPadding(int padding){ this.padding=padding; }
	public void hide(){ hide = true; }
	public void reveal(){ hide = false; }
	public void update(double x, double y){ 
		this.x=x;
		this.y=y;
		sprite.update(x, y); 
	}
	public boolean isPressed(){ return pressed; }
	public double getX(){ return x; }
	public double getY(){ return y; }
}
