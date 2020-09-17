package be.dog.d.steven.corona_tracker.model;

public class LocationStats {
    private String state;
    private String region;
    private int latestTotalDeath;
    private int deltaFromPreviousDeaths;
    private int latestTotalCases;
    private int deltaFromPreviousCases;

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

    public int getDeltaFromPreviousDeaths() {
        return deltaFromPreviousDeaths;
    }

    public void setDeltaFromPreviousDeaths(int deltaFromPreviousDeaths) {
        this.deltaFromPreviousDeaths = deltaFromPreviousDeaths;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDeltaFromPreviousCases() {
        return deltaFromPreviousCases;
    }

    public void setDeltaFromPreviousCases(int deltaFromPreviousCases) {
        this.deltaFromPreviousCases = deltaFromPreviousCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", region='" + region + '\'' +
                ", latestTotalDeath=" + latestTotalDeath +
                ", deltaFromPreviousDeaths=" + deltaFromPreviousDeaths +
                ", latestTotalCases=" + latestTotalCases +
                ", deltaFromPreviousCases=" + deltaFromPreviousCases +
                '}';
    }
}
