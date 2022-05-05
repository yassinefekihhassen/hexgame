import javax.swing.JOptionPane;

public class perso2 {


   int taille ;
   int[][] arene;
   int[][] tab_cache;

   // choisir la taille de l'arene
    public perso2(){
       
        this.taille =Integer.valueOf(JOptionPane.showInputDialog("choisissez la taille de la arene ?"))+1;
        while (this.taille <= 2) {
            this.taille =Integer.valueOf(JOptionPane.showInputDialog("Merci de choisir une taille plus grande que 1 ?"))+1;
        }
        
        arene = new int[taille][taille];
        tab_cache = new int[taille][taille];
        
    }

    // changer le tour entre les deux joueurs
    public void tour() {
        int temp =1 ;
        while (!gagnant()) {
            affichage(arene);
            System.out.println("___________________");
            affichage(tab_cache);
            // affichage(tab_cache);
            if (temp == 3) swap (arene , tab_cache);
            if (temp % 2 != 0) joueur(arene, tab_cache, 1);
            else joueur(arene, tab_cache, 2);
            temp++;
            // pour effacer la console j'ai trouvé la commande ici : https://www.delftstack.com/howto/java/java-clear-console/
            System.out.print("\033[H\033[2J");
        }
        affichage(arene);
        System.out.println("___________________");
        affichage(tab_cache);
        termine(temp);
    }

    // swap pour demander à l'utilisateur s'il préfère échanger
    public static void swap(int arene[][], int tab_cache[][]) {
        int temp = Integer.valueOf(JOptionPane.showInputDialog("J2: vous voulez échanger le cellule avec J1? (oui/0,non/1)"));
        if (temp == 0) chSwap(arene, tab_cache);
    }
    
    // on fait un échange entre les cellules après de finir le premier tour
    public static void chSwap(int arene[][], int tab_cache[][]) {
        for (int i = 1; i < tab_cache.length; i++) {
            for (int j = 1; j < tab_cache.length; j++) {
                if (arene[i][j] == 1) {
                    arene[i][j] = 2;
                    tab_cache[i][j] = 2;
                    valeur(tab_cache, i, j, 2);
                } else if (arene[i][j] == 2) {
                    arene[i][j] = 1;
                    tab_cache[i][j] = 1;
                    valeur(tab_cache, i, j, 1);
                }
            }
        }
        System.out.print("\033[H\033[2J");
        affichage(arene);
    }

    // un joueur human vs ordi
    public void joueur(int arene[][], int tab_cache[][], int j) {
        int x;
        int y;
        
            x = Integer.valueOf(JOptionPane.showInputDialog("j" + j + ": cordonnee X"));
            y = Integer.valueOf(JOptionPane.showInputDialog("j" + j + ": cordonnee Y"));
            // on envoie les coordonnées à la fonction vide pour tester si la cellule est disponible
            // si la cellule n'est pas disponible, on redemande à l'utilisateur
            while (!vide(tab_cache, x, y)) {
                x = Integer.valueOf(JOptionPane.showInputDialog("j" + j + ": Cette cellule est deja utilise. autre Cordonnee X"));
                y = Integer.valueOf(JOptionPane.showInputDialog("j" + j + ": Cette cellule est deja utilise. autre Cordonnee y"));
           }
            
        arene[x][y] = j;
        valeur(tab_cache, x, y, j);
        if (test4_5(tab_cache, j)) {
        if (balayage(tab_cache, j)) {
            balayage(tab_cache, j);
            }
        }
    }

    // test si la cellule est disponible
    public static boolean vide(int tab_cache[][],int x , int y ) {
        if (x < tab_cache.length
            && x > 0
            && y < tab_cache.length
            && y > 0
            && tab_cache[x][y] == 0) {
        return true;
    } else return false;
    }

    // initialiser les valeurs de chaque cellules de tab_cache avec joueur 1 = 4 et joueur 2 = 5
    public static int valeur(int tab_cache[][], int x, int y, int j) {
        if (x == 1 && j == 1) tab_cache[x][y] = 4;
        else if (y == 1 && j == 2) tab_cache[x][y] = 5;
        else tab_cache[x][y] = j;
        return 0;
    }

    // partie test le voisin
    static boolean test4_5(int tab_cache[][], int t) {
        for (int i = 1; i < tab_cache.length; i++) {
            for (int j = 1; j < tab_cache.length; j++) {
                if (tab_cache[1][j] == 4 || tab_cache[i][1] == 5) return true;
            }
        }
        return false;
    }

    static boolean balayage(int[][] tab_cache, int joueur) {
        int temp;
        if (joueur == 1) temp = 4;
        else temp = 5;
        for (int i = 0; i < tab_cache.length; i++) {
            for (int j = 0; j < tab_cache[i].length; j++) {
                if (tab_cache[i][j] == joueur
                        && ((j < tab_cache[i].length - 1 && tab_cache[i][j + 1] == temp)
                                || (i > 0 && tab_cache[i - 1][j] == temp)
                                || (i > 0
                                        && j < tab_cache[i].length - 1
                                        && tab_cache[i - 1][j + 1] == temp)
                                || (j > 0 && tab_cache[i][j - 1] == temp)
                                || (j > 0
                                        && i < tab_cache[i].length - 1
                                        && tab_cache[i + 1][j - 1] == temp)
                                || (i < tab_cache[i].length - 1 && tab_cache[i + 1][j] == temp))) {
                    tab_cache[i][j] = temp;
                    return true;
                }
            }
        }
        return false;
    }

    // on test si on a un gagnant 
    public boolean gagnant() {
        for (int i = 1; i < tab_cache.length; i++) {
            for (int j = 1; j < tab_cache.length; j++) {
                if (tab_cache[tab_cache.length - 1][j] == 4
                        || tab_cache[i][tab_cache.length - 1] == 5) return true;
            }
        }
        return false;
    }

    // on affiche l'arene
    public static void affichage(int arene[][]) {
        for (int i = 0; i < arene.length; i++) {
            for (int j = 0; j < arene.length; j++) {
                if (i == 0 && j == 0) System.out.print("  ");
                else if (i == 0 && j < 10) System.out.print(j + "  ");
                else if (i == 0 && j > 9) System.out.print(j + " ");
                else if (j == 0 && i > 0) {
                     bordGouche(i);
                    if (i < 10) System.out.print(i + "  ");
                    else if (i > 9) System.out.print(i + " ");
                } else if (j < 11) System.out.print(arene[i][j] + "  ");
                else if (j > 9) System.out.print(arene[i][j] + "  ");
            }
            System.out.println();
        }
    }
    // je l'ai coupé sinon la méthode affichage va être très long  
    public static void bordGouche(int temp) {
        for (int i = 0; i < temp - 1; i++) {
            System.out.print(" ");
        }
    }

    // on affiche le gagnant
    public static void termine(int temp) {
        if (temp % 2 == 0) System.out.print("jouer 1 a gagné");
        else System.out.print("jouer 2 a gagné");
    }

    // random pour l'IA
    public int Random(int min, int max){
        return (int) (Math.random()*max-min)+min;
    }

}
   
