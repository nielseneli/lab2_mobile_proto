package erica.locationscrapbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import layout.LocationDbSchema;


public class DBService {
    LocationDbSchema db;

    public DBService(Context context) {
        db = new LocationDbSchema(context);
    }

    public void addLoc(Marker marker) {
        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LocationDbSchema.LAT_TITLE, marker.getPosition().latitude);
        values.put(LocationDbSchema.LON_TITLE, marker.getPosition().longitude);
        values.put(LocationDbSchema.LOCATION_TITLE, marker.getTitle());
        values.put(LocationDbSchema.DESCRIPTION_TITLE, marker.getSnippet());
        try {
            sql.insertOrThrow(LocationDbSchema.TABLE_NAME, LocationDbSchema.LOCATION_TITLE, values);
        } finally {
            sql.close();
        }
    }

    public void deleteLoc (Marker marker) {
        SQLiteDatabase sql = db.getWritableDatabase();
        String selection = LocationDbSchema.ID_TITLE + " =?";
        String[] selectionArgs = {String.valueOf(marker.getId())};
        sql.delete(LocationDbSchema.TABLE_NAME, selection, selectionArgs);
        sql.close();
    }



    public ArrayList<Location> getAll() {
        ArrayList<Location> locList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery("select * from " + LocationDbSchema.TABLE_NAME, null);

        c.moveToFirst();

        while(!c.isAfterLast()) {
            long readID = c.getLong(0);
            Double readLat = c.getDouble(1);
            Double readLon = c.getDouble(2);
            String readTitle = c.getString(3);
            String readSnippet = c.getString(4);

            LatLng coord = new LatLng(readLat,readLon);
            Location locs = new Location(readID,readTitle,readSnippet,coord);

            locList.add(locs);



            c.moveToNext();
        }

        sql.close();
        return locList;
    }

    public void updateLoc(Marker marker) {
        SQLiteDatabase sql = db.getWritableDatabase();

        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(LocationDbSchema.TABLE_NAME);
        sb.append(" set ");
        sb.append(LocationDbSchema.LOCATION_TITLE);
        sb.append("='");
        sb.append(marker.getTitle());
        sb.append("', ");
        sb.append(LocationDbSchema.LAT_TITLE);
        sb.append("=");
        sb.append(marker.getPosition().latitude);
        sb.append(", ");
        sb.append(LocationDbSchema.LON_TITLE);
        sb.append("=");
        sb.append(marker.getPosition().longitude);
        sb.append(", ");
        sb.append(LocationDbSchema.DESCRIPTION_TITLE);
        sb.append("='");
        sb.append(marker.getSnippet());
        sb.append("' where ");
        sb.append(LocationDbSchema.LAT_TITLE);
        sb.append("=");
        sb.append(marker.getPosition().latitude);
        sb.append(" and ");
        sb.append(LocationDbSchema.LON_TITLE);
        sb.append("=");
        sb.append(marker.getPosition().longitude);

        Cursor c = sql.rawQuery(sb.toString(), null);

        c.moveToFirst();
        c.close();

    }



}
