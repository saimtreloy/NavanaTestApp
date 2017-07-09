package com.moodybugs.saim.navanatestapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moodybugs.saim.navanatestapp.Activity.MapsActivity;
import com.moodybugs.saim.navanatestapp.Activity.MapsActivityForAllProject;
import com.moodybugs.saim.navanatestapp.R;


public class FragmentHome extends Fragment {

    View view;
    Button btnProjectFuture, btnProjectOther, btnMapProject, btnGroupWork;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    public FragmentHome() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        return view;
    }

    public void init(){
        btnProjectFuture = (Button) view.findViewById(R.id.btnProjectFuture);
        btnProjectOther = (Button) view.findViewById(R.id.btnProjectOther);
        btnMapProject = (Button) view.findViewById(R.id.btnMapProject);
        btnGroupWork = (Button) view.findViewById(R.id.btnGroupWork);

        fragmentTransaction = FragmentHome.this.getFragmentManager().beginTransaction();

        ButtonClicked();
    }

    public void ButtonClicked(){
        btnProjectFuture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.main_container, new FragmentProjectFuture());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnProjectOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.main_container, new FragmentProjectOther());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnMapProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivityForAllProject.class);
                startActivity(intent);
            }
        });
    }

}
