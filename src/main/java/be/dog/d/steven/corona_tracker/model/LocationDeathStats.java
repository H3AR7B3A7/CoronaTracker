package be.dog.d.steven.corona_tracker.model;

public class LocationDeathStats {

    private String state;
    private String region;
    private int latestTotalDeath;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getLatestTotalDeath() {
        return latestTotalDeath;
    }

    public void setLatestTotalDeath(int latestTotalDeath) {
        this.latestTotalDeath = latestTotalDeath;
    }

    @Override
    public String toString() {
        return "LocationDeathStats{" +
                "state='" + state + '\'' +
                ", region='" + region + '\'' +
                ", latestTotalDeath=" + latestTotalDeath +
                '}';
    }
}
