package nongmangkhut_adventure.L_map;

import nongmangkhut_adventure.character_all.Enemy;
import nongmangkhut_adventure.character_all.Enemy1;
import nongmangkhut_adventure.character_all.Enemy2;
import nongmangkhut_adventure.character_all.Enemy3;
import nongmangkhut_adventure.character_all.Enemy4;
import nongmangkhut_adventure.character_all.Enemy5;
import nongmangkhut_adventure.character_all.Enemy6;
import nongmangkhut_adventure.character_all.Enemy7;
import nongmangkhut_adventure.character_all.Enemy8;
import nongmangkhut_adventure.character_all.Character;
import nongmangkhut_adventure.item_all.HealthItem;
import nongmangkhut_adventure.item_all.Coin;
import nongmangkhut_adventure.item_all.Tree;
import nongmangkhut_adventure.item_all.Tree1;
import nongmangkhut_adventure.item_all.Tree2;
import nongmangkhut_adventure.item_all.SilverCoin; //SilverCoin
import nongmangkhut_adventure.item_all.TreasureChest;
import nongmangkhut_adventure.item_all.ChocolateBar;

import java.util.ArrayList;
import java.util.List;


public class Level3 implements Runnable {

    private List<Enemy> enemies; // List to hold enemies
    private List<HealthItem> healthItems; // List to hold health items
    private List<Coin> coins;
    private List<Tree> tree;
  private List<Tree1> tree1;
  private List<Tree2> tree2;
    private Character mainCharacter; // Main character reference
    private boolean running; // Control flag for the thread
    private List<SilverCoin> silverCoins;
    private List<TreasureChest> treasureChests;
    private List<Enemy1> enemies1;
    private List<Enemy2> enemies2;
    private List<Enemy3> enemies3;
    private List<Enemy4> enemies4;
    private List<Enemy5> enemies5;
    private List<Enemy6> enemies6;
    private List<Enemy7> enemies7;
    private List<Enemy8> enemies8;
    private List<ChocolateBar> chocolateBars;

    public Level3(Character mainCharacter) {
        this.mainCharacter = mainCharacter;
         this.tree = createTree();
         this.tree1 = createTree1();
         this.tree2 = createTree2();
        this.enemies = createEnemies();
        this.enemies1 = createEnemies1();// Initialize enemies with the main character reference
        this.enemies2 = createEnemies2();
        this.enemies3 = createEnemies3();
        this.enemies4 = createEnemies4();
        this.enemies5 = createEnemies5();
        this.enemies6 = createEnemies6();
        this.enemies7 = createEnemies7();
        this.enemies8 = createEnemies8();
        this.healthItems = createHealthItems(); // Initialize health items
        this.coins = createCoins();
        this.silverCoins = createSilverCoins();
        this.treasureChests = createTreasureChests();
        this.chocolateBars = createChocolateBars();
        this.running = true; // Set running to true to start the thread
    
       
      
    }

    
  

    // Layout for the level (can be modified to suit your level design)
    public static int[][] getLayout() {
        return new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0,0, 0, 0, 0,0, 0,},
            {0, 0, 0, 0, 0, 0, 0,0, 0,0, 0, 0,0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, },
            {0,0, 0, 0, 1, 1, 0,0 ,0,0,0,0,0,0,0,0,0},
            {1, 1, 1, 1, 1, 1, 1, 1,0,0,0,0,0,0,0,0,0,0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
    }

    // Initialize health items with specified coordinates
    private List<HealthItem> createHealthItems() {
        List<HealthItem> healthItems = new ArrayList<>();
        //healthItems.add(new HealthItem(800, 920)); // Add health item at (800, 650)
        // Add more health items as needed
        return healthItems;
    }

    // Initialize enemies with positions and main character reference
    private List<Enemy> createEnemies() {
        List<Enemy> enemies = new ArrayList<>();
      //  enemies.add(new Enemy(100, 915, mainCharacter)); // First enemy at (100, 650)
        //  enemies.add(new Enemy(500, 915, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies;
    }

    private List<Enemy1> createEnemies1() {
        List<Enemy1> enemies1 = new ArrayList<>();
        //enemies1.add(new Enemy1(100, 915, mainCharacter)); // First enemy at (100, 650)
        //  enemies1.add(new Enemy1(500, 915, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies1;
    }

    private List<Enemy2> createEnemies2() {
        List<Enemy2> enemies2 = new ArrayList<>();
        //   enemies2.add(new Enemy2(100, 915, mainCharacter)); // First enemy at (100, 650)
        //   enemies2.add(new Enemy2(500, 915, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies2;
    }

    private List<Enemy3> createEnemies3() {
        List<Enemy3> enemies3 = new ArrayList<>();
        //  enemies3.add(new Enemy3(100, 915, mainCharacter)); // First enemy at (100, 650)
        // enemies3.add(new Enemy3(500, 915, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies3;
    }

    private List<Enemy4> createEnemies4() {
        List<Enemy4> enemies4 = new ArrayList<>();
        // enemies4.add(new Enemy4(100, 915, mainCharacter)); // First enemy at (100, 650)
        // enemies4.add(new Enemy4(500, 915, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies4;
    }

    private List<Enemy5> createEnemies5() {
        List<Enemy5> enemies5 = new ArrayList<>();
        // enemies5.add(new Enemy5(100, 915, mainCharacter)); // First enemy at (100, 650)
        // enemies5.add(new Enemy5(500, 915, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies5;
    }

