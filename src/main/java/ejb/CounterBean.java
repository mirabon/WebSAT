package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class CounterBean {

    private int hits = 1;
    private Map<String, Long> listSession = new HashMap<String, Long>();

    @Schedule(minute = "*/30", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*", persistent = false)
    public void myTimer() {
        try {
            Long time = System.currentTimeMillis();
            for (Map.Entry<String, Long> entry : listSession.entrySet()) {
                Long accessedTime = entry.getValue() + 1800000; //1800000 мс - время таймаута сессии (30 минут)
                if (accessedTime < time) {
                  listSession.remove(entry.getKey());                   
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public int getHits() {
        return hits++;
    }

    public void setListSession(String session, Long accessedTime) {
        listSession.put(session, accessedTime);
    }

    public Map<String, Long> getListSession() {
        return listSession;
    }
}
