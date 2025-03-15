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
    private final String userName;
    private final String pictureUrl;

    public User(String userName, String pictureUrl) {
        this.userName = userName;
        this.pictureUrl = pictureUrl;
    }

    public String getUser() {
        return userName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
