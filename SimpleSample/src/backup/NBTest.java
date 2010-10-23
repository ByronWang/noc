package backup;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author Administrator
 * @version
 */
public class NBTest {

	/** Creates new NBTest */
	public NBTest() {
	}
	
	private static void acceptConnections(int port) throws Exception {
	       // 打开一个Selector
	       Selector acceptSelector =
	           SelectorProvider.provider().openSelector();
	 
	       // 创建一个ServerSocketChannel，这是一个SelectableChannel的子类
	       ServerSocketChannel ssc = ServerSocketChannel.open();
	       // 将其设为non-blocking状态，这样才能进行异步IO操作
	       ssc.configureBlocking(false);
	 
	       // 给ServerSocketChannel对应的socket绑定IP和端口
	       InetAddress lh = InetAddress.getLocalHost();
	       InetSocketAddress isa = new InetSocketAddress(lh, port);
	       ssc.socket().bind(isa);
	 
	       // 将ServerSocketChannel注册到Selector上，返回对应的SelectionKey
	       SelectionKey acceptKey =
	           ssc.register(acceptSelector, SelectionKey.OP_ACCEPT);
	 
	       int keysAdded = 0;
	 
	       // 用select()函数来监控注册在Selector上的SelectableChannel
	       // 返回值代表了有多少channel可以进行IO操作 (ready for IO)
	       while ((keysAdded = acceptSelector.select()) > 0) {
	           // selectedKeys()返回一个SelectionKey的集合，
	           // 其中每个SelectionKey代表了一个可以进行IO操作的channel。
	           // 一个ServerSocketChannel可以进行IO操作意味着有新的TCP连接连入了
	           Set readyKeys = acceptSelector.selectedKeys();
	           Iterator i = readyKeys.iterator();
	 
	           while (i.hasNext()) {
	              SelectionKey sk = (SelectionKey) i.next();
	              // 需要将处理过的key从selectedKeys这个集合中删除
	              i.remove();
	              // 从SelectionKey得到对应的channel
	              ServerSocketChannel nextReady =
	                  (ServerSocketChannel) sk.channel();
	              // 接受新的TCP连接
	              Socket s = nextReady.accept().socket();
	              // 把当前的时间写到这个新的TCP连接中
	              PrintWriter out =
	                  new PrintWriter(s.getOutputStream(), true);
	              Date now = new Date();
	              out.println(now);
	              // 关闭连接
	              out.close();
	           }
	       }
	    }
	

	public void startServer() throws Exception {
		int channels = 0;
		int nKeys = 0;
		int currentSelector = 0;

		// 使用Selector
		Selector selector = Selector.open();

		// 建立Channel 并绑定到9000端口
		ServerSocketChannel ssc = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 9000);
		ssc.socket().bind(address);

		// 使设定non-blocking的方式。
		ssc.configureBlocking(false);

		// 向Selector注册Channel及我们有兴趣的事件
		SelectionKey s = ssc.register(selector, SelectionKey.OP_ACCEPT);
		printKeyInfo(s);

		while (true) // 不断的轮询
		{
			debug("NBTest: Starting select");

			// Selector通过select方法通知我们我们感兴趣的事件发生了。
			nKeys = selector.select();
			// 如果有我们注册的事情发生了，它的传回值就会大于0
			if (nKeys > 0) {
				debug("NBTest: Number of keys after select operation: " + nKeys);

				// Selector传回一组SelectionKeys
				// 我们从这些key中的channel()方法中取得我们刚刚注册的channel。
				Set selectedKeys = selector.selectedKeys();
				Iterator i = selectedKeys.iterator();
				while (i.hasNext()) {
					s = (SelectionKey) i.next();
					printKeyInfo(s);
					debug("NBTest: Nr Keys in selector: " + selector.keys().size());

					// 一个key被处理完成后，就都被从就绪关键字（ready keys）列表中除去
					i.remove();
					if (s.isAcceptable()) {
						// 从channel()中取得我们刚刚注册的channel。
						Socket socket = ((ServerSocketChannel) s.channel()).accept().socket();
						SocketChannel sc = socket.getChannel();

						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
						System.out.println(++channels);
					} else {
						debug("NBTest: Channel not acceptable");
					}
				}
			} else {
				debug("NBTest: Select finished without any keys.");
			}

		}

	}

	private static void debug(String s) {
		System.out.println(s);
	}

	private static void printKeyInfo(SelectionKey sk) {
		String s = new String();

		s = "Att: " + (sk.attachment() == null ? "no" : "yes");
		s += ", Read: " + sk.isReadable();
		s += ", Acpt: " + sk.isAcceptable();
		s += ", Cnct: " + sk.isConnectable();
		s += ", Wrt: " + sk.isWritable();
		s += ", Valid: " + sk.isValid();
		s += ", Ops: " + sk.interestOps();
		debug(s);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		NBTest nbTest = new NBTest();
		try {
			NBTest.acceptConnections(31000);
			//nbTest.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
