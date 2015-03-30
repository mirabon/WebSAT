package jsf;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.FileWorker;

/**
 * Класс для фиксации посещений
 *
 * @author Василий
 */
//@ManagedBean(name = "statistics")

public class SessionControl {

    protected int count;
    protected String tt;
    protected String name = "JSESSIONID";
    protected String fileName = "./../logs/count.txt";

    public SessionControl() {
        try {
            count = Integer.valueOf(FileWorker.read(fileName));
            if (getCookie()) {
                
            } else {
                setCookie();
                setCount();
                FileWorker.write(Integer.toString(count), fileName);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SessionControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public int getCount() {
        return count;
    }

    public void setCount() {
        count++;
    }

    public void setCookie() {
        String value = String.valueOf(System.currentTimeMillis());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath(request.getContextPath());
        }
        cookie.setMaxAge(31536000);
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    public boolean getCookie() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (Cookie userCookie : userCookies) {
                if (userCookie.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
}
