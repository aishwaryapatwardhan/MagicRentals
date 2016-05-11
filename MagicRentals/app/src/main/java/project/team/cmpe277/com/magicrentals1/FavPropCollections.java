package project.team.cmpe277.com.magicrentals1;

import java.util.ArrayList;

/**
 * Created by Raghu on 5/10/2016.
 */
public class FavPropCollections {
    public static ArrayList<FavPropertieDetails> favoriteAList = new ArrayList<>();

    public static void buildList(){
        favoriteAList.add(new FavPropertieDetails("guruz","49 West julia","San Jose","CA","95110","Villa","2.5","2","1232.50","3000","rag@gm.com","908978908","Good House","Nice one","Available","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg"));
        favoriteAList.add(new FavPropertieDetails("guruz","59 West marot","San Jose","CA","95110","Villa","2.5","2","1232.50","3000","rag@gm.com","908978908","Good House","Nice one","Available","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg"));
        favoriteAList.add(new FavPropertieDetails("guruz","69 East julia","San Jose","CA","95110","Villa","2.5","2","1232.50","3000","rag@gm.com","908978908","Good House","Nice one","Available","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg"));
        favoriteAList.add(new FavPropertieDetails("guruz","39 west julia","San Jose","CA","95110","Villa","2.5","2","1232.50","3000","rag@gm.com","908978908","Good House","Nice one","Available","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg"));
        favoriteAList.add(new FavPropertieDetails("guruz","79 North julia","San Jose","CA","95110","Villa","2.5","2","1232.50","3000","rag@gm.com","908978908","Good House","Nice one","Available","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg"));
        favoriteAList.add(new FavPropertieDetails("guruz","89 South julia","San Jose","CA","95110","Villa","2.5","2","1232.50","3000","rag@gm.com","908978908","Good House","Nice one","Available","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg"));
        favoriteAList.add(new FavPropertieDetails("guruz","99 julia","San Jose","CA","95110","Villa","2.5","2","1232.50","3000","rag@gm.com","908978908","Good House","Nice one","Available","https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg"));
    }

    public static ArrayList<FavPropertieDetails> getFavList(){
        if(favoriteAList.isEmpty()){
            buildList();
        }
        return favoriteAList;
    }
}
