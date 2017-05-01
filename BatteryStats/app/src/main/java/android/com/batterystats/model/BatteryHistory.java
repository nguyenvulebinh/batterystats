package android.com.batterystats.model;

/**
 * Created by nguyenbinh on 30/04/2017.
 */

public class BatteryHistory {
    private String description;
    private String url;
    private String timeReset;
    private boolean head;

    public BatteryHistory() {
        head = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeReset() {
        String[] listArg = timeReset.split("-");
        return listArg[3] + ":" + listArg[4] + ":" + listArg[5] + " " + listArg[2] + "/" + listArg[1] + "/" + listArg[0];
    }

    public String getRawTimeReset(){
        return timeReset;
    }

    public void setTimeReset(String timeReset) {
        this.timeReset = timeReset;
    }

    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
    }
}
