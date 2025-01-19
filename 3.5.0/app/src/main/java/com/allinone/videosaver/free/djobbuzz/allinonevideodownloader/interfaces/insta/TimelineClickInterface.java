package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.interfaces.insta;


import com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.model.instaio.timeline.TimelineItemModel;

public interface TimelineClickInterface {
    void DownloadClick(int i, TimelineItemModel timelineItemModel);

    void RepostClick(int i, TimelineItemModel timelineItemModel);

    void ShareClick(int i, TimelineItemModel timelineItemModel);

    void TimelineImageClick(int i, TimelineItemModel timelineItemModel);

    void UserProfileClick(int i, TimelineItemModel timelineItemModel);
}
