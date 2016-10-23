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
import java.util.Objects;

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

    public void deleteToDo (Marker marker) {
        SQLiteDatabase sql = db.getWritableDatabase();
        String selection = LocationDbSchema.ID_TITLE + " =?";
        String[] selectionArgs = {String.valueOf(marker.getId())};
        sql.delete(LocationDbSchema.TABLE_NAME, selection, selectionArgs);
        sql.close();
    }

//    public ArrayList<Object> deleteToDo (Marker marker) {
//        ArrayList<Object> location = new ArrayList<>();
//        SQLiteDatabase sql = db.getWritableDatabase();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("delete from ");
//        sb.append(LocationDbSchema.TABLE_NAME);
//        sb.append(" where ");
//        sb.append(LocationDbSchema.LAT_TITLE);
//        sb.append("=");
//        sb.append(marker.getId());
//
//        Cursor c = sql.rawQuery(sb.toString(), null);
//
//        c.moveToFirst();
//        c.close();
//
//
//
//        return taskArray;
//    }


    public ArrayList<Marker> getAll(GoogleMap mMap) {
        ArrayList<Marker> locList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();


        Cursor c = sql.rawQuery("select * from " + LocationDbSchema.TABLE_NAME, null);

        c.moveToFirst();

        while(!c.isAfterLast()) {
            long readID = c.getLong(0);
            Double readLat = c.getDouble(1);
            Double readLon = c.getDouble(2);
            String readTitle = c.getString(3);
            String readSnippet = c.getString(4);

            LatLng position = new LatLng(readLat,readLon);

            Marker location = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(readTitle)
                    .snippet(readSnippet));

            locList.add(location);

            c.moveToNext();
        }

        sql.close();
        return locList;
    }
//    public ArrayList<Object> getAll() {
//        ArrayList<Object> location = new ArrayList<>();
//        SQLiteDatabase sql = db.getReadableDatabase();
//
//
//        Cursor c = sql.rawQuery("select * from " + LocationDbSchema.TABLE_NAME, null);
//
//        c.moveToFirst();
//
//        while(!c.isAfterLast()) {
//            long readID = c.getLong(0);
//            Double readLat = c.getDouble(1);
//            Double readLon = c.getDouble(2);
//            String readTitle = c.getString(3);
//            String readSnippet = c.getString(4);\
//
//
//            location.add(readLat);
//            location.add(readLon);
//            location.add(readTitle);
//            location.add(readSnippet);
//
//            c.moveToNext();
//        }
//
//        sql.close();
//        return location;
//    }
//
//    public ArrayList<Object> updateToDo(ToDo task) {
//        ArrayList<ToDo> taskArray = new ArrayList<>();
//        SQLiteDatabase sql = db.getWritableDatabase();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("update ");
//        sb.append(TaskDbSchema.TABLE_NAME);
//        sb.append(" set ");
//        sb.append(TaskDbSchema.NAME_TITLE);
//        sb.append("='");
//        sb.append(task.getTaskName());
//        sb.append("', ");
//        sb.append(TaskDbSchema.STATUS_TITLE);
//        sb.append("=");
//        sb.append(task.getStatus());
//        sb.append(" where ");
//        sb.append(TaskDbSchema.ID_TITLE);
//        sb.append("=");
//        sb.append(task.getId());
//
//        Cursor c = sql.rawQuery(sb.toString(), null);
//
//        c.moveToFirst();
//        c.close();
//
//
//
//        return taskArray;
//    }




}
