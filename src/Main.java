import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 300,250,250,250};
    public static int[] heroesDamage = {10, 15, 20, 0 ,30,15,20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic","Berserk","Lucky","Thor"};
    public static int roundNumber = 0;
    public static String message = "";


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        message = "";
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
        medicHealth();
        hitBerserk();
        hitLucky();
        hitThor();
        hitGolem();
    }
    public static void hitGolem() {
        int atac = bossDamage / 5;
        int liveGolem = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 4) {
                continue;
            } else if (heroesHealth[i] > 0) {
                liveGolem++;
                heroesHealth[i] += atac;
            }
            heroesHealth[4] -= atac * liveGolem;
            System.out.println(" Голем получил урон: " + atac * liveGolem);
            break;
        }
    }


    private static void hitThor() {
        if (bossDamage >= 0){
            bossDamage = 50;
        }
        Random random = new Random();

        boolean indexThor = random.nextBoolean();
        if(indexThor && heroesHealth[6]>0){
           bossDamage = 0;
            System.out.println("Тор оглушил ");
        }

    }

    private static void hitLucky() {
        Random random = new Random();
        boolean indexLucky = random.nextBoolean();

        if(indexLucky && heroesHealth[5]>0){
            heroesHealth[5] += bossDamage;
            System.out.println("Уклонился");
        }
    }

    private static void hitBerserk(){
        if (heroesHealth[4]>0){
            heroesHealth[4] += 10;
            heroesDamage[4] += 10;
        }
        System.out.println(" режим берсерк");
    }

//    private static void bersekDemo() {
//        if (heroesHealth[4] > 0 ){
//            heroesDamage[4]=heroesDamage[4] + bossDamage /2;
//        }
//    }

    private static void medicHealth() {
            for (int i = 0; i <heroesHealth.length ; i++) {
                if(heroesAttackType[i].equals("Medic")){
                    continue;
                } else if (heroesHealth[i]>0 && heroesHealth[i]<100 && heroesHealth[3] > 0) {
                    heroesHealth[i] += 15;
                    System.out.println(" Medic heal " + heroesAttackType[i]);
                    break;
                }
            }
        }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10;
                    damage = damage * coefficient;
                    message = "Critical damage: " + damage;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
        System.out.println(message);
    }
}
