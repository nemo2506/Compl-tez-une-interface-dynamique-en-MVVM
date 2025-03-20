/*
 * Copyright (c) 2025. Copyright © 2025 Taj Mahal. Tous droits réservés.
 *
 * Cette application, y compris son code source, sa conception, son contenu et ses fonctionnalités, est la propriété exclusive de Taj Mahal.
 * Toute reproduction, distribution ou utilisation non autorisée, en tout ou en partie, est strictement interdite sans l'accord écrit préalable de Taj Mahal.
 *
 * Développé par Marc Navarro pour Taj Mahal.
 */

package com.openclassrooms.tajmahal.domain.model;

public class User {
    private String userName;
    private String userPictureUrl;
    private final String userPhone;

    public User(String userName, String userPictureUrl, String userPhone) {
        this.userName = userName;
        this.userPictureUrl = userPictureUrl;
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }
}
