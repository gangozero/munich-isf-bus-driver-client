package io.gangozero.isfdriver.managers;

import android.content.Context;
import io.gangozero.isfdriver.models.NotificationModel;
import io.gangozero.isfdriver.utils.SecurityUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.fusesource.mqtt.client.*;
import rx.Observable;
import rx.Subscriber;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import static org.fusesource.hawtbuf.Buffer.utf8;
//import org.eclipse.paho.android.service.MqttAndroidClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by eleven on 10/09/2016.
 */
public class MqttManagerImpl implements MqttManager {

	private Context context;
	private final String protocol = "tcp";
	private final String address = "89.188.110.213";
	private final String amazonAddress = "a1ckk9c9g2t33k.iot.eu-west-1.amazonaws.com";
	private final String clientId = "isf-driver";
	private final int port = 1883;

	private String initTopic = "hello";
	private String initMessage = "test-connect";

	private String notificationsChannel = "notifications";

	public MqttManagerImpl(Context context) {
		this.context = context;
	}

	@Override public void init() {

		//initTestPahoClient();
		//initAmazonPahoClient();
		//initFuzeTest();
	}

	@Override public Observable<NotificationModel> getSubscription() {
		return Observable.create(new Observable.OnSubscribe<NotificationModel>() {
			@Override public void call(Subscriber<? super NotificationModel> subscriber) {
				
			}
		});
	}

	private void initTestPahoClient() {

		try {
			MqttClient client = new MqttClient(protocol + "://" + address + ":" + port, clientId, new MemoryPersistence());
			client.connect();

			client.setCallback(new MqttCallback() {
				@Override public void connectionLost(Throwable cause) {

				}

				@Override public void messageArrived(String topic, MqttMessage message) throws Exception {

				}

				@Override public void deliveryComplete(IMqttDeliveryToken token) {

				}
			});
			client.publish(initTopic, new MqttMessage(initMessage.getBytes()));
			client.subscribe(notificationsChannel);

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	private void initFuzeTest() {
		MQTT mqtt = new MQTT();

		try {

			mqtt.setVersion("3.1");
			mqtt.setSslContext(SecurityUtils.initSSLContext(context));
//			mqtt.setHost("tcp://89.188.110.213:1883")[;
//			mqtt.setHost("tls://a1ckk9c9g2t33k.iot.eu-west-1.amazonaws.com:8883");
			mqtt.setClientId("gangozero");
			mqtt.setCleanSession(false);

//			mqtt.callbackConnection().connect(new Callback<Void>() {
//				@Override public void onSuccess(Void value) {
//
//				}
//
//				@Override public void onFailure(Throwable value) {
//
//				}
//			});

			FutureConnection connection = mqtt.futureConnection();
			connection.connect().await();
			Future<byte[]> f2 = connection.subscribe(new Topic[]{new Topic(utf8("notifications"), QoS.AT_LEAST_ONCE)});

		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//
//			final MqttAndroidClient client = new MqttAndroidClient(
//					context,
//					"tcp://a1ckk9c9g2t33k.iot.eu-west-1.amazonaws.com:8883",
//					"gangozero"
//			);
//
//			MqttConnectOptions options = new MqttConnectOptions();
//
//			client.connect();
//
//			client.connect();
//		} catch (MqttException e) {
//			e.printStackTrace();
//		}
	}

	private void initAmazonPahoClient() {

		try {
			MqttConnectOptions options = new MqttConnectOptions();

			options.setSocketFactory(SecurityUtils.initCertificate(context).getSocketFactory());
//			options.setSocketFactory(SecurityUtils.initSSLContext(context).getSocketFactory());

			MemoryPersistence persistence = new MemoryPersistence();
			MqttClient sampleClient = new MqttClient("ssl://" + amazonAddress + ":8883", clientId, persistence);

			sampleClient.connect();
			sampleClient.publish(initTopic, new MqttMessage(initMessage.getBytes()));
		} catch (MqttException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException | KeyStoreException e) {
			e.printStackTrace();
		}
	}


}
