package project.team.cmpe277.com.magicrentals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rekha on 4/27/2016.
 */
public class TenantSearchListFragment extends android.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View searchlistview = inflater.inflate(R.layout.searchlistfragment_tenant, container, false);


        return searchlistview;
    }

}
