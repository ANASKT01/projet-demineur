import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class Map {

                                                                                // Données des Cases
    private LinkedList<Case>    MapJeux;
    private int                 tailleM;
                                                                                // Accesseurs
    public  int              getTailleM()
    {
        return this.tailleM;
    }

    public  LinkedList<Case> getMap()
    {
        return this.MapJeux;
    }

    public                   Map(int taille)                                     // Construit un tableau de Map   || Entrée : int taille     
    {

        this.MapJeux          = new LinkedList<Case>();
        this.tailleM          = taille;
            
            for (int i = 0; i < tailleM; i++)
            {
                for (int j = 0; j < tailleM; j++)
                {
                    Case Case = new Case();

                    Case.setPosX(i);
                    Case.setPosY(j);
                    Case.setMine(false);
                    Case.mineAlea(this.tailleM);
                    Case.setCacher(true) ;
                    Case.setCompteurMine(0);

                    MapJeux.add(Case);
                }

            }
            calculerMines();

    }

    private void             calculerMines()                                     // Calcule les cases qui sont des mines dans un tableaux  
    {

        for (Case Case : this.MapJeux) 
        {
            
            int cpt = 0;
            for (Case CaseAutour : this.MapJeux)
            {

                if (MineAutour(Case, CaseAutour) && CaseAutour.getMine() == true) 
                {
                    cpt++;
                }

            }

            Case.setCompteurMine(cpt);

        }
    }

    private boolean          MineAutour(Case CaseA, Case CaseB)                  // Calcule les cases qui sont des mines dans un tableaux  || Entrée : int taille 
    {

        int AutourX; 
        int AutourY;


        if (CaseA.getPosX() > CaseB.getPosX()) {
            AutourX = CaseA.getPosX() - CaseB.getPosX();
        } else {
            AutourX = CaseB.getPosX() - CaseA.getPosX();
        }


        if (CaseA.getPosY() > CaseB.getPosY()) {
            AutourY = CaseA.getPosY() - CaseB.getPosY();
        } else {
            AutourY = CaseB.getPosY() - CaseA.getPosY();
        }

    
        if ((AutourX == 0 && AutourY == 1) || (AutourX == 1 && AutourY == 0) || (AutourX == 1 && AutourY == 1)) {
            return true;
        } else {
            return false;
        }

    } 
 
    public int               compteurMineMap()                                   // Retoune les nombres de Case qui sont des Mines 
    {

        int     nbrMine = 0;

        for(Case Case : this.MapJeux)
        {
            if(Case.getMine() == true)
            {
                nbrMine++;
            }
        } 

        return nbrMine;


    }

    public void              SaveMapEcritureFichier()                            // Permet d'écrire les États et les Mines de la map sur deux fichier texte
    {
        int cpt = 0;

        //FICHIER STATUT VISIBILITÉ
        String fichierEtat = "./MapEnCours/MapStatut/Map1Etat.txt";             //lieu et nom du fichier
        String fichierMine = "./MapEnCours/MapStatut/Map1Mine.txt";               //lieu et nom du fichier

        try {                                                                   //supression du contenue des fichier.txt
            FileWriter fe = new FileWriter(fichierEtat);
            FileWriter fm = new FileWriter(fichierMine);
            fm.write("");
            fe.write("");
            fm.close();
            fe.close();
            
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        }

      
       
        
        for (Case Case : this.MapJeux) 
        {
            if(Case.getCacher() == true)
            {
                try{
                    FileWriter fe = new FileWriter(fichierEtat, true);
                    BufferedWriter bw = new BufferedWriter(fe);
                    bw.write('C');
                    bw.close();
                    fe.close();
                }catch (IOException e) {
                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                }

        
            }
            else
            {
                try{
                    FileWriter fe = new FileWriter(fichierEtat, true);
                    BufferedWriter bw = new BufferedWriter(fe);
                    bw.write('N');
                    bw.close();
                    fe.close();
                }catch (IOException e) {
                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                }
            }


            //FICHIER MINE

            

            if(Case.getMine() == true)
            {
                try{
                    FileWriter fm = new FileWriter(fichierMine, true);
                    BufferedWriter bw = new BufferedWriter(fm);
                    bw.write('O');
                    bw.close();
                    fm.close();
                }catch (IOException e) {
                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                }

        
            }
            else
            {
                try{
                    FileWriter fm = new FileWriter(fichierMine, true);
                    BufferedWriter bw = new BufferedWriter(fm);
                    bw.write('-');
                    bw.close();
                    fm.close();
                }catch (IOException e) {
                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                }
            }

            cpt++;
            if(cpt == this.tailleM)
            {
                try{
                    FileWriter fe = new FileWriter(fichierEtat, true);
                    FileWriter fm = new FileWriter(fichierMine, true);
                    BufferedWriter bwfe = new BufferedWriter(fe);
                    BufferedWriter bwfm = new BufferedWriter(fm);
                    bwfe.newLine();
                    bwfe.close();
                    bwfm.newLine();
                    bwfm.close();
                    fe.close();
                    fm.close();
                }catch (IOException e) {
                    System.out.println("Une erreur s'est produite : " + e.getMessage());
                }
                cpt  = 0;
            }

        }

        


    }

    public void              SaveMapCreaMapFichier()                             // Permet de lire le contenue de deux fichier (État & Mine) pour modifier une map 
    {

        String fichierEtat = "./MapEnCours/MapStatut/Map1Etat.txt";             //lieu et nom du fichier
        String fichierMine = "./MapEnCours/MapStatut/Map1Mine.txt";             //lieu et nom du fichier

        try {

            FileReader fe = new FileReader(fichierEtat);
            BufferedReader brEtat = new BufferedReader(fe);
            int dataEtat = brEtat.read();

            FileReader fm = new FileReader(fichierMine);
            BufferedReader brMine = new BufferedReader(fm);
            int dataMine = brMine.read();

                for(Case Case : this.MapJeux)
                {
    
                    if((char)dataEtat == 'C')
                    {
                        Case.setCacher(true);
                    }
                    else
                    {
                        Case.setCacher(false);
                    }

                    dataEtat = brEtat.read();

                    if((char)dataMine == 'O')
                    {
                        Case.setMine(true);
                    }
                    else
                    {
                        Case.setMine(false);
                    }

                    dataMine = brMine.read();
                }

            
            brEtat.close();
            brMine.close();
            fe.close();
            fm.close();

            
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        }
    

    }

    public static int        TailleFichier()                                     // Calcule le taille d'une ligne d'un fichier texte
    {
        
        int nombreDeCaracteres = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("./MapEnCours/MapStatut/Map1Etat.txt"))) {

            String premiereLigne = br.readLine();
            if (premiereLigne != null) {
                nombreDeCaracteres = premiereLigne.length();
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
        }

        return nombreDeCaracteres;
        
    }
    
    public void              AfficheMap()                                        // Affiche la Map avec un repère 
    {
        int     cpt     = 1 ;
        int     repereY = 0 ;

        System.out.println("\n");
        System.out.print("   ");
        for(int i = 0; i <this.tailleM ; i++)
        {
            if(i<10)
            {
                System.out.print(" "+i+" ");
            }
            else
            {
                System.out.print(i+" ");;
            }
        }
        System.out.println("\n");  

        for(Case Case : this.MapJeux)
        {
            if(cpt == 1 )
            {
                if(repereY<10)
                {
                    System.out.print(" "+repereY+" ");
                }
                else
                {
                    System.out.print(repereY+" ");
                }
                repereY++;
            }

            if(Case.getCacher() == true)
            {
                System.out.print(" ? ");
            }
            else
            {
                if(Case.getMine() == true)
                {
                    System.out.print(" M ");
                }
                else if(Case.getCompteurMine() > 0)
                {
                    System.out.print(" " + Case.getCompteurMine() +  " ");
                }
                else
                {
                    System.out.print(" " + "-" +  " ");
                }
            }

            if(cpt == this.tailleM)
            {
                System.out.println(" ");

                cpt = 0;
            }
            cpt++;

        }
        System.out.println("\n");
       
    }

    public void              afficheMapReveler()                                 // Affiche la Map avec toutes les Case découvertes 
    {
            int     cpt     = 1;
            int     repereY = 0 ;

            System.out.println("\n");
            System.out.print("   ");
            for(int i = 0; i <this.tailleM ; i++)
            {
                if(i<10)
                {
                    System.out.print(" "+i+" ");
                }
                else
                {
                    System.out.print(i+" ");;
                }
            }
            System.out.println("\n");  
    
            for(Case Case : this.MapJeux)
            {
                if(cpt == 1 )
                {
                    if(repereY<10)
                    {
                        System.out.print(" "+repereY+" ");
                    }
                    else
                    {
                        System.out.print(repereY+" ");
                    }
                    repereY++;
                }
    
                
                if(Case.getMine() == true)
                {
                    System.out.print(" M ");
                }
                else if(Case.getCompteurMine() > 0)
                {
                    System.out.print(" " + Case.getCompteurMine() +  " ");
                }
                else
                {
                    System.out.print(" " + "-" +  " ");
                }

                if(cpt == this.tailleM)
                {
                    System.out.println(" ");
    
                    cpt = 0;
                }
                cpt++;
    
            }
            System.out.println("\n");
           
    }

    public void              afficheMapEtat()                                    // Affiche la Map avec les Case découverte ou non 
    {
            int     cpt     = 1;
            int     repereY = 0 ;

            System.out.println("\n");
            System.out.print("   ");
            for(int i = 0; i <this.tailleM ; i++)
            {
                if(i<10)
                {
                    System.out.print(" "+i+" ");
                }
                else
                {
                    System.out.print(i+" ");;
                }
            }
            System.out.println("\n");  
    
            for(Case Case : this.MapJeux)
            {
                
                if(cpt == 1 )
                {
                    if(repereY<10)
                    {
                        System.out.print(" "+repereY+" ");
                    }
                    else
                    {
                        System.out.print(repereY+" ");
                    }
                    repereY++;
                }

                if(Case.getCacher() == true)
                {
                    System.out.print(" " + "C" + " ");
                }else
                {
                    System.out.print(" " + "N" + " ");
                }

                if(cpt == this.tailleM)
                {
                    System.out.println(" ");
    
                    cpt = 0;
                }
                cpt++;
    
            }
            System.out.println("\n");
           
    }
    
}
