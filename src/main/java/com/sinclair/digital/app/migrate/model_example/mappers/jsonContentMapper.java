package com.sinclair.digital.app.migrate.model.mappers;

import org.json.JSONArray;
import org.json.JSONObject;
import com.sinclair.digital.app.utils.TimestampConvert;
import com.sinclair.digital.app.migrate.model.Content;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class jsonContentMapper {
    private final static Logger logger = Logger.getLogger(jsonContentMapper.class);

    public List<Content> jsonMapper(JSONArray jsonArray) {
        List<Content> storeModel = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            TimestampConvert timestampConvert = new TimestampConvert();
            JSONObject currJson = jsonArray.getJSONObject(i);
            Content currContent = new Content();


            currContent.setUuid(currJson.getString("uuid"));

            if (!currJson.isNull("uuid_plan")) {
                currContent.setUuidPlan(currJson.getString("uuid_plan"));
            }
            if (!currJson.isNull("uuid_location")) {
                currContent.setUuidLocation(currJson.getString("uuid_location"));
            }
            if (!currJson.isNull("created")) {
                java.sql.Timestamp sqlTimestamp = timestampConvert.stringToTimestamp(currJson.getString("created"));
                currContent.setCreated(sqlTimestamp);
            }
            if (!currJson.isNull("version")) {
                currContent.setVersion(currJson.getInt("version"));
            }
            if (!currJson.isNull("last_version")) {
                currContent.setLastVersion(currJson.getBoolean("last_version"));
            }
            if (!currJson.isNull("type")) {
                currContent.setType(currJson.getString("type"));
            }
            if (!currJson.isNull("content_type")) {
                currContent.setContentType(currJson.getString("content_type"));
            }
            if (!currJson.isNull("headline")) {
                currContent.setHeadline(currJson.getString("headline"));
            }
            if (!currJson.isNull("summary")) {
                currContent.setSummary(currJson.getString("summary"));
            }
            if (!currJson.isNull("canonical_url")) {
                currContent.setCanonicalUrl(currJson.getString("canonical_url"));
            }
            if (!currJson.isNull("published_date")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("published_date"));
                currContent.setPublishedDate(sqlDate);
            }
            if (!currJson.isNull("published_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("published_date_local"));
                currContent.setPublishedDateLocal(sqlDate);
            }
            if (!currJson.isNull("original_published_date")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("original_published_date"));
                currContent.setOriginalPublishedDate(sqlDate);
            }
            if (!currJson.isNull("original_published_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("original_published_date_local"));
                currContent.setOriginalPublishedDateLocal(sqlDate);
            }
            if (!currJson.isNull("scheduled_date")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("scheduled_date"));
                currContent.setScheduledDate(sqlDate);
            }
            if (!currJson.isNull("scheduled_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("scheduled_date_local"));
                currContent.setScheduledDateLocal(sqlDate);
            }
            if (!currJson.isNull("expires_date")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("expires_date"));
                currContent.setExpiresDate(sqlDate);
            }
            if (!currJson.isNull("expires_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("expires_date_local"));
                currContent.setExpiresDateLocal(sqlDate);
            }
            if (!currJson.isNull("terminated_date")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("terminated_date"));
                currContent.setTerminatedDate(sqlDate);
            }
            if (!currJson.isNull("terminated_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("terminated_date_local"));
                currContent.setTerminatedDateLocal(sqlDate);
            }
            if (!currJson.isNull("last_saved_date")) {
                java.sql.Timestamp sqlTimestamp = timestampConvert.stringToTimestamp(currJson.getString("last_saved_date"));
                currContent.setLastSavedDate(sqlTimestamp);
            }
            if (!currJson.isNull("last_saved_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("last_saved_date_local"));
                currContent.setLastSavedDateLocal(sqlDate);
            }
            if (!currJson.isNull("publish_authorization_date")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("publish_authorization_date"));
                currContent.setPublishAuthorizationDate(sqlDate);
            }
            if (!currJson.isNull("publish_authorization_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("publish_authorization_date_local"));
                currContent.setPublishAuthorizationDateLocal(sqlDate);
            }
            if (!currJson.isNull("unpublish_authorization_date")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("unpublish_authorization_date"));
                currContent.setUnpublishAuthorizationDate(sqlDate);
            }
            if (!currJson.isNull("unpublish_authorization_date_local")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("unpublish_authorization_date_local"));
                currContent.setUnpublishAuthorizationDateLocal(sqlDate);
            }
            if (!currJson.isNull("aspect_ratio")) {
                currContent.setAspectRatio(currJson.getDouble("aspect_ratio"));
            }
            if (!currJson.isNull("height")) {
                currContent.setHeight(currJson.getInt("height"));
            }
            if (!currJson.isNull("width")) {
                currContent.setWidth(currJson.getInt("width"));
            }
            if (!currJson.isNull("url")) {
                currContent.setUrl(currJson.getString("url"));
            }
            if (!currJson.isNull("caption")) {
                currContent.setCaption(currJson.getString("caption"));
            }
            if (!currJson.isNull("thumb_url")) {
                currContent.setThumbUrl(currJson.getString("thumb_url"));
            }
            if (!currJson.isNull("drm")) {
                currContent.setDrm(currJson.getString("drm"));
            }
            if (!currJson.isNull("external_id")) {
                currContent.setExternalId(currJson.getString("external_id"));
            }
            if (!currJson.isNull("story")) {
                currContent.setStory(currJson.getString("story"));
            }
            if (!currJson.isNull("byline")) {
                currContent.setByline(currJson.getString("byline"));
            }
            if (!currJson.isNull("title")) {
                currContent.setTitle(currJson.getString("title"));
            }
            if (!currJson.isNull("start")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("start"));
                currContent.setStart(sqlDate);
            }
            if (!currJson.isNull("end")) {
                java.sql.Timestamp sqlDate = timestampConvert.stringToTimestamp(currJson.getString("end"));
                currContent.setEnd(sqlDate);
            }
            if (!currJson.isNull("recurring")) {
                currContent.setRecurring(currJson.getBoolean("recurring"));
            }
            if (!currJson.isNull("frequency")) {
                currContent.setFrequency(currJson.getString("frequency"));
            }
            if (!currJson.isNull("occurrences")) {
                currContent.setOccurrences(currJson.getInt("occurrences"));
            }
            if (!currJson.isNull("date_line")) {
                currContent.setDateLine(currJson.getString("date_line"));
            }
            if (!currJson.isNull("attribute")) {
                currContent.setAttribute(currJson.getString("attribute"));
            }
            if (!currJson.isNull("updated_by")) {
                currContent.setUpdatedBy(currJson.getString("updated_by"));
            }
            if (!currJson.isNull("original")) {
                currContent.setOriginal(currJson.getString("original"));
            }
            if (!currJson.isNull("alt")) {
                currContent.setAlt(currJson.getString("alt"));
            }
            if (!currJson.isNull("metadata")) {
                currContent.setMetadata(currJson.getString("metadata"));
            }
            if (!currJson.isNull("large_16x9_url")) {
                currContent.setLarge16x9Url(currJson.getString("large_16x9_url"));
            }
            if (!currJson.isNull("medium_16x9_url")) {
                currContent.setMedium16x9Url(currJson.getString("medium_16x9_url"));
            }
            if (!currJson.isNull("small_16x9_url")) {
                currContent.setSmall16x9Url(currJson.getString("small_16x9_url"));
            }
            if (!currJson.isNull("large_36x25_url")) {
                currContent.setLarge36x25Url(currJson.getString("large_36x25_url"));
            }
            if (!currJson.isNull("medium_36x25_url")) {
                currContent.setMedium36x25Url(currJson.getString("medium_36x25_url"));
            }
            if (!currJson.isNull("small_36x25_url")) {
                currContent.setSmall36x25Url(currJson.getString("small_36x25_url"));
            }
            if (!currJson.isNull("large_1x1_url")) {
                currContent.setLarge1x1Url(currJson.getString("large_1x1_url"));
            }
            if (!currJson.isNull("medium_1x1_url")) {
                currContent.setMedium1x1Url(currJson.getString("medium_1x1_url"));
            }
            if (!currJson.isNull("small_1x1_url")) {
                currContent.setSmall1x1Url(currJson.getString("small_1x1_url"));
            }
            if (!currJson.isNull("original_url")) {
                currContent.setOriginalUrl(currJson.getString("original_url"));
            }
            if (!currJson.isNull("large_scale_url")) {
                currContent.setLargeScaleUrl(currJson.getString("large_scale_url"));
            }
            if (!currJson.isNull("small_scale_url")) {
                currContent.setSmallScaleUrl(currJson.getString("small_scale_url"));
            }
            if (!currJson.isNull("original_blur_url")) {
                currContent.setOriginalBlurUrl(currJson.getString("original_blur_url"));
            }
            if (!currJson.isNull("large_blur_url")) {
                currContent.setLargeBlurUrl(currJson.getString("large_blur_url"));
            }
            if (!currJson.isNull("small_blur_url")) {
                currContent.setSmallBlurUrl(currJson.getString("small_blur_url"));
            }
            if (!currJson.isNull("version_set_uuid")) {
                currContent.setVersionSetUuid(currJson.getString("version_set_uuid"));
            }
            if (!currJson.isNull("version_status")) {
                currContent.setVersionStatus(currJson.getString("version_status"));
            }
            if (!currJson.isNull("order_number")) {
                currContent.setOrderNumber(currJson.getInt("order_number"));
            }
            if (!currJson.isNull("security_context")) {
                currContent.setSecurityContext(currJson.getString("security_context"));
            }
            if (!currJson.isNull("update_token")) {
                currContent.setUpdateToken(currJson.getString("update_token"));
            }
            if (!currJson.isNull("gallery_allowed")) {
                currContent.setGalleryAllowed(currJson.getBoolean("gallery_allowed"));
            }
            if (!currJson.isNull("ugc_userid")) {
                currContent.setUgcUserid(currJson.getString("ugc_userid"));
            }
            if (!currJson.isNull("ugc_status")) {
                currContent.setUgcStatus(currJson.getString("ugc_status"));
            }
            if (!currJson.isNull("ugc_batch")) {
                currContent.setUgcBatch(currJson.getString("ugc_batch"));
            }
            if (!currJson.isNull("ugc_siteslug")) {
                currContent.setUgcSiteslug(currJson.getString("ugc_siteslug"));
            }
            if (!currJson.isNull("user_id")) {
                currContent.setUserId(currJson.getLong("user_id"));
            }
            if (!currJson.isNull("username")) {
                currContent.setUsername(currJson.getString("username"));
            }
            if (!currJson.isNull("status")) {
                currContent.setStatus(currJson.getString("status"));
            }
            if (!currJson.isNull("bulk_upload_uid")) {
                currContent.setBulkUploadUid(currJson.getString("bulk_upload_uid"));
            }
            if (!currJson.isNull("video_status")) {
                currContent.setVideoStatus(currJson.getString("video_status"));
            }
            if (!currJson.isNull("mp4_url")) {
                currContent.setMp4Url(currJson.getString("mp4_url"));
            }
            if (!currJson.isNull("brand_uuid")) {
                currContent.setBrandUuid(currJson.getString("brand_uuid"));
            }
			
            storeModel.add(currContent);

        }

        return storeModel;

    }
}