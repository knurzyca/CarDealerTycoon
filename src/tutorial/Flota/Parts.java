package tutorial.Flota;

public class Parts {
    private String name;
    private boolean damaged; // needs repiar


    public Parts(String name){
        this.name = name;
        if(Math.random()*3 > 1)
            damaged = true;
        else damaged = false;
    }

    public boolean isDamaged(){
        return damaged; //method t or f, return if its demange
    }

    public void changeDamaged(){
        damaged = !damaged; //if repaired switch
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
