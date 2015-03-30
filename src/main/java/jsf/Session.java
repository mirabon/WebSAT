package jsf;

import ejb.CounterBean;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "count")
@SessionScoped
public class Session implements Serializable {

    @EJB
    private CounterBean counterBean;

    private int hitCount;

    public Session() {
        this.hitCount = 0;
    }

    public int getHitCount() {
        HttpSession sess = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);   
        if (sess.getId() != null) {
            if (counterBean.getListSession().containsKey(sess.getId())) {
                counterBean.setListSession(sess.getId(), sess.getLastAccessedTime());
                return hitCount;
            } else {
                counterBean.setListSession(sess.getId(), sess.getLastAccessedTime());
                hitCount = counterBean.getHits();
            }
        }
        return hitCount;
    }

    public void setHitCount(int newHits) {
        this.hitCount = newHits;
    }

    public void getMap() {
        for (Map.Entry<String, Long> entry : counterBean.getListSession().entrySet()){
            System.out.println("Сессия: " + entry.getKey() + " " + new Date(entry.getValue()));
        }
    }
}
