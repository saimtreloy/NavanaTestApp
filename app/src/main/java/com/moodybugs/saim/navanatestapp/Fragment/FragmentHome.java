package com.moodybugs.saim.navanatestapp.Fragment;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moodybugs.saim.navanatestapp.Activity.Chat.GroupLoginActivity;
import com.moodybugs.saim.navanatestapp.Activity.MapsActivityForAllProject;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Service.MyService;


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

        btnGroupWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isMyServiceRunning(MyService.class)){
                    Log.d("SAIM SERVICE CHECK", "Running");
                }else {
                    Log.d("SAIM SERVICE CHECK", "Not Running");
                }
                Intent intent = new Intent(getActivity(), GroupLoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
