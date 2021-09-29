import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

// Get end point config from http header
public class MyServerEndpointConfig extends ServerEndpointConfig.Configurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		config.getUserProperties().put("MyHttpHeaders", request.getHeaders());
	}
}
