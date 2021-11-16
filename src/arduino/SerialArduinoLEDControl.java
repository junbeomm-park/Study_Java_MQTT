package arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

//아두이노 시리얼통신을 위한 객체
public class SerialArduinoLEDControl {
	OutputStream out;
		public void connect(String proName) {
				
				try {
					//COM포트가 실제 존재하고 사용가능한 상태인지 확인
					CommPortIdentifier comPortIdentifier = 
							CommPortIdentifier.getPortIdentifier(proName);
					//포트가 사용 중인지 체크
					if(comPortIdentifier.isCurrentlyOwned()) {
						System.out.println("포트를 사용할 수 없습니다");
					}else {
						System.out.println("포트 사용 가능");
						CommPort commPort = 
								comPortIdentifier.open("basic_serial", 3000);
						System.out.println(commPort);
						if(commPort instanceof SerialPort) {
							SerialPort serialPort = (SerialPort)commPort;
							serialPort.setSerialPortParams(
											9600, //Serial port통신속도
											SerialPort.DATABITS_8, //데이터길이, 한 번에 8bit씩 데이터가 전송
											SerialPort.STOPBITS_1, //stop bit(끌을 표시)
											SerialPort.PARITY_NONE); 
							//시리얼포트와 통신 할 수 있도록 input/output스트림객체 구하기
							InputStream in = serialPort.getInputStream();
							out = serialPort.getOutputStream();
							
					}
				}
				} catch (NoSuchPortException e) {
					e.printStackTrace();
				} catch (PortInUseException e) {
					e.printStackTrace();
				} catch (UnsupportedCommOperationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		public OutputStream getOutPut() {
			return out;

		}
}

