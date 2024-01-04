workspace {

    model {
        user = person "Library user"
        dkronSchedulerClient = softwareSystem "dkron scheduler client library"{

        }
        dkronApp = softwareSystem "Dkron application"

        user -> dkronSchedulerClient "schedule, unschedule jobs"
        dkronSchedulerClient -> user "notify about job execution"
        dkronSchedulerClient -> dkronApp "add, remove jobs to execute"
        dkronApp -> dkronSchedulerClient "trigger job execution"
    }

    views {
        systemContext dkronSchedulerClient {
            include *
            autolayout lr
        }
    }

}
