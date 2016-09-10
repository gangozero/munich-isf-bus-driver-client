package io.gangozero.isfdriver.managers;

import io.gangozero.isfdriver.models.NotificationModel;
import rx.Observable;

/**
 * Created by eleven on 10/09/2016.
 */
public interface NotificationsManager {
	Observable<NotificationModel> notificationsObservable();
}
