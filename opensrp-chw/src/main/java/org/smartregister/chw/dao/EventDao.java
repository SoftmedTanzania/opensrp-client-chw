package org.smartregister.chw.dao;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.application.ChwApplication;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.dao.AbstractDao;
import org.smartregister.sync.helper.ECSyncHelper;

import java.util.List;

import timber.log.Timber;

public class EventDao extends AbstractDao {


    public static List<Event> getEvents(String baseEntityID, String eventType, int limit) {
        String sql = "select json from event where baseEntityId = '" + baseEntityID + "' COLLATE NOCASE and eventType = '" + eventType + "' COLLATE NOCASE order by updatedAt desc limit " + limit;

        final ECSyncHelper syncHelper = ChwApplication.getInstance().getEcSyncHelper();
        DataMap<Event> dataMap = c -> {
            try {
                return syncHelper.convert(new JSONObject(getCursorValue(c, "json")), Event.class);
            } catch (JSONException e) {
                Timber.e(e);
            }
            return null;
        };
        return AbstractDao.readData(sql, dataMap);
    }

    @Nullable
    public static Event getLatestEvent(String baseEntityID, List<String> eventTypes) {
        StringBuilder types = new StringBuilder();
        for (String eventType : eventTypes) {
            if (types.length() > 0)
                types.append(" , ");

            types.append("'").append(eventType).append("'");
        }

        String sql = "select json from event where baseEntityId = '" + baseEntityID + "' COLLATE NOCASE and eventType in (" + types.toString() + ") COLLATE NOCASE order by updatedAt desc limit 1";

        final ECSyncHelper syncHelper = ChwApplication.getInstance().getEcSyncHelper();
        DataMap<Event> dataMap = c -> {
            try {
                return syncHelper.convert(new JSONObject(getCursorValue(c, "json")), Event.class);
            } catch (JSONException e) {
                Timber.e(e);
            }
            return null;
        };

        List<Event> res = AbstractDao.readData(sql, dataMap);
        if (res != null && res.size() > 0)
            return res.get(0);

        return null;
    }

    public static void closeReopenedClients(){
        // select delete events

        Timber.v("Closing kids");
        String sql = "update ec_child set is_closed = 1 " +
                "where  " +
                "base_entity_id in ( " +
                " select baseEntityId from event  " +
                " where eventType in ('Remove Child Under 5','Remove Family Member') " +
                ") or relational_id in ( " +
                " select baseEntityId from event where eventType in ('Remove Family') " +
                ")";

        updateDB(sql);

        Timber.v("Closing family members");
        String sql2 = "update ec_family_member set is_closed = 1 " +
                "where  " +
                "base_entity_id in ( " +
                " select baseEntityId from event  " +
                " where eventType in ('Remove Child Under 5','Remove Family Member') " +
                ") or relational_id in ( " +
                " select baseEntityId from event where eventType in ('Remove Family') " +
                ")";

        updateDB(sql2);

        Timber.v("Closing families");
        String sql3 = "update ec_family set is_closed = 1 " +
                "where  " +
                "base_entity_id in ( " +
                " select baseEntityId from event  " +
                " where eventType in ('Remove Family') " +
                ")";

        updateDB(sql3);
    }
}
