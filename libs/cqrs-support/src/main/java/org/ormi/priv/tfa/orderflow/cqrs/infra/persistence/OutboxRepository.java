package org.ormi.priv.tfa.orderflow.cqrs.infra.persistence;

import java.util.List;

import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.OutboxEntity;

/**
 * Contrat pour l'accès à la boîte de sortie des événements.
 * Permet de publier, récupérer et marquer les événements comme échoués.
 */

public interface OutboxRepository {
    void publish(OutboxEntity entity);
    List<OutboxEntity> fetchReadyByAggregateTypeOrderByAggregateVersion(String aggregateType, int limit, int maxRetries);
    void delete(OutboxEntity entity);
    void markFailed(OutboxEntity entity, String err);
    void markFailed(OutboxEntity entity, String err, int retryAfter);
}
