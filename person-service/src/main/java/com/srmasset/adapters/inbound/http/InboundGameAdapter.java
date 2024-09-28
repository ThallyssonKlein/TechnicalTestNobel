package com.srmasset.adapters.inbound.http;

import com.srmasset.adapters.outbound.database.OutboundGameAdapter;
import com.srmasset.adapters.outbound.database.OutboundMovementAdapter;
import com.srmasset.adapters.outbound.database.exception.GameClosedException;
import com.srmasset.domain.game.GameService;
import com.srmasset.ports.outbound.database.game.dao.StatusDAO;
import com.srmasset.ports.outbound.database.movement.dao.MovementValueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InboundGameAdapter {
    @Autowired
    private GameService gameService;

    @Autowired
    private OutboundMovementAdapter outboundMovementAdapter;

    @Autowired
    private OutboundGameAdapter outboundGameAdapter;

    public String nextServerMove(Long gameId) {
        StatusDAO status = this.outboundGameAdapter.getStatusByGameId(gameId);

        if (status != StatusDAO.OPENED) {
            throw new GameClosedException();
        }

        String serverMovement = this.gameService.generateServerMovement();
        String salt = this.gameService.generateSalt();
        String hash = this.gameService.generateHash(serverMovement, salt);

        this.outboundMovementAdapter.saveMovement(MovementValueDAO.valueOf(serverMovement), salt, hash, gameId);

        return hash;
    }
}
