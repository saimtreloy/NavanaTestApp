package com.moodybugs.saim.navanatestapp.Utility;

/**
 * Created by sam on 7/8/17.
 */

public class ApiUrl {
    public static String futureProject = "http://navana-realestate.com/mobile/web_service/index.php/service/get_featured_projects";
    public static String otherProject = "http://navana-realestate.com/mobile/web_service/index.php/service/get_other_projects";

    //map need parameter
    //0 for All, 1 for Dhaka, 2 for Chittagong
    public static String mapProject = "http://navana-realestate.com/mobile/web_service/index.php/service/projects_by_city_json_object/";

    //Image need parameter
    //parameter should be image name
    public static String imageLink = "http://navana-realestate.com/mobile/apps/upload_images/project/";
}
