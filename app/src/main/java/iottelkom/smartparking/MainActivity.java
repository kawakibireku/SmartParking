package iottelkom.smartparking;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    GridView simpleGrid;
    ArrayList<Device> devlist = new ArrayList<>();
    MyAdapter myAdapter;
    int i = 0;
    int j = 0;
    int l = 0;
//    int data;
    ArrayList<String> dev = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getlist();
        callAsynchronousTask();
    }
    //initialize gridview
    public void getlist(){
      new getlistDevice().execute();
    simpleGrid = (GridView) findViewById(R.id.gridview);
    myAdapter=new MyAdapter(this,R.layout.devicelist,devlist);

 }
    //handler every 5 seconds and 2 seconds delay for getData from every devices
    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            for(String x : dev){
//                                System.out.println("j "+j);
                                getData performBackgroundTask = new getData();
                                performBackgroundTask.execute(x);
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 2000, 5000); //execute in every 500 ms
    }
    //getListdevice from platform antares.id
    private class getlistDevice extends AsyncTask<String, Void, String> {
        String out;
        Response response;

        @Override
        protected String doInBackground(String... params) {

            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://platform.antares.id:8080/~/in-cse/in-name/SmartParking?fu=1&ty=3")
                        .get()
                        .addHeader("x-m2m-origin", "847a192494290511:5f77aeedf2d2c836")
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "a720dcde-9a06-5517-27e2-af038810e7ae")
                        .build();

                response = client.newCall(request).execute();
                out = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("No Connection");
                out = e.getMessage();
            }
            return out;
        }

        @Override
        protected void onPostExecute(String out) {

            try {
                String[] outSplit = out.split("\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/|\\s+|[\"^]");
                String temp = "NULL";
                for (String item : outSplit) {
                    if(temp.equals("SmartParking")){
                        dev.add(item);
                        devlist.add(new Device(item,R.drawable.redcar));
                        i++;
                    }
                    temp = item;
                }
                simpleGrid.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    //get value data from platform antares.id
    private class getData extends AsyncTask<String, Void, String> {
        String out;
        Response response;
        OkHttpClient client = new OkHttpClient();
        String id;
        @Override
        protected String doInBackground(String... params) {
            String identifier = params[0];
            id = identifier;
            System.out.println("doInBackground: (GetData)"+identifier);

            try {
                j = 0;
                /*if(selectedevice.equals("IRSensor")){
                    Request request = new Request.Builder()
                            .url("http://platform.antares.id:8080/~/in-cse/in-name/SmartParking/IRSensor/la")
                            .get()
                            .addHeader("x-m2m-origin", "847a192494290511:5f77aeedf2d2c836")
                            .addHeader("content-type", "application/json")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "3320e013-df1b-cfea-631c-a71382835825")
                            .build();
                    response = client.newCall(request).execute();
                    out = response.body().string();
                }else if(selectedevice.equals("MagneticSensor")){
                    Request request = new Request.Builder()
                            .url("http://platform.antares.id:8080/~/in-cse/in-name/SmartParking/MagneticSensor/la")
                            .get()
                            .addHeader("x-m2m-origin", "847a192494290511:5f77aeedf2d2c836")
                            .addHeader("content-type", "application/json")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "54fc13d2-2400-879a-ccbf-1b568f9bdb7b")
                            .build();
                    response = client.newCall(request).execute();
                    out = response.body().string();
                }*/
                    Request request = new Request.Builder()
                            .url("http://platform.antares.id:8080/~/in-cse/in-name/SmartParking/"+identifier+"/la")
                            .get()
                            .addHeader("x-m2m-origin", "847a192494290511:5f77aeedf2d2c836")
                            .addHeader("content-type", "application/json")
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "54fc13d2-2400-879a-ccbf-1b568f9bdb7b")
                            .build();
                    response = client.newCall(request).execute();
                    out = response.body().string();

            } catch (Exception e) {
                j = 1;
                e.printStackTrace();
                out = e.getMessage();
                System.out.println("No Connection");
            }
            return out;

        }

        @Override
        protected void onPostExecute(String out) {
            String identifier = null;
//            TextView tvData = (TextView) findViewById(R.id.tvValue);
            try {
                identifier = id;
                JSONObject output = (new JSONObject(out)).getJSONObject("m2m:cin");
                String outData = output.getString("con");
                int data = Integer.parseInt(outData.replaceAll("[\\D]", ""));
                if(j == 0){
                    cek(identifier,data);
                }else{
                    System.out.println("err");
                }
                simpleGrid.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Data did not exist");
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

    }
    //use in getData,to check every value in each devices, and update change value to gridview
    public void cek(String identifier, Integer data){
        System.out.println("DEVICE ID: " +identifier);

        for(String m : dev){ //someshit
            System.out.println("DEVICE M : " +m);
            if(l > dev.size()-1){
                l = 0;
            }
            if(m.equals(identifier)){
                System.out.println("INDEX L: "+l);

                if(data == 1){
                    devlist.set(l,new Device(identifier,R.drawable.redcar));
                }else if(data == 0){
                    devlist.set(l,new Device(identifier,R.drawable.greencar));
                }
                l++;
            }else{
                System.out.println("DATA NOT SAME");
            }
        }

    }


}