    private List<Enemy6> createEnemies6() {
        List<Enemy6> enemies6 = new ArrayList<>();

        // enemies6.add(new Enemy6(500, 850, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies6;
    }

    private List<Enemy7> createEnemies7() {
        List<Enemy7> enemies7 = new ArrayList<>();
      //  enemies7.add(new Enemy7(100, 860, mainCharacter)); // First enemy at (100, 650)

        // Add more enemies as needed
        return enemies7;
    }

    private List<Enemy8> createEnemies8() {
        List<Enemy8> enemies8 = new ArrayList<>();

        // enemies8.add(new Enemy8(500, 850, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return enemies8;
    }
     private List<Tree> createTree() {
        List<Tree> tree = new ArrayList<>();

       tree.add(new Tree(600, 940, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return tree;
    }
     
    private List<Tree1> createTree1() {
        List<Tree1> tree1 = new ArrayList<>();

        tree1.add(new Tree1(700, 940, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return tree1;
    }
   private List<Tree2> createTree2() {
        List<Tree2> tree2 = new ArrayList<>();

       // tree2.add(new Tree2(800, 700, mainCharacter)); // Second enemy at (500, 650)
        // Add more enemies as needed
        return tree2;
    }

    private List<Coin> createCoins() {
        List<Coin> coins = new ArrayList<>();
       // coins.add(new Coin(400, 920)); // Add a coin at (400, 600)
       // coins.add(new Coin(700, 920)); // Add another coin at (700, 600)
       // coins.add(new Coin(740, 920));
       // coins.add(new Coin(780, 920));
        // Add more coins as needed
        return coins;
    }

    private List<SilverCoin> createSilverCoins() {
        List<SilverCoin> silverCoins = new ArrayList<>();
       // silverCoins.add(new SilverCoin(800, 920)); 
       // silverCoins.add(new SilverCoin(600, 920)); 
    
        return silverCoins;
    }

    private List<TreasureChest> createTreasureChests() {
        List<TreasureChest> treasureChests = new ArrayList<>();
       // treasureChests.add(new TreasureChest(850, 920)); // Add a treasure chest at (850, 650)
       // treasureChests.add(new TreasureChest(900, 920)); // Add another treasure chest at (900, 650)
        // Add more treasure chests as needed
        return treasureChests;
    }

    private List<ChocolateBar> createChocolateBars() {
        List<ChocolateBar> chocolateBars = new ArrayList<>();
      //  chocolateBars.add(new ChocolateBar(750, 920)); // Example position
        // Add more chocolate bars as needed
        return chocolateBars;
    }

    public List<Coin> getCoins() {
        return coins;
    }
    public List<Tree> getTree() {
        return tree;
    }
 public List<Tree1> getTree1() {
        return tree1;
    } 
 public List<Tree2> getTree2() {
        return tree2;
    }
    // Accessor for enemies list
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Enemy1> getEnemies1() {
        return enemies1;
    }

    public List<Enemy2> getEnemies2() {
        return enemies2;
    }

    public List<Enemy3> getEnemies3() {
        return enemies3;
    }

    public List<Enemy4> getEnemies4() {
        return enemies4;
    }

    public List<Enemy5> getEnemies5() {
        return enemies5;
    }

    public List<Enemy6> getEnemies6() {
        return enemies6;
    }

    public List<Enemy7> getEnemies7() {
        return enemies7;
    }

    public List<Enemy8> getEnemies8() {
        return enemies8;
    }

    // Accessor for health items list
    public List<HealthItem> getHealthItems() {
        return healthItems;
    }

    public List<SilverCoin> getSilverCoins() {
        return silverCoins; // Correctly returns the list of silver coins
    }

    public List<TreasureChest> getTreasureChests() {
        return treasureChests; // Correctly returns the list of treasure chests
    }

    public List<ChocolateBar> getChocolateBars() {
        return chocolateBars; // Chocolate Bars
    }

    // Main game loop for handling logic
    @Override
    public void run() {
        while (running) {
            for (Coin coin : coins) {
                if (!coin.isCollected() && coin.isCollectedBy(mainCharacter)) {
                    mainCharacter.addScore(coin.getPoints()); // Add points to the character's score
                }
            }
            // Collision detection between the character and health items
            for (HealthItem healthItem : healthItems) {
                if (!healthItem.isCollected() && healthItem.checkCollision(mainCharacter.getX(), mainCharacter.getY(), mainCharacter.getWidth(), mainCharacter.getHeight())) {
                    healthItem.collect(mainCharacter); // Collect health item
                }
            }
            for (SilverCoin silverCoin : silverCoins) {
                if (!silverCoin.isCollected() && silverCoin.isCollectedBy(mainCharacter)) {
                    mainCharacter.addScore(silverCoin.getPoints()); 
                }
            }
            for (TreasureChest treasureChest : treasureChests) {
                if (!treasureChest.isCollected() && treasureChest.isCollectedBy(mainCharacter)) {
                    mainCharacter.addScore(treasureChest.getPoints()); // Add points from treasure chest
                }
            }
            for (ChocolateBar chocolateBar : chocolateBars) {
                if (!chocolateBar.isCollected() && chocolateBar.isCollectedBy(mainCharacter)) {
                    mainCharacter.increaseAttackPower(chocolateBar.getDamageIncrease());

                    // The chocolate bar is collected; you may want to log or display a message
                }
            }

            // Control the update rate (e.g., every 100 milliseconds)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // Draw method to render all enemies and health items

    // Stop the level thread
    public void stop() {
        running = false;
           
    }


 

}
