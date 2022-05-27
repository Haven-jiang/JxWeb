package com.Haven.service;

import com.Haven.entity.UserYouthData;

public interface UserYouthTaskService {
    boolean registerTask(UserYouthData userYouthData);

    boolean modifyTask(UserYouthData userYouthData);

    boolean modifyTask(UserYouthData lastUser, UserYouthData currentUser);

    boolean removeTask(UserYouthData userYouthData);
}
