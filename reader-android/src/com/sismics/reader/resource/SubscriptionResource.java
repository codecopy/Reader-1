package com.sismics.reader.resource;

import android.content.Context;

import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.sismics.android.SismicsHttpResponseHandler;
import com.sismics.reader.constant.Constants;

/**
 * Access to /subscription API.
 * 
 * @author bgamard
 */
public class SubscriptionResource extends BaseResource {

    /**
     * Resource initialization.
     * @param context
     */
    private static void init(Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    /**
     * GET /subscription.
     * @param context
     * @param responseHandler
     */
    public static void list(Context context, boolean unread, SismicsHttpResponseHandler responseHandler) {
        init(context);
        
        RequestParams params = new RequestParams();
        params.put("unread", Boolean.toString(unread));
        client.get(Constants.READER_API_URL + "/subscription", params, responseHandler);
    }
    
    /**
     * GET articles feed.
     * @param context
     * @param responseHandler
     */
    public static void feed(Context context, String url, boolean unread, int limit, int offset, SismicsHttpResponseHandler responseHandler) {
        init(context);
        
        RequestParams params = new RequestParams();
        params.put("unread", Boolean.toString(unread));
        params.put("limit", Integer.toString(limit));
        params.put("offset", Integer.toString(offset));
        client.get(Constants.READER_API_URL + url, params, responseHandler);
    }
    
    /**
     * Cancel pending requests.
     * @param context
     */
    public static void cancel(Context context) {
        client.cancelRequests(context, true);
    }
}
