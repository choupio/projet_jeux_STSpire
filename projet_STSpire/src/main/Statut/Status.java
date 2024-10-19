package main.Statut;


public class Status {
    //ATTRIBUTS
    private String name; 

    //CONSTRUCTEUR
    /**
     * Constructeur de la classe Status
     * 
     * @param name Le nom du statut
     */
    public Status(String name){
        this.name = name;
    }

    //GETTERS AND SETTERS

    /**
     * Retourne le nom du statut
     * 
     * @return Le nom du statut
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du statut
     * 
     * @param name Le nouveau nom 
     */
    public void setName(String name) {
        this.name = name;
    }
}
