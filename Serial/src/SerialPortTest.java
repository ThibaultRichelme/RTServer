

import com.fazecast.jSerialComm.*;
import java.util.*;
import java.io.*;


public abstract class SerialPortTest implements SerialPortDataListener 
{
	
	String header180x160 = "ffd8ffdb008400140e0f120f0d14121012171514181e32211e1c1c1e3d2c2e243249404c4b47404645505a736250556d5645466488656d777b8182814e608d978c7d96737e817c011517171e1a1e3b21213b7c5346537c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7c7cffc0001108007800a003012100021101031101ffdd0004000affc401a20000010501010101010100000000000000000102030405060708090a0b100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9fa0100030101010101010101010000000000000102030405060708090a0b1100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a7374757677";

	
	private static final class PacketListener implements SerialPortPacketListener 
	{
		@Override
		public int getListeningEvents() 
		{
			
			return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
		
		}

		@Override
		public void serialEvent(SerialPortEvent event) 
		{
			byte[] newData = event.getReceivedData();
			System.out.println("Received data of size: " + newData.length);
			for (int i = 0; i < newData.length; ++i)
				System.out.print((char) newData[i]);
			System.out.println("\n");
		}

		@Override
		public int getPacketSize() 
		{
			return 5058;
		}	
		
		
	}

	public static SerialPort userPort;

	public static void main(String args[]) throws IOException, InterruptedException 
	{

		Scanner input = new Scanner(System.in);

		SerialPort ports[] = SerialPort.getCommPorts();
		
		int i = 1;

		System.out.println("\nAvailable Ports:\n");

		for (int i1 = 0; i1 < ports.length; ++i1)
			System.out.println("   [" + i1 + "] " + ports[i1].getSystemPortName() + ": " + ports[i1].getDescriptivePortName());
					

		SerialPort userPort;
		System.out.print("\nChoose your desired serial port or enter -1 to specify a port directly: ");

		int serialPortChoice = (new Scanner(System.in)).nextInt();
		userPort = ports[serialPortChoice];
		
		System.out.println(userPort);
		
		userPort.openPort();

		// Initializing port with timeouts.
		if (userPort.isOpen()) 
		{
			
			System.out.println("Port initialized!");
			userPort.setBaudRate(115200);
		
		} 
		else
		{
			
			System.out.println("Port not available");
		
		}

		PacketListener listener = new PacketListener();
		userPort.addDataListener(listener);

		while (true) 
		{
			try 
			{
				
				String serialPortDescriptor = "";
				serialPortDescriptor = (new Scanner(System.in)).next();
				userPort.writeBytes(serialPortDescriptor.getBytes(), serialPortDescriptor.getBytes().length);

			} 
			catch (Exception e) 
			{
				
				
			}			
		}
	}
}
