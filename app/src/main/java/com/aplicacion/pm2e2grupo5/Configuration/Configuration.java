package com.aplicacion.pm2e2grupo5.Configuration;

public class Configuration {
    private static final String Server_http = "http://";
    private static final String Server_direction = "192.168.0.104/www/";
    private static final String Web_api = "API-REST-Examen/";
    private static final String Get_all_contacts = "api/read.php";
    private static final String Get_single_contact = "api/single_read.php";
    private static final String Create_contact = "api/create.php";
    private static final String Update_contact = "api/update.php";
    private static final String Delete_contact = "api/delete.php";

    public static final String Endpoint_get_all_contacts = Server_http + Server_direction + Web_api + Get_all_contacts;
    public static final String Endpoint_get_single_contact = Server_http + Server_direction + Web_api + Get_single_contact;
    public static final String Endpoint_create_contact = Server_http + Server_direction + Web_api + Create_contact;
    public static final String Endpoint_update_contact = Server_http + Server_direction + Web_api + Update_contact;
    public static final String Endpoint_delete_contact = Server_http + Server_direction + Web_api + Delete_contact;

//    private static String ipAddres() {
//        String ip = null;
//        try {
//            ip = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        return ip;
//    }
}
