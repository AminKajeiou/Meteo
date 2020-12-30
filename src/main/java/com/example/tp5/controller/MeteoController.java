package com.example.tp5.controller;

import com.example.tp5.model.Address;
import com.example.tp5.model.AddressRepository;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Optional;



@Controller
public class MeteoController {
    private static HttpURLConnection connection;

    @GetMapping("/meteo")
    public String getMeteo(){

        return "meteoG";
    }

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressRepository addressRepository1;
    @RequestMapping(value = "/meteo", method = RequestMethod.POST)
    public String newAddress(@ModelAttribute Address address, Model model) throws IOException, UnirestException {
        address.setCreation(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        addressRepository1.findAll();
        boolean a=true,b=true;
        int i=0;
        Optional<Address> ad;
        while(a){
            i++;
            ad=addressRepository1.findById((long)i);
            //System.out.println(ad.toString());
            if(ad.isEmpty()) a=false;
        }
        address.setId((long)i);
        //System.out.println(address);
        for(int j=1;j<i;j++){
            //System.out.println("for clause, content "+j+" : \t"+addressRepository1.findById((long)j).get().getContent());
            if(address.getContent().equals(addressRepository1.findById((long)j).get().getContent())) {
                if(address.getAuteur().equals(addressRepository1.findById((long)j).get().getAuteur()))
                    b=false;
            }
        }
        if(b) {
            //System.out.println("b clause");
            addressRepository.save(address);

        }
        model.addAttribute("adresse", address);
        //System.out.println("!b clause");
        String[] coords=  getCoord(address.getContent());
        //System.out.println(coords[0]+"   "+coords[1]);
        float temp=getMeteoInf(coords);
        //System.out.println(temp);
        model.addAttribute("temp",temp);



        return "meteo";
    }

    public String[] getCoord(String content) throws IOException, UnirestException {
        String[] cont=content.split(" ");
        String[] coords= null;
        String api="https://api-adresse.data.gouv.fr/search/",get="?q=";
        for(String s:cont){
            get+=s+"+";
        }
        get.substring(0, get.length() - 1);
        URL url= new URL(api+get);
        connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int status=connection.getResponseCode();
        //System.out.println(status);
        //System.out.println(connection.getResponseMessage());
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        if (status >299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line=reader.readLine())!= null) responseContent.append(line);
            reader.close();
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line=reader.readLine())!= null) responseContent.append(line);
            reader.close();
            //System.out.println(responseContent.toString());
            //System.out.println(responseContent);
            JSONObject jsnobject = new JSONObject(responseContent.toString());
            JSONArray jsonArray = jsnobject.getJSONArray("features");
            //System.out.println("FEATURES :\t "+jsonArray);
            JSONObject explrObject = jsonArray.getJSONObject(0);
            //System.out.println("geometry :\t "+explrObject);
            JSONObject jsonCoord = explrObject.getJSONObject("geometry");
            //System.out.println("COORD :\t "+jsonCoord);
            JSONArray jsonFinal = jsonCoord.getJSONArray("coordinates");
            //System.out.println("Final :\t "+jsonFinal.toString());
            String coord= jsonFinal.toString();
            coord=coord.substring(1,coord.length()-1);
            coords = coord.split(",");
            //System.out.println("test :\t "+coords[0]+"\t"+coords[1]);
        }
        connection.disconnect();

        return coords;

    }

    public Float getMeteoInf(String [] coords) throws IOException {
        Float temp=null;
        String api="http://api.openweathermap.org/data/2.5/weather?lat=";
        String key="8c26ecbb30d206446166c684fcd63d98";
        String unit="metric";
        String finalURL=api+coords[1]+"&lon="+coords[0]+"&appid="+key+"&units="+unit;
        URL url= new URL(finalURL);
        connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int status=connection.getResponseCode();
        //System.out.println(status);
        //System.out.println(connection.getResponseMessage());
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        if (status >299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while((line=reader.readLine())!= null) responseContent.append(line);
            reader.close();
        }else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line=reader.readLine())!= null) responseContent.append(line);
            System.out.println(responseContent.toString());
            reader.close();
            JSONObject jsnobject = new JSONObject(responseContent.toString());
            JSONObject jsonObjmMain = jsnobject.getJSONObject("main");
            temp= jsonObjmMain.getFloat("temp");
            //System.out.println(temp);
        }
        return temp;
    }
}
