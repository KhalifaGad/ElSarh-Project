/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khalifa.elsarh.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author khalifa
 */
public class JerseyClient {

    public static final String BASE_URI = "http://localhost/alsarh-work/";
    private Client client = null;
    private WebTarget target = null;

    public JerseyClient() {
        client = ClientBuilder.newBuilder()
                .register(MultiPartFeature.class).build();
        // client = ClientBuilder.newClient();
        target = client.target(BASE_URI);

    }

    public void reloadUri() {
        target = null;
        target = client.target(BASE_URI);
    }

    public void uploadUnit(File file1, File file2, File file3,
            File file4, File file5, File file6, String name, String type,
            String rooms, String areaLand, String area, String installmentStatus,
            String village, String status, String delivery, String price) {
        reloadUri();
        target = target.path("api_insert.php");

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(BASE_URI + "api_insert.php");
        StringBody add_unitStr = 
                new StringBody("addUnit", ContentType.MULTIPART_FORM_DATA);
        StringBody unit_nameStr =
                new StringBody(name, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_typeStr =
                new StringBody(type, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_roomsStr =
                new StringBody(rooms, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_area_landStr =
                new StringBody(areaLand, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_areaStr =
                new StringBody(area, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_instalStr =
                new StringBody(installmentStatus,
                        ContentType.MULTIPART_FORM_DATA);
        StringBody unit_villageStr =
                new StringBody(village, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_statusStr =
                new StringBody(status, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_deliveryStr =
                new StringBody(delivery, ContentType.MULTIPART_FORM_DATA);
        StringBody unit_priceStr =
                new StringBody(price, ContentType.MULTIPART_FORM_DATA);


        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("add_unit", add_unitStr);
        builder.addPart("unit_name", unit_nameStr);
        builder.addPart("unit_type", unit_typeStr);
        builder.addPart("unit_rooms", unit_roomsStr);
        builder.addPart("unit_area_land", unit_area_landStr);
        builder.addPart("unit_area", unit_areaStr);
        builder.addPart("unit_instal", unit_instalStr);
        builder.addPart("unit_village", unit_villageStr);
        builder.addPart("unit_status", unit_statusStr);
        builder.addPart("unit_delivery", unit_deliveryStr);
        builder.addPart("unit_price", unit_priceStr);
        
        if(file1 != null){
            FileBody fileBody1 = new FileBody(file1);
            builder.addPart("unit_pic", fileBody1);
        }
        if(file2 != null){
            FileBody fileBody2 = new FileBody(file2);
            builder.addPart("unit_pic1", fileBody2);
        }
        if(file3 != null){
            FileBody fileBody3 = new FileBody(file3);
            builder.addPart("unit_pic2", fileBody3);
        }
        if(file4 != null){
            FileBody fileBody4 = new FileBody(file4);
            builder.addPart("unit_pic3", fileBody4);
        }
        if(file5 != null){
           FileBody fileBody5 = new FileBody(file5);
            builder.addPart("unit_pic4", fileBody5); 
        }
        if(file6 != null){
            FileBody fileBody6 = new FileBody(file6);
            builder.addPart("unit_pic5", fileBody6);
        }

        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        try {

            HttpResponse response = httpClient.execute(httpPost);

            StringBuilder total;
            try (BufferedReader r = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()))) {
                total = new StringBuilder();
                String line = null;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                    total.append(System.lineSeparator());
                }
            }

            System.out.println("yaaaay, we did it ! " + total.toString());
            if (response.hashCode() == 200) {
                System.out.println("yaaaay, we did it ! ");
            }
        } catch (IOException ex) {
            Logger.getLogger(JerseyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getUnitsApi() {
        reloadUri();
        target = target.path("api_view.php");

        //Gson gson = new Gson();
        String input = "";

        Invocation.Builder invocationBuilder = target.
                request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder
                .post(Entity.entity(input, MediaType.APPLICATION_JSON),
                        Response.class);

        ////System.out.println("RESPONSE !!!! : "+response.readEntity(String.class));
        if (response.getStatus() == 200) {
            System.out.println("yaaaay, we did it ! ");
        }
    }

}
