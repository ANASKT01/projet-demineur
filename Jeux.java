import java.util.Random;

public class Jeux {


    public static void      clearScreen()                                        // Clear le terminale
    {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public                  Jeux()                                               // Constuit un objet Jeux qui vous nous servir pour les modalité la partie
    {
        clearScreen();

        Map         MapJeux                       ;
        Boolean     partieEnCours   = true        ;
        int         X               = 2           ;
        int         ChoixSousMenu                 ;
        int         ChoixMenu                     ;
        Random      random          = new Random();

       
        System.out.println("");
        afficheMenu();


        System.out.print("Que Voulez vous faire : ");
        ChoixMenu = Integer.parseInt(System.console().readLine());

        switch (ChoixMenu) 
        {

            case 0:
                clearScreen();
                System.out.println("Vous avez quittez le jeux.");
                break;


            case 1:
                clearScreen();
                System.out.println("Continuez.");
                afficheSousMenu();
                System.out.print("Que Voulez vous faire : ");

                ChoixSousMenu = Integer.parseInt(System.console().readLine());

                if(ChoixSousMenu == 1) 
                {
                    while(X<=2)
                    {
                        X = random.nextInt(15);
                    }
                    MapJeux = new Map(X);
                    Partie(MapJeux, partieEnCours);
                }
                else if(ChoixSousMenu == 2)
                {

                    System.out.print("Entrez la Taille de la Map (5min):  ");
                    X = Integer.parseInt(System.console().readLine());

                    while(X<5)
                    {

                        System.out.println("Attention : Taille trop petite : \nEntrez la Taille de la Map (6min):  ");
                        X = Integer.parseInt(System.console().readLine());
                    
                    }
                    MapJeux = new Map(X);
                    Partie(MapJeux, partieEnCours);

                }
                else if(ChoixSousMenu == 3)
                {

                    clearScreen();
                    int Taille = Map.TailleFichier();
                    MapJeux = new Map(Taille);
                    System.out.println("Chargement de la Map en cours...");
                    MapJeux.SaveMapCreaMapFichier();
                    Partie(MapJeux, partieEnCours);

                }
                else if(ChoixSousMenu == 0)
                {

                    clearScreen();
                    System.out.println("Vous avez quittez le jeux.");
                    System.exit(0);

                }

                else
                {

                    clearScreen();
                    System.out.println("Erreur de Saisie. Vous avez quittez le jeux.");
                    System.exit(0);

                }


                break;


            default:
                clearScreen();
                ChoixMenu = 0;
                System.out.println("Erreur de Saisie. Vous avez quittez le jeux.");
                System.exit(0);
        
        }


    }

    public        void      Partie(Map MapJeux, Boolean partieEnCours)           // Permet de manipiuler la partie et de savoir si la partie est fini
    {
        int     XPosition;
        int     YPosition;


        while (partieEnCours == true) 
        {   
            clearScreen();
            do 
            { 
                afficheTeteMenu();
                MapJeux.AfficheMap();
                //MapJeux.afficheMapEtat();

                System.out.print("Entrez : Ligne = ");
                XPosition = Integer.parseInt(System.console().readLine());

                System.out.print("Entrez : Colonne = ");
                YPosition = Integer.parseInt(System.console().readLine());
                
                if(positionValide(MapJeux, XPosition, YPosition) == false)
                {

                    System.out.println("Position invalide, veuillez recommencer");

                }
                

            } while(positionValide(MapJeux, XPosition, YPosition) == false);

            Case CaseDev = new Case();

            CaseDev.setPosX(XPosition);
            CaseDev.setPosY(YPosition);

            revealCell(MapJeux,CaseDev);
            CaseCacher(MapJeux,XPosition,YPosition);

            MapJeux.SaveMapEcritureFichier();


            if(PartieGagner(MapJeux) == true)
            {
                clearScreen(); 
                MapJeux.AfficheMap();
                partieEnCours = false;
                System.out.println(" VOUS AVEZ GAGNEZ !!! ");
                MapJeux.afficheMapReveler();
            }
            if( EstCeMine(MapJeux, XPosition, YPosition) == true )
            {

                clearScreen(); 
                MapJeux.AfficheMap();
                partieEnCours = false;
                System.out.println(" ATTENTION A LA MINE ! ");
                MapJeux.afficheMapReveler();

            }

        }
    

    }

    public        void      afficheTeteMenu()                                    // Afficher la tete du Jeux dans le terminale 
    {
        System.out.println("\n\n\t\t--- Jeux du DÉMINEUR --- ");
        System.out.println("\t\t\t\t\t -- Score : -- \n");
    }

    public        void      afficheMenu()                                        // Affiche le menu pour les 1ere modalitées 
    {

        System.out.println("1. Jouez ");
        System.out.println("0. Quittez ");

    }

    public        void      afficheSousMenu()                                    // Affiche le sous menu pour les 2eme modalitées 
    {

        System.out.println("1. Map Aléatoire : ");
        System.out.println("2. Map Définie  :");
        System.out.println("3. Map en Cours  :");
        System.out.println("0. Quittez :");

    }

    public        boolean   positionValide(Map Map,int posX, int posY)           // Retourn true ou false si une Case fait partie de la Map || Entrée : Map Map, int posX, int posY || Sortie : true ou false 
    {

        for(Case Case : Map.getMap())
        {
            if(Case.getPosX() == posX && Case.getPosY() == posY && Case.getCacher() == true)
            {
                return true;
            }
        }
        return false;

    }

    public        void      CaseCacher(Map Map,int posX, int posY)               // Permet de découvrir une Case
    {

        for(Case Case : Map.getMap())
        {
            if(Case.getPosX() == posX && Case.getPosY() == posY )
            {
                Case.setCacher(false);
            }
        }
    

    }

    public        boolean   PartieGagner(Map Map)                                //  Retourn true si la partie est Gagné || Entrée : Map Map || Sortie : true ou false
    {
        int cpt = 0;
        for(Case Case : Map.getMap())
        {  
            
            if(Case.getCacher() == false && Case.getMine() == false)
            {
                cpt++;
            }

        }

        if(cpt == (Map.getTailleM()*Map.getTailleM())-Map.compteurMineMap())
        {
            return true;
        }
        
        return false;
        
    }

    public        boolean   VideOuNumAutour(Case CaseA, Case CaseB)              //  Retourn true ou false si une case est aux alentourrs || Entrée : Case CaseA, Case CaseB || Sortie : true ou false
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

    public        void      revealCell(Map map, Case caseA)                      // Permet de Reveler les cases aulentours lors de la partie || Entrée : Map Map, int posX, int posY
    {

        caseA.setCacher(false);
    
        if (caseA.getCompteurMine() == 0) {
            int[] rowOffset = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] colOffset = {-1, 0, 1, -1, 1, -1, 0, 1};
    
            for (int i = 0; i < 8; i++) {
                int newRow = caseA.getPosX() + rowOffset[i];
                int newCol = caseA.getPosY() + colOffset[i];
    
                for (Case caseAutour : map.getMap()) {
                    if (caseAutour.getPosX() == newRow && caseAutour.getPosY() == newCol && caseAutour.getCacher() == true && caseAutour.getMine() == false) {
                        revealCell(map, caseAutour);
                    }
                }
            }
        }

    }
  
    public        boolean   EstCeMine(Map Map,int posX, int posY)                // Retourn true ou false si une case est une mine || Entrée : Map Map, int posX, int posY || Sortie : true ou false 
    {
        for(Case Case : Map.getMap())
        {
            if(Case.getPosX() == posX && Case.getPosY() == posY && Case.getMine() == true )
            {
                return true;
            }

        }
        return false;
    }


}