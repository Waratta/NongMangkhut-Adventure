package nongmangkhut_adventure.character_all;

public interface Damageable {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void takeDamage(int damage);
}