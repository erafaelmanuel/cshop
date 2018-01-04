package io.ermdev.cshop.data.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    private Long id;
    private Long userId;
    private String token;
    private Date expiryDate;
    private User user;

    public VerificationToken() {}

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        if(user != null) userId = user.getId();
    }

    public VerificationToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if(user != null) userId = user.getId();
    }

    public Date getExpiryDate() {
        if(expiryDate == null)
            return calculateExpiryDate(EXPIRATION);
        else
            return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
