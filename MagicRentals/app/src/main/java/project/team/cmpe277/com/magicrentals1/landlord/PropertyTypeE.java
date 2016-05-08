package project.team.cmpe277.com.magicrentals1.landlord;

/**
 * Created by savani on 5/4/16.
 */
public enum PropertyTypeE {
    Apartment(0),
    Condo(1),
    Villa(2),
    Town_House(3),
    Penthouse(4),
    Cottage(5);
    private int val;
    PropertyTypeE(int val){
        this.val = val;
    }
    int getVal(){
        return val;
    }
}



