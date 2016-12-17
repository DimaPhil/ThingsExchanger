package odeen.newrssreader.proj.model;

import java.util.Date;

public class Item {
    private String mTitle;
    private Date mPubDate;
    private String mLink;
    private String mDescription;
    private boolean mWatched;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getPubDate() {
        return mPubDate;
    }

    public void setPubDate(Date mPubDate) {
        this.mPubDate = mPubDate;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean isWatched() {
        return mWatched;
    }

    public void setWatched(boolean mWatched) {
        this.mWatched = mWatched;
    }

    @Override
    public String toString() {
        return "title = " + mTitle + ", pubDate = " + mPubDate + ", link = " +
                mLink + ", description = " + mDescription + ", watched = " + mWatched;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Item && mLink.equals(((Item) o).getLink());
    }
}
