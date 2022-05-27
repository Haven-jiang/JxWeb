package com.Haven.service.impl;

import com.Haven.DTO.CronTaskDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.scheduler.job.YouthLearnJob;
import com.Haven.service.UserYouthTaskService;
import com.Haven.scheduler.util.QuartzUtil;
import org.springframework.stereotype.Service;

@Service
public class UserYouthTaskServiceImpl implements UserYouthTaskService {

    @Override
    public boolean registerTask(UserYouthData userYouthData) {

        CronTaskDTO task = userYouthData.toCronTaskDTO();

        return QuartzUtil.addJob(task, YouthLearnJob.class);
    }

    @Override
    public boolean modifyTask(UserYouthData userYouthData) {

        CronTaskDTO task = userYouthData.toCronTaskDTO();

        return QuartzUtil.modifyJobTime(task);
    }

    @Override
    public boolean modifyTask(UserYouthData lastUser, UserYouthData currentUser) {

        CronTaskDTO lastTask = lastUser.toCronTaskDTO();
        CronTaskDTO currentTask = currentUser.toCronTaskDTO();

        return QuartzUtil.modifyJobTime(lastTask, currentTask, YouthLearnJob.class);
    }

    @Override
    public boolean removeTask(UserYouthData userYouthData) {

        CronTaskDTO task = userYouthData.toCronTaskDTO();

        return QuartzUtil.removeJob(task);
    }
}
