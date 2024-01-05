workspace "dkron scheduler client library" "library for scheduling jobs on promise in spring boot application that is adapter for" {

    model {
        user = person "Library user"
        dkronSchedulerClient = softwareSystem "dkron scheduler client library" "library that manage creation jobs in dkron and notifying system about job execution"{
            jobScheduler = container "JobScheduler" "Facade of library that expose functionality"
            jobExecutionNotifier = container "jobExecutionNotifer" "Notify library user about job execution"
            dkronRestClient = container "dkron rest client" "Manage jobs in dkron instance by rest"
            restWebhook = container "rest webhook" "Webhook for job execution"

            jobScheduler -> dkronRestClient "create, remove jobs to execute"
            restWebhook -> jobExecutionNotifier "notify about job execution"
        }
        dkronApp = softwareSystem "Dkron application"

        user -> jobScheduler "schedule, unschedule jobs"
        jobExecutionNotifier -> user "notify about job execution"
        dkronRestClient -> dkronApp "add, remove jobs to execute" "HTTP/REST"
        dkronApp -> restWebhook "trigger rest webhook about job execution" "HTTP/REST"
    }

    views {
        systemContext dkronSchedulerClient {
            include *
            autolayout
        }
        container dkronSchedulerClient {
            include *
            autolayout
        }
    }

}
