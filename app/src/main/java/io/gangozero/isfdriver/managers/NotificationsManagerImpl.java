package io.gangozero.isfdriver.managers;

import io.gangozero.isfdriver.models.NotificationModel;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by eleven on 10/09/2016.
 */
public class NotificationsManagerImpl implements NotificationsManager {

	@Override public Observable<NotificationModel> notificationsObservable() {
		return Observable.create(new Observable.OnSubscribe<NotificationModel>() {
			@Override public void call(Subscriber<? super NotificationModel> subscriber) {
				while (true) {
					try {
						Thread.sleep(1000);
						subscriber.onNext(new NotificationModel(NotificationModel.TYPE.random(), "Hello"));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
