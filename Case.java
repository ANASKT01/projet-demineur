import java.util.Random;

public class Case    
{   
                                            // Données des Cases

    private     int         posX;                 
    private     int         posY;
    private     boolean     cacher;
    private     boolean     Mine;
    private     int         compteurMine;

                                                            // Accesseurs

    public int      getPosX() 
    {
        return this.posX;
    }

    public int      getPosY() 
    {
        return this.posY;
    }

    public boolean  getCacher() 
    {
        return this.cacher;
    }

    public boolean  getMine()
    {
        return this.Mine;
    }

    public int      getCompteurMine()
    {
        return this.compteurMine;
    }

    public void     setPosX(int posX) 
    {
        this.posX = posX;
    }

    public void     setPosY(int posY) 
    {
        this.posY = posY;
    }

    public void     setCacher(boolean cacher) 
    {
        this.cacher = cacher;
    }

    public void     setMine(boolean Mine) 
    {
        this.Mine = Mine;
    }

    public void     setCompteurMine(int compteurMine) 
    {
        this.compteurMine = compteurMine;
    }

    public          Case()
    {
       
    }

    public void     mineAlea(int tailleMap)                 // renvoie true ou false pour que une case soit une mine ou non || entrée : Pourcentage de Mine
    {

        Random      random      = new Random();
        int         estCeMine   = random.nextInt(100);

        int         nbCases     = tailleMap * tailleMap;
        int         probaMine   = (int) (((float) tailleMap / nbCases) * 100); // Calcul de la probabilité d'avoir une mine


        if (estCeMine <= probaMine) {
            this.Mine = true;
        } else {
            this.Mine = false;
        }

    }


    
}
