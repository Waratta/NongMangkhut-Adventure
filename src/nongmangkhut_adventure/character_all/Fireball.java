package nongmangkhut_adventure.character_all;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Fireball implements Runnable {

    private int x, y;
    private int width, height; 
    private int distanceTravelled; 
    private static final int SPEED = 2; 
    private boolean isVisible; 
    private boolean direction; 
    private BufferedImage[] frames; 
    private int frameIndex; 
    private boolean running; 
    private int damage;
    private Character character;

    private Thread thread;

    public Fireball(int startX, int startY, boolean direction, Character character) {
        this.x = startX;
        this.y = startY;
        this.width = 32; 
        this.height = 32;
        this.isVisible = true; 
        this.direction = direction; 
        this.distanceTravelled = 0; 
        this.damage = 10 + character.getAttackPower();
        this.character = character;
     
        frames = new BufferedImage[4];
        try {
            BufferedImage fireballSpritesheet = ImageIO.read(getClass().getResource("../character/Magical_Orbs_Spell_(32 x 32).png"));
            for (int i = 0; i < 4; i++) {
                frames[i] = fireballSpritesheet.getSubimage(i * 32, 0, 32, 32); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        frameIndex = 0;
        running = true;
        thread = new Thread(this); 
        thread.start(); 
    }

    @Override
    public void run() {
        while (isVisible && running) {
            update(); 
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (isVisible) {
          
            x += (direction ? SPEED : -SPEED); 

        
            frameIndex = (frameIndex + 1) % frames.length;
          
            if (distanceTravelled > 500) { 
                isVisible = false;
            }
        }
    }
    // Fireball.java

    public void increaseDamage(int amount) {
        this.damage += amount; 
    }

    public void draw(Graphics g) {
        if (isVisible) {
            g.drawImage(frames[frameIndex], x, y, width, height, null);

        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void stop() {
        running = false;
        try {
            thread.join(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getDamage() {
        System.out.println("Damage: " + damage);
        return damage; 
    }

    public void addDamage(int damage) {
        if (character != null) {
            character.addScore(damage); 
        } else {
            System.out.println("Character is not set!");
        }
    }

    public boolean checkCollision(Damageable enemy) {
        Rectangle fireballRect = new Rectangle(x, y, width, height);
        Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        if (fireballRect.intersects(enemyRect)) {
            enemy.takeDamage(damage);
            isVisible = false; 
            return true; 
        }
        return false; 
    }
}
