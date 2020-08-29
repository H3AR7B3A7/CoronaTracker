package be.dog.d.steven.corona_tracker.model;

public class LocationConfirmedStats {

    private String state;
    private String region;
    private int latestTotalCases;
    private int deltaFromPreviousCases;

    public String getState() {
        return state;
    }

    public String getRegion() {
        return region;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRegion(String region) {
        this.region = region;
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
        return "LocationConfirmedStats{" +
                "state='" + state + '\'' +
                ", region='" + region + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", deltaFromPreviousCases=" + deltaFromPreviousCases +
                '}';
    }
}
