package com.moodybugs.saim.navanatestapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moodybugs.saim.navanatestapp.Adapter.AdapterFutureProject;
import com.moodybugs.saim.navanatestapp.Model.ModelFutureProject;
import com.moodybugs.saim.navanatestapp.R;
import com.moodybugs.saim.navanatestapp.Utility.SharedPrefDatabase;
import com.moodybugs.saim.navanatestapp.Utility.XmlParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;


public class FragmentProjectFuture extends Fragment {

    View view;
    ArrayList<ModelFutureProject> futureProjectList = new ArrayList<>();
    RecyclerView recyclerViewProjectFuture;
    RecyclerView.LayoutManager layoutManagerFutureProject;
    RecyclerView.Adapter futureAdapter;

    public FragmentProjectFuture() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_future, container, false);

        init();

        return view;
    }

    public void init(){
        futureProjectList.clear();
        recyclerViewProjectFuture = (RecyclerView) view.findViewById(R.id.recyclerViewProjectFuture);
        layoutManagerFutureProject = new GridLayoutManager(getContext(), 2);
        recyclerViewProjectFuture.setLayoutManager(layoutManagerFutureProject);
        recyclerViewProjectFuture.setHasFixedSize(true);

        PopulateFutureProject();
    }

    private void PopulateFutureProject() {
        String response = new SharedPrefDatabase(getContext()).RetriveFutureProject();
        XmlParser xmlParser = new XmlParser();
        Document doc = xmlParser.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("project");

        for (int i = 0; i < nl.getLength(); i++) {
            Element e = (Element) nl.item(i);
            String id = xmlParser.getValue(e, "id");
            String name = xmlParser.getValue(e, "name");
            String type = xmlParser.getValue(e, "type");
            String status = xmlParser.getValue(e, "status");
            String size = xmlParser.getValue(e, "size");
            String city = xmlParser.getValue(e, "city");
            String area_name = xmlParser.getValue(e, "area_name");
            String address = xmlParser.getValue(e, "address");
            String longitude = xmlParser.getValue(e, "longitude");
            String latitude = xmlParser.getValue(e, "latitude");
            String image = xmlParser.getValue(e, "image");

            ModelFutureProject modelFutureProject = new ModelFutureProject(id, name, type, status, size, city, area_name, address,
                    longitude, latitude, image);
            futureProjectList.add(modelFutureProject);
        }

        futureAdapter = new AdapterFutureProject(futureProjectList);
        recyclerViewProjectFuture.setAdapter(futureAdapter);
    }


}
