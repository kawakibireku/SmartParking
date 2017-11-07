package iottelkom.smartparking;

/**
 * Created by kawakibireku on 10/26/17.
 */

public class Device {

    String deviceListName;
    int deviceListImage;

    public Device(String deviceName, int deviceImage) {
        this.deviceListImage=deviceImage;
        this.deviceListName=deviceName;
    }

    public String getdeviceName(){
        return deviceListName;
    }
    public int getdeviceImage(){
        return deviceListImage;
    }
}
