package com.magesty.Server.service.implementation;

import com.magesty.Server.enumeration.Status;
import com.magesty.Server.model.Server;
import com.magesty.Server.repository.ServerRepository;
import com.magesty.Server.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.magesty.Server.enumeration.Status.*;
import static com.magesty.Server.enumeration.Status.SERVER_DOWN;
import static org.springframework.data.domain.PageRequest.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;

    /**
     * @param server
     * Create a New Server and set his Image by default
     * @return
     */
    @Override
    public Server create(Server server) {
        log.info("Saving new server : {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    /**
     * @param ipAddress
     * gets Inet of the ipAddress and checks if it's reachable and set the server state according to that
     * @return
     */
    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP {} ...", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        server.setStatus(inetAddress.isReachable(10000) ? SERVER_UP: SERVER_DOWN);
        serverRepository.save(server);

        return server;
    }

    /**
     * @param limit
     * Return list of servers with a pagination
     * @return
     */
    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(of(0, limit)).toList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Server get(Long id) {
        log.info("Fetching server by ID {}", id);
        return serverRepository.findById(id).get();
    }

    /**
     * @param server
     * Update a server or create a new if not exist
     * @return
     */
    @Override
    public Server update(Server server) {
        log.info("Updating a server : {}", server.getName());
        return serverRepository.save(server);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID {} ", id);
        serverRepository.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imageNames = { "server1.png", "server2.png", "server3.png", "server4.png", "server5.png", "server6.png" };
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image" + imageNames[new Random().nextInt(6)]).toUriString();
    }
}
