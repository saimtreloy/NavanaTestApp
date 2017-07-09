package com.moodybugs.saim.navanatestapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class FragmentProjectOther extends Fragment {

    View view;
    ArrayList<ModelFutureProject> otherProjectList = new ArrayList<>();
    RecyclerView recyclerViewProjectOther;
    RecyclerView.LayoutManager layoutManagerOtherProject;
    RecyclerView.Adapter otherAdapter;

    public FragmentProjectOther() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_other, container, false);

        init();

        return view;
    }


    public void init(){
        otherProjectList.clear();
        recyclerViewProjectOther = (RecyclerView) view.findViewById(R.id.recyclerViewProjectOther);
        layoutManagerOtherProject = new GridLayoutManager(getContext(), 2);
        recyclerViewProjectOther.setLayoutManager(layoutManagerOtherProject);
        recyclerViewProjectOther.setHasFixedSize(true);

        PopulateFutureProject();
    }

    private void PopulateFutureProject() {
        String response = new SharedPrefDatabase(getContext()).RetriveOtherProject();
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
            otherProjectList.add(modelFutureProject);
        }

        otherAdapter = new AdapterFutureProject(otherProjectList);
        recyclerViewProjectOther.setAdapter(otherAdapter);
    }

}
