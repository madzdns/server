package server.backend.kafka;

import java.io.IOException;
import java.util.Properties;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedZookeeper {
	
	private Logger log = LoggerFactory.getLogger(EmbedZookeeper.class);

	ZooKeeperServerMain zooKeeperServer;
	
	public EmbedZookeeper(Properties zkProperties) throws IOException {
		
		QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
		
		try {
			
		    quorumConfiguration.parseProperties(zkProperties);
		    
		} catch(Exception e) {
			
		    throw new RuntimeException(e);
		}
 
		zooKeeperServer = new ZooKeeperServerMain();
		
		final ServerConfig configuration = new ServerConfig();
		
		configuration.readFrom(quorumConfiguration);

		new Thread() {
			
		    public void run() {
		    	
		        try {
		        	
		            zooKeeperServer.runFromConfig(configuration);
		            
		        } catch (IOException e) {
		           
		        	log.error("",e);
		        }
		    }
		}.start();
	}
}
