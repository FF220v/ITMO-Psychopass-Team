[ISSUE_20](https://github.com/FF220v/ITMO-Psychopass-Team/issues/20)

As a policeman I wish to see when someone's psychopass value increased above average.

This PR adds a feature to send notifications about psychopass value above average.
Following behaviour was implemented:
- User checks psychopass value via:
  a. PoliceControl interface
  b. Device
- If the value is above average, notification is sent to all the policemen.

To make this possible following has been done:
- A new service NotificationsService was added and deployed. The service can be used by any other service whenever it is needed to send notification.
- A callback to NotificationService was added to AnalysisServer, thus when high psychopass value is detected, a request is sent to notification server. 