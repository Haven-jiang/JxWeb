create database myWebsite;
use myWebsite;
create table if not exists qrtz_calendars
(
    SCHED_NAME    varchar(120) not null,
    CALENDAR_NAME varchar(200) not null,
    CALENDAR      blob         not null,
    primary key (SCHED_NAME, CALENDAR_NAME)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_fired_triggers
(
    SCHED_NAME        varchar(120) not null,
    ENTRY_ID          varchar(95)  not null,
    TRIGGER_NAME      varchar(200) not null,
    TRIGGER_GROUP     varchar(200) not null,
    INSTANCE_NAME     varchar(200) not null,
    FIRED_TIME        bigint       not null,
    SCHED_TIME        bigint       not null,
    PRIORITY          int          not null,
    STATE             varchar(16)  not null,
    JOB_NAME          varchar(200) null,
    JOB_GROUP         varchar(200) null,
    IS_NONCONCURRENT  varchar(1)   null,
    REQUESTS_RECOVERY varchar(1)   null,
    primary key (SCHED_NAME, ENTRY_ID)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_job_details
(
    SCHED_NAME        varchar(120) not null,
    JOB_NAME          varchar(200) not null,
    JOB_GROUP         varchar(200) not null,
    DESCRIPTION       varchar(250) null,
    JOB_CLASS_NAME    varchar(250) not null,
    IS_DURABLE        varchar(1)   not null,
    IS_NONCONCURRENT  varchar(1)   not null,
    IS_UPDATE_DATA    varchar(1)   not null,
    REQUESTS_RECOVERY varchar(1)   not null,
    JOB_DATA          blob         null,
    primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_locks
(
    SCHED_NAME varchar(120) not null,
    LOCK_NAME  varchar(40)  not null,
    primary key (SCHED_NAME, LOCK_NAME)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_paused_trigger_grps
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_GROUP varchar(200) not null,
    primary key (SCHED_NAME, TRIGGER_GROUP)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_scheduler_state
(
    SCHED_NAME        varchar(120) not null,
    INSTANCE_NAME     varchar(200) not null,
    LAST_CHECKIN_TIME bigint       not null,
    CHECKIN_INTERVAL  bigint       not null,
    primary key (SCHED_NAME, INSTANCE_NAME)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_triggers
(
    SCHED_NAME     varchar(120) not null,
    TRIGGER_NAME   varchar(200) not null,
    TRIGGER_GROUP  varchar(200) not null,
    JOB_NAME       varchar(200) not null,
    JOB_GROUP      varchar(200) not null,
    DESCRIPTION    varchar(250) null,
    NEXT_FIRE_TIME bigint       null,
    PREV_FIRE_TIME bigint       null,
    PRIORITY       int          null,
    TRIGGER_STATE  varchar(16)  not null,
    TRIGGER_TYPE   varchar(8)   not null,
    START_TIME     bigint       not null,
    END_TIME       bigint       null,
    CALENDAR_NAME  varchar(200) null,
    MISFIRE_INSTR  smallint     null,
    JOB_DATA       blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_triggers_ibfk_1
        foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP) references qrtz_job_details (SCHED_NAME, JOB_NAME, JOB_GROUP)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_blob_triggers
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_NAME  varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    BLOB_DATA     blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_blob_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_cron_triggers
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(200) not null,
    TRIGGER_GROUP   varchar(200) not null,
    CRON_EXPRESSION varchar(200) not null,
    TIME_ZONE_ID    varchar(80)  null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_cron_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_simple_triggers
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(200) not null,
    TRIGGER_GROUP   varchar(200) not null,
    REPEAT_COUNT    bigint       not null,
    REPEAT_INTERVAL bigint       not null,
    TIMES_TRIGGERED bigint       not null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_simple_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists qrtz_simprop_triggers
(
    SCHED_NAME    varchar(120)   not null,
    TRIGGER_NAME  varchar(200)   not null,
    TRIGGER_GROUP varchar(200)   not null,
    STR_PROP_1    varchar(512)   null,
    STR_PROP_2    varchar(512)   null,
    STR_PROP_3    varchar(512)   null,
    INT_PROP_1    int            null,
    INT_PROP_2    int            null,
    LONG_PROP_1   bigint         null,
    LONG_PROP_2   bigint         null,
    DEC_PROP_1    decimal(13, 4) null,
    DEC_PROP_2    decimal(13, 4) null,
    BOOL_PROP_1   varchar(1)     null,
    BOOL_PROP_2   varchar(1)     null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_simprop_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create index SCHED_NAME
    on qrtz_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);

create table if not exists user_email_info
(
    uuid  varchar(30) not null
        primary key,
    email varchar(50) null
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists user_result_image
(
    uuid               varchar(30)  not null
        primary key,
    current_image_path varchar(500) not null,
    history_image_path blob         not null
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists user_youth_data
(
    userid           varchar(25)          not null,
    nid              varchar(25)          not null,
    uuid             varchar(30)          not null,
    status           tinyint(1) default 0 not null,
    trigger_name     varchar(100)         not null,
    trigger_group    varchar(100)         not null,
    job_name         varchar(100)         not null,
    job_group        varchar(100)         not null,
    cron             varchar(40)          not null,
    create_time      datetime             not null comment '????????????',
    update_time      datetime             null comment '????????????',
    last_finish_time datetime             null comment '??????????????????',
    finish_history   blob                 null comment '????????????',
    send_history     blob                 null comment '??????????????????',
    email_id         varchar(30)          null,
    image_id         varchar(30)          null,
    watermark_text   varchar(80)          null,
    primary key (userid, nid),
    constraint user_youth_data_pk
        unique (job_name, job_group, trigger_name, trigger_group),
    constraint user_youth_data_uuid_uindex
        unique (uuid),
    constraint user_youth_data_user_email_info_uuid_fk
        foreign key (email_id) references user_email_info (uuid),
    constraint user_youth_data_user_result_image_uuid_fk
        foreign key (image_id) references user_result_image (uuid)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists web_function_profile
(
    route            varchar(30) default '#' not null,
    primary_describe varchar(30)             null,
    minor_describe   varchar(50)             null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists web_witticism
(
    witticism varchar(50) not null
        primary key,
    constraint web_witticism_witticism_uindex
        unique (witticism)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists youth_course
(
    course      varchar(6) default 'course' not null
        primary key,
    id          varchar(10)                 null,
    title       varchar(30)                 null,
    uri         varchar(100)                null,
    update_time datetime                    null
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists youth_jx_organization
(
    organization_key   varchar(50) not null
        primary key,
    organization_value longblob    null,
    constraint youth_jx_organization_key_uindex
        unique (organization_key)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

