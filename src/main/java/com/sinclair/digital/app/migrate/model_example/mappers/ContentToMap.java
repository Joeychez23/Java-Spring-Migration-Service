package com.sinclair.digital.app.migrate.model.mappers;

import java.util.LinkedHashMap;

import com.sinclair.digital.app.migrate.model.Content;
import com.sinclair.digital.app.utils.TimestampConvert;
import org.apache.log4j.Logger;

public class ContentToMap {

	private final static Logger logger = Logger.getLogger(jsonContentMapper.class);

	private TimestampConvert timestampConvert = new TimestampConvert();
	
	public ContentToMap () {
		
	}
	
	public LinkedHashMap<String, Object> getMapfromContent(LinkedHashMap<String, Object> map, Content c) {
		putItem(map, "uuid", c.getUuid());
		putItem(map, "alt", c.getAlt());
		double aspectRatio = c.getAspectRatio();
		putItem(map, "aspect_ratio", aspectRatio);
		putItem(map, "attribute", c.getAttribute());
		putItem(map, "brand_uuid", c.getBrandUuid());
		putItem(map, "bulk_upload_uid", c.getBulkUploadUid());
		putItem(map, "byline", c.getByline());
		putItem(map, "canonical_url", c.getCanonicalUrl());
		putItem(map, "caption", c.getCaption());
		putItem(map, "content_type", c.getContentType());
		putItem(map, "created", (c.getCreated() == null ? null : timestampConvert.timestampToString(c.getCreated())));
		putItem(map, "date_line", c.getDateLine());
		putItem(map, "drm", c.getDrm());
		putItem(map, "end", (c.getEnd() == null ? null : timestampConvert.timestampToString(c.getEnd())));
		putItem(map, "expires_date", (c.getExpiresDate() == null ? null : timestampConvert.timestampToString(c.getExpiresDate())));
		putItem(map, "expires_date_local", (c.getExpiresDateLocal() == null ? null : timestampConvert.timestampToString(c.getExpiresDateLocal())));
		putItem(map, "external_id", c.getExternalId());
		putItem(map, "frequency", c.getFrequency());
		putItem(map, "gallery_allowed", c.isGalleryAllowed());
		putItem(map, "headline", c.getHeadline());
		putItem(map, "height", c.getHeight());
		putItem(map, "large_16x9_url", c.getLarge16x9Url());
		putItem(map, "large_1x1_url", c.getLarge1x1Url());
		putItem(map, "large_36x25_url", c.getLarge36x25Url());
		putItem(map, "large_blur_url", c.getLargeBlurUrl());
		putItem(map, "large_scale_url", c.getLargeScaleUrl());
		putItem(map, "last_saved_date", (c.getLastSavedDate() == null ? null : timestampConvert.timestampToString(c.getLastSavedDate())));
		putItem(map, "last_saved_date_local", (c.getLastSavedDateLocal() == null ? null : timestampConvert.timestampToString(c.getLastSavedDateLocal())));
		putItem(map, "last_version", c.isLastVersion());
		putItem(map, "medium_16x9_url", c.getMedium16x9Url());
		putItem(map, "medium_1x1_url", c.getMedium1x1Url());
		putItem(map, "medium_36x25_url", c.getMedium36x25Url());
		putItem(map, "metadata", c.getMetadata());
		putItem(map, "mp4_url", c.getMp4Url());
		putItem(map, "occurrences", c.getOccurrences());
		putItem(map, "order_number", c.getOrderNumber());
		putItem(map, "original", c.getOriginal());
		putItem(map, "original_blur_url", c.getOriginalBlurUrl());
		putItem(map, "original_published_date", (c.getOriginalPublishedDate() == null ? null : timestampConvert.timestampToString(c.getOriginalPublishedDate())));
		putItem(map, "original_published_date_local", (c.getOriginalPublishedDateLocal() == null ? null : timestampConvert.timestampToString(c.getOriginalPublishedDateLocal())));
		putItem(map, "original_url", c.getOriginalUrl());
		putItem(map, "publish_authorization_date", (c.getPublishAuthorizationDate() == null ? null : timestampConvert.timestampToString(c.getPublishAuthorizationDate())));
		putItem(map, "publish_authorization_date_local", (c.getPublishAuthorizationDateLocal() == null ? null : timestampConvert.timestampToString(c.getPublishAuthorizationDateLocal())));
		putItem(map, "published_date", (c.getPublishedDate() == null ? null : timestampConvert.timestampToString(c.getPublishedDate())));
		putItem(map, "published_date_local", (c.getPublishedDateLocal() == null ? null : timestampConvert.timestampToString(c.getPublishedDateLocal())));
		putItem(map, "recurring", c.isRecurring());
		putItem(map, "scheduled_date", (c.getScheduledDate() == null ? null : timestampConvert.timestampToString(c.getScheduledDate())));
		putItem(map, "scheduled_date_local", (c.getScheduledDateLocal() == null ? null : timestampConvert.timestampToString(c.getScheduledDateLocal())));
		putItem(map, "security_context", c.getSecurityContext());
		putItem(map, "small_16x9_url", c.getSmall16x9Url());
		putItem(map, "small_1x1_url", c.getSmall1x1Url());
		putItem(map, "small_36x25_url", c.getSmall36x25Url());
		putItem(map, "small_blur_url", c.getSmallBlurUrl());
		putItem(map, "small_scale_url", c.getSmallScaleUrl());
		putItem(map, "start", (c.getStart() == null ? null: timestampConvert.timestampToString(c.getStart())));
		putItem(map, "status", c.getStatus());
		putItem(map, "story", c.getStory());
		putItem(map, "summary", c.getSummary());
		putItem(map, "terminated_date", (c.getTerminatedDate() == null ? null : timestampConvert.timestampToString(c.getTerminatedDate())));
		putItem(map, "terminated_date_local", (c.getTerminatedDateLocal() == null ? null : timestampConvert.timestampToString(c.getTerminatedDateLocal())));
		putItem(map, "thumb_url", c.getThumbUrl());
		putItem(map, "title", c.getTitle());
		putItem(map, "type", c.getType());
		putItem(map, "ugc_batch", c.getUgcBatch());
		putItem(map, "ugc_siteslug", c.getUgcSiteslug());
		putItem(map, "ugc_status", c.getUgcStatus());
		putItem(map, "ugc_userid", c.getUgcUserid());
		putItem(map, "unpublish_authorization_date", (c.getUnpublishAuthorizationDate() == null ? null : timestampConvert.timestampToString(c.getUnpublishAuthorizationDate())));
		putItem(map, "unpublish_authorization_date_local", (c.getUnpublishAuthorizationDateLocal() == null ? null : timestampConvert.timestampToString(c.getUnpublishAuthorizationDateLocal())));
		putItem(map, "update_token", c.getUpdateToken());
		putItem(map, "updated_by", c.getUpdatedBy());
		putItem(map, "url", c.getUrl());
		putItem(map, "user_id", c.getUserId());
		putItem(map, "username", c.getUsername());
		putItem(map, "version", c.getVersion());
		putItem(map, "version_set_uuid", c.getVersionSetUuid());
		putItem(map, "version_status", c.getVersionStatus());
		putItem(map, "video_status", c.getVideoStatus());
		putItem(map, "width", c.getWidth());
		
		return map;
	}
	
	private void putItem(LinkedHashMap<String, Object> map, String token, Object obj) {
		if (!token.equals("aspect_ratio")) {
			if (obj != null) {
				map.put(token, obj);
			}			
		} else {
			if ((double) obj != 0.0) {
				map.put(token, obj);
			}
		}
	}
}
