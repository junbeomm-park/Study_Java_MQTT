package basic;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

//MQTT클라이언트 작성 - subscriber
//구독신청을 하고 메시지가 전달되는 것을 대기
//1. MqttCallback인터페이스를 상속
//2. MqttCallback인터페이스의 메소드를 오버라이딩
//3. MqttClient객체를 만들기 - broker와 통신하는 역할을 담당, subscriber, publisher의 역할을 담당
//4. MqttConnectOptions를 이용해서 MQTT의 연결정보를 설정한다.
public class MyMqtt_Sub_client implements MqttCallback {
	private MqttClient mqttclient;
	private MqttConnectOptions mqttOption;
	public MyMqtt_Sub_client init(String server, String clientId) {
		//mqtt클라이언트가 연결하기 위해서 필요한 정보를 설정
		mqttOption = new MqttConnectOptions();
		mqttOption.setCleanSession(true);
		mqttOption.setKeepAliveInterval(30);
		try {
			//broker에 subscribe하기 위한 클라이언트객체를 생성
			mqttclient = new MqttClient(server, clientId);
			//클라이언트객체에 콜백을 셋팅 - subscribe하면서 적절한 시점에 메소드가 호출될 수 있다.
			mqttclient.setCallback(this);
			//mqttclient가 broker에 연결할 수 있도록 설정
			mqttclient.connect(mqttOption);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return this;
	}
	//커넥션이 종료되면 호출 - 통신오류로 연결이 끊어진 경우에 호출
	@Override
	public void connectionLost(Throwable arg0) {
		
	}
	//메시지 배달이 완료되면 호출
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
	}
	//메시지가 도착하면 호출되는 메소드 - topic(broker구독신청한 topic명), MqttMessage는 메시지
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("=================메시지 도착==================");
		System.out.println(message+","+" topic : "+topic+", id : "+message.getId()+
													", Payload : "+new String(message.getPayload()));
	}
	//구독신청하기
	public boolean subscribe(String topic) {
		try {
			if(topic != null) {
				mqttclient.subscribe(topic, 0); //topic, Qos는 메시지를 전달하고 관리하는 방법(품질)
			}
		} catch (MqttException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String args[]) {
		MyMqtt_Sub_client client= new MyMqtt_Sub_client();
		//MqttClient객체가 broker에 연결되고 구독신청
		client.init("tcp://192.168.0.15:1883", "myid").subscribe("led");
	}
}
