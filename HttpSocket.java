import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket", configurator = MyServerEndpointConfig.class)
public class HttpSocket {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void OnOpen(Session session, EndpointConfig config) {
		clients.add(session);

		Map<String, List<String>> httpHeader = (Map<String, List<String>>) session.getUserProperties().get("MyHttpHeaders");

		/*
		 * do somethine about HttpHeader
		 */
		System.out.println(httpHeader.get("Origin"));
	}

	@OnClose
	public void OnClose(Session session) {
		clients.remove(session);
	}

	@OnMessage
	public void OnMessage(String message, Session session) throws IOException {
		synchronized (clients) {
			for (Session client : clients)
				client.getBasicRemote().sendText(message);
		}
	}

	@OnError
	public void OnError(Throwable t) {
		// handle error here
	}
}
