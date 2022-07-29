package DB;

import net.dv8tion.jda.api.entities.Member;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static util.Util.getUserIdentity;

public class DataBaseUtil {

    public static void initialize(List<Member> members) {
        try (DB db = DBMaker.fileDB("file.db").make()) {
            ConcurrentMap<String, Integer> map = db.hashMap("map", Serializer.STRING, Serializer.INTEGER).createOrOpen();
            if (map.isEmpty()) newMap(map, members);
            else updateMap(map, members);
            db.commit();
        }
    }

    private static void updateMap(ConcurrentMap<String, Integer> map, List<Member> members) {
        for (Member member : members) {
            if (!map.containsKey(getUserIdentity(member))) map.put(getUserIdentity(member), 0);
        }
    }

    private static void newMap(ConcurrentMap<String, Integer> map, List<Member> members) {
        for (Member member : members) {
            map.put(getUserIdentity(member), 0);
        }
    }

    public static Map<String, Integer> getPoints() {
        try (DB db = DBMaker.fileDB("file.db").make()) {
            ConcurrentMap<String, Integer> pointsList = db.hashMap("map", Serializer.STRING, Serializer.INTEGER).createOrOpen();
            return severListFromConnection(pointsList);
        }
    }

    public static void updatePoints(String nickname, int amount) {
        try (DB db = DBMaker.fileDB("file.db").make()) {
            ConcurrentMap<String, Integer> map = db.hashMap("map", Serializer.STRING, Serializer.INTEGER).createOrOpen();
            int oldVal = map.get(nickname);
            int val = oldVal+amount;

            if (val<-99999) {
                val = -99999;
            } else if (val>99999) {
                val = 99999;
            }

            map.replace(nickname,val);
            db.commit();
        }
    }

    public static void addUser(String nickname) {
        try (DB db = DBMaker.fileDB("file.db").make()) {
            ConcurrentMap<String, Integer> map = db.hashMap("map", Serializer.STRING, Serializer.INTEGER).createOrOpen();
            map.put(nickname, 0);
            db.commit();
        }
    }

    public static void removeUser(String nickname) {
        try (DB db = DBMaker.fileDB("file.db").make()) {
            ConcurrentMap<String, Integer> map = db.hashMap("map", Serializer.STRING, Serializer.INTEGER).createOrOpen();
            map.remove(nickname);
            db.commit();
        }
    }

    private static Map<String, Integer> severListFromConnection(ConcurrentMap<String, Integer> hashMap) {
        return new HashMap<>(hashMap);
    }

    public static void resetList(List<Member> members){
        try (DB db = DBMaker.fileDB("file.db").make()) {
            ConcurrentMap<String, Integer> map = db.hashMap("map", Serializer.STRING, Serializer.INTEGER).createOrOpen();
            map.clear();
            for (Member member : members) {
                map.put(getUserIdentity(member), 0);
            }
            db.commit();
        }
    }
}
