package com.drunkmel.drunkmel.helpers;

import android.content.Context;

/**
 * Created by everv on 1/30/2017.
 */
/**
 * Singleton utility class to get the id of a given resource
 * **/
public class ResourceGetter {
    private static ResourceGetter instance;
    Context context;

    public ResourceGetter(Context context) {
        this.context = context;
    }

    public static ResourceGetter getInstance(Context context) {
        if (instance == null) {
            instance = new ResourceGetter(context);
        }
        return instance;
    }

    /**
     * Gets a given resource id by specifing its name and type
     *
     * @param variableName the name of the resource file (example: json, image) or name of the key (example: string language key).
     * @param resourceName the given type of the resource needed.
     * @param packageName where the resource is located, usually given by getPackageName().
     * @throws Exception -1 if the resource is not found.
     *
     * Example:
     * Trying to get the id of a resource from drawable getResourceId("icon", "drawable", getPackageName())
     * Trying to get the id of a string resource  getResourceId("appName", "string", getPackageName())
     */
    /* (accept a raw type for API compatibility) */
    public  int getResourceId(String variableName, String resourceName, String packageName)    {
        try {
            return this.context.getResources().getIdentifier(variableName, resourceName, packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
